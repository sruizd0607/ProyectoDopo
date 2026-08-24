/**
 * Representa un simbolo de la maquina tragamonedas.
 */
public class Symbol
{
    private String color;
    private Circle figure;
    
    /**
     * Construye un simbolo.
     * @param color del simbolo.
     */
    public Symbol(String color)
    {
        this.color = color;
        figure = new Circle();
        figure.changeColor(color);
        figure.changeSize(40);
    }
    /**
     * Obtiene el color del simbolo
     */
    public String getColor()
    {
        return color;
    }
    /**
     * Hace visible el simbolo
     */
    public void makeVisible()
    {
        figure.makeVisible();
    }
    /**
     * Hace invisible el simbolo
     */
    public void makeInvisible()
    {
        figure.makeInvisible();
    }

}