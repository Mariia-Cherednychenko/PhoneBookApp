package pattern.request;

import lombok.Builder;

import java.net.URI;
import java.net.http.HttpRequest;

@Builder
public class HttpRequestProductGetJson implements HttpRequestProductGet {
    @Override
    public HttpRequest buildHttpRequestProductGet(String url) {
        return  HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .header("Accept","application/json")
                .build();
    }
}
