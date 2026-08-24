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

    /**
     * Construye una rueda vacia
     */
    public Wheel()
    {
        symbols = new ArrayList<Symbol>();
        visibleIndex = -1;
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
        symbols.add(index - 1, new Symbol(color));
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
        if(visibleIndex != -1){
            symbols.get(visibleIndex).makeVisible();
        }
    }
    /**
     * Hace invisible la rueda
     */
    public void makeInvisible()
    {
        for(Symbol symbol : symbols){
            symbol.makeInvisible();
        }
    }
    /**
     * Ubica como visible un simbolo por su color
     * @param color (color del simbolo a mostrar)
     */
    public void placeSymbol(String color)
    {
        int index = findSymbol(color);
        if(index != -1){
            makeInvisible();
            visibleIndex = index;
            symbols.get(visibleIndex).makeVisible();
        }
    }
    /**
     * Gira la rueda seleccionando aleatoriamente un simbolo
     */
    public void spin()
    {
        if(!symbols.isEmpty()){
            makeInvisible();
            Random random = new Random();
            visibleIndex = random.nextInt(symbols.size());
            symbols.get(visibleIndex).makeVisible();
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
}
