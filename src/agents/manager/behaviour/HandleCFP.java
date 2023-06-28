package agents.manager.behaviour;

import Helper.ListSerializer;
import domain.Book;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.ContractNetResponder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HandleCFP extends CyclicBehaviour {
    
    Map<String, Book> books;
    public HandleCFP(Agent agent, Map<String, Book> books){
        this.books = books;
    }
    
    @Override
    public void action() {
        MessageTemplate template = MessageTemplate.MatchPerformative(ACLMessage.CFP);
        ACLMessage message = myAgent.receive(template);
        
        if (message != null){
            String content = message.getContent();
            String[] contentParts = content.split(";");
            List<Book> clientBooks = ListSerializer.deserializeList(contentParts[0].split(":")[1]);
            if( clientBooks == null){
                System.err.println("Manager Agent couldn't deserialize the Client's request");
                return;
            }
            double requiredPrice = Double.parseDouble(contentParts[1].split(":")[1]);
            String requiredDate = contentParts[2].split(":")[1];
            
            ACLMessage reply = message.createReply();
            List<Book> availableBooks = checkAvailability(clientBooks);
            double totalPrice = calculateTotalPrice(availableBooks);
            String estimateDeliveryDate = estimateDeliveryDate(availableBooks);
            
            if(!availableBooks.isEmpty() && totalPrice <= requiredPrice && estimateDeliveryDate.compareTo(requiredDate) <=0){
                reply.setPerformative(ACLMessage.PROPOSE);
                reply.setContent("price:" + totalPrice + ",deliveryDate:" + estimateDeliveryDate);
            } else {
                reply.setPerformative(ACLMessage.REFUSE);
                reply.setContent("book-unavailable");
            }
            
            myAgent.send(reply);
        } else {
            block();
        }
    }
    
    public List<Book> checkAvailability(List<Book> clientBooks){
        List<Book> availableBooks = new ArrayList<>();
        for(Book clientBook : clientBooks){
            Book book = books.get(clientBook.getTitle());
            if(book != null && book.isAvailable()){
                availableBooks.add(book);
            }
        }
        return availableBooks;
    }
    
    public double calculateTotalPrice(List<Book> availableBooks){
        double totalPrice = 0.0;
        for(Book book: availableBooks){
            totalPrice += book.getPrice();
        }
        return totalPrice;
    }
    
    public String estimateDeliveryDate(List<Book> availableBooks){
        String latestDeliveryDate = null;
        for(Book book: availableBooks){
            String deliverDate = book.getDeliveryDate();
            if(latestDeliveryDate == null || deliverDate.compareTo(latestDeliveryDate) > 0){
                latestDeliveryDate = deliverDate;
            }
        }
        return latestDeliveryDate;
    }
}
