package agents.seller;

import agents.seller.behaviour.HandleCFP;
import domain.BookGenre;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class SellerAgent extends Agent {
    private AID managerAID;
    private BookGenre genre;
    
    public SellerAgent(AID managerAID, BookGenre genre) {
        this.managerAID = managerAID;
        this.genre = genre;
    }
    
    
    protected void setup() {
        registerAgentInDF();
        
        MessageTemplate template = MessageTemplate.MatchPerformative(ACLMessage.CFP);
        addBehaviour(new HandleCFP(this, template, managerAID));
    }
    
    private void registerAgentInDF(){
        final DFAgentDescription dfAgentDescription = new DFAgentDescription();
        dfAgentDescription.setName(getAID());
        final ServiceDescription serviceDescription = new ServiceDescription();
        serviceDescription.setType(genre.toString());
        serviceDescription.setName(genre.toString());
        dfAgentDescription.addServices(serviceDescription);
        
        try{
            DFService.register(this, dfAgentDescription);
        } catch(final FIPAException e){
            e.printStackTrace();
        }
    }
}
