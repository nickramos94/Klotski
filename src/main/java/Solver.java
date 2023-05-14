import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

//Classe che invia la configurazione della scacchiera al solver e restituisce le mosse successive
public class Solver {
    private static URL url;

    public static void sendToSolver(String file) throws MalformedURLException {
        try {
            URL url = new URL("http://16.16.110.46/");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            byte[] out = file.getBytes(StandardCharsets.UTF_8);
            OutputStream stream = con.getOutputStream();
            stream.write(out);
            System.out.println(con.getResponseCode() + " " + con.getResponseMessage()); // THis is optional
            con.disconnect();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
