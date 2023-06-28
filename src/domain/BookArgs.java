package domain;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import java.io.Serializable;
import java.util.List;

public class BookArgs implements Serializable {

    @JacksonXmlElementWrapper(localName = "books")
    private List<Book> books;
    
    public BookArgs(){
    
    }
    
    public BookArgs(List<Book> books) {
        this.books = books;
    }
    
    public List<Book> getBooks() {
        return books;
    }
    
    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
