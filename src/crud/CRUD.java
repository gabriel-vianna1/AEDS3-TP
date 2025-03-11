package crud;

import java.io.*;
import produtos.*;
import java.util.*;

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

  
}
