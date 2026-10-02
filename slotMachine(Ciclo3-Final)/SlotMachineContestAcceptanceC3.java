import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
 
/**
* The test class SlotMachineContestAcceptanceC3.
* @author  (your name)
* @version (a version number or a date)
*/
public class SlotMachineContestAcceptanceC3 {
 
    @Test
    public void acceptanceSolve(){
 
        int[][] actions = SlotMachineContest.solve(5);
 
        System.out.println("Acciones generadas:");
 
        for(int i = 0; i < actions.length; i++){
 
            System.out.println(
                "Rueda: " + actions[i][0] +
                " Pasos: " + actions[i][1]
            );
        }
    }
    @Test
    public void acceptanceSimulate(){
        SlotMachineContest contest = new SlotMachineContest();
        contest.simulate(5);
    }
}