package by.bsuir.iit.aipos.domain;

import by.bsuir.iit.aipos.service.constant.RequestLine;

import java.io.Serializable;

public class HTTPResponse implements Serializable {

    private static final long serialVersionUID = 78438895578939905L;

    private StringBuilder headerField = new StringBuilder();
    private StringBuilder entityBody = new StringBuilder();

    public StringBuilder getHeaderField() {
        return headerField;
    }

    public void appendHeaderField(String headerFieldLine) {
        this.headerField.append(headerFieldLine + RequestLine.CRLF);
    }

    public StringBuilder getEntityBody() {
        return entityBody;
    }

    public void appendEntityBody(String entityBodyLine) {
        this.entityBody.append(entityBodyLine + RequestLine.CRLF);
    }
}
