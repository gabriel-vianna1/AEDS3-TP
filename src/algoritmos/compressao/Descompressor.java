package algoritmos.compressao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;

public class Descompressor {
    
@SuppressWarnings("unchecked")
public static void Huffman(String caminhoCompactado, String caminhoSaida) throws IOException, ClassNotFoundException {
    Instant inicio = Instant.now();

    ObjectInputStream ois = new ObjectInputStream(new FileInputStream(caminhoCompactado));
    HashMap<Byte, String> codigos = (HashMap<Byte, String>) ois.readObject();
    int numBitsValidos = ois.readInt();
    int tamanhoBytes = ois.readInt();
    byte[] dadosCompactados = new byte[tamanhoBytes];
    ois.readFully(dadosCompactados);
    ois.close();

    // Converte o vetor de bits para uma String binária (ex: "01001010...")
    VetorDeBits bits = new VetorDeBits(dadosCompactados);
    StringBuilder sequenciaBinaria = new StringBuilder();
    for (int i = 0; i < numBitsValidos; i++) {
        sequenciaBinaria.append(bits.get(i) ? '1' : '0');
    }

    // Usa seu método decodifica
    byte[] dadosDecodificados = Huffman.decodifica(sequenciaBinaria.toString(), codigos);

    FileOutputStream fos = new FileOutputStream(caminhoSaida);
    fos.write(dadosDecodificados);
    fos.close();

    Instant fim = Instant.now();
    Duration dur = Duration.between(inicio, fim);

    System.out.println("Descompressão concluída.");
    System.out.println("Tempo necessário (segundos): " + dur.toSeconds());
    System.out.println("Arquivo final tem " + dadosDecodificados.length + " bytes.");
}

public static void LZW(String caminhoCompactado, String caminhoSaida) {
    Instant inicio = Instant.now();

    try {
        // 1. Lê o arquivo compactado como array de bytes
        FileInputStream fis = new FileInputStream(caminhoCompactado);
        byte[] dadosCompactados = fis.readAllBytes();
        fis.close();

        // 2. Usa seu método de decodificação
        byte[] dadosDecodificados = LZW.decodifica(dadosCompactados);

        // 3. Escreve os dados decodificados no arquivo de saída
        FileOutputStream fos = new FileOutputStream(caminhoSaida);
        fos.write(dadosDecodificados);
        fos.close();

        Instant fim = Instant.now();
        Duration dur = Duration.between(inicio, fim);

        System.out.println("Descompressão concluída .");
        System.out.println("Tempo necessário(segundos): " + dur.toSeconds());
        System.out.println("Tamanho do arquivo descomprimido: " + dadosDecodificados.length + " bytes");
    } catch (Exception e) {
        e.printStackTrace();
    }
}



}
