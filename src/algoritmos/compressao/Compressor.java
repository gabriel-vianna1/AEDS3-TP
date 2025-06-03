package algoritmos.compressao;

import java.io.*;
import java.util.HashMap;

public class Compressor {

    public static void Huffman(String caminhoEntrada, String caminhoSaida) throws IOException {
        // 1. Ler o arquivo original
        FileInputStream fis = new FileInputStream(caminhoEntrada);
        byte[] dadosOriginais = fis.readAllBytes();
        fis.close();

        // 2. Gerar códigos Huffman
        HashMap<Byte, String> codigos = Huffman.codifica(dadosOriginais);

        // 3. Codificar
        VetorDeBits bits = new VetorDeBits();
        int i = 0;
        for (byte b : dadosOriginais) {
            String codigo = codigos.get(b);
            for (char c : codigo.toCharArray()) {
                if (c == '1') bits.set(i);
                else bits.clear(i);
                i++;
            }
        }
        byte[] dadosCompactados = bits.toByteArray();

        // 4. Salvar no arquivo
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(caminhoSaida));
        oos.writeObject(codigos);        // Mapa de códigos
        oos.writeInt(i);                 // Bits válidos
        oos.writeInt(dadosCompactados.length); // Tamanho do vetor
        oos.write(dadosCompactados);     // Dados compactados
        oos.close();

        System.out.println("Compressão concluída.");
        System.out.println("Original: " + dadosOriginais.length + " bytes");
        System.out.println("Compactado: " + dadosCompactados.length + " bytes");
    }

    public static void LZW(String caminhoArquivoEntrada, String caminhoArquivoSaida){
      // 1. Ler os bytes do arquivo original
      try{
    FileInputStream entrada = new FileInputStream(caminhoArquivoEntrada);
    byte[] dadosOriginais = entrada.readAllBytes();
    entrada.close();

    // 2. Codificar os dados com LZW
    byte[] dadosCompactados = LZW.codifica(dadosOriginais);

    // 3. Salvar os dados compactados no arquivo de saída
    FileOutputStream saida = new FileOutputStream(caminhoArquivoSaida);
    saida.write(dadosCompactados);
    saida.close();
      
    System.out.println("Compressão concluída.");
    System.out.println("Tamanho original: " + dadosOriginais.length + " bytes");
    System.out.println("Tamanho compactado: " + dadosCompactados.length + " bytes");
    }catch(IOException e){
        e.printStackTrace();
      }catch(Exception e){
        e.printStackTrace();
      }

    }
}
