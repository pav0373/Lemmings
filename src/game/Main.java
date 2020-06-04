package game;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.event.EventHandler;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import javafx.scene.input.MouseEvent;
import javafx.animation.*;

public class Main extends Application
{
	public static final int HEIGHT = 550;
	public static final int WIDTH = 500;

	public static final double SPEED = 1;
	public static final int FALLHEIGHT= 120;
	
	public static void restart() throws IOException
	{
		 Runtime.getRuntime().exec("java -jar Lemmings.jar"); 
		 System.exit(0);
		
	}
	
	public static void main(String[] args)
	{
		launch(args);
	}
	
	public void startGame(Stage theStage)
	{
		theStage.setTitle("Lemmings");
		Group root = new Group();
		Scene theScene = new Scene(root,WIDTH,HEIGHT);
		theStage.setScene(theScene);
		Canvas canvas = new Canvas(WIDTH,HEIGHT);
		root.getChildren().add(canvas);
		int selection=1;
		//typ 
		//1 - climber
		//2 - slow fall
		//3 - bomber
		//4 - blocker
		//5 - miner
		//6 - basher
		//7 - finish
		//8 - restart
		//9 - faster spawn
		//10 - slower spawn
		ArrayList<String> maps = new ArrayList<String>();
		maps.add("1");
		maps.add("2");
		
		ChoiceDialog<String> mapSelect = new ChoiceDialog<>("1",maps);
		mapSelect.setTitle("Level Selection");
		mapSelect.setHeaderText(null);
		mapSelect.setContentText("Choose level");
		Optional<String> result = mapSelect.showAndWait();
		if (result.isPresent()){ 
			 selection = Integer.parseInt(result.get());
		}
		else {
			 System.exit(0);
		}
		
		Map Level= new Map();
		Level.GenerateMap(HEIGHT, WIDTH, selection);
		theScene.setFill(Color.BLACK);
		 
		ArrayList<Lemming> Lemmings = Level.getLemmings();
		ArrayList<Block> Dirts = Level.getDirts();
		ArrayList<Butt> Buttons = Level.getButtons();
		
		ArrayList<Lemming> DeadLemmings = Level.getDeadLemmings();
		ArrayList<Lemming> SavedLemmings = Level.getSavedLemmings();
		ArrayList<Block> RemovedDirt= Level.getRemovedDirt();
		
		GraphicsContext gc = canvas.getGraphicsContext2D();
		Font EndText = Font.font( "Helvetica", FontWeight.BOLD, 20 );
		Font ButtonText = Font.font( "Helvetica", FontWeight.LIGHT, 12 );
		ChosenType type = new ChosenType();

		theScene.setOnMouseClicked(
		        new EventHandler<MouseEvent>()
		        {
		        	int t = 0;
		        	Butt Clicked;
		            public void handle(MouseEvent e)
		            {
		                for (Butt Button : Buttons) 
		                {
							if (Button.isInBounds(e.getX(), e.getY()))
							{	
								t = Button.getType();
							}
							if (Button.getType()==type.getType())
								Clicked = Button;
						}

		                if (t==8)
		                {
		                	t=0;
		                	try {
								restart();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
		                }
		                type.setType(t);	        
		                if (t==9)
		                	t=0;       
                		
		                for (Lemming Lemm : Lemmings) {
		                	if (Lemm.isInBounds(e.getX(), e.getY()))
		                	{
		                		if (Lemm.getType()!=4 && Clicked.getClicks()>0 && Lemm.getType()!=type.getType())
		                		{
		                			Lemm.setType(type.getType());
		                			Clicked.setClicks(Clicked.getClicks()-1);
		                		}
		                	}
						}
		            }
		        });
		


		new AnimationTimer()
		{
			private int wait=Level.getSpawnrate();
			private int count=0;
			private int seconds=0;
			private int minutes=0;
			private long lastTime=0;
			public void handle(long currentNanoTime)
			{
			
                if (seconds>=60)
                {
                	seconds=0;
                	minutes++;
                }
 
                if (currentNanoTime > lastTime + 1000000000)
                {
                	seconds++;
                	lastTime = currentNanoTime;
                }

				gc.clearRect(0, 0, WIDTH,HEIGHT);
				Level.render(gc);
				
				if(type.getType()==7 || SavedLemmings.size()+DeadLemmings.size()==Level.getMaxlemmings())
				{ 	
					gc.setFont( EndText );
					gc.setStroke( Color.RED);
					gc.setLineWidth(0.5);
					gc.setFill(Color.RED);
					gc.fillText("Time: "+minutes + ":" +seconds, 100, 290);
					gc.fillText("You saved: "+Integer.toString(SavedLemmings.size())+"/"+Level.getMaxlemmings(), 100, 250);

					
					if(SavedLemmings.size()<Level.getRequiredlemmings())
					{
						gc.fillText("You needed at least: " +Level.getRequiredlemmings(), 100, 270);
					}
					
					else if(SavedLemmings.size()<Level.getMaxlemmings())
					{
						gc.fillText("That is " +Integer.toString(SavedLemmings.size()-Level.getRequiredlemmings()) + " more than required", 100, 270);
					}
					

					else if(SavedLemmings.size()==Level.getMaxlemmings())
					{
						gc.fillText("Congratulations, you saved everyone!", 100, 270);
					}
					gc.setFont(ButtonText);
					this.stop();
				}

                if (type.getType()==9 && Level.getSpawnrate()<=90)
                {
                	Level.setSpawnrate(Level.getSpawnrate()+10);
                	type.setType(0);
                }
                
                if (type.getType()==10 && Level.getSpawnrate()>0)
                {
                	Level.setSpawnrate(Level.getSpawnrate()-10);
                	type.setType(0);
                }
                
                gc.setStroke( Color.BLACK );
				gc.setFill(Color.BLACK);
				gc.setLineWidth(0.2);

				for (Butt Butt : Buttons)
				{

					if(Butt.getType()==type.getType())
					{
						Butt.renderClicked(gc);
					}
					
					switch (Butt.getType()) {
					case 1: gc.fillText("Climb",Butt.getX()+5,Butt.getY()+Butt.getHeight()/2);
							gc.strokeText("Climb",Butt.getX()+5,Butt.getY()+Butt.getHeight()/2);
							break;
					case 2: gc.fillText("Slow fall",Butt.getX()+5,Butt.getY()+Butt.getHeight()/2);
							gc.strokeText("Slow fall",Butt.getX()+5,Butt.getY()+Butt.getHeight()/2);
							break;
					case 3: gc.fillText("Bomb",Butt.getX()+5,Butt.getY()+Butt.getHeight()/2);
							gc.strokeText("Bomb",Butt.getX()+5,Butt.getY()+Butt.getHeight()/2);
							break;
					case 4: gc.fillText("Block",Butt.getX()+5,Butt.getY()+Butt.getHeight()/2);
							gc.strokeText("Block",Butt.getX()+5,Butt.getY()+Butt.getHeight()/2);
							break;
					case 5: gc.fillText("Mine",Butt.getX()+5,Butt.getY()+Butt.getHeight()/2);
							gc.strokeText("Mine",Butt.getX()+5,Butt.getY()+Butt.getHeight()/2);
							break;
					case 6: gc.fillText("Bash",Butt.getX()+5,Butt.getY()+Butt.getHeight()/2);
							gc.strokeText("Bash",Butt.getX()+5,Butt.getY()+Butt.getHeight()/2);
							break;
					case 7: gc.fillText("Finish",Butt.getX()+5,Butt.getY()+Butt.getHeight()/2);
							gc.strokeText("Finish",Butt.getX()+5,Butt.getY()+Butt.getHeight()/2);
							break;
					case 8: gc.fillText("Restart",Butt.getX()+5,Butt.getY()+Butt.getHeight()/2);
							gc.strokeText("Restart",Butt.getX()+5,Butt.getY()+Butt.getHeight()/2);
							break;
					case 9: gc.fillText("+Spawn",Butt.getX()+5,Butt.getY()+Butt.getHeight()/2);
							gc.strokeText("+Spawn",Butt.getX()+5,Butt.getY()+Butt.getHeight()/2);
							break;
					case 10:gc.fillText("-Spawn",Butt.getX()+5,Butt.getY()+Butt.getHeight()/2);
							gc.strokeText("-Spawn",Butt.getX()+5,Butt.getY()+Butt.getHeight()/2);
							break;
					default:
						break;
					}


				}
				if (wait>=100 && count<Level.getMaxlemmings())
				{
				    Lemming Lemming = new Lemming();
				    Lemming.setImage("/Lemming.png");     
				    Lemming.setPosition(Level.getStart().getX(),Level.getStart().getY());
				    Lemming.setVelocity(0, SPEED);
				    Lemmings.add(  Lemming );
				    wait=Level.getSpawnrate();
				    count++;
				}
				wait++;
				
				

				gc.setStroke( Color.RED );
				gc.setLineWidth(1);
				
				for (Lemming Lemm : Lemmings )
				{	
					boolean bot=false;
					boolean left=false;
					boolean right=false;
					
					
					
					Block botBlock = new Block();	
					Block leftBlock = new Block();	
					Block rightBlock = new Block();	
					
					if(Lemm.getType()==0)
					{
						Lemm.setClimb(0);
	
					}
					
					for (Lemming Lem1 : Lemmings) 
					{
						
						if(Lemm.intersects(Lem1) && Lem1.getType()==4 && Lemm!=Lem1)
						{
							Lemm.setVelocity(Lemm.getVelocityX()*(-1), Lemm.getVelocityY());
							if(Lemm.bottomCollision(Lem1))
							Lemm.setPosition(Lem1.getX()+20*Lemm.getDirection(), Lem1.getY());
						}
						
						if(Lemm.intersects(Lem1) && Lemm.getType()==3 && Lemm!=Lem1 && !DeadLemmings.contains(Lem1))
						{
							DeadLemmings.add(Lem1);
						}
						
					}
					
					if(Lemm.getType()==3)
					{
						for (Block Dirt : Dirts) {
							if ((Dirt.getX()>Lemm.getX()-Dirt.getWidth()*2 && Dirt.getX()<Lemm.getX()+Lemm.getWidth()+Dirt.getWidth()*2) &&
								(Dirt.getY()>Lemm.getY()-Dirt.getHeight()*2 && Dirt.getY()<Lemm.getY()+Lemm.getHeight()+Dirt.getHeight()))
							{
								RemovedDirt.add(Dirt);
							}
						}
						Lemm.setType(0);
						if(!DeadLemmings.contains(Lemm))
						DeadLemmings.add(Lemm);
					}
					

					for (Block Dirt : Dirts )
					{
						if(Lemm.intersects(Dirt))
						{
							if(Lemm.leftCollision(Dirt))
							{
								left=true;
								leftBlock= Dirt;
							}

							
							if(Lemm.rightCollision(Dirt))
							{
								right=true;
								rightBlock = Dirt;
							}

						
							if(Lemm.bottomCollision(Dirt))
							{
								bot=true;
								botBlock = Dirt;
							}
							
						}
						
					}
					if(Lemm.getX()<0 || Lemm.getY()<0 || Lemm.getX()+Lemm.getWidth()>WIDTH || Lemm.getY()+Lemm.getHeight()>HEIGHT-Buttons.get(0).getHeight() && !DeadLemmings.contains(Lemm))
					{
						DeadLemmings.add(Lemm);
					}
					
					if(Lemm.intersects(Level.getEnd()) && !SavedLemmings.contains(Lemm))
					{
						SavedLemmings.add(Lemm);
					}
					if(bot)
					{
						if(Lemm.getVelocityY()!=0)
						{
							if(Lemm.getFall()>=FALLHEIGHT && Lemm.getType()!=2 && !DeadLemmings.contains(Lemm))
							{
								Lemm.setType(0);
								DeadLemmings.add(Lemm);
							}
								
							else
							{
								Lemm.setFall(0);
								if(Lemm.getType()==2)
								Lemm.setType(0);
							}
							
						
							if(!left & Lemm.getDirection()<0)
							{
								Lemm.setVelocity(-SPEED, 0);
								Lemm.setClimb(0);
							}

							if(!right & Lemm.getDirection()>0)
							{
								Lemm.setVelocity(SPEED, 0);
								Lemm.setClimb(0);
							}
						}
						
						
						if(Lemm.getVelocityX()==0)
						{
							Lemm.setVelocity(SPEED,0);
							Lemm.setDirection(SPEED);
						}
						else
						{
							Lemm.setVelocity(Lemm.getVelocityX(), 0);
							Lemm.setDirection(Lemm.getVelocityX());
						}
						if(Lemm.getType()==4)
						{
							Lemm.setVelocity(0, 0);
							Lemm.setDirection(0);
						}
						
						if(Lemm.getType()==1)
						{
							if(right)
							{	Lemm.setDirection(SPEED);
								Lemm.setVelocity(0,-SPEED);
							}
							if(left)
							{
								Lemm.setDirection(-SPEED);
								Lemm.setVelocity(0, -SPEED);
							}
						}
						
						if(Lemm.getType()==5)
						{
							RemovedDirt.add(botBlock);
							Lemm.setType(0);
						}
						

					}
					if(!bot)
					{
						if(Lemm.getType()==2)
						{
							Lemm.setVelocity(0, SPEED/2);
							Lemm.setFall(0);
						}
						else if(Lemm.getType()==1)
						{
							if(right)
							{	

								if(Lemm.getClimb()>40)
								{
								Lemm.setClimb(0);
								Lemm.setType(0);

								}
								else {
								Lemm.setVelocity(0,-SPEED);
								Lemm.increaseClimb();
								}
							}

							if(left)
							{

								if(Lemm.getClimb()>40)
								{
								Lemm.setClimb(0);
								Lemm.setType(0);

								}
								else {
								Lemm.setVelocity(0,-SPEED);
								Lemm.increaseClimb();
								}
							}
		
							if(Lemm.getClimb()>0 && Lemm.getClimb()<=40)
							{	
								
								if(!left & Lemm.getDirection()<0)
								{
									Lemm.setVelocity(-SPEED, 0);
									Lemm.setType(0);
									Lemm.setClimb(0);
								}

								if(!right & Lemm.getDirection()>0)
								{
									Lemm.setVelocity(SPEED, 0);
									Lemm.setType(0);
									Lemm.setClimb(0);
								}
								
							}
							
							if(Lemm.getClimb()==0)
							{	
								
								if(!left & Lemm.getDirection()<0)
								{
									Lemm.setVelocity(SPEED, 0);
									Lemm.setType(0);
									Lemm.setClimb(0);
								}

								else if(!right & Lemm.getDirection()>0)
								{
									Lemm.setVelocity(-SPEED, 0);
									Lemm.setType(0);
									Lemm.setClimb(0);
								}
								
								else
								{
									Lemm.setVelocity(Lemm.getVelocityX(), SPEED);
									Lemm.increaseFall();

								}
							}
							
						}
						else
						{
							Lemm.setVelocity(0, SPEED);
							Lemm.increaseFall();
						}
								
					}
					if(left)
					{
						if(Lemm.getType()==6 && bot)
						{
							RemovedDirt.add(leftBlock);
							Lemm.setType(0);
						}
						else if(Lemm.getVelocityX()>=0)
						{
							Lemm.setVelocity(Lemm.getVelocityX()*-1, Lemm.getVelocityY());
						}

					}
				
					if(right)
					{
						if(Lemm.getType()==6 && bot)
						{
							RemovedDirt.add(rightBlock);
							Lemm.setType(0);
						}
						else if(Lemm.getVelocityX()<=0)
						{
							Lemm.setVelocity(Lemm.getVelocityX()*-1, Lemm.getVelocityY());
						}

					}
					
					Lemm.update(1);
				}
				
				Lemmings.removeAll(DeadLemmings);
				Lemmings.removeAll(SavedLemmings);
				Dirts.removeAll(RemovedDirt);
				
				gc.setStroke( Color.RED );
				gc.setFill(Color.RED);
				gc.setLineWidth(0.2);
				
				gc.fillText("Spawnrate:" + Level.getSpawnrate(), 400, 40);
				gc.strokeText("Spawnrate:" + Level.getSpawnrate(), 400, 40);
				gc.fillText("Alive: "+Integer.toString(Lemmings.size())+"/"+Level.getMaxlemmings(), 400, 60);
				gc.strokeText("Alive: "+Integer.toString(Lemmings.size())+"/"+Level.getMaxlemmings(), 400, 60);
				gc.fillText("Saved: "+Integer.toString(SavedLemmings.size())+"/"+Level.getRequiredlemmings(), 400, 80);
				gc.strokeText("Saved: "+Integer.toString(SavedLemmings.size())+"/"+Level.getRequiredlemmings(), 400, 80);
				gc.fillText("Time: "+minutes+":"+seconds, 400, 100);
				gc.strokeText("Time: "+minutes+":"+seconds, 400, 100);
				
			}
		}.start();
		
		theStage.show();
	}

	@Override
	public void start(Stage theStage) {
		startGame(theStage);
		
	}
	
}
