package com.zuosuo.component.tool;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.zuosuo.component.response.FuncResult;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpTool {

    public static FuncResult get(String url) {
        FuncResult result = new FuncResult();
        CloseableHttpClient client = HttpClients.createDefault();
        try {
            HttpGet httpGet = new HttpGet(new URIBuilder(url).build());
            HttpResponse response = client.execute(httpGet);
            result.setStatus(true);
            result.setResult(response.getEntity());
        } catch (URISyntaxException e) {
            result.setMessage("请求错误!");
        } catch (ClientProtocolException e) {
            result.setMessage("请求错误!!");
        } catch (IOException e) {
            result.setMessage("请求错误!!!");
        }
        return result;
    }

    public static FuncResult post(String url, Map<String, Object> params, String charset){
        FuncResult result = new FuncResult();
        CloseableHttpClient client = HttpClients.createDefault();
        try {
            HttpPost httpPost = new HttpPost(url);
            if (params != null && !params.isEmpty()) {
                StringEntity se = new StringEntity(JsonTool.toJson(params), charset);
                se.setContentType("text/json");
                httpPost.setEntity(se);
            }
            httpPost.setHeader("Content-Type", "application/json;charset=" + charset);
            httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
            CloseableHttpResponse response = client.execute(httpPost);
            result.setStatus(true);
            result.setResult(response.getEntity());
        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("请求错误!!!");
        }
        return result;
    }

    public static FuncResult postJson(String url, Map<String, Object> params, String charset){
        FuncResult result = post(url, params, charset);
        if (!result.isStatus()) {
            return result;
        }
        HttpEntity entity = (HttpEntity) result.getResult();
        try {
            JSONObject obj = JSON.parseObject(entity.getContent(), Object.class, Feature.AllowArbitraryCommas);
            result.setResult(obj);
        } catch (IOException e) {
            result.setStatus(false);
            result.setMessage("请求格式解析失败");
        }
        return result;
    }

    public static FuncResult upload(String url, MultipartFile file) {
        FuncResult result = new FuncResult();
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-Type", "application/octet-stream");
        try {
            InputStream inputStream = file.getInputStream();
            byte[] byt = new byte[inputStream.available()];
            inputStream.read(byt);
            httpPost.setEntity(new ByteArrayEntity(byt, ContentType.create(file.getContentType())));
            CloseableHttpResponse response = client.execute(httpPost);
            result.setStatus(true);
            result.setResult(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
            result.setMessage("请求错误!!!");
        }
        return result;
    }

    public static FuncResult uploadJson(String url, MultipartFile file){
        FuncResult result = upload(url, file);
        if (!result.isStatus()) {
            return result;
        }
        HttpEntity entity = (HttpEntity) result.getResult();
        try {
            JSONObject obj = JSON.parseObject(entity.getContent(), Object.class, Feature.AllowArbitraryCommas);
            result.setResult(obj);
        } catch (IOException e) {
            result.setStatus(false);
            result.setMessage("请求格式解析失败");
        }
        return result;
    }

    public static String getIpAddr(HttpServletRequest request) {
        String ipAddress = null;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if (ipAddress.equals("127.0.0.1")) {
                    // 根据网卡取本机配置的IP
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                    } catch (UnknownHostException e) {
                        return "";
                    }
                    ipAddress = inet.getHostAddress();
                }
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if (ipAddress != null && ipAddress.length() > 15) {
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress="";
        }
        return ipAddress;
    }

    public static String getResponseContent(HttpEntity entity) {
        String content = null;
        try {
            content = new BufferedReader(new InputStreamReader(entity.getContent())).lines().parallel().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return content;
    }
}
