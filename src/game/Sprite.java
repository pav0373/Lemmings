package game;
import javafx.scene.image.Image;
import javafx.scene.canvas.GraphicsContext;
import javafx.geometry.Rectangle2D;

public interface Sprite
{

    
    public double getX();
    
    public double getY();
    
    public double getHeight();
    
    public double getWidth();
     
    public void setImage(Image i);

    public void setImage(String filename);

    public void setPosition(double x, double y);

    public void render(GraphicsContext gc);
    
    public Rectangle2D getBoundary();

    public boolean intersects(Sprite s);
    
    public boolean bottomCollision(Sprite s);
    
    public boolean topCollision(Sprite s);
    
    public boolean leftCollision(Sprite s);
    
    public boolean rightCollision(Sprite s);
    
    public String toString();
    
    public boolean isInBounds(double x,double y);
   
}