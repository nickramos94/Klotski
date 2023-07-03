import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SolverTest {

    @Test
    void sendToSolver() throws MalformedURLException {
        Solver s = new Solver();

        String board = "[ {\n" +
                "  \"name\" : \"Game\",\n" +
                "  \"blocks\" : [ {\n" +
                "    \"shape\" : [ 2, 2 ],\n" +
                "    \"position\" : [ 2, 1 ]\n" +
                "  } ]\n" +
                "} ]";
        Move move = new Move(1,0,0);
        List<Move> result = new ArrayList<Move>();
        result.add(move);

        List<Move> solver = s.sendToSolver(board);

        Assert.assertTrue(result.get(0).toString().equals(s.sendToSolver(board).get(0).toString()));


    }
}