
package client;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Converter implements JsonSerializer<ZonedDateTime>, JsonDeserializer<ZonedDateTime>
{
    /** Formatter. */
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_DATE_TIME;


    @Override
    public ZonedDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
    {
        return FORMATTER.parse(json.getAsString(), ZonedDateTime::from);
    }

    @Override
    public JsonElement serialize(ZonedDateTime src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(FORMATTER.format(src));
    }
}
