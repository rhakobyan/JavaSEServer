import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.util.stream.Collectors;

public class XHandler implements HttpHandler {
        HttpExchange exchange;
        @Override
        public void handle(HttpExchange t) throws IOException{
            exchange = t;
            String requestMethod = exchange.getRequestMethod();
            System.out.println(requestMethod);
            if (requestMethod.equals("GET")){
                get();
            }
            else if(requestMethod.equals("POST")){
                post();
            }

        }

        private void get() throws IOException{
            Headers h = exchange.getResponseHeaders();

            Router.route(exchange.getRequestURI().getPath());
            h.add("Content-Type", "text/html");
            exchange.sendResponseHeaders(Router.getCode(), Router.getResponse().length());
            OutputStream os = exchange.getResponseBody();
            os.write(Router.getResponse().getBytes());
            os.close();
        }

        private void post(){
            String body = new BufferedReader(
                    new InputStreamReader(
                            exchange.getRequestBody()
                    )
            ).lines().collect(Collectors.joining("\n"));

            System.out.println(body);
        }

    }

