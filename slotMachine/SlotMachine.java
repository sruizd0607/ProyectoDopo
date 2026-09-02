import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JOptionPane;
/**
 * SlotMachine: Proyecto Inicial DOPO
 * Máquina que permite simular una máquina tragamonedas
 * Se pueden añadir ruedas y simbolos
 * Se puede girar y sacar 'Jackpot'
 * @version 26/08/2026
 */
public class SlotMachine
{
    private static final int SEPARATION = 60;
    private static final String NORMAL_COLOR = "gray";
    private static final String WIN_COLOR = "gold";

    private ArrayList<Wheel> wheels;
    private Rectangle frame;
    private boolean visible;
    private boolean ok;
    /**
     * Constructor for objects of class SlotMachine
     */
    public SlotMachine()
    {
        // initialise instance variables
        wheels = new ArrayList<Wheel>();
        visible = false;
        ok = true;
        frame = new Rectangle();
        frame.moveHorizontal(-70);
        frame.moveVertical(-15);
        frame.changeColor(NORMAL_COLOR);
        
        
    }

    /**
     * Se agrega una rueda en una posición deseada.
     * Reposiciona automaticamente las ruedas para pintar adecuadamente.
     * @param pos Posición deseada de la rueda.
     */
    public void addWheel(int pos)
    {
        if(pos < 1){
            pos = 1;
        }
        if(pos > wheels.size() + 1){
            pos = wheels.size() + 1;
        }
        wheels.add(pos-1, new Wheel());
        repositionWheels();
        ok = true;
    }
    /**
     * Borra la rueda de una posición específica y reposiciona
     * automaticamente las ruedas restantes de la máquina.
     * Si no se da posición correcta, hay error.
     * @param pos posición de la rueda a borrar.
     */
    public void delWheel(int pos){
        if(pos >= 1 && pos <= wheels.size()){
            wheels.get(pos-1).makeInvisible();
            wheels.remove(pos-1);
            repositionWheels();
            ok = true;
        }
        else {
            ok = false;     //No se hace operacion, no hay rueda en la posicion
        }
    }
    /**
     * Adiciona un nuevo símbolo de color específico a las ruedas, 
     * verifica que este color no esté entre la lista y que si hayan
     * ruedas creadas
     * @param pos   posición en la que se inserta el símbolo
     * @param color color del nuevo símbolo
     */
    public void addSymbol(int pos, String color){
        if(wheels.isEmpty()){
            ok = false;     //no hay ruedas para adicionar simbolos
        }
        else if(colorInUse(color)){
            ok = false;     //Ya existe un simbolo de ese color
        }
        else{
            for(Wheel w : wheels){
                w.addSymbol(pos,color);
            }
            ok = true;
        }
    }
    /**
     * Elimina el símbolo del color indicado de todas las ruedas.
     * No se hace la operación si ese color no existe.
     * @param color color del símbolo que se quiere eliminar.
     */
    public void delSymbol(String color){
        if(colorInUse(color)){
            for(Wheel w : wheels){
                w.delSymbol(color);
            }
            ok = true;
            refreshVisual();
        }
        else{
            ok = false;     //No existe simbolo de ese color para eliminar
        }
    }
    /**
     * Establece manualmente el símbolo visible en una rueda específica
     * dado el color. Automaticamente muestra ese nuevo símbolo en el
     * canvas.
     * @param wheel La posición de la rueda donde va a estar el símbolo
     * @param symbol Color del símbolo que quedará visible.
     */
    public void placeSymbol(int wheel, String symbol)
    {
        if(wheel >= 1 && wheel <= wheels.size() && wheelHasColor(wheel, symbol)){
            wheels.get(wheel - 1).placeSymbol(symbol);
            ok = true;
            refreshVisual();
        }
        else{
            ok = false;  //No existe la rueda o no tiene ese color
            showMessage("No es posible ubicar el simbolo " + symbol + " en la rueda " + wheel);
        }
    }
    /**
     * Gira solo una rueda de toda la máquina para obtener un nuevo
     * símbolo aleatorio.
     * @param wheel Posición de la rueda a girar
     */
    public void spin(int wheel)
    {
        if(wheel >= 1 && wheel <= wheels.size()){
            wheels.get(wheel - 1).spin();
            ok = true;
            refreshVisual();
        }
        else{
            ok = false;
            showMessage("No existe una rueda en la posicion " + wheel);
        }
    }
    /**
     * Gira todas las ruedas de la máquina a la vez para obtener nuevos
     * símbolos visibles. Se juega y se revisa si se obtuvo jackpot.
     */
    public void spin()
    {
        for(Wheel wheel : wheels){
            wheel.spin();
        }
        ok = true;
        refreshVisual();
    }
    /**
     * Consulta los colores de los símbolos que hay en la máquina.
     * Toma como referencia la primera rueda disponible.
     * @return answer Arreglo con los nombres de los colores que se
     * tienen en la máquina.
     */
    public String[] symbols()
    {
        String[] answer = new String[0];
        if(!wheels.isEmpty()){
            ArrayList<Symbol> list = wheels.get(0).getSymbols();
            answer = new String[list.size()];
            for(int i = 0; i < list.size(); i++){
                answer[i] = list.get(i).getColor();
            }
        }
        return answer;
    }
    /**
     * Calcula el numero de colores distintos que tiene la máquina
     * @return int numero de colores 
     */
    public int distinctSymbols()
    {
        Set<String> distinct = new HashSet<String>();
        for(String color : symbols()){
            distinct.add(color);
        }
        return distinct.size();
    }
    /**
     * Obtiene los colores de los símbolos visibles en las ruedas de
     * la máquina. (Ordenados de izq a der)
     * @return answer Arreglo de colores de símbolos visibles
     */
    public String[] configuration()
    {
        String[] answer = new String[wheels.size()];
        for(int i = 0; i < wheels.size(); i++){
            Symbol shown = wheels.get(i).getVisibleSymbol();
            answer[i] = (shown == null) ? null : shown.getColor();
        }
        return answer;
    }
    /**
     * Determina si todas las ruedas tienen el mismo color de símbolo
     * visible. En este caso, cambia el color de la máquina
     * @return win Booleano que indica si todas las ruedas coinciden
     * con el mismo color visible.
     */
    public boolean isJackpot()
    {
        String[] config = configuration();
        boolean win = config.length > 0;
        for(String color : config){
            if(color == null || !color.equals(config[0])){
                win = false;
            }
        }
        return win;
    }
    /**
     * Hace visible todos los componentes de la máquina en el Canvas.
     */
        public void makeVisible()
    {
        visible = true;
        frame.makeVisible();
        for(Wheel wheel : wheels){
            wheel.makeVisible();
        }
        refreshVisual();
        ok = true;
    }
    /**
     * Hace invisible la máquina en su totalidad.
     */
    public void makeInvisible()
    {
        visible = false;
        frame.makeInvisible();
        for(Wheel wheel : wheels){
            wheel.makeInvisible();
        }
        ok = true;
    }
    /**
     * Sale de la máquina basicamente ocultandola.
     */
    public void exit()
    {
        makeInvisible();
        ok = true;
    }
    /**
     * Indica si la ultima operacion se ejecutó correctamente
     */
    public boolean ok()
    {
        return ok;
    }
    /**
     * Método para recalcular la posición horizontal que debe tener cada
     * rueda según la separación definida. También ajusta el marco de la
     * máquina. Usado cuando el numero de ruedas cambia.
     */
        private void repositionWheels()
    {
        for(int i = 0; i < wheels.size(); i++){
            wheels.get(i).setColumn(i * SEPARATION);
        }
        frame.changeSize(60, wheels.size() * SEPARATION + 20);
    }
    /**
     * Revisa si existe jackpot (las ruedas muestran el mismo símbolo)
     * Dependiendo de esto, cambia el color del frame de la máquina
     * entre el color ganador y el color normal
     */
    private void refreshVisual()
    {
        if(visible){
            frame.changeColor(isJackpot() ? WIN_COLOR : NORMAL_COLOR);
            for(Wheel wheel : wheels){
                wheel.makeVisible();
            }
        }
    }
    /**
     * Muestra un mensaje de error si se realiza una operación errada
     * exclusivamente is la máquina es visible.
     * @param message Mensaje a mostrar.
     */
    private void showMessage(String message)
    {
        if(visible){
            JOptionPane.showMessageDialog(null, message);
        }
    }
    /**
     * Verifica si el color indicado ya se encuentra como símbolo de la
     * máquina.
     * @param color Color que se quiere verificar en la lista.
     * @return found Booleano que indica si el color ya estaba o no.
     */
    private boolean colorInUse(String color)
    {
        boolean found = false;
        if(!wheels.isEmpty()){
            for(Symbol symbol : wheels.get(0).getSymbols()){
                if(symbol.getColor().equals(color)){
                    found = true;
                }
            }
        }
        return found;
    }
    /**
     * Verifica si una rueda específica tiene un color dado.
     * Usado a la hora de intentar usar placeSymbol().
     * @param wheel Posición de la rueda que se verifica.
     * @param color Color que se quiere buscar en la rueda.
     */
    private boolean wheelHasColor(int wheel, String color)
    {
        boolean found = false;
        for(Symbol symbol : wheels.get(wheel - 1).getSymbols()){
            if(symbol.getColor().equals(color)){
                found = true;
            }
        }
        return found;
    }
}