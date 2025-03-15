package algoritmos;

import java.io.*;
import produtos.Jogo;


import java.io.*;
import java.util.*;

public class OrdenacaoExterna {
    private int numCaminhos;
    private int tamanhoBloco;
    private String arquivoOriginal;
    private String arquivoOrdenado;
    
    public OrdenacaoExterna(String arquivoOriginal, String arquivoOrdenado, int numCaminhos, int tamanhoBloco) {
        this.arquivoOriginal = arquivoOriginal;
        this.arquivoOrdenado = arquivoOrdenado;
        this.numCaminhos = numCaminhos;
        this.tamanhoBloco = tamanhoBloco;
    }
    
    public void ordenarArquivo() throws IOException {
        List<String> arquivosTemporarios = dividirEOrdenarBlocos();
        intercalarArquivos(arquivosTemporarios);
    }
    
    //Divide o arquivo games.bd em blocos menores, ordena esses blocos e salva em um arquivo temporário
    private List<String> dividirEOrdenarBlocos() throws IOException {
        List<String> arquivosTemporarios = new ArrayList<>();
        try (RandomAccessFile arq = new RandomAccessFile(arquivoOriginal, "r")) {
            arq.seek(4); // Ignora o cabeçalho com o último ID
            int numArquivo = 0;
            while (arq.getFilePointer() < arq.length()) {
                List<Jogo> bloco = new ArrayList<>();
                for (int i = 0; i < tamanhoBloco && arq.getFilePointer() < arq.length(); i++) {
                    if (arq.readByte() != ' ') {
                    int tamanho = arq.readInt();
                    byte[] ba = new byte[tamanho];
                    arq.readFully(ba);
                    Jogo jogo = new Jogo();
                    jogo.fromByteArray(ba);
                    bloco.add(jogo);}
                }
                Collections.sort(bloco, Comparator.comparingInt(Jogo::getId));
                String nomeTemp = "temp_" + (numArquivo++) + ".db";
                arquivosTemporarios.add(nomeTemp);
                escreverBlocoOrdenado(nomeTemp, bloco);
            }
        }
        return arquivosTemporarios;
    }
    
    //Cada bloco ordenado é salvo em um arquivo temporário para ser intercalado.
    private void escreverBlocoOrdenado(String nomeArquivo, List<Jogo> bloco) throws IOException {
        try (RandomAccessFile arq = new RandomAccessFile(nomeArquivo, "rw")) {
            for (Jogo jogo : bloco) {
                byte[] ba = jogo.toByteArray();
                arq.writeByte(' ');
                arq.writeInt(ba.length);
                arq.write(ba);
            }
        }
    }
    
    private void intercalarArquivos(List<String> arquivosTemporarios) throws IOException {
        PriorityQueue<ElementoIntercalacao> heap = new PriorityQueue<>(); // Usa uma fila de prioridade para sempre selecionar o menor id de registro.
        Map<String, RandomAccessFile> arquivos = new HashMap<>();
        
        for (String nomeArquivo : arquivosTemporarios) {
            RandomAccessFile arq = new RandomAccessFile(nomeArquivo, "r");
            arquivos.put(nomeArquivo, arq);
            if (arq.length() > 0) {
                leProximoRegistro(arq, nomeArquivo, heap);
            }
        }
        
        try (RandomAccessFile arqFinal = new RandomAccessFile(arquivoOrdenado, "rw")) {
            arqFinal.writeInt(0); // Cabeçalho inicial
            while (!heap.isEmpty()) {
                ElementoIntercalacao elemento = heap.poll();
                Jogo jogo = elemento.jogo;
                byte[] ba = jogo.toByteArray();
                arqFinal.writeByte(' ');
                arqFinal.writeInt(ba.length);
                arqFinal.write(ba);
                leProximoRegistro(arquivos.get(elemento.nomeArquivo), elemento.nomeArquivo, heap);
            }
        }
        
        for (RandomAccessFile arq : arquivos.values()) {
            arq.close();
        }
        
        for (String nomeArquivo : arquivosTemporarios) {
            new File(nomeArquivo).delete();
        }
    }
    
    private void leProximoRegistro(RandomAccessFile arq, String nomeArquivo, PriorityQueue<ElementoIntercalacao> heap) throws IOException {
        if (arq.getFilePointer() < arq.length()) {
            if (arq.readByte() != ' ') return;
            int tamanho = arq.readInt();
            byte[] ba = new byte[tamanho];
            arq.readFully(ba);
            Jogo jogo = new Jogo();
            jogo.fromByteArray(ba);
            heap.add(new ElementoIntercalacao(jogo, nomeArquivo));
        }
    }
    
    private static class ElementoIntercalacao implements Comparable<ElementoIntercalacao> {
        Jogo jogo;
        String nomeArquivo;
        
        public ElementoIntercalacao(Jogo jogo, String nomeArquivo) {
            this.jogo = jogo;
            this.nomeArquivo = nomeArquivo;
        }
        
        @Override
        public int compareTo(ElementoIntercalacao outro) {
            return Integer.compare(this.jogo.getId(), outro.jogo.getId());
        }
    }
}
