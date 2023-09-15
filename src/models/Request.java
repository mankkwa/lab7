package models;

import com.google.gson.Gson;

public class Request {

    private String message;
    private String data;
    private String action;

    public Request(String message) {
        this.message = message;
    }

    public static Request fromJson(String json) {
        return new Gson().fromJson(json, Request.class);
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    public String getMessage() {
        return message;
    }

}
