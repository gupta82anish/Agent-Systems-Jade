package domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.io.Serializable;

@JsonRootName("book")
public class Book implements Serializable {
    
    @JsonProperty("title")
    private String title;
    @JsonProperty("author")
    private String author;
    @JsonProperty("genre")
    private BookGenre genre;
//    @JsonProperty("price")
    private double price;
//    @JsonProperty("availability")
    private boolean availability;
//    @JsonProperty("deliveryDate")
    private String deliveryDate;
    
    
    public Book(){
    
    }
    public Book(String title, String author, BookGenre genre, double price, boolean availability, String deliveryDate) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.price = price;
        this.availability = availability;
        this.deliveryDate = deliveryDate;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public void setAuthor(String author) {
        this.author = author;
    }
    
    public BookGenre getGenre() {
        return genre;
    }
    
    public void setGenre(BookGenre genre) {
        this.genre = genre;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    public boolean isAvailable() {
        return availability;
    }
    
    public void setAvailability(boolean availability) {
        this.availability = availability;
    }
    
    public String getDeliveryDate() {
        return deliveryDate;
    }
    
    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
    
    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", genre=" + genre +
                ", price=" + price +
                ", availability=" + availability +
                ", deliveryDate='" + deliveryDate + '\'' +
                '}';
    }
}
