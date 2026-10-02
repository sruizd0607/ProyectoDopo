import java.util.ArrayList;
/**
 * Para resolver el problema de la maratón de SLotMachine
 * 
 * @author Sergio Ruiz - Santiago Rojas
 */
public class SlotMachineContest
{


    /**
     * Constructor for objects of class SlotMachineContest
     */
    public SlotMachineContest()
    {
    }

    /**
     * Indica la secuencia de acciones {i,j} necesarias para ganar(jackpot)
     * usando SlotMachine.
     *
     * @param n entero que indica la cantidad de ruedas y símbolos de la máquina
     */
    public static int[][] solve(int n){
        return solveMachine(new SlotMachine(n),n);
    }

    /**
     * Simula visualmente las acciones necesarias
     * para la solución del problema.
     *
     * @param n entero que indica la cantidad de ruedas y símbolos de la máquina
     */
    public void simulate(int n)
    {
        SlotMachine machine = new SlotMachine(n);
        machine.makeVisible();
        
        solveMachine(machine, n);
        
    }

    /**
     * Indica la secuencia de acciones {i,j} necesarias para ganar(jackpot)
     * usando una slotMachine ya creada. Para el simulate
     *
     * @param machine creada con n ruedas y símbolos. 
     * @param n entero que indica la cantidad de ruedas y símbolos de la máquina
     */
    public static int[][] solveMachine(SlotMachine machine, int n){
     
        ArrayList<int[]> actions = new ArrayList<int[]>();
     
        // Primera parte: Hacer que todas las ruedas muestren símbolos diferentes
    
        for(int wheel = 1; wheel <= n; wheel++){
            int max = machine.distinctSymbols();
            int best = 0;
            for(int position = 1; position < n; position++){
                machine.spin(wheel, 1);     //Avanza un paso
                actions.add(new int[]{wheel, 1});
                int distinct = machine.distinctSymbols();
                if(distinct > max){     //más símbolos distintos
                    max = distinct;
                    best = position;
                }
    
            }
     
            int returnSteps = best - (n - 1);   //Retrocede a la mejor posición.
            if(returnSteps != 0){
                machine.spin(wheel, returnSteps);
                actions.add(new int[]{wheel, returnSteps});
            }
        }
     
     
        //Segunda parte: Encontrar el orden, la rueda que va 1 posición adelante de i
    
        int[] next = new int[n];
        for(int i = 0; i < n; i++){
            next[i] = -1;
            for(int j = 0; j < n && next[i] == -1; j++){    //Para cuando se encuentra
                if(j != i){
                    machine.spin(i + 1, 1);
                    actions.add(new int[]{i + 1, 1});
                    machine.spin(j + 1, -1);
                    actions.add(new int[]{j + 1, -1});
                    if(machine.distinctSymbols() == n){ //Mismos n símbolos = j delante de i
                        next[i] = j;
                    }
                    //restaurar posiciones
                    machine.spin(i + 1, -1);
                    actions.add(new int[]{i + 1, -1});
                    machine.spin(j + 1, 1);
                    actions.add(new int[]{j + 1, 1});
                }
            }
        }
     
     
        //Tercera parte: Alinear usando el ciclo next
    
        int current = 0;
        for(int steps = 1; steps < n; steps++){
            current = next[current];            //k-esima rueda sigu
            machine.spin(current + 1, -steps);  //retrocede k pasos hasta posición rueda 1
            actions.add(new int[]{current + 1, -steps});
    
        }
     
        return actions.toArray(new int[0][]);
    
    }
}