import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class BoardParser {

    private static Piece[] pieces;
    public BoardParser()
    {}
    public static void parseBoard(Piece[] p) throws IOException {
        pieces = p;

        //Oggetti della libreria Jackson per serializzare gli attributi dei pezzi in un JSON
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode root = mapper.createArrayNode();
        ObjectNode jBoard = root.addObject();
        jBoard.put("name", "Game");

        ArrayNode jblocks = jBoard.putArray("blocks");

        ObjectNode jBlock;

        Position value;
        String result = null;
        int[] properties = new int[4];
        List<int[]> pieceList = new LinkedList<>();

        for(int i = 0; i < pieces.length; i++){
            properties = pieces[i].getProperties().clone();
            pieceList.add(properties);
            jBlock = jblocks.addObject();
            jBlock.putArray("shape").add(properties[3]).add(properties[2]);
            jBlock.putArray("position").add(properties[1]).add(properties[0]);
        }

        String jBoardString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(root);
        System.out.println(jBoardString);


    }
}
