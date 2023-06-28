package agents.seller.behaviour;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.ContractNetInitiator;
import jade.proto.ContractNetResponder;

public class HandleCFP extends ContractNetResponder {
    
    private AID managerAID;
    public HandleCFP(Agent agent, MessageTemplate template, AID managerAID){
        super(agent, template);
        this.managerAID = managerAID;
    }
    
    @Override
    protected ACLMessage handleCfp(ACLMessage cfp) throws RefuseException, FailureException, NotUnderstoodException {
        ACLMessage request = new ACLMessage(ACLMessage.CFP);
        request.addReceiver(managerAID);
        request.setContent(cfp.getContent());
        myAgent.send(request);
        
        ACLMessage managerReply = myAgent.blockingReceive();
        ACLMessage reply = cfp.createReply();
        
        if(managerReply.getPerformative() == ACLMessage.PROPOSE){
            reply.setPerformative(ACLMessage.PROPOSE);
            reply.setContent(managerReply.getContent());
        } else {
            reply.setPerformative(ACLMessage.REFUSE);
            reply.setContent("Book not available");
        }
        return reply;
    }
    
    @Override
    protected ACLMessage handleAcceptProposal(ACLMessage cfp, ACLMessage propose, ACLMessage accept) throws FailureException {
        ACLMessage inform =accept.createReply();
        inform.setPerformative(ACLMessage.INFORM);
        inform.setContent("Transaction has been completed");
        myAgent.send(inform);
        return null;
    }
    
    @Override
    protected void handleRejectProposal(ACLMessage cfp, ACLMessage propose, ACLMessage reject) {
    
    }
}
