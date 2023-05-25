import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SolverTest {

    @Test
    void sendToSolver() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("solverTest.json"));
        String testFile = "";
        String line;

        while((line = reader.readLine()) != null)
        {
            testFile = testFile + line;
        }

        Solver solver = new Solver();
        List<Move> testMoveList = new ArrayList<Move>();
        testMoveList = solver.sendToSolver(testFile);

        Assert.assertEquals(1, testMoveList.get(0).getStep());
        Assert.assertEquals(9, testMoveList.get(0).getBlockIdx());
        Assert.assertEquals(57, testMoveList.get(testMoveList.size()-1).getStep());
        Assert.assertEquals(0, testMoveList.get(testMoveList.size()-1).getBlockIdx());
    }
}