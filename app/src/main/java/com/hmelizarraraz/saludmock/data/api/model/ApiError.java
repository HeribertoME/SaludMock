package com.hmelizarraraz.saludmock.data.api.model;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * Created by heriberto on 26/05/17.
 */

public class ApiError {
    private int status;
    private int code;
    private String message;
    private String moreInfo;
    private String developerMessage;

    public ApiError(int status, int code, String message, String moreInfo, String developerMessage) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.moreInfo = moreInfo;
        this.developerMessage = developerMessage;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMoreInfo() {
        return moreInfo;
    }

    public void setMoreInfo(String moreInfo) {
        this.moreInfo = moreInfo;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public void setDeveloperMessage(String developerMessage) {
        this.developerMessage = developerMessage;
    }

    public static ApiError fromResponseBody(ResponseBody responseBody) {
        Gson gson = new Gson();
        try {
            return gson.fromJson(responseBody.string(), ApiError.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
