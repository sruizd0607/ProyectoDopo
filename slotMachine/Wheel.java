import java.util.Random;
import java.util.ArrayList;

/**
 * Representa una rueda de la maquina tragamonedas.
 * Una rueda puede contener varios simbolos y seleccionar
 * uno aleatoriamente cuando gira.
 */
public class Wheel
{
    private ArrayList<Symbol> symbols;
    private int visibleIndex;
    private int columnMovement;
    private boolean isVisible;

    /**
     * Construye una rueda vacia
     */
    public Wheel()
    {
        symbols = new ArrayList<Symbol>();
        visibleIndex = -1;
        columnMovement = 0;
        isVisible = false;
    }
    /**
     * Adiciona un simbolo a la rueda
     * @param index (donde se adicionara el simbolo)
     * @param color (color del simbolo)
     */
    public void addSymbol(int index, String color)
    {
        if(index < 1){
            index = 1;}
        if(index > symbols.size() + 1){
            index = symbols.size() + 1;}
            
        Symbol symbol = new Symbol(color);
        if(columnMovement != 0){
            symbol.moveHorizontal(columnMovement);
        }
        symbols.add(index - 1, symbol);
    }
    /**
     * Elimina un simbolo de acuerdo al color
     * @param color (color del simbolo a eliminar)
     */
    public void delSymbol(String color)
    {
        int index = findSymbol(color);
        if(index != -1){
            symbols.get(index).makeInvisible();
            symbols.remove(index);
            if(visibleIndex == index){
                visibleIndex = -1;
            }
            else if(visibleIndex > index){
                visibleIndex--;
            }
        }
    }
    /**
     * Hace visible la rueda
     */
    public void makeVisible()
    {
        isVisible = true;
        if(visibleIndex != -1){
            symbols.get(visibleIndex).makeVisible();
        }
    }
    /**
     * Hace invisible la rueda
     */
    public void makeInvisible()
    {
        hideSymbols();
        isVisible = false;
    }
    /**
     * Ubica como visible un simbolo por su color
     * @param color (color del simbolo a mostrar)
     */
    public void placeSymbol(String color)
    {
        int index = findSymbol(color);
        if(index != -1){
            hideSymbols();
            visibleIndex = index;
            if(isVisible){
            symbols.get(visibleIndex).makeVisible();
            }
        }
    }
    /**
     * Gira la rueda seleccionando aleatoriamente un simbolo
     */
    public void spin()
    {
        if(!symbols.isEmpty()){
            hideSymbols();
            Random random = new Random();
            visibleIndex = random.nextInt(symbols.size());
            if(isVisible){
            symbols.get(visibleIndex).makeVisible();
            }
        }
    }
    /**
     * Retorna simbolo actualmente visible
     * @return simbolo visible
     */
    public Symbol getVisibleSymbol()
    {
        Symbol answer = null;
        if(visibleIndex != -1){
            answer = symbols.get(visibleIndex);
        }
        return answer;
    }
    /**
     * Retorna los simbolos de la rueda
     * @return lista de simbolos
     */
    public ArrayList<Symbol> getSymbols()
    {
        return symbols;
    }
    /**
     * Busca un simbolo por su color
     * @param color (color que se desea buscar)
     * @return posicion del smbolo o -1 si no existe
     */
    private int findSymbol(String color)
    {
        int answer = -1;
        int index = 0;
        while(index < symbols.size() && answer == -1){
            if(symbols.get(index).getColor().equals(color)){
                answer = index;
            }
            index++;
        }
        return answer;
    }
    /**
     * Borra del canvas el símbolo dibujado sin necesidad de esconder la rueda
     * 
     */
    private void hideSymbols(){
        for(Symbol symbol : symbols){
            symbol.makeInvisible();
        }
    }
    /**
     * Para ubicar la rueda completa en una columna del canvas cuando
     * se cambia el número de ruedas.
     * @param offset posición horizontal en la que se debe ubicar la rueda
     */
    public void setColumn(int offset){
        int delta = offset - columnMovement;    //Cuanto falta moverse
        if(delta != 0){
            for(Symbol symbol:symbols){
                symbol.moveHorizontal(delta);
            }
            columnMovement = offset;            //Actualizamos donde quedamos
        }
    }
}
