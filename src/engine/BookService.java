package engine;

import agents.client.ClientAgent;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import containers.BookStoreContainer;
import domain.Book;
import domain.BookArgs;
import domain.BookGenre;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class BookService {

    public static void setDataAndCreateAgents(final String fileName, final ContainerController container){
        try{
            final File testFile = new File("./././resources/" + fileName + ".xml");
            final XmlMapper mapper = new XmlMapper();
            final BookArgs books = mapper.readValue(testFile, BookArgs.class);
            
            if(Objects.nonNull(books.getBooks())){
//                books.getBooks().forEach(a -> createAgent(a));
                List<Book> bookList = books.getBooks();
                System.out.println(bookList.size());
                List<Book> bookStore1 = createStoreStock(bookList, BookGenre.CLASSIC);
                List<Book> bookStore2 = createStoreStock(bookList, BookGenre.FANTASY);
                List<Book> bookStore3 = createStoreStock(bookList, BookGenre.FICTION);
                List<Book> bookStore4 = createStoreStock(bookList, BookGenre.SCIENCE);
                List<Book> bookStore5 = createStoreStock(bookList, BookGenre.THRILLER);
                
                BookStoreContainer b1 = new BookStoreContainer("BookStore", 1, BookGenre.CLASSIC, bookStore1);
                BookStoreContainer b1c = new BookStoreContainer("BookStore", 2, BookGenre.CLASSIC, createStoreStock(bookList, BookGenre.CLASSIC));
                BookStoreContainer b2 = new BookStoreContainer("BookStore",3,BookGenre.FANTASY, bookStore2);
                BookStoreContainer b2c = new BookStoreContainer("BookStore",4,BookGenre.FANTASY, createStoreStock(bookList, BookGenre.FANTASY));
                BookStoreContainer b3 = new BookStoreContainer("BookStore",5,BookGenre.FICTION, bookStore3);
                BookStoreContainer b3c = new BookStoreContainer("BookStore",6,BookGenre.FICTION, createStoreStock(bookList, BookGenre.FICTION));
                BookStoreContainer b4 = new BookStoreContainer("BookStore", 7, BookGenre.SCIENCE, bookStore4);
                BookStoreContainer b4s = new BookStoreContainer("BookStore", 8, BookGenre.SCIENCE, createStoreStock(bookList, BookGenre.SCIENCE));
                BookStoreContainer b5 = new BookStoreContainer("BookStore",9,BookGenre.THRILLER, bookStore5);
                BookStoreContainer b5t = new BookStoreContainer("BookStore",10,BookGenre.THRILLER, createStoreStock(bookList, BookGenre.THRILLER));
                
                List<Book> clientBooks = initClientBooks(bookList, 2);
                System.out.println(clientBooks.size());
                ClientAgent clientAgent = new ClientAgent(clientBooks, 1000.0, "2024-01-01");
                try{
                    final AgentController agentController = container.acceptNewAgent("ClientAgent", clientAgent);
                    agentController.start();
                } catch (StaleProxyException e){
                    e.printStackTrace();
                }
                
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }
    
    
    public static List<Book> initClientBooks(List<Book> list, int number){
        Collections.shuffle(list);
        return new ArrayList<>(list.subList(0, Math.min(number, list.size())));
    }
    
    
    public static List<Book> createStoreStock(List<Book> bookList, BookGenre genre){
         final Random random = new Random();
         
         List<Book> filteredBooks = bookList.stream()
                 .filter(book -> book.getGenre() == genre)
                 .collect(Collectors.toList());
         
         for(Book book: filteredBooks){
             double price = 9.99 + random.nextInt(20);
             boolean availability = random.nextBoolean();
             String deliveryDate = "2023-" + (random.nextInt(11) + 1) + "-" + (random.nextInt(27) + 1);
             
             book.setAvailability(availability);
             book.setDeliveryDate(deliveryDate);
             book.setPrice(price);
         }
         return filteredBooks;
    }
    
    
    public static void createAgent(final Book b){
        System.out.println(b.getTitle() + " " +b.getDeliveryDate() + " " + b.getGenre());
    }
}
