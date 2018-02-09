package by.bsuir.iit.aipos.service;

import by.bsuir.iit.aipos.service.constant.HeaderField;
import by.bsuir.iit.aipos.service.constant.HttpMethod;
import by.bsuir.iit.aipos.service.constant.RequestLine;
import by.bsuir.iit.aipos.service.constant.StatusCode;
import by.bsuir.iit.aipos.exception.InvalidURLException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProtocolCreator {

    private final String BUNDLE_NAME = "httpPatterns";

    private ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME);
    private Map<HeaderField, String> headerFieldMap = new HashMap<>();

    public void addHeaderCode(HeaderField headerField, String value) {

        headerFieldMap.put(headerField, value);
    }

    public void removeHeaderCode(HeaderField headerField) {

        headerFieldMap.remove(headerField);
    }

    public String prepareRequest(String address, HttpMethod httpMethod) throws InvalidURLException {

        try {
            String requestLine = prepareRequestLine(new URL(address), httpMethod);
            String headerField = prepareHeaderField();
            String entityField = httpMethod.getEntityBody() + RequestLine.CRLF;
            return requestLine + headerField + RequestLine.CRLF + entityField;
        } catch (MalformedURLException e) {
            throw new InvalidURLException("Bad request exception", e);
        }
    }

    public StatusCode getStatusCode(String response) {

        Pattern responseStatusPattern = Pattern.compile("(HTTP\\/\\d\\.\\d)\\s+(\\d+)\\s+(.+)");
        Matcher responseStatusMatcher = responseStatusPattern.matcher(response);
        responseStatusMatcher.find();
        String statusName = responseStatusMatcher.group(StatusCode.getStatusNameGroup());
        return StatusCode.valueOf(statusName.toUpperCase().replace(" ", "_"));
    }

    private String prepareRequestLine(URL url, HttpMethod httpMethod) {

        String requestLine = bundle.getString(RequestLine.REQUEST_LINE_CODE);
        requestLine = requestLine.replace(RequestLine.REQUEST_METHOD, httpMethod.name());
        requestLine = requestLine.replace(RequestLine.URL, url.toString());
        requestLine = requestLine + RequestLine.CRLF;
        return requestLine;
    }

    private String prepareHeaderField() {

        String headerField = "";
        Set<HeaderField> keySet = headerFieldMap.keySet();
        for (HeaderField key : keySet) {
            String header = bundle.getString(key.getPatternCode()).replace(key.name(), headerFieldMap.get(key));
            headerField += (header + RequestLine.CRLF);
        }
        return headerField;
    }
}
