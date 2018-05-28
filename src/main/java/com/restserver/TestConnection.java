package com.restserver;

import com.google.gson.Gson;
import com.models.Player;
import com.restserver.json.request.account.Login;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

public class TestConnection {

        public static void main(String[] args) throws Exception {
            Gson gson = new Gson();
            Player player = new Player("Henk","Henk@gmail.com","Henk123");
            String temp = "data={" +
                    "\"username\": \"admin\", " +
                    "\"first_name\": \"System\", " +
                    "\"last_name\": \"Administrator\"" +
                    "}";
            String payload = gson.toJson(player);
            StringEntity entity = new StringEntity(payload,
                    ContentType.APPLICATION_JSON);

            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost request = new HttpPost("http://localhost:8090/account/register");
            request.setEntity(entity);

            Login login = new Login("Henk@gmail.com", "Henk123");
            String payloadLogin = gson.toJson(login);
            StringEntity entityLogin = new StringEntity(payloadLogin,
                    ContentType.APPLICATION_JSON);

            HttpClient httpClientLogin = HttpClientBuilder.create().build();
            HttpPost requestLogin = new HttpPost("http://localhost:8090/account/login");
            requestLogin.setEntity(entityLogin);

            HttpResponse response = httpClient.execute(request);
            System.out.println(response.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());

            HttpResponse responseLogin = httpClientLogin.execute(requestLogin);
            System.out.println(responseLogin.getStatusLine().getStatusCode() + " " + response.getStatusLine().getReasonPhrase());
        }
    }