import engine.BookService;
import engine.DataService;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

public class Main {
    public static void main(String[] args) {
        final jade.core.Runtime runtime = jade.core.Runtime.instance();
        final Profile profile = new ProfileImpl();
        profile.setParameter(Profile.CONTAINER_NAME, "Main-Container");
        profile.setParameter(Profile.MAIN_HOST, "localhost");
        profile.setParameter(Profile.MAIN_PORT, "1099");
        profile.setParameter(Profile.GUI, "true");
        final ContainerController container = runtime.createMainContainer(profile);
        
        BookService.setDataAndCreateAgents("bookList", container);
        
    }
}