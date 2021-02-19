package pattern.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import pattern.factory.HttpRequestFactory;

import java.net.http.HttpRequest;

@RequiredArgsConstructor
public class JsonRequestFactory implements HttpRequestFactory {
    private final ObjectMapper objectMapper;
    @Override
    public HttpRequest createGetRequest(String url) {
        return (new HttpRequestProductGetJson()).buildHttpRequestProductGet(url);
    }

    @Override
    public HttpRequest createPostRequest(String url, Object obj) throws JsonProcessingException {
             return (new HttpRequestProductGetJson()).buildHttpRequestProductGet(url);
        }
}
