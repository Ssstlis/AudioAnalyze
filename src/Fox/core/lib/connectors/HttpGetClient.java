package Fox.core.lib.connectors;

import okhttp3.*;
import org.slf4j.Logger;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class HttpGetClient
{
    private Logger logger;
    private OkHttpClient client;
    private Request req;

    public HttpGetClient(Logger logger)
    {
        this.logger = logger;
        client = new OkHttpClient
                .Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(300, TimeUnit.SECONDS)
                .writeTimeout(300,TimeUnit.SECONDS)
                .connectionPool(new ConnectionPool(3, 1L, TimeUnit.MINUTES))
                .build();
    }

    public HttpGetClient build(String adress)
    {
        req = new Request.Builder()
                .url(adress)
                .build();
        return this;
    }

    public String run(long Elapse, int tries)
            throws
            IOException,
            InterruptedException,
            NullPointerException
    {
        TimeUnit.MILLISECONDS.sleep(Elapse);
        Response response = null;
        boolean success = false;

        do
        {
            try
            {
                response = client.newCall(req).execute();
                success = true;
            }
            catch (IOException e)
            {
                if (logger != null && logger.isErrorEnabled())
                    logger.error("", e);
            }
        }
        while (!success && --tries > 0);

        String Resp = null;

        if (response != null)
        {
            ResponseBody responseBody = response.body();
            if (responseBody != null)
            {
                Resp = responseBody.string();
                responseBody.close();
            }
        }
        return Resp;
    }
}
