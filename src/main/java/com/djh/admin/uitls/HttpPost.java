package com.djh.admin.uitls;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import static org.apache.http.impl.client.HttpClients.createDefault;

/**
 * Created by Administrator on 2019/2/18.
 */
public class HttpPost {
    public static String sendPost(String url, List<NameValuePair> nvps) throws Exception {

        HttpResponse response;
        try (CloseableHttpClient client = createDefault()) {
//            HttpGet request = new HttpGet(url);
            org.apache.http.client.methods.HttpPost request= new org.apache.http.client.methods.HttpPost(url);


            //添加请求头
            request.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            request.addHeader("Accept", "text/html");
            request.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
            response = client.execute(request);
//            System.out.println("\nSending 'GET' request to URL : " + url);
//            System.out.println("Response Code : " +
//                    response.getStatusLine().getStatusCode());

            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));

            StringBuffer result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            return result.toString();
        }
    }
}
