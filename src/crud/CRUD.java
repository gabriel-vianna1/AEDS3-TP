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

  public boolean update(Jogo novoJogo){

    try(RandomAccessFile raf = new RandomAccessFile("games.db", "rw")){
    //Vai para o primeiro registro após o cabeçalho
    raf.seek(4);
    while(raf.getFilePointer() < raf.length()){
    
        long pos = raf.getFilePointer();

        if(raf.readByte() != '*'){
            int tamRegistro = raf.readInt();
    
            byte[] ba  = new byte[tamRegistro];
            //Uso o método readFully porque eu sei o tamanho do que vai ser lido
            raf.readFully(ba);
    
            Jogo jogo = new Jogo();
            jogo.fromByteArray(ba);

            if(jogo.getId() == novoJogo.getId()){
                byte[] novoObjeto = jogo.toByteArray();
                if(novoObjeto.length <=  tamRegistro){
                    //Aponta para o início do registro porém pula o byte que tem a indicação de lápide e pula também os 4 bytes que indicam o tamanho
                    raf.seek(pos + 5);
                    raf.write(novoObjeto);
                    }
                else{
                 raf.seek(pos);
                 //Como há mudança no tamanho para mais, faz a "exclusão" do registro para escreve-lo no final do arquivo
                 raf.writeByte('*');    
                 raf.seek(raf.length());
                 //Escreve a indicação de que ele está váilido, seu tamanho e dps o registro
                 raf.writeByte(' ');
                 raf.writeInt(novoObjeto.length);
                 raf.write(novoObjeto);
                 }
               return true;
            }        
         }
      }
    }catch(IOException e){
        e.printStackTrace();
    }
    return false;
  }
}
