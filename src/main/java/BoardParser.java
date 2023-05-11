import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class BoardParser {

//    private class PieceParse { //Classe che converte un piece standard in un piece adatto per
//        private int[] shape = new int[2];
//        private int[] position = new int[2];
//
//        private PieceParse(){}
//
//        //private static void parsePiece();
//
//    }

    private static Piece[] pieces;
    public BoardParser()
    {}
    public static void parseBoard(Piece[] p) throws IOException {
        pieces = p;

        ObjectMapper mapper = new ObjectMapper(); //oggetto del parser json
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
            jBlock.putArray("shape").add(properties[2]).add(properties[3]);
            jBlock.putArray("position").add(properties[0]).add(properties[1]);
        }

        String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(root);
        System.out.println(jsonString);



//        for(int i = 0; i < pieces.length; i++){ //Creo una lista delle posizioni contenute nella board
//            pieceList.add(pieces[i]);
//        }
//
//        try {
//            mapper.writeValue(new File("prova.json"), pieceList); //Converto le proprietá di ogni posizione in un file json
//        } catch (IOException e) {
//            throw new RuntimeException(e); //
//        }

    }
}
