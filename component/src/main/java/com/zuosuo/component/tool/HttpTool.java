package com.zuosuo.component.tool;

import com.zuosuo.component.response.FuncResult;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.net.URISyntaxException;

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
}
