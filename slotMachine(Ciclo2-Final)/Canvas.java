import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Canvas is a class to allow for simple graphical drawing on a canvas.
 * This is a modification of the general purpose Canvas, specially made for
 * the BlueJ "shapes" example. 
 *
 * @author: Bruce Quig
 * @author: Michael Kolling (mik)
 *
 * @version: 1.6 (shapes)
 */
public class Canvas{
    // Note: The implementation of this class (specifically the handling of
    // shape identity and colors) is slightly more complex than necessary. This
    // is done on purpose to keep the interface and instance fields of the
    // shape objects in this project clean and simple for educational purposes.

    private static Canvas canvasSingleton;

    /**
     * Factory method to get the canvas singleton object.
     */
    public static Canvas getCanvas(){
        if(canvasSingleton == null) {
            canvasSingleton = new Canvas("BlueJ Shapes Demo", 300, 300, 
                                         Color.white);
        }
        canvasSingleton.setVisible(true);
        return canvasSingleton;
    }

    //  ----- instance part -----

    private JFrame frame;
    private CanvasPane canvas;
    private Graphics2D graphic;
    private Color backgroundColour;
    private Image canvasImage;
    private List <Object> objects;
    private HashMap <Object,ShapeDescription> shapes;
    
    /**
     * Create a Canvas.
     * @param title  title to appear in Canvas Frame
     * @param width  the desired width for the canvas
     * @param height  the desired height for the canvas
     * @param bgClour  the desired background colour of the canvas
     */
    private Canvas(String title, int width, int height, Color bgColour){
        frame = new JFrame();
        canvas = new CanvasPane();
        frame.setContentPane(canvas);
        frame.setTitle(title);
        canvas.setPreferredSize(new Dimension(width, height));
        backgroundColour = bgColour;
        frame.pack();
        objects = new ArrayList <Object>();
        shapes = new HashMap <Object,ShapeDescription>();
    }

    /**
     * Set the canvas visibility and brings canvas to the front of screen
     * when made visible. This method can also be used to bring an already
     * visible canvas to the front of other windows.
     * @param visible  boolean value representing the desired visibility of
     * the canvas (true or false) 
     */
    public void setVisible(boolean visible){
        if(graphic == null) {
            // first time: instantiate the offscreen image and fill it with
            // the background colour
            Dimension size = canvas.getSize();
            canvasImage = canvas.createImage(size.width, size.height);
            graphic = (Graphics2D)canvasImage.getGraphics();
            graphic.setColor(backgroundColour);
            graphic.fillRect(0, 0, size.width, size.height);
            graphic.setColor(Color.black);
        }
        frame.setVisible(visible);
    }

    /**
     * Draw a given shape onto the canvas.
     * @param  referenceObject  an object to define identity for this shape
     * @param  color            the color of the shape
     * @param  shape            the shape object to be drawn on the canvas
     */
     // Note: this is a slightly backwards way of maintaining the shape
     // objects. It is carefully designed to keep the visible shape interfaces
     // in this project clean and simple for educational purposes.
    public void draw(Object referenceObject, String color, Shape shape){
        objects.remove(referenceObject);   // just in case it was already there
        objects.add(referenceObject);      // add at the end
        shapes.put(referenceObject, new ShapeDescription(shape, color));
        redraw();
    }
 
    /**
     * Erase a given shape's from the screen.
     * @param  referenceObject  the shape object to be erased 
     */
    public void erase(Object referenceObject){
        objects.remove(referenceObject);   // just in case it was already there
        shapes.remove(referenceObject);
        redraw();
    }

    /**
     * Set the foreground colour of the Canvas.
     * @param  ColorString  CSS color name for the foreground 
     */
    public void setForegroundColor(String colorString){
        Color color = cssColors.get(colorString == null ? "" : colorString.toLowerCase());
        graphic.setColor(color != null ? color : Color.black);
    }
    /**
     * Tabla nombres de color CSS aceptadors
     * Nombre que no esté en al tabla se cambia a negro.
     */
    private static final Map<String, Color> cssColors = new HashMap<String, Color>();
    static{
        cssColors.put("black", Color.black);
        cssColors.put("white", Color.white);
        cssColors.put("red", Color.red);
        cssColors.put("green", Color.green);
        cssColors.put("blue", Color.blue);
        cssColors.put("yellow", Color.yellow);
        cssColors.put("cyan", Color.cyan);
        cssColors.put("magenta", Color.magenta);
        cssColors.put("gray", Color.gray);
        cssColors.put("grey", Color.gray);
        cssColors.put("darkgray", Color.darkGray);
        cssColors.put("lightgray", Color.lightGray);
        cssColors.put("orange", Color.orange);
        cssColors.put("pink", Color.pink);
        cssColors.put("purple", new Color(128, 0, 128));
        cssColors.put("brown", new Color(165, 42, 42));
        cssColors.put("lime", new Color(0, 255, 0));
        cssColors.put("navy", new Color(0, 0, 128));
        cssColors.put("teal", new Color(0, 128, 128));
        cssColors.put("olive", new Color(128, 128, 0));
        cssColors.put("maroon", new Color(128, 0, 0));
        cssColors.put("silver", new Color(192, 192, 192));
        cssColors.put("gold", new Color(255, 215, 0));
        cssColors.put("coral", new Color(255, 127, 80));
        cssColors.put("salmon", new Color(250, 128, 114));
        cssColors.put("turquoise", new Color(64, 224, 208));
        cssColors.put("violet", new Color(238, 130, 238));
        cssColors.put("indigo", new Color(75, 0, 130));
        cssColors.put("khaki", new Color(240, 230, 140));
        cssColors.put("plum", new Color(221, 160, 221));
        cssColors.put("orchid", new Color(218, 112, 214));
        cssColors.put("crimson", new Color(220, 20, 60));
        cssColors.put("chocolate", new Color(210, 105, 30));
        cssColors.put("tan", new Color(210, 180, 140));
        cssColors.put("beige", new Color(245, 245, 220));
        cssColors.put("ivory", new Color(255, 255, 240));
        cssColors.put("lavender", new Color(230, 230, 250));
        cssColors.put("skyblue", new Color(135, 206, 235));
        cssColors.put("steelblue", new Color(70, 130, 180));
        cssColors.put("forestgreen", new Color(34, 139, 34));
        cssColors.put("darkgreen", new Color(0, 100, 0));
        cssColors.put("darkblue", new Color(0, 0, 139));
        cssColors.put("darkred", new Color(139, 0, 0));
        cssColors.put("hotpink", new Color(255, 105, 180));
        cssColors.put("deeppink", new Color(255, 20, 147));
    
    }

    /**
     * Wait for a specified number of milliseconds before finishing.
     * This provides an easy way to specify a small delay which can be
     * used when producing animations.
     * @param  milliseconds  the number 
     */
    public void wait(int milliseconds){
        try{
            Thread.sleep(milliseconds);
        } catch (Exception e){
            // ignoring exception at the moment
        }
    }

    /**
     * Redraw ell shapes currently on the Canvas.
     */
    private void redraw(){
        erase();
        for(Iterator i=objects.iterator(); i.hasNext(); ) {
                       shapes.get(i.next()).draw(graphic);
        }
        canvas.repaint();
    }
       
    /**
     * Erase the whole canvas. (Does not repaint.)
     */
    private void erase(){
        Color original = graphic.getColor();
        graphic.setColor(backgroundColour);
        Dimension size = canvas.getSize();
        graphic.fill(new java.awt.Rectangle(0, 0, size.width, size.height));
        graphic.setColor(original);
    }


    /************************************************************************
     * Inner class CanvasPane - the actual canvas component contained in the
     * Canvas frame. This is essentially a JPanel with added capability to
     * refresh the image drawn on it.
     */
    private class CanvasPane extends JPanel{
        public void paint(Graphics g){
            g.drawImage(canvasImage, 0, 0, null);
        }
    }
    
    /************************************************************************
     * Inner class CanvasPane - the actual canvas component contained in the
     * Canvas frame. This is essentially a JPanel with added capability to
     * refresh the image drawn on it.
     */
    private class ShapeDescription{
        private Shape shape;
        private String colorString;

        public ShapeDescription(Shape shape, String color){
            this.shape = shape;
            colorString = color;
        }

        public void draw(Graphics2D graphic){
            setForegroundColor(colorString);
            graphic.draw(shape);
            graphic.fill(shape);
        }
    }
    /**
     * Valida si un color es nombre CSS válido.
     * @param color nombre del color a validar
     * @return true si es un color valido.
     */
    public boolean isValidColor(String colorString){
        return colorString != null && cssColors.containsKey(colorString.toLowerCase());
    }

}
