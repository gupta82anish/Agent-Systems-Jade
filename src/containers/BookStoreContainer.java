package containers;

import agents.manager.ManagerAgent;
import agents.seller.SellerAgent;
import domain.Book;
import domain.BookGenre;
import jade.core.*;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookStoreContainer implements Serializable {
    
    private AgentContainer bookstoreContainer;
    public BookStoreContainer(String containerName, int storeID, BookGenre genre, List<Book> books) {
        Runtime runtime = Runtime.instance();
        Profile profile = new ProfileImpl();
        profile.setParameter(Profile.MAIN_HOST, "localhost");
        profile.setParameter(Profile.MAIN_PORT, "1099");
        profile.setParameter(Profile.CONTAINER_NAME, containerName + storeID);
        bookstoreContainer = runtime.createAgentContainer(profile);
        
        prepareManagerAgent(books, storeID);
        
        
        AID managerAID = new AID("ManagerAgent"+storeID, AID.ISLOCALNAME);
        SellerAgent sellerAgent = new SellerAgent(managerAID, genre);
        addAgentToContainer("SellerAgent"+storeID, sellerAgent);
        
    }
    
    public void prepareManagerAgent(List<Book> books, int storeID){
        Map<String, Book> map =new HashMap<>();
        for(Book book: books){
            map.put(book.getTitle(), book);
        }
        
        // create manageragent here
        ManagerAgent managerAgent = new ManagerAgent(map);
        addAgentToContainer("ManagerAgent" + storeID, managerAgent);
        
    }
    
    public void addAgentToContainer(String agentName, Agent agent) {
        try {
            AgentController agentController = bookstoreContainer.acceptNewAgent(agentName, agent);
            agentController.start();
        } catch (StaleProxyException e) {
            e.printStackTrace();
        }
    }
}
