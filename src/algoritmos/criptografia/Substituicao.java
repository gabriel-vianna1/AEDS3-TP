package algoritmos.criptografia;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Substituicao {

     public static void criptografarArquivo(String caminhoEntrada, String caminhoSaida) throws IOException {
        try (FileInputStream entrada = new FileInputStream(caminhoEntrada);
             FileOutputStream saida = new FileOutputStream(caminhoSaida)) {

            int b;
            while ((b = entrada.read()) != -1) {
                byte novoByte = cifrarByte((byte) b);
                saida.write(novoByte);
            }
        }

        System.out.println("Arquivo Criptografado com sucesso");
    }

      public static void descriptografarArquivo(String caminhoEntrada, String caminhoSaida) throws IOException {
        try (FileInputStream entrada = new FileInputStream(caminhoEntrada);
             FileOutputStream saida = new FileOutputStream(caminhoSaida)) {

            int b;
            while ((b = entrada.read()) != -1) {
                byte novoByte = cifrarByte((byte) b);
                saida.write(novoByte);
            }
        }
        System.out.println("Arquivo Descriptografado com sucesso");
    }

    private static byte cifrarByte(byte b) {
        char c = (char) (b & 0xFF);
        int deslocamento = 3;

        if (c >= 'A' && c <= 'Z') {
            c = (char) ('A' + (c - 'A' + deslocamento) % 26);
        } else if (c >= 'a' && c <= 'z') {
            c = (char) ('a' + (c - 'a' + deslocamento) % 26);
        }
        return (byte) c;
    }

    
}
