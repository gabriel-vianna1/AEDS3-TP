package crud;

import java.io.*;
import java.util.*;

import produtos.*;



public class ImportadorCSV {
   // protected static String caminho = "games.csv";


    //Método que vai retornar todos os registros da nossa base dados em uma lista 

    public static List<Jogo> CriarLista(){
        List<Jogo> listaJogos = new ArrayList<>();

        try{
          
            RandomAccessFile raf = new RandomAccessFile("games.csv", "r");
           
            //Pula a linha que vai corresponder ao cabeçalho(metadado) do arquivo
            raf.readLine();   
            String linha;
           

            while((linha = raf.readLine()) != null){
                Jogo jogo = ImportadorCSV.processCSV(linha);
                listaJogos.add(jogo);
            }
            
            raf.close();

        }catch(Exception e){
            e.printStackTrace();
        }
        
    
        return listaJogos;
    }
    

    /*
    Esse método vai ser responsável por transformar uma linha do arquivo csv em um objeto
     da nossa classe jogo. Ele vai tratar dos separadores para que o reconhecimento de todos os atributos
    sejam feitas da forma certa
    */
    public static Jogo processCSV(String linha){
        
        //Vai separar os atributos pela vírgula, porém sem considerar as que estão dentro de aspas.
        String[] atributos = linha.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
        Jogo jogo = new Jogo();
        
        try{
        jogo.setId(Integer.parseInt(atributos[0]));
        jogo.setTitle(atributos[1]);


        if (atributos[2] != null) {
            //Tira as aspas que estão armazenadas na data
            atributos[2] = atributos[2].replaceAll("^\"|\"$", "").trim();
            jogo.setReleaseDate(Jogo.FormatDate(atributos[2]));
        } else {
            jogo.setReleaseDate(null);  // Ou algum valor padrão
        }
        
    

        if (!atributos[3].isEmpty()) {
            jogo.setRating(Float.parseFloat(atributos[3]));
        } else {
            jogo.setRating(0.0f); //valor padrão caso esteja vazio
        }
        
        jogo.setReviews(atributos[4]);

        if(atributos[5] != null){
            // Deixa os genêros escritos separados apenas com vírgula para facilitar o acesso
            atributos[5] = atributos[5].replaceAll("[\\[\\]']", "").trim();
            //Separa pela vírgula desconsiderando os espaços
            jogo.setGenres(atributos[5].split("\\s*,\\s*"));
        }

        jogo.setPlays(atributos[6]);
        jogo.setCountry(atributos[7]);
         }catch(NumberFormatException e){
            e.printStackTrace();
         }catch(NullPointerException e){
            e.printStackTrace();
         }
       return jogo;
    }
 /*
  * Esse método vai vai receber a lista de objetos Jogo criada pelo método e
  * criar um novo arquivo que vai conter esses mesmo objetos, porém escritos em formato de fluxo de Bytes.
  */
    public static void criaByteArq(List<Jogo> lista)throws Exception{
        if(lista == null || lista.isEmpty()){
            throw new Exception("Lista nula ou vazia, impossível continuar");}
        // cria um novo arquivo para a escrita de bytes
        try (RandomAccessFile arq = new RandomAccessFile("games.db", "rw")) {
        //Escreve, no ínicio do meu arquivo, o id do último registro q eu tenho
        arq.writeInt(1511);
        //Percorre todos os elementos da lista, usa o método para transformar em um array de bytes e escreve esses bytes no arquivo, junto com seu tamanho
        for(Jogo j : lista){         
            byte[] ba;
             ba = j.toByteArray();
             arq.writeByte(' ');
             //Escreve o tamanho de cada registro
             arq.writeInt(ba.length);
             arq.write(ba);  
        }

        }catch(IOException e){
         e.printStackTrace();   
        }


    }
}
