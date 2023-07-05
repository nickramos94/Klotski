import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * BoardParser handles the conversion of the board's configuration to and from a JSON file format.
 * It also saves and loads the board from the filesystem.
 */
public class BoardParser {

    public BoardParser()
    {}

    /**
     * Turns the board in a JSON string
     * @param p an array containing the board's pieces
     * @param moves number of moves that have been done so far
     * @return a string containing the JSON of the board
     * @throws JsonProcessingException
     */
    //
    public String exportBoard(Piece[] p, int moves) throws JsonProcessingException {

        //Objects of the Jackson library that serializes the pieces' attributes in a JSON
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode root = mapper.createArrayNode();
        ObjectNode jBoard = root.addObject();
        jBoard.put("name", "Game");

        ArrayNode jblocks = jBoard.putArray("blocks");

        ObjectNode jBlock;

        int[] properties = new int[4];
        List<int[]> pieceList = new LinkedList<>();

        //JSON buildup
        for(int i = 0; (i < p.length) && p[i]!=null; i++){
            properties = p[i].getProperties().clone();
            pieceList.add(properties);
            jBlock = jblocks.addObject();
            jBlock.putArray("shape").add(properties[3]).add(properties[2]);
            jBlock.putArray("position").add(properties[1]).add(properties[0]);
        }

        jBoard.put("moves", moves); //Inserts into the JSON the number of moves that have been done so far


        String jBoardString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(root);
        return jBoardString;
    }

    /**
     * Generates an array of Pieces from a JSON file
     * @param fileName name of the JSON file to import
     * @return pieces a list of pieces on the board
     * @throws IOException
     */
    public List<int[]> importBoard(String fileName) throws IOException {

        List<int[]> pieces = new ArrayList<int[]>();
        int[] moves = new int[1];

        InputStream input = null;
        List<Map<String, Object>> jsonList = null;

        ObjectMapper objectMapper = new ObjectMapper();
        if(fileName.equals(Game.SAVE_FILE)) {
            jsonList = objectMapper.readValue(new File(fileName), List.class);
        }
        else {
            // search the file in the root of the classpath
            input = getClass().getResourceAsStream("/" + fileName);
            jsonList = objectMapper.readValue(input, List.class);
        }

        //Reads the JSON file
        for (Map<String, Object> jsonMap : jsonList) {
            String name = (String) jsonMap.get("name");
            moves[0] = (int) jsonMap.get("moves");
            pieces.add(moves);
            List<Map<String, Object>> blocks = (List<Map<String, Object>>) jsonMap.get("blocks");

            //Reads the pieces
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

    /**
     * Saves the positions of the board's pieces in a JSON file
     * @param p an array containing the board's pieces
     * @param file file name
     * @param moves number of moves that have been made so far
     * @throws IOException
     */

    public void saveState(Piece[] p, String file, int moves) throws IOException {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(exportBoard(p, moves));
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
