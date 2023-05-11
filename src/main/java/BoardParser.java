import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class BoardParser {

    private static Position[] positions;
    private static void parseBoard(Position[] p) throws IOException {
        positions = p;

        ObjectMapper mapper = new ObjectMapper(); //oggetto del parser json
        Position value;
        String result = null;

        try {
            mapper.writeValue(new File("prova.json"), positions[0]);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
