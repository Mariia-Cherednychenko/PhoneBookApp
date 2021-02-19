package pattern.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import pattern.factory.HttpRequestFactory;

import java.net.http.HttpRequest;

@RequiredArgsConstructor
public class XmlRequestFactory implements HttpRequestFactory {
    @Override
    public HttpRequest createGetRequest(String url) {
        return (new HttpRequestProductGetXml()).buildHttpRequestProductGet(url);
    }

    @Override
    public HttpRequest createPostRequest(String url, Object obj) throws JsonProcessingException {
        return (new HttpRequestProductPostXml()).buildHttpRequestProductPost(url, (String) obj);
    }
}
