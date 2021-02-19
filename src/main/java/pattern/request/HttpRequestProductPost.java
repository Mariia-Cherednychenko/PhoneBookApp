package pattern.request;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.net.http.HttpRequest;


public interface HttpRequestProductPost {
    public HttpRequest buildHttpRequestProductPost(Object obj, String url) throws JsonProcessingException;

}
