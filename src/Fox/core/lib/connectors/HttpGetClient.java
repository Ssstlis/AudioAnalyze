package Fox.core.lib.connectors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class HttpGetClient
{
    private OkHttpClient client;
    private Request req;

    public HttpGetClient()
    {
        client = new OkHttpClient
                .Builder()
                .connectTimeout(60,
                                TimeUnit.SECONDS
                               )
                .build();
    }

    public HttpGetClient build(String adress)
    {
        req = new Request.Builder()
                .url(adress)
                .build();
        return this;
    }

    public String run(long Elapse)
            throws
            IOException,
            InterruptedException,
            NullPointerException
    {
        TimeUnit.MILLISECONDS.sleep(Elapse);
        Response response = client.newCall(req).execute();

        String Resp = null;
        ResponseBody responseBody = response.body();

        if (responseBody != null)
        {
            Resp = responseBody.string();
            responseBody.close();
        }
        return Resp;
    }
}
