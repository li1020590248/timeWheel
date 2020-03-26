import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpUtils {
    public static void main(String[] args) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 创建Get请求
        //HttpGet httpGet = new HttpGet("http://localhost:9002/sayHello");
        HttpGet httpGet = new HttpGet("http://www.baidu.com");
        HttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpGet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpEntity httpEntity = httpResponse.getEntity();
        if (httpEntity != null){
            InputStream instreams = null;
            try {
                System.out.println("entitylength"+ httpEntity.getContentLength());
                instreams = httpEntity.getContent();

            } catch (IOException e) {
                e.printStackTrace();
            }
            String str = ConvertStreamToString(instreams);
            System.out.println("Response:" + "\n" + str);
        }
    }

    public static String ConvertStreamToString(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error=" + e.toString());
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                System.out.println("Error=" + e.toString());
            }
        }
        return sb.toString();

    }
}

