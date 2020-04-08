package com.kotor.request;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class SendRequest {
    public static String findCity(String name) {
        String targetURL = "http://api:8000/city/name/" + name, urlParameters = "";
        HttpGet request = new HttpGet(targetURL);
        String result = "";

        try (CloseableHttpClient ignored = HttpClients.createDefault();
             CloseableHttpResponse response = ignored.execute(request)) {

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                // return it as a String
                result = EntityUtils.toString(entity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String findById(String id) {
        String targetURL = "http://api:8000/city/" + id, urlParameters = "";
        HttpGet request = new HttpGet(targetURL);
        String result = "";

        try (CloseableHttpClient ignored = HttpClients.createDefault();
             CloseableHttpResponse response = ignored.execute(request)) {

            // Get HttpResponse Status
            System.out.println(response.getProtocolVersion());              // HTTP/1.1
            System.out.println(response.getStatusLine().getStatusCode());   // 200
            System.out.println(response.getStatusLine().getReasonPhrase()); // OK
            System.out.println(response.getStatusLine().toString());        // HTTP/1.1 200 OK

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                // return it as a String
                result = EntityUtils.toString(entity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String createCity(String name, String content) {
        String result ="";
        HttpPost post = new HttpPost("http://api:8000/city");
        List<NameValuePair> urlParameters = new ArrayList<>();
        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("\"name\":\"" + name + "\",");
        json.append("\"content\":\"" + content +"\"");
        json.append("}");

        // send a JSON data
        try {
            StringEntity temp = new StringEntity(json.toString());
            temp.setContentType("application/json");
            temp.setContentEncoding("UTF-8");
            post.setEntity(temp);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post)) {
            result = EntityUtils.toString(response.getEntity());
            System.out.println(result);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}