package by.bsuir.iit.aipos.service;

import by.bsuir.iit.aipos.domain.HTTPResponse;
import by.bsuir.iit.aipos.exception.InvalidURLException;
import by.bsuir.iit.aipos.exception.ServiceException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;

public class Connector {

    private final int HTTP_PORT = 80;

    private Socket clientSocket = new Socket();
    private BufferedReader input;
    private PrintWriter output;

    public HTTPResponse httpRequest(String address, String request) throws ServiceException {

        try {
            clientSocket = new Socket(InetAddress.getByName(getHost(address)), HTTP_PORT);
            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            output = new PrintWriter(clientSocket.getOutputStream(), true);
            output.println(request);
            return getResponse();
        } catch (IOException e) {
            throw new SecurityException("Error with socket handling", e);
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                throw new SecurityException("Closing socket error", e);
            }
        }
    }

    private String getHost(String address) throws InvalidURLException {

        try {
            URL url = new URL(address);
            return url.getHost();
        } catch (MalformedURLException e) {
            throw new InvalidURLException("Bad request exception", e);
        }
    }

    private HTTPResponse getResponse() throws ServiceException {

        try {
            HTTPResponse response = new HTTPResponse();
            boolean headerFieldHandling = true;
            for (String line = input.readLine(); input.ready() && line != null; line = input.readLine()) {
                if (!headerFieldHandling && !line.isEmpty()) {
                    response.appendEntityBody(line);
                } else if (line.isEmpty()) {
                    headerFieldHandling = false;
                } else {
                    response.appendHeaderField(line);
                }
            }
            return response;
        } catch (IOException e) {
            throw new ServiceException("Error with data reading", e);
        } finally {
            try {
                input.close();
            } catch (IOException e) {
                throw new ServiceException("Closing buffered reader error", e);
            }
        }
    }
}
