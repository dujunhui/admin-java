package com.djh.admin.uitls;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;

import static org.apache.http.impl.client.HttpClients.createDefault;

/**
 * Created by Administrator on 2019/2/18.
 */
public class HttpPostYD {
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


    /**
     * 发起webservice请求
     * @param url
     * @param soap
     * @param SOAPAction
     * @return
     */
    public static String doPostSoap(String url, String soap, String SOAPAction) {
        //请求体
        String retStr = "";
        // 创建HttpClientBuilder
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        // HttpClient
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
        HttpPost httpPost = new HttpPost(url);
        //  设置请求和传输超时时间
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(30000)
                .setConnectTimeout(30000).build();
        httpPost.setConfig(requestConfig);
        try {
            httpPost.setHeader("Content-Type", "text/xml;charset=UTF-8");
            httpPost.setHeader("SOAPAction", SOAPAction);
            StringEntity data = new StringEntity(soap, Charset.forName("UTF-8"));
            httpPost.setEntity(data);
            CloseableHttpResponse response = closeableHttpClient.execute(httpPost);
            HttpEntity httpEntity = response.getEntity();
            if (httpEntity != null) {
                // 打印响应内容
                retStr = EntityUtils.toString(httpEntity, "UTF-8");
                System.err.println("response:" + retStr);
            }
            // 释放资源
            closeableHttpClient.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retStr;
    }
}
