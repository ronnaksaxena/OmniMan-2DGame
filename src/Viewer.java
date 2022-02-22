import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import util.GameObject;


/*
 * Created by Abraham Campbell on 15/01/2020.
 *   Copyright (c) 2020  Abraham Campbell

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

   (MIT LICENSE ) e.g do what you want with this :-) 

 * Credits: Kelly Charles (2020)
 */ 
public class Viewer extends JPanel {
	private long CurrentAnimationTime= 0; 

	Model gameworld =new Model();
	Controller controller = Controller.getInstance();

	public Viewer(Model World) {
		this.gameworld=World;
		// TODO Auto-generated constructor stub
	}

	public Viewer(LayoutManager layout) {
		super(layout);
		// TODO Auto-generated constructor stub
	}

	public Viewer(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	public Viewer(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	public void updateview() {

		this.repaint();
		// TODO Auto-generated method stub

	}
	
	public long getCurrentAnimationTime() {
		return CurrentAnimationTime;
	}
	
	//fn to load image
	BufferedImage LoadImage (String FileName) {
		BufferedImage img = null;
		
		try {
			img = ImageIO.read(new File(FileName));
			
		}
		catch (IOException e) {
			System.out.println("Can't load Bufferred iamge :(");
		}
		return img;
	}
	



	public void paintComponent(Graphics g) {

		super.paintComponent(g); 
		CurrentAnimationTime++; // runs animation time step 


		
		//Draw background 
		drawBackground(g);
		//Draw Health Status
		//drawHealth(int x, int y, int width, int height, String borderTexture, String amtTexture,Graphics g)
		drawHealth(30, 40, 200, 50, "res/healthBar.png", "res/healthAmt.png", g);

		/*
		 *  Want to draw bullets inbetween player and gun
		 * 
		 * 
		 */
		
		
		
		//Player GameObject attributes 
		int x = (int) gameworld.getPlayer().getCentre().getX();
		int y = (int) gameworld.getPlayer().getCentre().getY();
		int width = (int) gameworld.getPlayer().getWidth();
		int height = (int) gameworld.getPlayer().getHeight();
		String texture = gameworld.getPlayer().getTexture();

		//Gun Object attributes
		int gunX = (int) gameworld.getGun().getCentre().getX();
		int gunY = (int) gameworld.getGun().getCentre().getY();
		double gunScaleX = (double) gameworld.getGun().getScaleX();
		double gunScaleY = (double) gameworld.getGun().getScaleY();
		String gunTexture = gameworld.getGun().getTexture();
		double gunAngle = gameworld.getGun().getAngle();
		
		
		//Draws gun first if player is moving up
		if (gameworld.getPlayer().getDirection() == 1) {
			drawGun(gunX, gunY, gunScaleX, gunScaleY, gunTexture, gunAngle, g);
			
			//Draw Bullets
			// Params: (int x, int y, double scaleX, double scaleY, String texture, double angle, Graphics g)
			gameworld.getBullets().forEach((temp) -> 
			{ 
				drawBullet((int) temp.getCentre().getX(), (int) temp.getCentre().getY(), (double) temp.getScaleX(), (double) temp.getScaleY(), temp.getTexture(), temp.getAngle(), g);	 
			});
			
			drawPlayer(x, y, width, height, texture,g);
		}
		else {
			drawPlayer(x, y, width, height, texture,g);
			
			drawGun(gunX, gunY, gunScaleX, gunScaleY, gunTexture, gunAngle, g);
			
			//Draw Bullets
			// Params: (int x, int y, double scaleX, double scaleY, String texture, double angle, Graphics g)
			gameworld.getBullets().forEach((temp) -> 
			{ 
				drawBullet((int) temp.getCentre().getX(), (int) temp.getCentre().getY(), (double) temp.getScaleX(), (double) temp.getScaleY(), temp.getTexture(), temp.getAngle(), g);	 
			});
		}


		 

		
		//Draw Enemies   
		gameworld.getEnemies().forEach((temp) -> 
		{
			drawEnemies((int) temp.getCentre().getX(), (int) temp.getCentre().getY(), (int) temp.getWidth(), (int) temp.getHeight(), temp.getTexture(),g);	 

		}); 
	}

	private void drawEnemies(int x, int y, int width, int height, String texture, Graphics g) {
		File TextureToLoad = new File(texture); 
		try {
			Image myImage = ImageIO.read(TextureToLoad); 
			int currentPositionInAnimation= ((int)((CurrentAnimationTime%14)/2))*100; 
			g.drawImage(myImage, x,y, x+width, y+height, currentPositionInAnimation  , 0, currentPositionInAnimation+99, 100, null); 

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}

	private void drawBackground(Graphics g)
	{
		File TextureToLoad = new File("res/prismBackground.png");  //should work okay on OSX and Linux but check if you have issues depending your eclipse install or if your running this without an IDE 
		try {
			Image myImage = ImageIO.read(TextureToLoad); 
			g.drawImage(myImage, 0,0, 1000, 1000, 0 , 0, 1000, 1000, null); 

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void drawBullet (int x, int y, double scaleX, double scaleY, String texture, double angle, Graphics g)
	{
		//TRYING TO DRAW BULLETS
		BufferedImage BulletImg = LoadImage(texture);
		AffineTransform at = AffineTransform.getTranslateInstance(x, y);
		at.rotate(Math.toRadians(angle), (((double)BulletImg.getWidth()) * scaleX)/2, (((double)BulletImg.getHeight())*scaleY)/2);
		at.scale(scaleX, scaleY);

		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(BulletImg, at, null);
	}


	private void drawPlayer(int x, int y, int width, int height, String texture,Graphics g) { 
		File TextureToLoad = new File(texture);  //should work okay on OSX and Linux but check if you have issues depending your eclipse install or if your running this without an IDE 
		try {
			Image myImage = ImageIO.read(TextureToLoad);
			
			int currentDirection = (int) gameworld.getPlayer().getDirection(); //find direction
			int yOffset = currentDirection * 24;
			int currentPositionInAnimation= ((int)((CurrentAnimationTime%12)/3)) *14;
			//want to slow down idle animation
			if (currentDirection == 0) {
				currentPositionInAnimation = ((int) ((CurrentAnimationTime%20)/5))*14;
			}
			//center, dimensions, bottom left, top right
			g.drawImage(myImage, x,y, x+width, y+height, currentPositionInAnimation  , yOffset, currentPositionInAnimation+13, yOffset+23, null); 

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		
	}
	
	//function to return mirrored buffer image
	
	private void drawGun(int x, int y, double scaleX, double scaleY, String texture, double angle, Graphics g) {
		//if mouse is left of player points gun left
		if (controller.getMouseX() < gameworld.getPlayer().getCentre().getX()) {
			
			texture = texture + "Left.png"; 
		}
		//if mouse is right of player points gun right
		else {
			texture = texture + "Right.png";
		}

		//TRYING TO DRAW GUN
		BufferedImage gunImg = LoadImage(texture);
		AffineTransform at = AffineTransform.getTranslateInstance(x, y);
		at.rotate(Math.toRadians(angle), (((double)gunImg.getWidth()) * scaleX)/2, (((double)gunImg.getHeight())*scaleY)/2);
		at.scale(scaleX, scaleY);

		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(gunImg, at, null);


	}
	
	//fn to draw health bar by amount of health player has
	//NOT SCALABLE! DONT CHANGE!
	private void drawHealth(int x, int y, int width, int height, String borderTexture, String amtTexture,Graphics g) { 
		
		File TextureToLoad = new File(amtTexture);  
		try {
			Image amtImg  = ImageIO.read(TextureToLoad);
			
			int health = Math.max((int) gameworld.getPlayer().getHealth(), 0); //finds health
			
			//TOO MESSY
			//findBorders for health bar
			int x1 = x+40;
			double healthPercent = ((double) health) / 100.0;
			double amtWidth = (double)(width-45) * healthPercent + (double)x1;
			
			g.drawImage(amtImg, x1,y+10, (int)amtWidth, y+height-10, 0, 0, 100, 50, null); 

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		File secondLayer = new File(borderTexture);
		try {
			Image borderImg  = ImageIO.read(secondLayer);
			
			int health = (int) gameworld.getPlayer().getHealth(); //finds health
			
			g.drawImage(borderImg, x,y, x+width, y+height, 0, 0, 200, 54, null); 

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		//Draws health label
		g.setColor(Color.RED);
		g.setFont(new Font("SansSerif", Font.PLAIN, 20));
		g.drawString("Health: " + gameworld.getPlayer().getHealth(), 80, 38);
	}



}


/*
 * 
 * 
 *              VIEWER HMD into the world                                                             

                                      .                                         
                                         .                                      
                                             .  ..                              
                               .........~++++.. .  .                            
                 .   . ....,++??+++?+??+++?++?7ZZ7..   .                        
         .   . . .+?+???++++???D7I????Z8Z8N8MD7I?=+O$..                         
      .. ........ZOZZ$7ZZNZZDNODDOMMMMND8$$77I??I?+?+=O .     .                 
      .. ...7$OZZ?788DDNDDDDD8ZZ7$$$7I7III7??I?????+++=+~.                      
       ...8OZII?III7II77777I$I7II???7I??+?I?I?+?+IDNN8??++=...                  
     ....OOIIIII????II?I??II?I????I?????=?+Z88O77ZZO8888OO?++,......            
      ..OZI7III??II??I??I?7ODM8NN8O8OZO8DDDDDDDDD8DDDDDDDDNNNOZ= ......   ..    
     ..OZI?II7I?????+????+IIO8O8DDDDD8DNMMNNNNNDDNNDDDNDDNNNNNNDD$,.........    
      ,ZII77II?III??????DO8DDD8DNNNNNDDMDDDDDNNDDDNNNDNNNNDNNNNDDNDD+.......   .
      7Z??II7??II??I??IOMDDNMNNNNNDDDDDMDDDDNDDNNNNNDNNNNDNNDMNNNNNDDD,......   
 .  ..IZ??IIIII777?I?8NNNNNNNNNDDDDDDDDNDDDDDNNMMMDNDMMNNDNNDMNNNNNNDDDD.....   
      .$???I7IIIIIIINNNNNNNNNNNDDNDDDDDD8DDDDNM888888888DNNNNNNDNNNNNNDDO.....  
       $+??IIII?II?NNNNNMMMMMDN8DNNNDDDDZDDNN?D88I==INNDDDNNDNMNNMNNNNND8:..... 
   ....$+??III??I+NNNNNMMM88D88D88888DDDZDDMND88==+=NNNNMDDNNNNNNMMNNNNND8......
.......8=+????III8NNNNMMMDD8I=~+ONN8D8NDODNMN8DNDNNNNNNNM8DNNNNNNMNNNNDDD8..... 
. ......O=??IIIIIMNNNMMMDDD?+=?ONNNN888NMDDM88MNNNNNNNNNMDDNNNMNNNMMNDNND8......
........,+++???IINNNNNMMDDMDNMNDNMNNM8ONMDDM88NNNNNN+==ND8NNNDMNMNNNNNDDD8......
......,,,:++??I?ONNNNNMDDDMNNNNNNNNMM88NMDDNN88MNDN==~MD8DNNNNNMNMNNNDND8O......
....,,,,:::+??IIONNNNNNNDDMNNNNNO+?MN88DN8DDD888DNMMM888DNDNNNNMMMNNDDDD8,.... .
...,,,,::::~+?+?NNNNNNNMD8DNNN++++MNO8D88NNMODD8O88888DDDDDDNNMMMNNNDDD8........
..,,,,:::~~~=+??MNNNNNNNND88MNMMMD888NNNNNNNMODDDDDDDDND8DDDNNNNNNDDD8,.........
..,,,,:::~~~=++?NMNNNNNNND8888888O8DNNNNNNMMMNDDDDDDNMMNDDDOO+~~::,,,.......... 
..,,,:::~~~~==+?NNNDDNDNDDNDDDDDDDDNNND88OOZZ$8DDMNDZNZDZ7I?++~::,,,............
..,,,::::~~~~==7DDNNDDD8DDDDDDDD8DD888OOOZZ$$$7777OOZZZ$7I?++=~~:,,,.........   
..,,,,::::~~~~=+8NNNNNDDDMMMNNNNNDOOOOZZZ$$$77777777777II?++==~::,,,......  . ..
...,,,,::::~~~~=I8DNNN8DDNZOM$ZDOOZZZZ$$$7777IIIIIIIII???++==~~::,,........  .  
....,,,,:::::~~~~+=++?I$$ZZOZZZZZ$$$$$777IIII?????????+++==~~:::,,,...... ..    
.....,,,,:::::~~~~~==+?II777$$$$77777IIII????+++++++=====~~~:::,,,........      
......,,,,,:::::~~~~==++??IIIIIIIII?????++++=======~~~~~~:::,,,,,,.......       
.......,,,,,,,::::~~~~==+++???????+++++=====~~~~~~::::::::,,,,,..........       
.........,,,,,,,,::::~~~======+======~~~~~~:::::::::,,,,,,,,............        
  .........,.,,,,,,,,::::~~~~~~~~~~:::::::::,,,,,,,,,,,...............          
   ..........,..,,,,,,,,,,::::::::::,,,,,,,,,.,....................             
     .................,,,,,,,,,,,,,,,,.......................                   
       .................................................                        
           ....................................                                 
               ....................   .                                         


                                                                 GlassGiant.com
 */
