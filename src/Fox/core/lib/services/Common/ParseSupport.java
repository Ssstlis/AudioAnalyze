package Fox.core.lib.services.Common;

import com.google.gson.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.math.BigInteger;

public class ParseSupport
{
    @Nullable
    public static Object GetSource(@NotNull JsonObject object,
                                   @NotNull String name,
                                   @NotNull Class target)
    {
        JsonElement element;
        if (object.has(name))
        {
            element = object.get(name);
        }
        else
        {
            return null;
        }

        if (target == BigDecimal.class)
        {
            return element.getAsBigDecimal();
        }

        if (target == BigInteger.class)
        {
            return element.getAsBigInteger();
        }

        if (target == boolean.class)
        {
            return element.getAsBoolean();
        }

        if (target == byte.class)
        {
            return element.getAsByte();
        }

        if (target == char.class)
        {
            return element.getAsCharacter();
        }

        if (target == double.class)
        {
            return element.getAsDouble();
        }

        if (target == float.class)
        {
            return element.getAsFloat();
        }

        if (target == int.class)
        {
            return element.getAsInt();
        }

        if (target == JsonArray.class)
        {
            return element.getAsJsonArray();
        }

        if (target == JsonNull.class)
        {
            return element.getAsJsonNull();
        }

        if (target == JsonObject.class)
        {
            return element.getAsJsonObject();
        }

        if (target == JsonPrimitive.class)
        {
            return element.getAsJsonPrimitive();
        }

        if (target == long.class)
        {
            return element.getAsLong();
        }

        if (target == Number.class)
        {
            return element.getAsNumber();
        }

        if (target == short.class)
        {
            return element.getAsShort();
        }

        if (target == String.class)
        {
            return element.getAsString();
        }

        return null;
    }
}
