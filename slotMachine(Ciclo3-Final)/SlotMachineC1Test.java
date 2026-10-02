
import static org.junit.jupiter.api.Assertions.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Pruebas de Unidad del Ciclo 1 para SlotMachine.
 * Prueba los métodos de SlotMachine de gestión de ruedas y símbolos
 * Spin de ruedas y visibilidad.
 */
public class SlotMachineC1Test
{
    private SlotMachine machine;
    
    @Before
    public void setUp()
    {
        machine = new SlotMachine();
    }


    // ---------- addWheel ----------

    @Test
    public void addWheelAddsANewWheel()
    {
        machine.addWheel(1);
        assertTrue(machine.ok());
        assertEquals(1, machine.configuration().length);
    }

    @Test
    public void addWheelCopiesSymbolsFromExistingWheels()
    {
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.addWheel(2);
        assertTrue(machine.ok());
        assertArrayEquals(new String[]{"red", "blue"}, machine.symbols());
    }

    // ---------- delWheel ----------

    @Test
    public void delWheelRemovesAnExistingWheel()
    {
        machine.addWheel(1);
        machine.addWheel(2);
        machine.delWheel(1);
        assertTrue(machine.ok());
        assertEquals(1, machine.configuration().length);
    }

    @Test
    public void delWheelInvalidPositionFails()
    {
        machine.addWheel(1);
        machine.delWheel(5);
        assertFalse(machine.ok());
        assertEquals(1, machine.configuration().length);
    }

    // ---------- addSymbol ----------

    @Test
    public void addSymbolAddsColorToAllWheels()
    {
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addSymbol(1, "red");
        assertTrue(machine.ok());
        assertArrayEquals(new String[]{"red"}, machine.symbols());
    }

    @Test
    public void addSymbolInvalidCssColorFails()
    {
        machine.addWheel(1);
        machine.addSymbol(1, "notacolor");
        assertFalse(machine.ok());
    }

    @Test
    public void addSymbolDuplicateColorFails()
    {
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.addSymbol(1, "red");
        assertFalse(machine.ok());
        assertEquals(1, machine.distinctSymbols());
    }

    // ---------- delSymbol ----------

    @Test
    public void delSymbolRemovesColorFromAllWheels()
    {
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.delSymbol("red");
        assertTrue(machine.ok());
        assertArrayEquals(new String[]{"blue"}, machine.symbols());
    }

    // ---------- placeSymbol ----------

    @Test
    public void placeSymbolShowsRequestedColor()
    {
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.placeSymbol(1, "blue");
        assertTrue(machine.ok());
        assertEquals("blue", machine.configuration()[0]);
    }

    @Test
    public void placeSymbolColorNotInWheelFails()
    {
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.placeSymbol(1, "blue");
        assertFalse(machine.ok());
    }

    // ---------- spin(wheel) / spin() ----------

    @Test
    public void spinOneWheelChangesItsVisibleSymbol()
    {
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.spin(1);
        assertTrue(machine.ok());
        assertEquals("red", machine.configuration()[0]);
    }

    @Test
    public void spinAllWheelsSetsAllVisibleSymbols()
    {
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addSymbol(1, "red");
        machine.spin();
        assertTrue(machine.ok());
        assertArrayEquals(new String[]{"red", "red"}, machine.configuration());
    }

    // ---------- distinctSymbols() ----------

    @Test
    public void distinctSymbolsCountsUniqueColors()
    {
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.addSymbol(3, "green");
        assertEquals(3, machine.distinctSymbols());
    }

    // ---------- isJackpot() ----------

    @Test
    public void isJackpotTrueWhenAllWheelsMatch()
    {
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addSymbol(1, "red");
        machine.placeSymbol(1, "red");
        machine.placeSymbol(2, "red");
        assertTrue(machine.isJackpot());
    }

    @Test
    public void isJackpotFalseWhenWheelsDiffer()
    {
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.placeSymbol(1, "red");
        machine.placeSymbol(2, "blue");
        assertFalse(machine.isJackpot());
    }

}