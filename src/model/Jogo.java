package model;

import java.util.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

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

public LocalDate FormatDate(String date){
    
    DateTimeFormatter format = DateTimeFormatter.ofPattern("MMM dd, yyyy");
    LocalDate d = LocalDate.parse(date, format);
 
    return d; 
}


}