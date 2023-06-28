package engine;

import domain.Book;
import domain.BookGenre;
import jade.wrapper.ContainerController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataService {
    
    private static final Random random = new Random();
    
//    String[] bookTitles = {"A Game of Thrones", "Harry Potter and the Sorcerer's Stone", "The Hunger Games",
//            "The Girl with the Dragon Tattoo", "The Da Vinci Code", "The Maze Runner",
//            "Ready Player One", "Ender's Game", "The Silent Patient", "The Alchemist", "Gone Girl",
//            "Pride and Prejudice"};
//
//    String[] authors = {"George R.R. Martin", "J.K. Rowling", "Suzanne Collins",
//            "Stieg Larsson", "Dan Brown", "James Dashner",
//            "Ernest Cline", "Orson Scott Card", "Alex Michaelides",
//            "Paulo Coelho", "Gillian Flynn", "Jane Austen"};
//
    
    public static void begin(BookGenre genre, int numberOfBooks) {
        List<Book> books = new ArrayList<>();
        
        
//        for (int i = 0; i < numberOfBooks; i++) {
//            String title = this.bookTitles[random.nextInt(this.bookTitles.length)];
//            String author = this.authors[random.nextInt(this.authors.length)];
//            double price = 9.99 + random.nextInt(20);
//            boolean availability = random.nextBoolean();
//            String deliveryDate = "2023-" + (random.nextInt(11) + 1) + "-" + (random.nextInt(27) + 1);
//            Book book = new Book(title, author, genre, price, availability, deliveryDate);
//            books.add(book);
//        }
    }
}
