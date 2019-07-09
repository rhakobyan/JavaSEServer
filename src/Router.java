import com.sun.net.httpserver.HttpExchange;

import java.io.*;

public class Router {
    private static int code;
    private static String response;
    public static void route(String link)
    {
        response ="";
        String line;
        if(link.equals("/"))
        {
            link = "/index.html";
        }
        try{
            File newFile = new File("view"+ link);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(newFile)));
            while ((line = bufferedReader.readLine()) != null) {
                response += line;
            }

            bufferedReader.close();
        }  catch(IOException e){
            code = 404;
            response = "<h1>404 Page Not Found!</h1>";
        }

    }

    public static int getCode(){
        return code;
    }

    public static String getResponse(){
        return response;
    }
}
