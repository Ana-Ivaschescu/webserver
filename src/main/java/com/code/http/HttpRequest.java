package com.code.http;

public class HttpRequest extends HttpMessage{

    private HttpMethod method;
    private String requestTime;
    private String httpVersion;

    HttpRequest(){

    }

    public HttpMethod getMethod() {
        return method;
    }

    void setMethod(String methodName) {
        this.method = HttpMethod.valueOf(methodName);
    }
}
