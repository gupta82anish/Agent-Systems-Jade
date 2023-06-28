package agents.manager;

import agents.manager.behaviour.HandleCFP;
import domain.Book;
import jade.core.Agent;

import java.util.Map;

public class ManagerAgent extends Agent {
    private Map<String, Book> books;
    
    public ManagerAgent(Map<String, Book> books){
        this.books = books;
    }
    
    @Override
    protected void setup() {
        addBehaviour(new HandleCFP(this, books));
    }
}
