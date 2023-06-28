package agents.client.behaviour;

import jade.core.Agent;
import jade.core.ContainerID;
import jade.lang.acl.ACLMessage;
import jade.proto.ContractNetInitiator;

import java.util.Vector;

public class InitiateCFP extends ContractNetInitiator {
    
    double price;
    double idealPrice;
    String desiredDate;
    public InitiateCFP(Agent agent, ACLMessage cfp, double idealPrice) {
        super(agent, cfp);
        this.idealPrice = idealPrice;
    }
    
    @Override
    protected void handleInform(ACLMessage inform) {
        String name = inform.getSender().getLocalName();
        String agentID = name.substring("SellerAgent".length());
        System.out.println("Visiting bookstore " + agentID);
        
        ContainerID newContainer = new ContainerID();
        newContainer.setName("BookStore" + agentID);
        newContainer.setAddress("localhost");
        newContainer.setPort("1099");
        myAgent.doMove(newContainer);
        System.out.println(inform.getContent() + " with " + name);
    }
    
    
    @Override
    protected void handleAllResponses(Vector responses, Vector acceptances) {
        ACLMessage bestProposal = getBestResponse(responses);
        if(bestProposal != null){
            ACLMessage accept = bestProposal.createReply();
            accept.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
            accept.setContent("amount-paid:" + price);
            acceptances.addElement(accept);
        } else {
            System.out.println("No Proposal Found for these books");
        }
    }
    
    private ACLMessage getBestResponse(Vector responses){
        ACLMessage bestResponse = null;
        double bestPrice = Double.MAX_VALUE;
        String bestDeliveryDate = "";
        
        for (Object response : responses) {
            ACLMessage proposal = (ACLMessage) response;
            // Iterate only through proposals
            if (proposal.getPerformative() == ACLMessage.PROPOSE) {
                String[] parts = proposal.getContent().split(",");
                double price = Double.parseDouble(parts[0].split(":")[1]);
                String deliveryDate = parts[1].split(":")[1];
                
                if (price <= this.idealPrice && (bestResponse == null || price < bestPrice || (price == bestPrice && deliveryDate.compareTo(bestDeliveryDate) < 0))) {
                    bestResponse = proposal;
                    bestPrice = price;
                    bestDeliveryDate = deliveryDate;
                }
            }
        }
        price = bestPrice;
        return bestResponse;
    }
}
