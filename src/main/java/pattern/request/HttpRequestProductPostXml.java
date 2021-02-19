package pattern.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpRequest;


public class HttpRequestProductPostXml implements HttpRequestProductPost {
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public HttpRequest buildHttpRequestProductPost(Object obj, String url) throws JsonProcessingException {
        return HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(
                        objectMapper.writeValueAsString(obj)))
                .uri(URI.create(url))
                .header("Accept","application/json")
                .header("Content-type", "application/json")
                .build();
    }
}