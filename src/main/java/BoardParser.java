import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class BoardParser {

    private static Piece[] pieces;
    public BoardParser()
    {}
    public static void parseBoard(Piece[] p) throws IOException {
        pieces = p;

        ObjectMapper mapper = new ObjectMapper(); //oggetto del parser json
        Position value;
        String result = null;
        List<Piece> pieceList = new LinkedList<>();

        for(int i = 0; i < pieces.length; i++){ //Creo una lista delle posizioni contenute nella board
            pieceList.add(pieces[i]);
        }

        try {
            mapper.writeValue(new File("prova.json"), pieceList); //Converto le proprietá di ogni posizione in un file json
        } catch (IOException e) {
            throw new RuntimeException(e); //
        }

    }
}
