package crud;


import java.io.*;
import produtos.*;

public class CRUD {
    
    public static void create(Jogo jogo){

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

  public static Jogo read(int id){

    try(RandomAccessFile raf = new RandomAccessFile("games.db", "r")){
   
    raf.seek(4); // Move o ponteiro para o byte logo após o cabeçalho
    //Vai fazer a leitura do arquivo até ele acabar
    while(raf.getFilePointer() < raf.length()){
       // Salva a posição antes de ler o status

        byte status = raf.readByte();
        int tamRegistro = raf.readInt(); 
        // Lê o status do registro
        if (status != '*') { // Se o registro não estiver excluído
            // Volta para a posição correta e pula só o status

        byte[] ba  = new byte[tamRegistro];
        //Uso do método readFully porque eu sei o tamanho do que vai ser lido
        raf.readFully(ba);

        Jogo jogo = new Jogo();
        jogo.fromByteArray(ba);

        if(jogo.getId() == id){
            return jogo;
        }
      }else {
        // Se o registro estiver excluído, pula todos os bytes que representam esse registro
        raf.skipBytes(tamRegistro);
    }
}
 }catch(IOException e){
        e.printStackTrace();
    }

    return null;
  }

  public static boolean update(Jogo novoJogo){

    try(RandomAccessFile raf = new RandomAccessFile("games.db", "rw")){

    //Vai para o primeiro registro após o cabeçalho
    raf.seek(4);
    while(raf.getFilePointer() < raf.length()){
    
        long pos = raf.getFilePointer();
        byte status = raf.readByte();
        int tamRegistro = raf.readInt(); 

        if(status != '*'){
        
            byte[] ba  = new byte[tamRegistro];
            //Uso o método readFully porque eu sei o tamanho do que vai ser lido
            raf.readFully(ba);
    
            Jogo jogo = new Jogo();
            jogo.fromByteArray(ba);

            if(jogo.getId() == novoJogo.getId()){
                byte[] novoObjeto = novoJogo.toByteArray();
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
         } else {
            
            raf.skipBytes(tamRegistro);
        }
      }
    }catch(IOException e){
        e.printStackTrace();
    }
    return false;

  }

  public static boolean delete(int id){

    try(RandomAccessFile raf = new RandomAccessFile("games.db", "rw")){
        raf.seek(4);//Pula o cabeçalho para a posição correspondente ao primeiro registro
        while(raf.getFilePointer() < raf.length()){
            long pos = raf.getFilePointer();
            byte status = raf.readByte();

            if(status != '*'){
                raf.seek(pos + 1);
                int tamRegistro = raf.readInt();

                byte[] ba  = new byte[tamRegistro];
                //Uso o método readFully porque eu sei o tamanho do que vai ser lido
                raf.readFully(ba);
                Jogo jogo = new Jogo();
                jogo.fromByteArray(ba);

                //Quando achar o registro com o id indicado, vai marcar como excluído
                if(jogo.getId() == id){
                    raf.seek(pos);
                    raf.writeByte('*');
                    return true;
                }
            }
        }

    }catch(IOException e){
        e.printStackTrace();
    }
   return false;
  } 

  public static void list(){

    try(RandomAccessFile raf = new RandomAccessFile("games.db", "r")){
     
        int numRegistros = raf.readInt();

    System.out.println("Número de jogos na lista: " + numRegistros);
    
   
    while(raf.getFilePointer() < raf.length()){
        byte status = raf.readByte();

        int tam = raf.readInt();
    
        if(status != '*'){
            Jogo jogo = new Jogo();
         
            byte[] ba =  new byte[tam];

            raf.readFully(ba);
            jogo.fromByteArray(ba);

            System.out.println(jogo.toString());
            }else{
                raf.skipBytes(tam);
            }
          }

        }catch (IOException e) {
    e.printStackTrace();
        }
     }

}