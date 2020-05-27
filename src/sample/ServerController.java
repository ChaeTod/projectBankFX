package sample;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ServerController {
    private static String fname;
    private static String lname;
    private static String login;
    private static String token;

    public static String getFname() {
        return fname;
    }

    public static String getLname() {
        return lname;
    }

    public static String getLogin() {
        return login;
    }

    public static String getToken() {
        return token;
    }

    public boolean logIn(String login, String password) throws IOException {
            URL url = new URL("http://localhost:8080/login");  //create a connection to a selected url using POST method
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            int status = connection.getResponseCode();

            Map<String, String> parameters = new HashMap<>();
            parameters.put("login", login);
            parameters.put("password", password);




            String inputParams = "{login:" + parameters.get("login") + ", password:" + parameters.get("password") + "}";

            try (OutputStream outputStream = connection.getOutputStream()) {
                byte[] input = inputParams.getBytes(StandardCharsets.UTF_8);
                //byte[] input = parameters.get("login" + "password").getBytes(StandardCharsets.UTF_8);
                outputStream.write(input, 0, input.length);
            } catch (Exception e) {
                e.printStackTrace();
            }

            status = connection.getResponseCode();
            System.out.println(status);

            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();

                JSONObject obj = new JSONObject(content.toString());
                fname = obj.getString("fname");
                lname = obj.getString("lname");
                this.login = obj.getString("login");
                token = obj.getString("token");

                System.out.println(login);
                return true;

            } catch (Exception e) {
                e.printStackTrace();
            }
        return false;
    }

    public boolean logOut(String login, String password) {
        try {
            URL url = new URL("http://localhost:8080/logout?token=" + token);  //create a connection to a selected url using POST method
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");

            Map<String, String> parameters = new HashMap<>();
            parameters.put("login", login);
            parameters.put("password", password);

            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            String inputs = "login:" + parameters.get("login");

            try (OutputStream outputStream = connection.getOutputStream()) {
                //byte[] input = parameters.get("login" + "password").getBytes(StandardCharsets.UTF_8);
                byte[] input = inputs.getBytes(StandardCharsets.UTF_8);
                outputStream.write(input, 0, input.length);
            } catch (Exception e) {
                e.printStackTrace();
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            System.out.println(content.toString());
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
