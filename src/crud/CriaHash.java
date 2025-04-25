package crud;

import java.lang.reflect.*;
import java.io.*;
import produtos.*;



public class CriaHash {

    public static void main(String[] args) {
        try {
            String nomeArquivoDados = "games.db";
            String nomeArquivoDiretorio = "diretorio.hash";
            String nomeArquivoCestos = "cestos.hash";

            // Criação do índice
            Constructor<ParIDEndereco> construtor = ParIDEndereco.class.getConstructor();
            HashExtensivel<ParIDEndereco> indice = new HashExtensivel<>(construtor, 4, nomeArquivoDiretorio, nomeArquivoCestos);

            // Abrir o arquivo de dados
            RandomAccessFile raf = new RandomAccessFile(nomeArquivoDados, "r");

            raf.seek(4); // Pula o cabeçalho (último ID usado)

            while (raf.getFilePointer() < raf.length()) {
                long posRegistro = raf.getFilePointer();

                byte lapide = raf.readByte();
                int tamanho = raf.readInt();
                byte[] ba = new byte[tamanho];
                raf.readFully(ba);

                if (lapide != '*') {
                    // Reconstrói o objeto jogo
                    Jogo jogo = new Jogo(); 
                    jogo.fromByteArray(ba);

                    int id = jogo.getId();

                    // Cria o par ID-endereço e insere no índice
                    ParIDEndereco par = new ParIDEndereco(id, posRegistro);
                    indice.create(par);
                }
            }

            raf.close();
            System.out.println("Índice criado com sucesso!");

        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
    }
}
