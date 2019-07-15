import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class XHandler implements HttpHandler {
    HttpExchange exchange;

    @Override
    public void handle(HttpExchange t) throws IOException {
        exchange = t;
        String requestMethod = exchange.getRequestMethod();
        System.out.println(requestMethod);
        if (requestMethod.equals("GET")) {
            get();
        } else if (requestMethod.equals("POST")) {
            post();
        }

    }

    private void get() throws IOException {
        Headers h = exchange.getResponseHeaders();

        Router.route(exchange.getRequestURI().getPath());
        String query = exchange.getRequestURI().getQuery();
        System.out.println(exchange.getRequestURI().getPath());
        h.add("Content-Type", "text/html");
        exchange.sendResponseHeaders(Router.getCode(), Router.getResponse().length());
        OutputStream os = exchange.getResponseBody();
        os.write(Router.getResponse().getBytes());
        os.close();
    }

    private void post() throws IOException {
        Map<String, Object> parameters = new HashMap<String, Object>();
        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(isr);
        String query = br.readLine();
        Router.parseQuery(query, parameters);

        // send response
        String response = "";
        if (exchange.getRequestURI().getPath().equals("/form")) {
            RegistrationHandler registration = new RegistrationHandler(parameters);
        }
        for (String key : parameters.keySet())
            response += key + " = " + parameters.get(key) + "\n";
        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.toString().getBytes());
        os.close();

    }
}