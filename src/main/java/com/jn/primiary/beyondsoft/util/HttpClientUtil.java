package com.jn.primiary.beyondsoft.util;

import com.jn.primiary.commons.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class HttpClientUtil {
    private Logger logger = Logger.getLogger(HttpClientUtil.class);

    /**
     * 单点登录，忽略证书
     * @return
     * @throws Exception
     */
    public static CloseableHttpClient getTrustHttpsCertificatesHttpClient() throws Exception {
        // 信任所有
        SSLContext sslcontext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy()
        {
            // 信任所有
            public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException
            {
                return true;
            }
        }).build();
        HostnameVerifier allowAllHosts = new NoopHostnameVerifier();
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[] { "TLSv1","TLSv1.2","TLSv1.1","SSLv3"},
                null,
                allowAllHosts);
        final Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", new PlainConnectionSocketFactory())
                .register("https",sslsf)
                .build();

        final PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(registry);
        cm.setMaxTotal(100);

        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .setConnectionManager(cm)
                .build();
        return httpClient;
    }



    //get请求
    public String get(String url) throws Exception {
        return get(url,null);
    }

    public String get(String url,Map<String,String> headerMap) throws Exception {
        String content = "";
        try {
            //创建httpclient对象
            CloseableHttpClient httpClient = HttpClients.createDefault();
            //创建Http get请求
            HttpGet httpGet = new HttpGet(url);

            if(headerMap != null){
                for (Map.Entry<String,String> entry : headerMap.entrySet()){
                    httpGet.setHeader(entry.getKey(),entry.getValue());
                }
            }

            //接收返回值
            CloseableHttpResponse response = httpClient.execute(httpGet);

            logger.info(response.getStatusLine().getStatusCode());
            if(response.getStatusLine().getStatusCode()==200){
                content = EntityUtils.toString(response.getEntity(), "utf-8");
                // logger.info("--------->get请求返回值：" + content);
            }
            if(response!=null){
                response.close();
            }
            httpClient.close();

        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return content;
    }

    public String post(String url) throws Exception {
        try{
            // 创建Httpclient对象
            CloseableHttpClient httpclient = HttpClients.createDefault();

            // 创建http POST请求
            HttpPost httpPost = new HttpPost(url);
            // 伪装成浏览器
            //httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.94 Safari/537.36");

            CloseableHttpResponse response = httpclient.execute(httpPost);
            // 判断返回状态是否为200
            String content = "";
            if (response.getStatusLine().getStatusCode() == 200) {
                content = EntityUtils.toString(response.getEntity(), "UTF-8");
                System.out.println(content);
            }
            if (response != null) {
                response.close();
            }
            httpclient.close();
            return content;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public String post_with_form(String url, Map<String,String> parammap) throws Exception {
        try{
            //创建httpclient
            CloseableHttpClient httpClient = HttpClients.createDefault();
            //创建http post
            HttpPost httpPost = new HttpPost(url);
            //以表单格式提交
            httpPost.addHeader("Content-type","application/x-www-form-urlencoded;charset=UTF-8");
            //httpPost.addHeader("Content-type","application/json;charset=UTF-8");
            //模拟浏览器设置头
//                httpPost.setHeader(
//                        "User-Agent",
//                        "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.94 Safari/537.36");

            //设置请求数据
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            for (Map.Entry<String, String> entry : parammap.entrySet()) {
                params.add(new BasicNameValuePair(entry.getKey(), URLDecoder.decode(entry.getValue(),"UTF-8") ));
            }

            //构建表单
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(params);
            //formEntity.setContentType("application/x-www-form-urlencoded;charset=UTF-8");
            //将表达请求放入到httpost
            httpPost.setEntity(formEntity);

            //返回类型
            CloseableHttpResponse response = httpClient.execute(httpPost);
            String content = EntityUtils.toString(response.getEntity(), "utf-8");
            if (response.getStatusLine().getStatusCode() == 200) {
                content = EntityUtils.toString(response.getEntity(), "utf-8");
                System.out.println(content);
                response.close();
                httpClient.close();
            }else{
                throw new Exception("http request fail");
            }
            response.close();
            httpClient.close();
            return content;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }


    public String post_with_json(String url, String jsonstr) throws Exception {
        String result = "";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try{
            //创建httpclient

            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);

            StringEntity requestEntity = new StringEntity(jsonstr,"utf-8");
            requestEntity.setContentEncoding("UTF-8");
            httpPost.setHeader("Content-type","application/json;charset=UTF-8");
            httpPost.setEntity(requestEntity);

            result = httpClient.execute(httpPost,responseHandler);

            return result;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

/**
     * post，json参数提交
     * @param url
     * @param json
     * @return
     */
    public String postWithJson(String url, String json) throws Exception {
        return postWithJson(url,json,null);
        /*String returnValue = "这是默认返回值，接口调用失败";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        try{
            //第一步：创建HttpClient对象
            httpClient = HttpClients.createDefault();

            //第二步：创建httpPost对象
            HttpPost httpPost = new HttpPost(url);

            //第三步：给httpPost设置JSON格式的参数
            StringEntity requestEntity = new StringEntity(json,"utf-8");
            requestEntity.setContentEncoding("UTF-8");
            httpPost.setHeader("Content-type", "application/json;charset=utf-8");
            httpPost.setEntity(requestEntity);

            //第四步：发送HttpPost请求，获取返回值
            returnValue = httpClient.execute(httpPost,responseHandler); //调接口获取返回值时，必须用此方法

        }
        catch(Exception e) {
            throw new Exception(e.getMessage());
        }finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        //第五步：处理返回值
        return returnValue;*/
    }


    public String postJsonCommit(String url,String data,CloseableHttpClient hp) throws IOException {
        Charset charset = Charset.forName("UTF-8");
        HttpPost httpPost = new HttpPost(url);
        StringEntity se = new StringEntity(data, charset);
        se.setContentType("application/json");
        httpPost.setEntity(se);
        CloseableHttpResponse response = hp.execute(httpPost);
        HttpEntity entity = response.getEntity();
        String responseStr = EntityUtils.toString(entity,"utf-8");
        return responseStr;
    }

    public String postWithJson(String url, String json,Map<String,String> headerMap) throws Exception {
        String returnValue = "这是默认返回值，接口调用失败";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        try{
            //第一步：创建HttpClient对象
            httpClient = HttpClients.createDefault();

            //第二步：创建httpPost对象
            HttpPost httpPost = new HttpPost(url);

            //第三步：给httpPost设置JSON格式的参数
            StringEntity requestEntity = new StringEntity(json,"utf-8");
            requestEntity.setContentEncoding("UTF-8");
            httpPost.setHeader("Content-type", "application/json;charset=utf-8");

            if(headerMap != null){
                for (Map.Entry<String,String> entry : headerMap.entrySet()){
                    httpPost.setHeader(entry.getKey(),entry.getValue());
                }
            }

            httpPost.setEntity(requestEntity);

            //第四步：发送HttpPost请求，获取返回值
            returnValue = httpClient.execute(httpPost,responseHandler); //调接口获取返回值时，必须用此方法

        }
        catch(Exception e) {
            throw new Exception(e.getMessage());
        }finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        //第五步：处理返回值
        return returnValue;
    }

}