package model.crud;
import java.io.*;
import java.util.*;


import model.Jogo;

public class ImportadorCSV {
    protected String caminho = "games.csv";


    //Método que vai retornar todos os registros da nossa base dados em uma lista 

    public List<Jogo> CriarLista(){
        List<Jogo> listaJogos = new ArrayList<>();

        try{
          
            RandomAccessFile raf = new RandomAccessFile(caminho, "rw");
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
        
        // Tira as aspas que estão armazenadas junto com a data
        atributos[2] = atributos[2].replaceAll("^\"|\"$", "").trim();
        jogo.setReleaseDate(Jogo.FormatDate(atributos[2]));

        jogo.setRating(Float.parseFloat(atributos[3]));
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
    public void criaByteArq(List<Jogo> lista)throws Exception{
        if(lista == null){
            throw new Exception("Lista nula, impossível continuar");}
        // cria um novo arquivo para a escrita de bytes
        try (FileOutputStream fos = new FileOutputStream("games.db");
             DataOutputStream dos = new DataOutputStream(fos)) {
        //Percorre todos os elementos da lista, usa o método para transformar em um array de bytes e escreve esses bytes no arquivo, junto com seu tamanho
        for(Jogo j : lista){         
            byte[] ba;
             ba = j.toByteArray();
             //Escreve o tamanho de cada 
             dos.writeInt(ba.length);
             dos.write(ba);  
        }
        }catch(IOException e){
         e.printStackTrace();   
        }
    }
}
