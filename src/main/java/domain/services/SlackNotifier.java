package domain.services;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class SlackNotifier {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/obsidian";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    public static void sendNotification(String message) {
        try {
            String webhookUrl = "";
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
                saveMessageToDatabase(message);

            } else {
                System.out.println("Erro ao enviar a mensagem. Código de resposta: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void saveMessageToDatabase(String message) {
        System.out.println("Mensagem recebida para salvar: " + message);
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "INSERT INTO alerta (descricao) VALUES (?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, message);
                stmt.executeUpdate();
                System.out.println("Mensagem salva no banco de dados.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao salvar mensagem no banco de dados:");
            e.printStackTrace();
        }
    }

    public static void testDatabaseConnection() {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            System.out.println("Conexão com o banco de dados bem-sucedida.");
        } catch (Exception e) {
            System.out.println("Erro ao conectar ao banco de dados:");
            e.printStackTrace();
        }
    }

}