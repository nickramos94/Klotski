import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class BoardParser {

    public BoardParser()
    {}
    public String exportBoard(Piece[] p) {

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
        System.out.println(jBoardString);
        return jBoardString;
    }

    public int[] importBoard(String jBoard){

    }

}
