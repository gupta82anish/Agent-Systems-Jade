package Helper;

import domain.Book;

import java.io.*;
import java.util.Base64;
import java.util.List;

public class ListSerializer {

    public static String serializeList(List<Book> list){
        
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream out = new ObjectOutputStream(bos)) {
            
            out.writeObject(list);
            out.flush();
            byte[] bytes = bos.toByteArray();
            return Base64.getEncoder().encodeToString(bytes);
            
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static List<Book> deserializeList(String str){
        try {
            byte[] bytes = Base64.getDecoder().decode(str);
            try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
                 ObjectInputStream in = new ObjectInputStream(bis)) {
                
                List<Book> list = (List<Book>)in.readObject();
                return list;
                
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }
    }
}
