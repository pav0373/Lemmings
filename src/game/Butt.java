package game;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;


public class Butt implements Sprite{
	private Image image;
    private double positionX;
    private double positionY;    
    private double width;
    private double height;
    private int type;
    private int clicks;

    public Butt()
    {
        positionX = 0;
        positionY = 0;   
        type=0;
        clicks=0;
    }

    
    public double getX()
    {
    	return this.positionX;
    }
    
    public double getY()
    {
    	return this.positionY;
    }
    
    public double getHeight()
    {
    	return this.height;
    }
    
    public double getWidth()
    {
    	return this.width;
    }
    
    public void setImage(Image i)
    {
        image = i;
        width = i.getWidth();
        height = i.getHeight();
    }

    public void setImage(String filename)
    {
        Image i = new Image(filename);
        setImage(i);
    }

    public void setPosition(double x, double y)
    {
        positionX = x;
        positionY = y;
    }

    public void render(GraphicsContext gc)
    {
        gc.drawImage( image, positionX, positionY );
        if(this.type<7)
        {
		gc.fillText(Integer.toString(this.clicks), positionX+10, positionY+height-10);
		gc.strokeText(Integer.toString(this.clicks), positionX+10, positionY+height-10);
        }
    }
    
    public void renderClicked(GraphicsContext gc)
    {
		gc.fillText("X", positionX+width/2, positionY+height-10);
		gc.strokeText("X", positionX+width/2, positionY+height-10);
    }

    public Rectangle2D getBoundary()
    {
        return new Rectangle2D(positionX,positionY,width,height);
    }

    public boolean intersects(Sprite s)
    {
        return s.getBoundary().intersects( this.getBoundary() );
    }
    
    public boolean bottomCollision(Sprite s)
    {
    	double player_bot=this.positionY+this.height;
    	double block_bot=s.getY()+s.getHeight();
     	double pright=this.positionX+this.width;
    	double bright=s.getX()+s.getWidth();
    	
    	double b_collision = block_bot - this.getY();
    	double t_collision = player_bot - s.getY();
    	double l_collision = pright - s.getX();
    	double r_collision = bright - this.getX();
    	
    	return (t_collision < b_collision && t_collision < l_collision && t_collision < r_collision );
   
    }
    
    public boolean topCollision(Sprite s)
    {
    	double player_bot=this.positionY+this.height;
    	double block_bot=s.getY()+s.getHeight();
     	double pright=this.positionX+this.width;
    	double bright=s.getX()+s.getWidth();
    	
    	double b_collision = block_bot - this.getY();
    	double t_collision = player_bot - s.getY();
    	double l_collision = pright - s.getX();
    	double r_collision = bright - this.getX();
    	
    	return (b_collision < t_collision && b_collision < l_collision && b_collision < r_collision);
   
    }
    
    public boolean leftCollision(Sprite s)
    {
    	double player_bot=this.positionY+this.height;
    	double block_bot=s.getY()+s.getHeight();
     	double pright=this.positionX+this.width;
    	double bright=s.getX()+s.getWidth();
    	
    	double b_collision = block_bot - this.getY();
    	double t_collision = player_bot - s.getY();
    	double l_collision = pright - s.getX();
    	double r_collision = bright - this.getX();
    	
    	return (l_collision < r_collision && l_collision < t_collision && l_collision < b_collision);
   
    }
    
    public boolean rightCollision(Sprite s)
    {
    	double player_bot=this.positionY+this.height;
    	double block_bot=s.getY()+s.getHeight();
     	double pright=this.positionX+this.width;
    	double bright=s.getX()+s.getWidth();
    	
    	double b_collision = block_bot - this.getY();
    	double t_collision = player_bot - s.getY();
    	double l_collision = pright - s.getX();
    	double r_collision = bright - this.getX();
    	
    	return (r_collision < l_collision && r_collision < t_collision && r_collision < b_collision );
   
    }
    
    public boolean isInBounds(double x,double y)
    {
    	if ((this.positionX<=x &&  this.positionX+this.width>=x) && (this.positionY<=y && this.positionY+this.height>=y))
    		return true;
    	else return false;
    }
    
    public int getType()
    {
    	return this.type;
    }
    
    public void setType(int t)
    {
    	this.type=t;
    }
    
    public int getClicks()
    {
    	return this.clicks;
    }
    
    public void setClicks(int c)
    {
    	this.clicks=c;
    }
  
}
