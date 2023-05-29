import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoardParserTest {

    @Test
    void exportBoard() throws JsonProcessingException {
        int[] testPiece = new int[4];
        testPiece[0] = 0;
        testPiece[1] = 0;
        testPiece[2] = 1;
        testPiece[3] = 1;
        List<int[]> piece = new ArrayList<int []>();
        piece.add(testPiece);

        BoardParser b = new BoardParser();
        Board board = new Board(piece);
        String result = "[ {\n" +
                "  \"name\" : \"Game\",\n" +
                "  \"blocks\" : [ {\n" +
                "    \"shape\" : [ 1, 1 ],\n" +
                "    \"position\" : [ 0, 0 ]\n" +
                "  } ]\n" +
                "} ]";
        //Assert.assertEquals(result, b.exportBoard(board.getPieces()));
    }

    @Test
    void importBoard() throws IOException {
        String pathTestJson = "test.json";
        BoardParser bp = new BoardParser();
        Assert.assertEquals(0, bp.importBoard(pathTestJson).get(0)[0]);
        Assert.assertEquals(0, bp.importBoard(pathTestJson).get(0)[1]);
        Assert.assertEquals(1, bp.importBoard(pathTestJson).get(0)[2]);
        Assert.assertEquals(1, bp.importBoard(pathTestJson).get(0)[3]);
    }

    @Test
    void saveState() throws IOException {
        Piece[] testArray = {new Piece(0,0,1,1)};

        BoardParser bp = new BoardParser();
        String path = "saveTest.json";
        try {
            bp.saveState(testArray, path);
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String testResult= "";
            String line;

            while((line = reader.readLine()) != null) {
                testResult = testResult + line;
            }

            Assert.assertEquals("[ {  \"name\" : \"Game\",  \"blocks\" : [ {    \"shape\" : [ 1, 1 ],    \"position\" : [ 0, 0 ]  } ]} ]", testResult);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}