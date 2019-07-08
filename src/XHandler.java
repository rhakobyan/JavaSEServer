import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;

    public class XHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            Headers h = t.getResponseHeaders();
            String line;
            String response = "";
            try{
                File newFile = new File("view/index.html");
                System.out.println("File name " + newFile.getName());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(newFile)));
                while ((line = bufferedReader.readLine()) != null) {
                    response += line;
                }
                bufferedReader.close();
            }  catch(IOException e){
                e.printStackTrace();
            }
            h.add("Content-Type", "text/html");
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
            System.out.println("Handling");
        }
    }

