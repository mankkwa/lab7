package models;

import com.google.gson.Gson;

public class Response {


    private String message;

    public Response(String message) {
        this.message = message;
    }

    public static Response fromJson(String json)  {
        return new Gson().fromJson(json, Response.class);
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    public String getMessage() {
        return message;
    }
}
