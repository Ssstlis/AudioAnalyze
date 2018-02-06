package Fox.core.lib.general.DOM;

import java.util.Map;
import java.util.Set;

public class JSON
{
    private String source;

    public JSON()
    {
    }

    public void build(Map<String, String> json)
    {
        if (json == null)
        {
            source = null;
        }

        if (json.isEmpty())
        {
            source = "{}";
        }

        source = "{";

        Set<String> keys = json.keySet();

        for (String key : keys)
        {
            source = source.concat("\"" + key + "\":\"" + json.get(key) + "\",");
        }

        source = source.substring(0,
                                  source.length() - 1
                                 );
        source = source.concat("}");
    }

    public String getSource()
    {
        return source;
    }
}
