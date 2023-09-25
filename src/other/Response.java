package other;

import client.Converter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.ZonedDateTime;

public class Response {

    private Object argument;
    public Status status;

    public Response(Status status, Object argument) {
        this.status = status;
        this.argument = new Gson().toJson(argument);
    }

    public static Response fromJson(String json)  {
        return new Gson().fromJson(json, Response.class);
    }

    public <T> T getArgumentAs(Class<T> clazz) {
        Gson gson = new GsonBuilder().registerTypeAdapter(ZonedDateTime.class, new Converter()).create();;
        return gson.fromJson((String) argument, clazz);
    }

    public String toJson() {
        return new Gson().toJson(this);
    }


    public enum Status {
        OK,
        ERROR,
        SERVER_EXIT
    }
}
