import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class RegistrationHandler {

    Map<String, Object> query;

    public RegistrationHandler(Map<String, Object> query){
        this.query = query;
        handleRegistration();
    }

    private void handleRegistration(){
        try {
            FileWriter writer = new FileWriter("resources/users.csv");
            String fname = (String) query.get("fname");
            String lname = (String) query.get("lname");
            String username = (String) query.get("username");
            String password = (String) query.get("password");
            password = hash(password);
            writer.write(fname);
            writer.write(",");
            writer.write(lname);
            writer.write(",");
            writer.write(username);
            writer.write(",");
            writer.write(password);
            writer.write("\n");
            writer.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    private String hash(String passwordToHash) //ecrypt the password
    {
        String generatedPassword = null;    //declare the generatedpassword variale
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(passwordToHash.getBytes());
            //Get the hash's bytes
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e) //catch exeptions
        {
            e.printStackTrace(); // prints the stack trace of the Exception
        }
        return(generatedPassword); //return the password to the program
    }
}
