package agents.client.behaviour;

import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class RequestBookstoreList extends OneShotBehaviour {
    @Override
    public void action() {
        ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
//        request.addReceiver(new AID());
    }
}
