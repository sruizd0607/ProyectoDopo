

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class SlotMachineAcceptanceC2.
 *
 * Se observa en el canvas las acciones de SlotMachine de lock/unlock, swap de ruedas y la
 * spin de la rueda con animación.
 * 
 * @author  Sergio Ruiz - Santiago Rojas
 * @version (a version number or a date)
 */
public class SlotMachineAcceptanceC2
{
    private static final int PAUSE_MS = 4000;
    private SlotMachine machine;
    
    /**
     * Default constructor for test class SlotMachineAcceptanceC2
     */
    public SlotMachineAcceptanceC2()
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
     * Arma una máquina de 3 ruedas con los colores red/blue/green y la
     * deja en una configuración inicial conocida, visible en el Canvas.
     * @return la máquina ya configurada.
     */
    private SlotMachine buildVisibleMachine()
    {
        SlotMachine m = new SlotMachine();
        m.addWheel(1);
        m.addSymbol(1, "red");
        m.addSymbol(2, "blue");
        m.addSymbol(3, "green");
        m.addWheel(2);
        m.addWheel(3);
        m.makeVisible();
        m.placeSymbol(1, "red");
        m.placeSymbol(2, "blue");
        m.placeSymbol(3, "green");
        return m;
    }
    
    /**
     * Se bloquea una rueda, se intenta girar y falla
     * Se desbloquea y ahora sí gira.
     */
    @Test
    public void acceptance_lockYUnlockDeUnaRueda()
    {
        machine = buildVisibleMachine();
        step("Máquina lista: red, blue, green.");

        machine.lock(1);
        step("Paso 1: se bloqueó la rueda 1 (lock(1)).");

        machine.spin(1);
        step("Paso 2: se intentó girar la rueda 1 bloqueada (spin(1)). Debe fallar y no cambiar.");
        assertFalse(machine.ok());
        assertEquals("red", machine.configuration()[0]);

        machine.unlock(1);
        step("Paso 3: se desbloqueó la rueda 1 (unlock(1)).");

        machine.spin(1, 1);
        step("Paso 4: ahora sí se pudo rotar la rueda 1 un paso (spin(1,1)).");
        assertTrue(machine.ok());
        assertEquals("blue", machine.configuration()[0]);
    }

    /**
     * Se intercambian dos ruedas de posición con swap().
     */
    @Test
    public void acceptance_swapEntreDosRuedas()
    {
        machine = buildVisibleMachine();
        step("Máquina lista: [red, blue, green] en las posiciones 1, 2 y 3.");

        machine.swap(1, 3);
        step("Paso 1: se intercambiaron las ruedas 1 y 3 (swap(1,3)).");

        assertTrue(machine.ok());
        assertArrayEquals(new String[]{"green", "blue", "red"}, machine.configuration());
    }

    /**
     * Gira una rueda varios pasos hacia adelante y luego hacia atrás, 
     * para que se observe la animación paso a paso en el Canvas.
     */
    @Test
    public void acceptance_spinPorStepsConAnimacionVisible()
    {
        machine = buildVisibleMachine();
        step("Máquina lista, rueda 1 mostrando 'red'. Empieza la animación de spin por pasos.");

        machine.spin(1, 2);
        step("Paso 1: spin(1, 2) -> avanza 2 símbolos (red -> blue -> green), " +
             "mostrando cada símbolo intermedio en el Canvas.");
        assertTrue(machine.ok());
        assertEquals("green", machine.configuration()[0]);

        machine.spin(1, -1);
        step("Paso 2: spin(1, -1) -> retrocede 1 símbolo (green -> blue).");
        assertTrue(machine.ok());
        assertEquals("blue", machine.configuration()[0]);
    }

    /**
     * Se deja la máquina en una configuración específica con
     * spin(String[]), respetando las ruedas bloqueadas.
     */
    @Test
    public void acceptance_spinConConfiguracionYRuedaBloqueada()
    {
        machine = buildVisibleMachine();
        step("Máquina lista: [red, blue, green].");

        machine.lock(2);
        step("Paso 1: se bloqueó la rueda 2 (debe conservar 'blue').");

        machine.spin(new String[]{"green", "red", "red"});
        step("Paso 2: spin({green, red, red}) -> la rueda 1 y 3 cambian, la 2 (bloqueada) no.");

        assertTrue(machine.ok());
        assertArrayEquals(new String[]{"green", "blue", "red"}, machine.configuration());
    }
}
