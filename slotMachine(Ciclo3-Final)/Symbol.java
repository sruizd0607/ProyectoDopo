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
    /**
     * Desplaza horizontalmente el símbolo en el canvas.
     * Para ubicar las ruedas en columnas distintas
     * @param distance desplazamiento horizontal
     */
    public void moveHorizontal(int distance){
        figure.moveHorizontal(distance);
    }
    /**
     * Verifica que el color es un nombre de CSS válido
     * @param color a verificar
     * @return true si es un color CSS reconocido
     */
    public static boolean isValidColor(String color){
        return Canvas.getCanvas().isValidColor(color);
    }
    
    /**
     * Muestra el símbolo por pasos simulando una animación, mientras
     * realiza una pausa después de mostrarlo.
     * @param milliseconds tiempo de espera tras mostrar el símbolo
     */
    public void showStep(int milliseconds){
        figure.makeVisible();
        Canvas.getCanvas().wait(milliseconds);
    }
}