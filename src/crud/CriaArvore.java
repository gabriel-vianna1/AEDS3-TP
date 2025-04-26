package crud;

import java.lang.reflect.*;
import java.io.*;
import produtos.*;

public class CriaArvore {

    public static void Criar() {
        try {
            String nomeArquivoDados = "games.db";
            String nomeArquivoArvore = "arvore.b";

            // Criação do índice
            Constructor<ParIntInt> construtor = ParIntInt.class.getConstructor();
            ArvoreBMais<ParIntInt> indice = new ArvoreBMais<>(construtor, 4, nomeArquivoArvore);

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
                    int endereco = (int) posRegistro; // Conversão para int se precisar

                    // Cria o par ID-endereço e insere no índice
                    ParIntInt par = new ParIntInt(id, endereco);
                    indice.create(par);
                }
            }

            raf.close();
            System.out.println("Árvore B criada com sucesso!");

        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
    }
}
