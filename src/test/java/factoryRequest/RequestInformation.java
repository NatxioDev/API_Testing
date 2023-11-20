package factoryRequest;

import java.util.HashMap;
import java.util.Map;

public class RequestInformation {
    private String url;
    private String body;
    private Map<String, String> header;

    public RequestInformation() {
        header = new HashMap<>();
    }

    public String getUrl() {
        return url;
    }

    public String getBody() {
        return body;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public RequestInformation setUrl(String url) {
        this.url = url;
        return this;
    }

    public RequestInformation setBody(String body) {
        this.body = body;
        return this;
    }

    public RequestInformation setHeader(String key, String value) {
        this.header.put(key, value);
        return this;
    }


}
