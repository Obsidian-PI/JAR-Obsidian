import domain.services.SlackNotifier;

public class MainSlack {
    public static void main(String[] args) {

        SlackNotifier.sendNotification("Testando");
    }
}
