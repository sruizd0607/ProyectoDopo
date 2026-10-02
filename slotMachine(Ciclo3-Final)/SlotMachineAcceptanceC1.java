

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class SlotMachineAcceptanceC1.
 * Para observar como responde SlotMachine. Se realizan las acciones:
 * Crear/eliminar ruedas y simbolos, girar las ruedas, ubicar símbolos manualmente.
 *
 * @author  Sergio Ruiz-Santiago Rojas
 * @version (a version number or a date)
 */
public class SlotMachineAcceptanceC1
{
    private static final int PAUSE_MS = 4000;
    private SlotMachine machine;
    /**
     * Default constructor for test class SlotMachineAcceptanceC1
     */
    public SlotMachineAcceptanceC1()
    {
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @AfterEach
    public void tearDown()
    {
        if(machine != null){
            machine.exit();
        }
    }
    
    /**
     * Espera una cantidad de milisegundos para poder observar lo que
     * realiza la máquina.
     * @param message texto que describe el siguiente paso que hará la máquina
     */
    private void step(String message)
    {
        System.out.println(message);
        try{
            Thread.sleep(PAUSE_MS);
        } catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }
    
     /**
     * Se arma una máquina de 3 ruedas con 3 símbolos, se deja visible.
     * Se verifica que cada rueda queda correctamente ubicada.
     */
    @Test
    public void acceptance_construirYMostrarLaMaquina()
    {
        machine = new SlotMachine();
        machine.makeVisible();
        step("Paso 1: máquina creada y visible, todavía sin ruedas.");

        machine.addWheel(1);
        step("Paso 2: se agregó la rueda 1.");

        machine.addSymbol(1, "red");
        step("Paso 3: se agregó el símbolo rojo a la rueda 1.");

        machine.addSymbol(2, "blue");
        step("Paso 4: se agregó el símbolo azul a la rueda 1.");

        machine.addWheel(2);
        step("Paso 5: se agregó la rueda 2 (hereda los símbolos rojo y azul).");

        machine.addWheel(3);
        step("Paso 6: se agregó la rueda 3. La máquina queda con 3 ruedas.");

        assertTrue(machine.ok());
        assertEquals(3, machine.configuration().length);
        assertArrayEquals(new String[]{"red", "blue"}, machine.symbols());
    }

    /**
     * Se ubica manualmente un símbolo en cada rueda con
     * placeSymbol, mostrando el cambio inmediato en el Canvas.
     * Muestra también que funciona el jackpot.
     */
    @Test
    public void acceptance_ubicarSimbolosManualmente(){
        machine = new SlotMachine();
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.addWheel(2);
        machine.makeVisible();
        step("Máquina de 2 ruedas lista, sin símbolo visible todavía.");

        machine.placeSymbol(1, "red");
        step("Paso 1: se ubicó 'red' en la rueda 1.");

        machine.placeSymbol(2, "blue");
        step("Paso 2: se ubicó 'blue' en la rueda 2. Colores distintos: sin Jackpot.");

        assertTrue(machine.ok());
        assertArrayEquals(new String[]{"red", "blue"}, machine.configuration());
        assertFalse(machine.isJackpot());

        machine.placeSymbol(2, "red");
        step("Paso 3: se cambió la rueda 2 a 'red'. Ahora ambas ruedas coinciden: Jackpot.");

        assertTrue(machine.isJackpot());
    }
    /**
     * Se gira una sola rueda (spin(wheel)) y luego todas las
     * ruedas a la vez (spin()), verificando que la máquina resuelve
     * correctamente el Jackpot en cada caso.
     */
    @Test
    public void acceptance_girarUnaRuedaYTodasLasRuedas()
    {
        machine = new SlotMachine();
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.addWheel(2);
        machine.addWheel(3);
        machine.makeVisible();
        step("Máquina de 3 ruedas lista, con un único color disponible: 'red'.");

        machine.spin(1);
        step("Paso 1: se giró solo la rueda 1 (spin(1)).");
        assertTrue(machine.ok());

        machine.spin();
        step("Paso 2: se giraron todas las ruedas a la vez (spin()). " +
             "Como solo hay un color, el resultado debe ser Jackpot.");

        assertTrue(machine.ok());
        assertTrue(machine.isJackpot());
    }
    /**
     * Se elimina un símbolo y luego una rueda, mostrando
     * cómo la máquina se reacomoda automáticamente en el Canvas.
     */
    @Test
    public void acceptance_eliminarSimboloYRueda()
    {
        machine = new SlotMachine();
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.addSymbol(3, "green");
        machine.addWheel(2);
        machine.addWheel(3);
        machine.makeVisible();
        step("Máquina de 3 ruedas con 3 colores (red, blue, green).");

        machine.delSymbol("green");
        step("Paso 1: se eliminó el símbolo 'green' de todas las ruedas.");
        assertTrue(machine.ok());
        assertEquals(2, machine.distinctSymbols());

        machine.delWheel(2);
        step("Paso 2: se eliminó la rueda 2. Las ruedas restantes se reacomodan.");
        assertTrue(machine.ok());
        assertEquals(2, machine.configuration().length);

        machine.makeInvisible();
        step("Paso 3: se ocultó la máquina (makeInvisible).");
    }  
    
}