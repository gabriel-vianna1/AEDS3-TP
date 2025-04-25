package crud;

import java.io.IOException;

public interface RegistroHashExtensivel<T> {

  public int hashCode(); // chave numérica para ser usada no diretório

  public short size(); // tamanho FIXO do registro

  public byte[] toByteArray() throws IOException; // representação do elemento em um vetor de bytes

  public void fromByteArray(byte[] ba) throws IOException; // vetor de bytes a ser usado na construção do elemento

}