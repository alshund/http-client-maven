package by.bsuir.iit.aipos.controller;

import by.bsuir.iit.aipos.service.constant.HeaderField;
import by.bsuir.iit.aipos.service.constant.HttpMethod;
import by.bsuir.iit.aipos.domain.HTTPResponse;
import by.bsuir.iit.aipos.service.Connector;
import by.bsuir.iit.aipos.service.ProtocolCreator;
import by.bsuir.iit.aipos.exception.InvalidURLException;
import by.bsuir.iit.aipos.exception.ServiceException;
import by.bsuir.iit.aipos.service.constant.StatusCode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebView;

import java.util.Optional;

public class RequestController {

    private ObservableList<String> methodsList = FXCollections.observableArrayList();
    {
        for (HttpMethod httpMethod : HttpMethod.values()) {
            methodsList.add(httpMethod.name());
        }
    }

    @FXML
    private TextField urlField;
    @FXML
    private TextArea httpRequest;
    @FXML
    private TextArea httpResponse;
    @FXML
    private WebView messageBody;
    @FXML
    private TextField responseCode;
    @FXML
    private ComboBox httpMethodsBox;

    private ProtocolCreator protocolCreator = new ProtocolCreator();
    private Connector connector = new Connector();

    @FXML
    private void initialize() {

        httpMethodsBox.setItems(methodsList);
        httpMethodsBox.setValue(HttpMethod.GET.name());
    }

    public void httpRequest(ActionEvent actionEvent) {

        try {
            HttpMethod httpMethod = HttpMethod.valueOf((String) httpMethodsBox.getValue());
            String requestMessage = protocolCreator.prepareRequest(urlField.getText(), httpMethod);
            httpRequest.setText(requestMessage);
            responseHandling(requestMessage);
        } catch (InvalidURLException e) {
            messageBody.getEngine().loadContent(StatusCode.BAD_REQUEST.name());
        }
    }

    private void responseHandling(String requestMessage) {

        try {
            HTTPResponse response = connector.httpRequest(urlField.getText(), requestMessage);
            String responseMessage = response.getHeaderField().toString();
            String entityBody = response.getEntityBody().toString();
            httpResponse.setText(responseMessage);
            statusCodeHandling(responseMessage, entityBody);
        } catch (InvalidURLException e) {
            messageBody.getEngine().loadContent(StatusCode.BAD_REQUEST.name());
        } catch (ServiceException e) {
            messageBody.getEngine().loadContent(StatusCode.BAD_REQUEST.name());

        }
    }

    private void statusCodeHandling(String responseMessage, String entityBody) {

        StatusCode statusCode = protocolCreator.getStatusCode(responseMessage);
        switch (statusCode) {
            case OK:
                messageBody.getEngine().loadContent(entityBody);
                break;
            default:
                messageBody.getEngine().loadContent(statusCode.name());
        }
        responseCode.setText(statusCode.name() + ":" + statusCode.getCode());
    }

    public void headerClicked(MouseEvent mouseEvent) {

        CheckBox activeCheckBox = (CheckBox) mouseEvent.getSource();
        HeaderField headerField = HeaderField.valueOf(toHeaderFieldName(activeCheckBox));
        if (activeCheckBox.isSelected()) {
            handlingInputDialog(headerField, activeCheckBox.getText(), activeCheckBox);
        } else {
            protocolCreator.removeHeaderCode(headerField);
        }
    }

    private String toHeaderFieldName(CheckBox checkBox) {

        return checkBox.getText().toUpperCase().replace("-", "_");
    }

    private void handlingInputDialog(HeaderField headerField, String headerName, CheckBox activeCheckBox) {

        Optional<String> inputData = createInputDialog(headerName);
        if (inputData.isPresent()) {
            protocolCreator.addHeaderCode(headerField, inputData.get());
        } else {
            activeCheckBox.setSelected(false);
        }
    }

    private Optional<String> createInputDialog(String headerName) {

        TextInputDialog inputDialog = new TextInputDialog();
        inputDialog.setTitle(headerName);
        inputDialog.setContentText(headerName);
        inputDialog.setHeaderText(headerName);
        return inputDialog.showAndWait();
    }
}
