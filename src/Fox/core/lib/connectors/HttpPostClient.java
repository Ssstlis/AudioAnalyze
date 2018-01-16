package Fox.core.lib.connectors;

import Fox.core.lib.general.DOM.JSON;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.util.concurrent.TimeUnit;

//TODO NOT FINAL VERSION
public class HttpPostClient
{
    private OkHttpClient client;
    private Request Req;

    public HttpPostClient()
    {
        client = new OkHttpClient
                .Builder()
                .connectTimeout(30,
                                TimeUnit.SECONDS
                               )
                .build();
    }

    public HttpPostClient build(String adress,
                                JSON param)
    {
        final MediaType JSON
                = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,
                                              param.getSource()
                                             );
        Request request = new Request.Builder()
                .url(adress)
                .post(body)
                .build();
        return this;
    }
}
