package game;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Map {
	

	private ArrayList<Block> Dirts;
	private ArrayList<Butt> Buttons;
	private ArrayList<Lemming> Lemmings;
	
	private Block Start;
	private Block End;
	
	private int maxlemmings;
	private int requiredlemmings;
	private int spawnrate;

	ArrayList<Lemming> DeadLemmings;
	ArrayList<Lemming> SavedLemmings;
	
	ArrayList<Block> RemovedDirt;
	
	public Map()
	{

		this.spawnrate=0;
		this.maxlemmings=0;
		this.requiredlemmings=0;

		Dirts = new ArrayList<Block>();
		Buttons = new ArrayList<Butt>();
		
		Lemmings =  new ArrayList<Lemming>();
		
		DeadLemmings = new ArrayList<Lemming>();
		SavedLemmings = new ArrayList<Lemming>();
		
		RemovedDirt = new ArrayList<Block>();
		
		Start = new Block();
		End = new Block();
	}
	
	public void TopBorder(int mapheight, int mapwidth)
	{
		double width;
		double px=0,py=0;
		
		Block Dirt = new Block();
		Dirt.setImage("/Dirt.png");
		

		width=Dirt.getWidth();
		
		for(int i=0;i<mapwidth/width;i++)
		{
			boolean contains = false;
			
			
			Dirt.setPosition(px,py);
			for (Block sprite : Dirts) {
				if (sprite.getBoundary()==Dirt.getBoundary())
					contains=true;
			}
			
			if (!contains)
				Dirts.add( Dirt );	
	
			px+=width;
			
			Dirt = new Block();
			Dirt.setImage("/Dirt.png");
			
		}
	}
	
	public void BottomBorder(int mapheight, int mapwidth)
	{
		double width,height;
		
		
		Block Dirt = new Block();
		Dirt.setImage("/Dirt.png");
		

		width=Dirt.getWidth();
		height=Dirt.getHeight();
		
				
		double px=0,py=mapheight-height-50;
		
		for(int i=0;i<mapwidth/width;i++)
		{
			boolean contains = false;
			
			
			Dirt.setPosition(px,py);
			for (Block sprite : Dirts) {
				if (sprite.getBoundary()==Dirt.getBoundary())
					contains=true;
			}
			
			if (!contains)
				Dirts.add( Dirt );	
	
			px+=width;
			
			Dirt = new Block();
			Dirt.setImage("/Dirt.png");
			
		}
	}

	public void LeftBorder(int mapheight, int mapwidth)
	{
		double height;
		double px=0,py=0;
		
		Block Dirt = new Block();
		Dirt.setImage("/Dirt.png");
		
		height=Dirt.getHeight();

		
		for(int i=0;i<(mapheight-50)/height;i++)
		{
			boolean contains = false;
			
			
			Dirt.setPosition(px,py);
			for (Block sprite : Dirts) {
				if (sprite.getBoundary()==Dirt.getBoundary())
					contains=true;
			}
			
			if (!contains)
				Dirts.add( Dirt );	
	
			py+=height;
			
			Dirt = new Block();
			Dirt.setImage("/Dirt.png");
			
		}
	}

	public void RightBorder(int mapheight, int mapwidth)
	{
		double height,width;
	
		
		Block Dirt = new Block();
		Dirt.setImage("/Dirt.png");
		
		height=Dirt.getHeight();
		width=Dirt.getWidth();
		
		double px=mapwidth-width,py=0;
		for(int i=0;i<(mapheight-50)/height;i++)
		{
			boolean contains = false;
			
			
			Dirt.setPosition(px,py);
			for (Block sprite : Dirts) {
				if (sprite.getBoundary()==Dirt.getBoundary())
					contains=true;
			}
			
			if (!contains)
				Dirts.add( Dirt );	
	
			py+=height;
			
			Dirt = new Block();
			Dirt.setImage("/Dirt.png");
			
		}
	}

	public void GenerateMap(int mapheight, int mapwidth,int selected)
	{

		Lemmings.clear();
		Buttons.clear();
		Dirts.clear();
		SavedLemmings.clear();
		DeadLemmings.clear();
		RemovedDirt.clear();
		
		
	this.TopBorder(mapheight, mapwidth);
	this.BottomBorder(mapheight, mapwidth);
	this.LeftBorder(mapheight, mapwidth);
	this.RightBorder(mapheight, mapwidth);
	
	switch (selected) {
	case 1:
		this.Map1(mapheight,mapwidth);
		break;

	case 2:
		this.Map2(mapheight, mapwidth);
		break;
	}
	
	
	}
	
	public void Map1(int mapheight, int mapwidth)
	{
		double width,height;
		double px,py;
		 

		maxlemmings=10;
		requiredlemmings=5;
		Start.setImage("/Start.png");
		Start.setPosition(250, 40);
		End.setImage("/Finish.png");
		End.setPosition(450, 450);
		
		Block Dirt = new Block();
		Dirt.setImage("/Dirt.png");
		

		width=Dirt.getWidth();
		
		px=0;
		py=80; 
		
		for(int i=0;i<mapwidth/width-5;i++)
		{
			boolean contains = false;
			if (i < 5)
			{
				px+=width;
				continue;
			}
			Dirt.setPosition(px,py);
			for (Block sprite : Dirts) {
				if (sprite.getBoundary()==Dirt.getBoundary())
					contains=true;
			}
			
			if (!contains)
				Dirts.add( Dirt );	
	
			px+=width;
			
			Dirt = new Block();
			Dirt.setImage("/Dirt.png");
			
		}
		
		px=0;
		py=120; 
		
		for(int i=0;i<mapwidth/width;i++)
		{
			boolean contains = false;
			if (i < 3)
			{
				px+=width;
				continue;
			}
			Dirt.setPosition(px,py);
			for (Block sprite : Dirts) {
				if (sprite.getBoundary()==Dirt.getBoundary())
					contains=true;
			}
			
			if (!contains)
				Dirts.add( Dirt );	
	
			px+=width;
			
			Dirt = new Block();
			Dirt.setImage("/Dirt.png");
			
		}
		
		px=0;
		py=160; 
		
		for(int i=0;i<mapwidth/width-2;i++)
		{
			boolean contains = false;

			Dirt.setPosition(px,py);
			for (Block sprite : Dirts) {
				if (sprite.getBoundary()==Dirt.getBoundary())
					contains=true;
			}
			
			if (!contains)
				Dirts.add( Dirt );	
	
			px+=width;
			
			Dirt = new Block();
			Dirt.setImage("/Dirt.png");
			
		}
		px=0;
		py=200; 
		
		for(int i=0;i<mapwidth/width;i++)
		{
			boolean contains = false;

			if (i < 4)
			{
				px+=width;
				continue;
			}
			Dirt.setPosition(px,py);
			for (Block sprite : Dirts) {
				if (sprite.getBoundary()==Dirt.getBoundary())
					contains=true;
			}
			
			if (!contains)
				Dirts.add( Dirt );	
	
			px+=width;
			
			Dirt = new Block();
			Dirt.setImage("/Dirt.png");
			
		}
		px=0;
		py=240; 
		
		for(int i=0;i<mapwidth/width-2;i++)
		{
			boolean contains = false;

			Dirt.setPosition(px,py);
			for (Block sprite : Dirts) {
				if (sprite.getBoundary()==Dirt.getBoundary())
					contains=true;
			}
			
			if (!contains)
				Dirts.add( Dirt );	
	
			px+=width;
			
			Dirt = new Block();
			Dirt.setImage("/Dirt.png");
			
		}
		px=0;
		py=280; 
		
		for(int i=0;i<mapwidth/width-2;i++)
		{
			boolean contains = false;

			Dirt.setPosition(px,py);
			for (Block sprite : Dirts) {
				if (sprite.getBoundary()==Dirt.getBoundary())
					contains=true;
			}
			
			if (!contains)
				Dirts.add( Dirt );	
	
			px+=width;
			
			Dirt = new Block();
			Dirt.setImage("/Dirt.png");
			
		}
		px=0;
		py=320; 
		
		for(int i=0;i<mapwidth/width;i++)
		{
			boolean contains = false;
			
			
			Dirt.setPosition(px,py);
			for (Block sprite : Dirts) {
				if (sprite.getBoundary()==Dirt.getBoundary())
					contains=true;
			}
			
			if (!contains)
				Dirts.add( Dirt );	
	
			px+=width;
			
			Dirt = new Block();
			Dirt.setImage("/Dirt.png");
			
		}

		
		px=0;
		py=400; 
		
		for(int i=0;i<mapwidth/width;i++)
		{
			boolean contains = false;
			
			
			Dirt.setPosition(px,py);
			for (Block sprite : Dirts) {
				if (sprite.getBoundary()==Dirt.getBoundary())
					contains=true;
			}
			
			if (!contains)
				Dirts.add( Dirt );	
	
			px+=width;
			
			Dirt = new Block();
			Dirt.setImage("/Dirt.png");
			
		}
		
		px=100;
		py=220; 

		boolean contains = false;
			
		
		Dirt.setPosition(px,py);
		for (Block sprite : Dirts) {
			if (sprite.getBoundary()==Dirt.getBoundary())
				contains=true;
		}
			
		if (!contains)
			Dirts.add( Dirt );	
	
		px+=width;
			
		Dirt = new Block();
		Dirt.setImage("/Dirt.png");
			
		
		px=0;
		for(int i=1;i<=10;i++)
		{
			Butt Button= new Butt();
			Button.setImage("/Button.png");
			height = Button.getHeight();
			width = Button.getWidth();
			
			py=mapheight-height;
			Button.setType(i);
			Button.setPosition(px,py);
			px+=width;
			Button.setClicks(5);
		/*
			switch (i) {

				case 4:
					Button.setClicks(3);
					break;
				case 5:
					Button.setClicks(2);
					break;
				case 6:
					break;
				default:
					Button.setClicks(0);
					break;
			}*/
			Buttons.add( Button );	
			
		}
		
		
	}
	
	public void Map2(int mapheight, int mapwidth)
	{
		double width,height;
		double px,py;
		 

		maxlemmings=30;
		requiredlemmings=25;
		Start.setImage("/Start.png");
		Start.setPosition(250, 40);
		End.setImage("/Finish.png");
		End.setPosition(450, 450);
		
		Block Dirt = new Block();
		Dirt.setImage("/Dirt.png");
		

		width=Dirt.getWidth();
		
		px=0;
		py=80; 
		
		for(int i=0;i<mapwidth/width-5;i++)
		{
			boolean contains = false;
			if (i < 5)
			{
				px+=width;
				continue;
			}
			Dirt.setPosition(px,py);
			for (Block sprite : Dirts) {
				if (sprite.getBoundary()==Dirt.getBoundary())
					contains=true;
			}
			
			if (!contains)
				Dirts.add( Dirt );	
	
			px+=width;
			
			Dirt = new Block();
			Dirt.setImage("/Dirt.png");
			
		}
		
		
		px=0;
		py=120; 
		
		for(int i=0;i<mapwidth/width-8;i++)
		{
			boolean contains = false;
			
			if (i == 5 || i<2)
			{
				px+=width;
				continue;
			}
			
			Dirt.setPosition(px,py);
			for (Block sprite : Dirts) {
				if (sprite.getBoundary()==Dirt.getBoundary())
					contains=true;
			}
			
			if (!contains)
				Dirts.add( Dirt );	
	
			px+=width;
			
			Dirt = new Block();
			Dirt.setImage("/Dirt.png");
			
		}
		
		px=0;
		py=200; 
		
		for(int i=0;i<mapwidth/width-2;i++)
		{
			boolean contains = false;
			if (i < 5)
			{
				px+=width;
				continue;
			}
			Dirt.setPosition(px,py);
			for (Block sprite : Dirts) {
				if (sprite.getBoundary()==Dirt.getBoundary())
					contains=true;
			}
			
			if (!contains)
				Dirts.add( Dirt );	
	
			px+=width;
			
			Dirt = new Block();
			Dirt.setImage("/Dirt.png");
			
		}
		
		
		px=0;
		py=260; 
		
		for(int i=0;i<mapwidth/width;i++)
		{
			boolean contains = false;
			
			
			Dirt.setPosition(px,py);
			for (Block sprite : Dirts) {
				if (sprite.getBoundary()==Dirt.getBoundary())
					contains=true;
			}
			
			if (!contains)
				Dirts.add( Dirt );	
	
			px+=width;
			
			Dirt = new Block();
			Dirt.setImage("/Dirt.png");
			
		}
		
		px=0;
		py=350; 
		
		for(int i=0;i<mapwidth/width;i++)
		{
			boolean contains = false;
			
			
			Dirt.setPosition(px,py);
			for (Block sprite : Dirts) {
				if (sprite.getBoundary()==Dirt.getBoundary())
					contains=true;
			}
			
			if (!contains)
				Dirts.add( Dirt );	
	
			px+=width;
			
			Dirt = new Block();
			Dirt.setImage("/Dirt.png");
			
		}
		
		px=0;
		py=400; 
		
		for(int i=0;i<mapwidth/width;i++)
		{
			boolean contains = false;
			
			
			Dirt.setPosition(px,py);
			for (Block sprite : Dirts) {
				if (sprite.getBoundary()==Dirt.getBoundary())
					contains=true;
			}
			
			if (!contains)
				Dirts.add( Dirt );	
	
			px+=width;
			
			Dirt = new Block();
			Dirt.setImage("/Dirt.png");
			
		}
		
		px=0;
		for(int i=1;i<=10;i++)
		{
			Butt Button= new Butt();
			Button.setImage("/Button.png");
			height = Button.getHeight();
			width = Button.getWidth();
			
			py=mapheight-height;
			Button.setType(i);
			Button.setPosition(px,py);
			px+=width;
			switch (i) {

			case 3:
				Button.setClicks(3);
				break;
			case 4:
				Button.setClicks(2);
				break;
			case 6:
				break;
			default:
				Button.setClicks(0);
				break;
			}
			Buttons.add( Button );	
			
		}
		
		
	}
	
	public ArrayList<Block> getDirts() 
	{
		return Dirts;
	}
	

	public ArrayList<Block> getRemovedDirt() 
	{
		return RemovedDirt;
	}
	
	public ArrayList<Butt> getButtons()
	{
		return Buttons;
	}
	
	public ArrayList<Lemming> getLemmings()
	{
		return Lemmings;
	}
	
	public ArrayList<Lemming> getSavedLemmings()
	{
		return SavedLemmings;
	}
	
	public ArrayList<Lemming> getDeadLemmings()
	{
		return DeadLemmings;
	}
	
	public int getSpawnrate()
	{
		return spawnrate;
	}
	
	public void setSpawnrate(int spawn)
	{
		this.spawnrate=spawn;
	}
	
	public int getMaxlemmings()
	{
		return maxlemmings;
	}
	
	public void setMaxlemmings(int max)
	{
		this.maxlemmings=max;
	}
	
	public int getRequiredlemmings()
	{
		return requiredlemmings;
	}
	
	public void setRequiredlemmings(int req)
	{
		this.requiredlemmings=req;
	}
	
    public void render(GraphicsContext gc)
    {
    	for (Block Dirt : Dirts) {
    		Dirt.render(gc);
       }
    	Start.render(gc);
    	End.render(gc);

    	Font ButtonText = Font.font( "Helvetica", FontWeight.LIGHT, 12 );
    	gc.setFont(ButtonText);
    	gc.setStroke( Color.BLACK );
		gc.setFill(Color.BLACK);
		gc.setLineWidth(0.2);
    	for (Butt Button : Buttons) {
			Button.render(gc);
		}
    	

		gc.setStroke( Color.RED );
		gc.setFill(Color.RED);
		gc.setLineWidth(0.2);
		
    	for (Lemming Lemm : Lemmings) {
			Lemm.render(gc);

		}
    }
   

    public Block getStart()
    {
    	return this.Start;
    }
    
    public Block getEnd()
    {
    	return this.End;
    }


}

