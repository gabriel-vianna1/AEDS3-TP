package produtos;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.io.*;
//Implementação da classe Jogo

public class Jogo{

 private int id;
 private String title;
 private LocalDate releaseDate;
 private float rating;
 private String NumberOfReviews;
 private String[] genres;
 private String plays;
 private String country;

 public Jogo() {}

 public Jogo(int id, String title, LocalDate release, float rating, String reviews, String[] genres, String plays, String Country){

 setId(id);
 setTitle(title);
 setReleaseDate(release);
 setRating(rating);
 setReviews(reviews);
 setGenres(genres);
 setPlays(plays);
 setCountry(Country);

 }

 public int getId(){
    return id;
 }

public void setId(int id){
    this.id = id;
 }

 public String getTitle(){
    return title;
 }

 public void setTitle(String title){
    this.title = title;
 }

 public LocalDate getReleaseDate(){
    return releaseDate;
 }
 
 public void setReleaseDate(LocalDate releaseDate){
    this.releaseDate = releaseDate;
 }
 public float getRating(){
    return rating;
 }   
 public void setRating(float rating){
    this.rating = rating;
 }
public String getReviews(){
    return NumberOfReviews;
}
public void setReviews(String reviews){
    this.NumberOfReviews = reviews;
}
public String[] getGenres(){
    return genres;
}
public void setGenres(String[] genres){
    this.genres = genres;
}
public String getPlays(){
    return plays;
}
public void setPlays(String plays){
    this.plays = plays;
}
public String getCountry(){
    return country;
}
public void setCountry(String country){
    this.country = country;
}

/*
 * Esse método pega a representação de data do arquivo (Jun 16, 2016 por exemplo), que não é reconhecida e a transforma para o 
 * padrão da classe usada, permitindo usar a classe LocalDate e facilitando o uso de datas
 */

public static LocalDate FormatDate(String date){
    
    DateTimeFormatter format = DateTimeFormatter.ofPattern("MMM dd, yyyy");
    LocalDate d = LocalDate.parse(date, format);
 
    return d; 
}

public String toString(){
    return "\nID: " + id + 
           "\ntitle: " + title +
           "\nrelease_date: "+  releaseDate +
           "\nrating: "+ rating +
           "\nnumber_of_reviews: " + NumberOfReviews +
           "\ngenres: " + genres +
           "\nplays: " + plays +
           "\ncountry: " + country;
        }

// Esse método vai retornar as informações de um registro como um fluxo de Bytes        

public byte[] toByteArray() throws IOException{
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    /*O DataOutput stream ajuda na escrita do fluxo de bytes, já que a classe ByteArrayOutputStream faz isso somente
     * de forma isolada
     */
    DataOutputStream dos = new DataOutputStream(baos);
    dos.writeInt(id);
    dos.writeUTF(title);
    /*
     * A decisão de salvar a data como String é porque a calsse DataOutputStream só escreve tipos primitivos, dentro das alternativas
     * achamos melhor o uso da String porque facilita a ligibilidade e a transformação entre eles é muito prática usando o LocalDate.
     */
    dos.writeUTF(releaseDate.toString());
    dos.writeFloat(rating);
    dos.writeUTF(NumberOfReviews);
    //Escrever o tamanho do meu array
    dos.writeInt(genres != null ? genres.length : 0);
 
        for(String s : genres){
            dos.writeUTF(s != null ? s : "");
        }

    dos.writeUTF(plays);
    dos.writeUTF(country);
    
    return baos.toByteArray();
}

// Lê as informações de um fluxo de Bytes
public void fromByteArray(byte[] ba) throws IOException{
 ByteArrayInputStream bais = new ByteArrayInputStream(ba);
 DataInputStream dis = new DataInputStream(bais);

 id = dis.readInt();
 title = dis.readUTF();
 // Transforma a String em um LocalDate
 releaseDate = LocalDate.parse(dis.readUTF());
 rating = dis.readFloat();
 NumberOfReviews = dis.readUTF();
 int genresSize = dis.readInt();
 genres = new String[genresSize];
 for (int i = 0; i < genresSize; i++) {
    genres[i] = dis.readUTF();
}
 plays = dis.readUTF();
 country = dis.readUTF();
 }
}