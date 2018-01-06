package Fox.core.lib.general;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.concurrent.TimeUnit;

public class HttpClient {
    private OkHttpClient client;
    private Request req;

    public HttpClient() {
        client = new OkHttpClient
                .Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    public HttpClient build(String adress) {
        req = new Request.Builder()
                .url(adress)
                .build();
        return this;
    }

    public String run() {
        try {
            Response response = client.newCall(req).execute();

            String Resp = response.body().string();

            if (199 < response.code()
                    && 300 > response.code()
                    || response.code() == 400) return Resp;
            else
                return "";
        } catch (Exception e) {
            return "";
        }
    }
}
