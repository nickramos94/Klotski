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

//Class that sends the board's configuration to the solver and return the next moves
public class Solver {
    private static URL url;

    public Solver() {};

    //Sends the positions' JSON of the pieces on the board to the external server and receiver the moves list needed to win the match
    public static List<Move> sendToSolver(String file) throws MalformedURLException {

        String resp = null;
        try {
            URL url = new URL("http://13.53.240.231/");
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

            //Receives as a repsonde the moves' JSON
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

            List<Move> moves;

            //Reads the moves' JSON and stores them in a list
            ObjectMapper objectMapper = new ObjectMapper();
            moves = objectMapper.readValue(resp, new TypeReference<List<Move>>() {
            });


            //System.out.println(con.getResponseCode() + " " + con.getResponseMessage());

            con.disconnect();

            return moves;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
