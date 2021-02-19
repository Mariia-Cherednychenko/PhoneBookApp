package pattern.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpRequest;


public class HttpRequestProductPostJson implements HttpRequestProductPost {
    @Override
    public HttpRequest buildHttpRequestProductPost(Object obj, String url) throws JsonProcessingException {
        ObjectMapper objectMapper = null;
        return  HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(
                        objectMapper.writeValueAsString(obj)))
                .uri(URI.create(url))
                .header("Accept","application/json")
                .header("Content-type", "application/json")
                .build();
    }
}
