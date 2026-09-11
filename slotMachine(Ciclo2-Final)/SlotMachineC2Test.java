import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Pruebas de unidad del Ciclo 2 para SlotMachine.
 */
public class SlotMachineC2Test
{
    private SlotMachine machine;

    @Before
    public void setUp()
    {
        machine = new SlotMachine();
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.addWheel(2);
        machine.addWheel(3);
        machine.placeSymbol(1, "red");
        machine.placeSymbol(2, "blue");
        machine.placeSymbol(3, "red");
    }

    // ---------- swap ----------

    @Test
    public void swapExchangesSymbols()
    {
        machine.swap(1, 2);
        assertTrue(machine.ok());
        assertArrayEquals(new String[]{"blue", "red", "red"}, machine.configuration());
    }

    @Test
    public void swapInvalidPositionFails()
    {
        machine.swap(1, 5);
        assertFalse(machine.ok());
        assertArrayEquals(new String[]{"red", "blue", "red"}, machine.configuration());
    }

    // ---------- lock / unlock ----------

    @Test
    public void lockedWheelDoesNotSpin()
    {
        machine.lock(1);
        machine.spin(1);
        assertFalse(machine.ok());
        assertEquals("red", machine.configuration()[0]);
    }

    @Test
    public void unlockedWheelSpins()
    {
        machine.lock(1);
        machine.unlock(1);
        machine.spin(1);
        assertTrue(machine.ok());
    }

    @Test
    public void lockInvalidPositionFails()
    {
        machine.lock(9);
        assertFalse(machine.ok());
    }

    // ---------- spin(wheel, steps) ----------

    @Test
    public void spinStepsAdvancesPosition()
    {
        machine.spin(1, 1);
        assertTrue(machine.ok());
        assertEquals("blue", machine.configuration()[0]);
    }

    @Test
    public void spinStepsLockedWheelFails()
    {
        machine.lock(2);
        machine.spin(2, 1);
        assertFalse(machine.ok());
        assertEquals("blue", machine.configuration()[1]);
    }

    // ---------- spin(setSymbols) ----------

    @Test
    public void spinConfigSetsConfiguration()
    {
        machine.spin(new String[]{"blue", "blue", "blue"});
        assertTrue(machine.ok());
        assertArrayEquals(new String[]{"blue", "blue", "blue"}, machine.configuration());
    }

    @Test
    public void spinConfigWrongLengthFails()
    {
        machine.spin(new String[]{"blue", "blue"});
        assertFalse(machine.ok());
        assertArrayEquals(new String[]{"red", "blue", "red"}, machine.configuration());
    }

    @Test
    public void spinConfigInvalidColorFails()
    {
        machine.spin(new String[]{"blue", "green", "red"});
        assertFalse(machine.ok());
        assertArrayEquals(new String[]{"red", "blue", "red"}, machine.configuration());
    }

    @Test
    public void spinConfigSkipsLockedWheel()
    {
        machine.lock(1);
        machine.spin(new String[]{"blue", "red", "blue"});
        assertTrue(machine.ok());
        assertArrayEquals(new String[]{"red", "red", "blue"}, machine.configuration());
    }
}
