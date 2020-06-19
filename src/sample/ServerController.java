package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.JSONObject;

import java.io.*;
import java.net.*;

import java.nio.charset.StandardCharsets;

public class ServerController {
    private String fname;
    private String lname;
    private String login;
    private String token;

    private JSONObject serverResponse;

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

    public JSONObject getServerResponse() {
        return serverResponse;
    }

    public boolean makePostAction(String operationURL, String inputParams) throws IOException {
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

            InputStream inputStream = null;
            int serverResponceCode = connection.getResponseCode();

            if (!(serverResponceCode >= 200 && serverResponceCode < 400)) {
                inputStream = connection.getErrorStream();

                try (BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) { //make write to calleted result tp the variable
                    String inputLine;
                    StringBuilder content = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        content.append(inputLine);
                    }
                    this.serverResponse = new JSONObject(content.toString());
                    return false;

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            inputStream = connection.getInputStream();
            try (BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) { //make write to calleted result tp the variable
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                this.serverResponse = new JSONObject(content.toString());
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean logIn(String login, String password) throws IOException {
        if (makePostAction("/login", "{login: " + login + " , password: " + password + "}")) {
            JSONObject obj = new JSONObject(getServerResponse().toString());
            fname = obj.getString("fname");
            lname = obj.getString("lname");
            this.login = obj.getString("login");
            token = obj.getString("token");

            System.out.println(login + " " + getToken());
            return true;
        } else {
            return false;
        }
    }

    public boolean logOut() throws IOException {
        if (makePostAction("/logout?token=" + getToken(), "{login: " + getLogin() + "}")) {
            return true;
        } else {
            return false;
        }
    }

    public ObservableList showLog() throws IOException {
        ObservableList logList = FXCollections.observableArrayList();
        if (makePostAction("/log?token=" + getToken(), "{login: " + getLogin() + "}")) {

            JSONObject response = new JSONObject(serverResponse.toString());
            JSONObject parser;

            for (int i = 1; i <= response.length(); i++) {
                parser = response.getJSONObject(String.valueOf(i));
                logList.addAll(parser.get("datetime").toString() + ". User:  " + parser.get("login").toString() + " - Action: " + parser.get("type").toString());
            }
            return logList;
        } else {
            return null;
        }
    }

    public boolean registerNewUser(String newUserFname, String newUserLname, String newUserLogin, String newUserPassword) throws IOException {
        if (makePostAction("/signup", "{fname: " + newUserFname + " , lname: " + newUserLname + " , login: " + newUserLogin + " , password: " + newUserPassword + "}"))
            return true;
        else
            return false;
    }

    public boolean sendMessage(String userTo, String userMessage) throws IOException {
        if (makePostAction("/message/new?token=" + getToken(), "{from: " + getLogin() + " , to: " + userTo + " , message: " + userMessage + "}")) {
            return true;
        } else {
            return false;
        }
    }

    public ObservableList showUserChat() throws IOException {
        ObservableList chatList = FXCollections.observableArrayList();
        if (makePostAction("/messages?token=" + getToken(), "{login: " + getLogin() + "}")) {

            JSONObject response = new JSONObject(serverResponse.toString());
            JSONObject parser;

            for (int i = 1; i <= response.length(); i++) {
                parser = response.getJSONObject(String.valueOf(i));
                chatList.addAll(parser.get("time").toString() + "  " + parser.get("from").toString() + " to " + parser.get("to").toString() + ":  '" + parser.get("message").toString() + "'");
            }

            return chatList;
        } else {
            return null;
        }
    }

    public boolean changePassword(String oldpassword, String newpassword) throws IOException {
        if (makePostAction("/changepassword?token=" + getToken(), "{login: " + getLogin() + " , oldpassword: " + oldpassword + " , newpassword: " + newpassword + "}"))
            return true;
        else
            return false;
    }

    public String makeDeleteAction(String operationURL, String inputParams) throws IOException {
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
            connection.setRequestMethod("DELETE");
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

    public boolean deleteUserChat() throws IOException {
        String response = makeDeleteAction("/deletechat?token=" + getToken(), "{login: " + getLogin() + "}");
        System.out.println(response);
        return true;
    }
}
