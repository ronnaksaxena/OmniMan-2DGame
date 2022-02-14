import java.awt.Color;
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
import javax.swing.JPanel;
import javax.swing.Timer;
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
		//Draw background 
		drawBackground(g);


		//Draws gun first if player is moving up
		if (gameworld.getPlayer().getDirection() == 1) {
			drawGun(gunX, gunY, gunScaleX, gunScaleY, gunTexture, gunAngle, g);
			drawPlayer(x, y, width, height, texture,g);
		}
		else {
			drawPlayer(x, y, width, height, texture,g);
			drawGun(gunX, gunY, gunScaleX, gunScaleY, gunTexture, gunAngle, g);
		}


		//Draw Bullets 
		// change back 
		gameworld.getBullets().forEach((temp) -> 
		{ 
			drawBullet((int) temp.getCentre().getX(), (int) temp.getCentre().getY(), (int) temp.getWidth(), (int) temp.getHeight(), temp.getTexture(),g);	 
		}); 

		//Draw Enemies   
		gameworld.getEnemies().forEach((temp) -> 
		{
			drawEnemies((int) temp.getCentre().getX(), (int) temp.getCentre().getY(), (int) temp.getWidth(), (int) temp.getHeight(), temp.getTexture(),g);	 

		}); 
	}

	private void drawEnemies(int x, int y, int width, int height, String texture, Graphics g) {
		File TextureToLoad = new File(texture);  //should work okay on OSX and Linux but check if you have issues depending your eclipse install or if your running this without an IDE 
		try {
			Image myImage = ImageIO.read(TextureToLoad);
			//The spirte is 32x32 pixel wide and 4 of them are placed together so we need to grab a different one each time 
			//remember your training :-) computer science everything starts at 0 so 32 pixels gets us to 31  
			int currentPositionInAnimation= ((int)(CurrentAnimationTime%7))*365; //slows down animation so every 10 frames we get another frame so every 100ms 
			g.drawImage(myImage, x,y, x+width, y+height, currentPositionInAnimation  , 0, currentPositionInAnimation+364, 365, null); 

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

	private void drawBullet(int x, int y, int width, int height, String texture,Graphics g)
	{
		File TextureToLoad = new File(texture);  //should work okay on OSX and Linux but check if you have issues depending your eclipse install or if your running this without an IDE 
		try {
			Image myImage = ImageIO.read(TextureToLoad); 
			//													height x width
			g.drawImage(myImage, x,y, x+width, y+height, 0 , 0, 28, 38, null); 

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	private void drawPlayer(int x, int y, int width, int height, String texture,Graphics g) { 
		File TextureToLoad = new File(texture);  //should work okay on OSX and Linux but check if you have issues depending your eclipse install or if your running this without an IDE 
		try {
			Image myImage = ImageIO.read(TextureToLoad);
			//The spirte is 32x32 pixel wide and 4 of them are placed together so we need to grab a different one each time 
			//remember your training :-) computer science everything starts at 0 so 32 pixels gets us to 31
			//HOW to speed up my character??
			
			int currentDirection = (int) gameworld.getPlayer().getDirection(); //find direction
			int yOffset = currentDirection * 24;
			int currentPositionInAnimation= ((int) (CurrentAnimationTime%4))*14;
			//want to slow down idle animation
			if (currentDirection == 0) {
				currentPositionInAnimation = ((int) (CurrentAnimationTime%12)/3)*14;
			}
			//center, dimensions, bottom left, top right
			g.drawImage(myImage, x,y, x+width, y+height, currentPositionInAnimation  , yOffset, currentPositionInAnimation+13, yOffset+23, null); 

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		//g.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, observer));
		//Lighnting Png from https://opengameart.org/content/animated-spaceships  its 32x32 thats why I know to increament by 32 each time 
		// Bullets from https://opengameart.org/forumtopic/tatermands-art 
		// background image from https://www.needpix.com/photo/download/677346/space-stars-nebula-background-galaxy-universe-free-pictures-free-photos-free-images

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
