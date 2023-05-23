import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//Classe che invia la configurazione della scacchiera al solver e restituisce le mosse successive
public class Solver {
    private static URL url;

    public Solver() {};

    //Manda il JSON delle posizioni dei pezzi sulla scacchiera al server esterno e riceve la lista delle mosse per vincere la partita
    public static List<Move> sendToSolver(String file) throws MalformedURLException {

        String resp = null;
        try {
            URL url = new URL("http://13.48.196.51/");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            //Manda il JSON al server
            try(OutputStream os = con.getOutputStream()) {
                byte[] input = file.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            //Riceve in risposta il JSON con le mosse
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
            System.out.println(resp);

            List<Move> moves;

            ObjectMapper objectMapper = new ObjectMapper();
            moves = objectMapper.readValue(resp, new TypeReference<List<Move>>() {
            });


            System.out.println(con.getResponseCode() + " " + con.getResponseMessage());

            con.disconnect();

            return moves;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
