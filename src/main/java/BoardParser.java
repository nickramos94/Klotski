import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BoardParser {

    public BoardParser()
    {}
    public String exportBoard(Piece[] p) throws JsonProcessingException {

        //Oggetti della libreria Jackson per serializzare gli attributi dei pezzi in un JSON
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode root = mapper.createArrayNode();
        ObjectNode jBoard = root.addObject();
        jBoard.put("name", "Game");

        ArrayNode jblocks = jBoard.putArray("blocks");

        ObjectNode jBlock;

        int[] properties = new int[4];
        List<int[]> pieceList = new LinkedList<>();

        //Costruzione del JSON
        for(int i = 0; i < p.length; i++){
            properties = p[i].getProperties().clone();
            pieceList.add(properties);
            jBlock = jblocks.addObject();
            jBlock.putArray("shape").add(properties[3]).add(properties[2]);
            jBlock.putArray("position").add(properties[1]).add(properties[0]);
        }

        String jBoardString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(root);
        //System.out.println(jBoardString);
        return jBoardString;
    }

    //Metodo che genera un array di Pieces a partire da un file JSON
    public List<int[]> importBoard(String fileName) throws IOException {

        List<int[]> pieces = new ArrayList<int[]>();

        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, Object>> jsonList = objectMapper.readValue(new File(fileName), List.class);

        //Lettura del file JSON
        for (Map<String, Object> jsonMap : jsonList) {
            String name = (String) jsonMap.get("name");
            List<Map<String, Object>> blocks = (List<Map<String, Object>>) jsonMap.get("blocks");

            //Lettura dei pezzi
            for (Map<String, Object> block : blocks) {
                int[] jTemp = new int[4];

                List<Integer> shape = (List<Integer>) block.get("shape");
                List<Integer> position = (List<Integer>) block.get("position");

                jTemp[3] = shape.get(0);
                jTemp[2] = shape.get(1);
                jTemp[1] = position.get(0);
                jTemp[0] = position.get(1);

                pieces.add(jTemp);
            }
        }

        return pieces;
    }

    public void saveState(Piece[] p) throws IOException {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("save.json"));
            writer.write(exportBoard(p));
            writer.close();
            System.out.println("\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
