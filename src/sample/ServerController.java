package sample;

import com.sun.deploy.net.HttpRequest;
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
    private String fname;
    private String lname;
    private String login;
    private String token;

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getLogin() {
        return login;
    }

    public String getToken() {
        return token;
    }

    public String makePostAction(String operationURL, String inputParams) throws IOException {
        try {
            URL url = new URL("http://localhost:8080" + operationURL);  //create a connection to a selected url using POST method
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
/*
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://openjdk.java.net/"))
                    .timeout(Duration.ofMinutes(1))
                    .header("Content-Type", "application/json")
                    .POST(BodyPublishers.ofFile(Paths.get("file.json")))
*/
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            try (OutputStream outputStream = connection.getOutputStream()) {
                byte[] input = inputParams.getBytes(StandardCharsets.UTF_8);
                outputStream.write(input, 0, input.length);
            } catch (Exception e) {
                e.printStackTrace();
            }

            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) { //make write to calleted result tp the variable
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                //in.close();
                return content.toString();

            } catch (Exception e) {
                e.printStackTrace();
            }
            //return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean logIn(String login, String password) throws IOException {
        String response = makePostAction("/login", "{login: " + login + " , password: " + password + "}");
        System.out.println(response);

        if (response.equals("Password and login are mandatory fields!") || response.equals("Wrong login or password!"))
            return false;

        JSONObject obj = new JSONObject(response);
        fname = obj.getString("fname");
        lname = obj.getString("lname");
        this.login = obj.getString("login");
        token = obj.getString("token");

        System.out.println(login + " " + getToken());
        return true;
    }

    public void logOut() throws IOException {
        String response = makePostAction("/logout?token=" + getToken(), "{login: " + getLogin() + "}");
        System.out.println(response);
    }

    public String showLog() throws IOException{
        String response = makePostAction("/log?token=" + getToken(), "{login: " + getLogin() + "}");
        System.out.println(response);
        return response;
    }

    public boolean registerNewUser(String newUserFname, String newUserLname, String newUserLogin, String newUserPassword) throws IOException {
        String response = makePostAction("/signup", "{fname: " + newUserFname +  " , lname: " + newUserLname + " , login: " + newUserLogin +  " , password: " + newUserPassword + "}");
        System.out.println(response);

        /* Check for an errors */
        return true;
    }

    public boolean sendMessage(String userTo, String userMessage) throws IOException {
        String response = makePostAction("/message/new?token=" + getToken(), "{from: " + getLogin() + " , to: " + userTo + " , message: " + userMessage + "}");
        System.out.println(response);

        /* Check for an errors */
        return true;
    }

    public String showUserChat() throws IOException {
        String response = makePostAction("/messages?token=" + getToken(), "{login: " + getLogin() + "}");
        System.out.println(response);
        return response;
    }
}
