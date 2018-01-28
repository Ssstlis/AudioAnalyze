package Fox.core.lib.connectors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.concurrent.TimeUnit;

public class HttpGetClient
{
    private OkHttpClient client;
    private Request req;

    public HttpGetClient()
    {
        client = new OkHttpClient
                .Builder()
                .connectTimeout(30,
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
    {
        try
        {
            TimeUnit.MILLISECONDS.sleep(Elapse);
            Response response = client.newCall(req)
                                      .execute();

            String Resp = response.body()
                                  .string();
            return Resp;
        }
        catch (Exception e)
        {
            return null;
        }
    }
}
