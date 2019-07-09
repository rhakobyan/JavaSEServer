import java.io.*;
import java.net.*;

import com.sun.net.httpserver.*;


public class XServer {

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/", new XHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
        System.out.println("Server up and running");
    }

}
