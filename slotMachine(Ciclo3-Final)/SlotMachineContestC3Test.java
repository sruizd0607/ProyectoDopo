import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
 
public class SlotMachineContestC3Test {
 
    @Test

    public void shouldSolveOneWheel(){

        int[][] actions = SlotMachineContest.solve(1);
 
        assertNotNull(actions);

        assertEquals(0, actions.length);

    }
 
    @Test

    public void shouldGenerateSolution(){

        int[][] actions = SlotMachineContest.solve(5);
 
        assertNotNull(actions);

        assertTrue(actions.length > 0);

    }

}
 