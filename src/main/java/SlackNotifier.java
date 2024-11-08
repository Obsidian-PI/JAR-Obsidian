import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SlackNotifier {
    public static void sendNotification(String message) {
        try {
            String webhookUrl = "https://hooks.slack.com/services/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";
            URL url = new URL(webhookUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            String payload = "{\"text\":\"" + message + "\"}";

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = payload.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("Mensagem enviada com sucesso.");
            } else {
                System.out.println("Erro ao enviar a mensagem. Código de resposta: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
