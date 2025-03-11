package crud;

import java.io.*;
import produtos.*;

public class CRUD {
    
    public void create(Jogo jogo){

    try(RandomAccessFile raf = new RandomAccessFile("games.db", "rw")){
        // move o ponteiro para o inicio do arquivo, onde ele vai ler o id do ultimo registro
        raf.seek(0);
        int lastID = raf.readInt();
        jogo.setId(lastID + 1);
        //voltar para o início para poder atualizar o valor do último id
        raf.seek(0);
        raf.writeInt(jogo.getId());
        // transforma o novo registro em um array de bytes
        byte[] ba = jogo.toByteArray();
        raf.seek(raf.length());
        //Escreve o registro no final do arquivo
        raf.writeByte(' ');
        raf.writeInt(ba.length);
        raf.write(ba);

        System.out.println("Registro adicionado com sucesso!");


    }catch(IOException e){
        e.printStackTrace();
    }
  }

  public Jogo read(int id){

    try(RandomAccessFile raf = new RandomAccessFile("games.db", "r")){
   
    raf.seek(4); // Move o ponteiro para o byte logo após o cabeçalho
    //Vai fazer a leitura do arquivo até ele acabar
    while(raf.getFilePointer() < raf.length()){
      
    if(raf.readByte() != '*'){
        int tamRegistro = raf.readInt();

        byte[] ba  = new byte[tamRegistro];
        //Uso o método readFully porque eu sei o tamanho do que vai ser lido
        raf.readFully(ba);

        Jogo jogo = new Jogo();
        jogo.fromByteArray(ba);

        if(jogo.getId() == id){
            return jogo;
        }
      }
    }
 }catch(IOException e){
        e.printStackTrace();
    }
    return null;
  }



  
}
