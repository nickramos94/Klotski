import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

//Classe che invia la configurazione della scacchiera al solver e restituisce le mosse successive
public class Solver {
    private static URL url;

    public Solver() {};

    public static void sendToSolver(String file) throws MalformedURLException {
        String resp = null;
        try {
            URL url = new URL("http://16.171.59.73/");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);
            try(OutputStream os = con.getOutputStream()) {
                byte[] input = file.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            if(con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                try (InputStream inputStream = con.getInputStream()) {
                    StringBuilder response = new StringBuilder();
                    try(BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                        String line;
                        while((line = reader.readLine()) != null) {
                            response.append(line);
                        }
                    }
                    resp = response.toString();
                }
            }
            System.out.println(con.getResponseCode() + " " + con.getResponseMessage());
            System.out.println(resp);
            con.disconnect();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
