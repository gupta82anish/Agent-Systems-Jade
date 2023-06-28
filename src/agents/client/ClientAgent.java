package agents.client;

import Helper.ListSerializer;
import agents.client.behaviour.InitiateCFP;
import domain.Book;
import domain.BookGenre;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ClientAgent extends Agent {
    private List<Book> requiredBooks;
    
    private double desiredPrice;
    private String desiredDate;
    private List<AID> sellerAgents;
    
    public ClientAgent(List<Book> requiredBooks, double desiredPrice, String desiredDate) {
        this.requiredBooks = requiredBooks;
        this.desiredPrice = desiredPrice;
        this.desiredDate = desiredDate;
    }
    
    @Override
    protected void setup() {
        sellerAgents = getSellerAgentsFromDF(this, this.requiredBooks.get(0).getGenre());
        
        sellerAgents.forEach(a -> System.out.println(a.getLocalName()));
        
        if(!sellerAgents.isEmpty()){
            sendCFP();
        } else {
            System.out.println("we couldn't find anything that matched your query");
        }
    }
    
    private static List<AID> getSellerAgentsFromDF(final ClientAgent agent, BookGenre genre) {
        final List<AID> agentsDF = new ArrayList<>();
        
        final DFAgentDescription template = new DFAgentDescription();
        final ServiceDescription serviceDescription = new ServiceDescription();
        serviceDescription.setType(genre.toString());
        serviceDescription.setName(genre.toString());
        template.addServices(serviceDescription);
        
        try {
            final DFAgentDescription[] agents = DFService.search(agent, template);
            Arrays.stream(agents).forEach(gatewayAgent -> agentsDF.add(gatewayAgent.getName()));
        } catch (FIPAException e) {
            e.printStackTrace();
            agent.doDelete();
        }
        
        return agentsDF;
    }
    
    
    private void sendCFP() {
        ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
        for (AID sellerAgent : sellerAgents) {
            cfp.addReceiver(sellerAgent);
        }
        cfp.setContent("request-books:" + ListSerializer.serializeList(requiredBooks) + ";desiredPrice:" + desiredPrice + ";desiredDate:" + desiredDate);
        
        cfp.setReplyByDate(new Date(System.currentTimeMillis() + 10000)); // 10 seconds
        // Add the behavior responsible for this CFP
        addBehaviour(new InitiateCFP(this, cfp, desiredPrice));
    }
}
