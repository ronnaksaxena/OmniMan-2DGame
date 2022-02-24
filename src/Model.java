import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import util.GameObject;
import util.Point3f;
import util.Vector3f;
import util.GunObject;
import util.PlayerObject;
import util.BulletObject;
import util.EnemyObject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Timer;
import java.lang.Math.*; //Math.PI Math.sin(double radians) Math.cos(double radians) all of type double
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

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
 */ 
public class Model {
	
	//game states
	private boolean isTimerRunning = false;
	private int Score=0;
	private int curClicks = 0;
	public boolean gameLost = false;
	public boolean gameWon = false;
	private int level = 0;
	private int revives = 3; //number of health resets
	
	//versions by level
	//gun versions (level, gunType)
	private HashMap<Integer, GunObject> gunType = new HashMap<Integer, GunObject>();
	public void addGuns() {
		gunType.put(0, new GunObject("res/gun1", new Point3f(Player.getCentre().getX()+15, Player.getCentre().getY()+30,0), 0.3,  0.3, (double)0, (double)1.0));
		gunType.put(1, new GunObject("res/gun1", new Point3f(Player.getCentre().getX()+15, Player.getCentre().getY()+30,0), 0.3,  0.3, (double)0, (double)1.0));
		gunType.put(2, new GunObject("res/gun2", new Point3f(Player.getCentre().getX()+15, Player.getCentre().getY()+25,0), 0.3,  0.3, (double)0, (double)1.0));
		gunType.put(3, new GunObject("res/gun3", new Point3f(Player.getCentre().getX()+15, Player.getCentre().getY()+25,0), 0.2,  0.2, (double)0, (double)1.0));
	}
	
	//enemy versions by level
	private HashMap<Integer, EnemyObject> enemyType = new HashMap<Integer, EnemyObject>();
	
	//These are blueprints so you have to make a copy to original enemy blueprint each instantiation
	public void addEnemies() { //CHANGE enemy spawns every time you make an enemy object
		//EnemyObject (String textureLocation,int width,int height,Point3f centre, double curAngle, double sp, int dmg)
		enemyType.put(0, new EnemyObject("res/covidCell1.png", 100, 100, new Point3f(0,0,0), 0, 0.25, 1));
		enemyType.put(1, new EnemyObject("res/covidCell1.png", 100, 100, new Point3f(0,0,0), 0, 0.5, 5));
		enemyType.put(2, new EnemyObject("res/covidCell2.png", 100, 100, new Point3f(0,0,0), 0, 1.25, 10));
		enemyType.put(3, new EnemyObject("res/covidCell3.png", 100, 100, new Point3f(0,0,0), 0, 2, 20));
	}
	private HashMap<Integer, CopyOnWriteArrayList<EnemyObject>> enemyLists = new HashMap<Integer, CopyOnWriteArrayList<EnemyObject>>();
	public void addEnemyLists() {
		enemyLists.put(0, new CopyOnWriteArrayList<EnemyObject>());
		enemyLists.put(1, new CopyOnWriteArrayList<EnemyObject>());
		enemyLists.put(2, new CopyOnWriteArrayList<EnemyObject>());
		enemyLists.put(3, new CopyOnWriteArrayList<EnemyObject>());
	}
	
	//bullet versions by level, set the angle after instantiation
	private HashMap<Integer, BulletObject> bulletType = new HashMap<Integer, BulletObject>();
	public void addBullets() {
		bulletType.put(0, new BulletObject("res/bullet0.png", new Point3f(Player.getCentre().getX()+10,Player.getCentre().getY()+25,0.0f), 1.0, 1.0, 0));
		bulletType.put(1, new BulletObject("res/bullet1.png", new Point3f(Player.getCentre().getX()+10,Player.getCentre().getY()+25,0.0f), 1.0, 1.0, 0));
		bulletType.put(2, new BulletObject("res/bullet2.png", new Point3f(Player.getCentre().getX()+10,Player.getCentre().getY()+25,0.0f), 0.5, 0.5, 0));
		bulletType.put(3, new BulletObject("res/bullet3.png", new Point3f(Player.getCentre().getX()+10,Player.getCentre().getY()+25,0.0f), 0.5, 0.5, 0));
	}
	private HashMap<Integer, CopyOnWriteArrayList<BulletObject>> bulletLists = new HashMap<Integer, CopyOnWriteArrayList<BulletObject>>();
	public void addBulletLists() {
		bulletLists.put(0, new CopyOnWriteArrayList<BulletObject>());
		bulletLists.put(1, new CopyOnWriteArrayList<BulletObject>());
		bulletLists.put(2, new CopyOnWriteArrayList<BulletObject>());
		bulletLists.put(3, new CopyOnWriteArrayList<BulletObject>());
	}
	
	//switch map per level
	private HashMap<Integer, String> backgroundType = new HashMap<Integer, String>();
	public void addBackgrounds() {
		backgroundType.put(0, "res/background1.png");
		backgroundType.put(1, "res/background2.png");
		backgroundType.put(2, "res/background3.png");
		backgroundType.put(3, "res/background4.png");
	}
	
	//Score multiplier, how many points you get per kill
	private HashMap<Integer, Integer> scoreMult = new HashMap<Integer, Integer>();
	public void addScoreMults() {
		scoreMult.put(0, 1);
		scoreMult.put(1, 5);
		scoreMult.put(2, 10);
		scoreMult.put(3, 25);
	}
	
	
	
	//game elements
	private  static PlayerObject Player;
	private Controller controller = Controller.getInstance();
	private  CopyOnWriteArrayList<EnemyObject> EnemiesList;
	private  CopyOnWriteArrayList<BulletObject> BulletList;
	private static GunObject Gun;
	
	
	

	public Model() { 
		//setup game world 
		
		//Instantiate player first so other types get positions relative to player
		Player= new PlayerObject("res/OMDirects.png",50,80,new Point3f(500,500,0));

		//addTypes to HashMaps
		addGuns();
		addEnemies();
		addEnemyLists();
		addBullets();
		addBulletLists();
		addBackgrounds();
		addScoreMults();
		
		//gun
		//need to add Right.png or Left.png to texture
		Gun = gunType.get(level);
		EnemiesList = enemyLists.get(level);
		BulletList = bulletLists.get(level);

		

	}

	// calls all logics here
	// This is the heart of the game , where the model takes in all the inputs ,decides the outcomes and then changes the model accordingly. 
	public void gamelogic() throws CloneNotSupportedException 
	{
		//only runs if game is in session
		if (!gameLost && !gameWon) {
			//Rotates Gun
			gunLogic();
			// Player Logic
			playerLogic(); 
			// Enemy Logic next
			enemyLogic();
			// Bullets move next 
			bulletLogic();
			// interactions between objects 
			gameLogic();
		}
		
		// checks if game state should change
		stateLogic();
		
		

	}

	//collision detection
	private void gameLogic() { 


		// this is a way to increment across the array list data structure 
		

		//maybe make quad tree later
		//see if they hit anything 
		// using enhanced for-loop style as it makes it alot easier both code wise and reading wise too 
		for (EnemyObject temp : EnemiesList) 
		{
			for (BulletObject Bullet : BulletList) 
			{
				if ( Math.abs(temp.getCentre().getX()- Bullet.getCentre().getX())< temp.getWidth() 
						&& Math.abs(temp.getCentre().getY()- Bullet.getCentre().getY()) < temp.getHeight())
				{
					EnemiesList.remove(temp);
					BulletList.remove(Bullet);
					Score += scoreMult.get(level);
					
					//plays gun sound every time enemy is killed
					
					String soundName = "res/laserSound.wav";    
					AudioInputStream audioInputStream;
					try {
						audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
						Clip clip = AudioSystem.getClip();
						clip.open(audioInputStream);
						clip.start();
					} catch (UnsupportedAudioFileException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (LineUnavailableException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}  
			}
		}

	}
	
	//directly above is 0, 90 to right is 90, directly below is 180, 90 to left is 270
	//method to find angle between 2 coordinates
	public static double calculateAngle(double x1, double y1, double x2, double y2)
	{
		double angle = Math.toDegrees(Math.atan2(x2 - x1, y2 - y1));
		// Keep angle between 0 and 360
		angle = angle + Math.ceil( -angle / 360 ) * 360;

		return angle;
	}
	
	//to calculate bullet  and enemy movement
	public static double degreeToRadians (double theta) {
        return (theta/180.0) * Math.PI;
    }
    public static double nextXPosition (double angle) {
        return Math.cos(degreeToRadians(angle));
    }
    public static double nextYPosition (double angle) {
        return Math.sin(degreeToRadians(angle));
    }
    
    

    

	private void enemyLogic() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		for (EnemyObject temp : EnemiesList) 
		{
			// Move enemies towards player
			
			//find angle between enemy and player
			double eX = (double)temp.getCentre().getX();
			double eY = (double)temp.getCentre().getY();
			double pX = (double)Player.getCentre().getX();
			double pY = (double)Player.getCentre().getY();
			
			//have to switch position of Enemy Y and Player Y since Y decreases when you move up
			double theta = calculateAngle(eX, pY, pX, eY);
			
			//finds next position to move enemy
			double speedFactor = temp.getSpeed();
			double y = nextXPosition(theta); //swapped x and y for enemies? Works somehow!!
			double x = nextYPosition(theta); 
			temp.getCentre().ApplyVector( new Vector3f((float)(x * speedFactor), (float)(y * speedFactor),(float) 0.0));
			
			//adjusted shell for collision since player center is actually top left
			//if player collides with enemy, health decreases
			
			if ( Math.abs(temp.getCentre().getX()- (Player.getCentre().getX()-20))< (temp.getWidth()-25) 
					&& Math.abs(temp.getCentre().getY()- (Player.getCentre().getY()-20)) < (temp.getHeight()-20)) {
				EnemiesList.remove(temp);
				Player.health -= temp.getDamage();
				
				//plays sound everytime player is hit
				
				String soundName = "res/grunt.wav";    
				AudioInputStream audioInputStream;
				try {
					audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
					Clip clip = AudioSystem.getClip();
					clip.open(audioInputStream);
					clip.start();
				} catch (UnsupportedAudioFileException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (LineUnavailableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				

				
			}
			

			 
		}
		
		
		 if (EnemiesList.size()<2)
		{
			while (EnemiesList.size()<5)
			{
				//need to add a copy of enemy type blueprint to corresponding enemyList
				EnemyObject curType = enemyType.get(level);
				EnemyObject newEnemy = (EnemyObject)curType.clone();
				
				//give it a random spawn 200 pts away from player if level < 3, 300 pts away for level 3
				double distanceFromPlayer = (level < 3) ? 200.0 : 300.0;
				float xSpawn = getXSpawn((double) Player.getCentre().getX(), distanceFromPlayer);
				float ySpawn = getYSpawn((double) Player.getCentre().getY(), distanceFromPlayer);
				newEnemy.setCentre(new Point3f(xSpawn, ySpawn, 0));
				EnemiesList.add(newEnemy); 
			}
		}

		
	}
	
	//find valid spawning points given player position and distance away you want to spawn
	
	private float getXSpawn(double playerX, double distance) {
		Random r = new Random();
    	//picks a random point [0, (pX - distance), (pX + distance), (distance,950)]
    	double xSpawn = r.nextBoolean() ? Math.random()*(playerX-distance) : playerX+distance +Math.random()*(950.0-(playerX+distance));
    	return (float) xSpawn;
	}
	
	private float getYSpawn(double playerY, double distance) {
		Random r = new Random();
    	//picks a random point [0, (pY - distance), (pY + distance), (distance,800)]
    	double ySpawn = r.nextBoolean() ? Math.random()*(playerY-distance) : playerY+distance +Math.random()*(800-(playerY+distance));
    	return (float) ySpawn;
	}


    

	private void bulletLogic() {
		// move bullets 
		//multiple to change speed of bullet
		double speedFactor = 4;

		for (BulletObject temp : BulletList) 
		{
			//check to move them
			//get angle of direction
			double theta = temp.getAngle();
			double x = nextXPosition(theta);
			double y = nextYPosition(theta);
			y = y * -1; //since y plane is inverse
			temp.getCentre().ApplyVector( new Vector3f((float)(x * speedFactor), (float)(y * speedFactor),(float) 0.0));
			
			
			
			//see if they hit anything 

			//remove if they go out of boundary
			if (temp.getCentre().getY()<=0 || temp.getCentre().getY() >= 800 || temp.getCentre().getX() <= 0 || temp.getCentre().getX() >= 900)
			{
				BulletList.remove(temp);
			} 
		} 

	}

	private void playerLogic() throws CloneNotSupportedException {

		// smoother animation is possible if we make a target position  // done but may try to change things for students  

		//check for movement and if you fired a bullet
		
		//BULLET CONTROLS
		//Timer to fire bullet every 3 seconds
		
		Timer t = new Timer(300 , new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (isTimerRunning) {
					try {
						CreateBullet();
					} catch (CloneNotSupportedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		if(controller.getMousePressed() && !(isTimerRunning)) {
			t.start();
			isTimerRunning = true;
		}
		if(!(controller.getMousePressed()) && isTimerRunning) {
			t.stop();
			isTimerRunning = false;
		}
		
		//Fires bullet is player clicks mouse
		if (controller.getMouseClicks() > this.curClicks)  {
			CreateBullet();
			this.curClicks = controller.getMouseClicks();
		}
		
		
		//PLAYER + Gun MOVEMENT
		//0 == not moving, 1 == up, 2 == right, 3 == down, 4 == left

		if(Controller.getInstance().isKeyAPressed()) {
			Player.setDirection(4);
			//checking for OOB
			if (Player.getCentre().getX() > 0) {
				Player.getCentre().ApplyVector( new Vector3f(-2,0,0));
				Gun.getCentre().ApplyVector( new Vector3f(-2,0,0));
			}
			
		}
		else if(Controller.getInstance().isKeyDPressed())
		{
			Player.setDirection(2);
			if (Player.getCentre().getX() < 900) {
				Player.getCentre().ApplyVector( new Vector3f(2,0,0));
				Gun.getCentre().ApplyVector( new Vector3f(2,0,0));
			}
			
		}
		else if(Controller.getInstance().isKeyWPressed())
		{
			Player.setDirection(1);
			if (Player.getCentre().getY() > 0) {
				Player.getCentre().ApplyVector( new Vector3f(0,2,0));
				Gun.getCentre().ApplyVector( new Vector3f(0,2,0));
				}
			
		}
		else if(Controller.getInstance().isKeySPressed()) {
			Player.setDirection(3);
			if (Player.getCentre().getY() < 700) {
				Player.getCentre().ApplyVector( new Vector3f(0,-2,0));
				Gun.getCentre().ApplyVector( new Vector3f(0,-2,0));
			}
			
		}
		
		
		//cheeky escape keys for checking if gameWon or gameLost states work
		else if(Controller.getInstance().isKeyXPressed()) {
			gameWon = true;
		}
		else if(Controller.getInstance().isKeyZPressed()) {
			gameLost = true;
			
		}
		
		//Puts player idle if no keys are pressed
		else {
			Player.setDirection(0);
		}
		
		//If I want to use space bar later...
		/*
		if(Controller.getInstance().isKeySpacePressed())
		{
			
			CreateBullet();
			Controller.getInstance().setKeySpacePressed(false);
			
		}
		*/
		
		


	}
	

	
	//To rotate gun to follow mouse
	private void gunLogic() {
		//find angle between gun and mouse
		double gunX = (double)Gun.getCentre().getX();
		double gunY = (double)Gun.getCentre().getY();
		double mouseX = (double)controller.getMouseX();
		double mouseY = (double)controller.getMouseY();
		
		//have to switch position of mouseY and gunY since Y decreases when you move up
		double theta = calculateAngle(gunX, mouseY, mouseX, gunY);
		
		Gun.setAngle(theta);
		
	}
	
	//check if need to set the gameLost or gameWon flag
	private void stateLogic() {
		//make if statement to check for advance to next level
		if (Score >= 15 && Score < 100) {
			level = 1;
			EnemiesList = enemyLists.get(level);
			BulletList = bulletLists.get(level);
			if (revives == 3) {
				Player.setHealth(100);
				revives = 2;
			}
			
		}
		if (Score >= 100 && Score < 300) {
			level = 2;
			Gun = gunType.get(level);
			//adjust gun2 position
			Gun.setCentre(new Point3f(Player.getCentre().getX()+15, Player.getCentre().getY()+25,0));
			EnemiesList = enemyLists.get(level);
			BulletList = bulletLists.get(level);
			if (revives == 2) {
				Player.setHealth(100);
				revives = 1;
			}
		}
		if (Score >= 300) {
			level = 3;
			Gun = gunType.get(level);
			//adjust gun2 position
			Gun.setCentre(new Point3f(Player.getCentre().getX()+10, Player.getCentre().getY(),0));
			EnemiesList = enemyLists.get(level);
			BulletList = bulletLists.get(level);
			if (revives == 1) {
				Player.setHealth(100);
				revives = 0;
			}
		}
		//checks if won game
		if (Score >= 1000) {
			gameWon = true;
		}
		
		//if statement to check for gameLost
		if (Player.getHealth() <= 0 ) {
			//plays losing sound
			if (!gameLost) {
				String soundName = "res/gameOverSound.wav";    
				AudioInputStream audioInputStream;
				try {
					audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
					Clip clip = AudioSystem.getClip();
					clip.open(audioInputStream);
					clip.start();
				} catch (UnsupportedAudioFileException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (LineUnavailableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			gameLost = true;
			
			
		}
		
	}

	private void CreateBullet() throws CloneNotSupportedException {
		//trying to find bullet angle
		//find angle between bullet and mouse
		double gunX = (double)Gun.getCentre().getX();
		double gunY = (double)Gun.getCentre().getY();
		double mouseX = (double)controller.getMouseX();
		double mouseY = (double)controller.getMouseY();
		
		//have to switch position of mouseY and gunY since Y decreases when you move up
		double theta = calculateAngle(gunX, mouseY, mouseX, gunY);
		//since bullets start pointed to right
		theta -= 90;
		
		//created a copy of bullet blueprint
		BulletObject curType = bulletType.get(level);
		BulletObject newBullet = (BulletObject)curType.clone();
		newBullet.setAngle(theta);
		//offsets to make it look better
		int dx = 0; 
		int dy = 0;
		if (level == 0 || level == 1) {
			dx = 10;
			dy = 25;
		}
		else if (level == 2) {
			dx = -25;
			dy = 0;
		}
		else if (level == 3) {
			dx = -10;
			dy = 20;
		}
		
		newBullet.changeCentre(Player.getCentre().getX()+dx,Player.getCentre().getY()+dy,0.0f);
		BulletList.add(newBullet);
		
		
		
	}
	//function to reset game
	public void resetGame() {
		gameLost = false;
		Score = 0;
		Player.setHealth(100);
		level = 0;
		
		
	}

	public PlayerObject getPlayer() {
		return Player;
	}

	public CopyOnWriteArrayList<EnemyObject> getEnemies() {
		return EnemiesList;
	}

	public CopyOnWriteArrayList<BulletObject> getBullets() {
		return BulletList;
	}

	public int getScore() { 
		return Score;
	}
	
	public GunObject getGun() {
		return Gun;
	}
	
	public Controller getController() {
		return Controller.getInstance();
	}
	
	public int getLevel() {
		return this.level;
	}
	
	public void setLevel(int x) {
		this.level = x;
	}
	
	public String getBackground(int level) {
		return backgroundType.get(level);
	}
	public boolean isGameLost() {
		return this.gameLost;
	}
	public boolean isGameWon() {
		return this.gameWon;
	}
	
	


};


/* MODEL OF your GAME world 
 * MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWWNNNXXXKKK000000000000KKKXXXNNNWWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWNXXK0OOkkxddddooooooolllllllloooooooddddxkkOO0KXXNWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWNXK0OkxddooolllllllllllllllllllllllllllllllllllllllloooddxkO0KXNWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWNXK0OkdooollllllllooddddxxxkkkOOOOOOOOOOOOOOOkkxxdddooolllllllllllooddxO0KXNWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWNK0kxdoollllllloddxkO0KKXNNNNWWWWWWMMMMMMMMMMMMMWWWWNNNXXK00Okkxdoollllllllloodxk0KNWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWXKOxdooolllllodxkO0KXNWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWNXK0OkxdolllllolloodxOKXWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWNKOxdoolllllodxO0KNWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWNXKOkdolllllllloodxOKNWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWX0kdolllllooxk0KNWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWNK0kdolllllllllodk0XWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWX0xdolllllodk0XNWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWWMMMMMMMMMMMWN0kdolllllllllodx0XWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWX0xoollllodxOKNWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWWMMMMMMMMMMWNXKOkkkk0WMMMMMMMMMMMMWNKkdolllloololodx0XWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWN0kdolllllox0XWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWNXK0kxk0KNWWWWNX0OkdoolllooONMMMMMMMMMMMMMMMWXOxolllllllollodk0XWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWXOdollllllllokXMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWN0xooollloodkOOkdoollllllllloxXWMMMMMMMMMMMMMMMWXkolllllllllllllodOXWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWN0koolllllllllllokNMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWKxolllllllllllllllllllllllllllox0XWWMMMMMMMMMWNKOdoloooollllllllllllok0NWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWX0xoolllllllllllllloONMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWKxllolllllllllllllllllloollllllolodxO0KXNNNXK0kdoooxO0K0Odolllollllllllox0XWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMWXOdolllllllllllllllllokXMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNkolllllllllloolllllllllllllllllllolllloddddoolloxOKNWMMMWNKOxdolollllllllodOXWMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMWXOdolllolllllllllllllloxKWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMXxlllolllllloxkxolllllllllllllllllolllllllllllllxKWMWWWNNXXXKKOxoollllllllllodOXWMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMWXOdollllllllllllllllllllokNMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNOollllllllllxKNKOxooollolllllllllllllllllllolod0XX0OkxdddoooodoollollllllllllodOXWMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMN0xollllllllllllllllllllllld0NMMMMMMMMMMMMMMMMMMMMMMMWWNKKNMMMMMMMMMMMW0dlllllllllokXWMWNKkoloolllllllllllllllllllolokkxoolllllllllllllollllllllllllllox0NMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMWKxolllllllllllllllllllllllllloONMMMMMMMMMMMMMMMMMMMWNKOxdookNMMMMMMMMMWXkollllllodx0NWMMWWXkolooollllllllllllllllllllooollllllllllllllolllllllllllloooolloxKWMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMWXOdllllllllllllllooollllllllollld0WMMMMMMMMMMMMMMMMWXOxollllloOWMMMMMMMWNkollloodxk0KKXXK0OkdoollllllllllllllllllllllllllllllollllllllloollllllollllllllllllldOXWMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMN0xolllllllllllolllllllllllloodddddONMMMMMMMMMMMMMMMNOdolllllllokNMMMMMMWNkolllloddddddoooolllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllox0NMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMWXkolllllllllllllllllllodxxkkO0KXNNXXXWMMMMMMMMMMMMMMNkolllllllllod0NMMMMMNOollllloollllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllolllllllllllllokXWMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMWKxollllllllllllllllllox0NWWWWWMMMMMMMMMMMMMMMMMMMMMMW0dlllllllllllookKNWWNOolollloolllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllloxKWMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMN0dlllllllllllllllllllldKWMMMMMMMMMMMMMMMMMMMMMMMMMMMMNkoloolllollllolloxO0Odllllllllllllllllllllllllllllllllllllllllllllollllllllllllllllllllllllllllllllllllllllllllllld0NWMMMMMMMMMMMMM
MMMMMMMMMMMMMXkolllllllllllllllllolllxXMMMMMMMMMMMMMMMMMMMMMMMMMMMMMXOO0KKOdollllllllllooolllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllloONWMMMMMMMMMMMM
MMMMMMMMMMMWXkollllllllllllllllllllllxXMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWMMMMWNKOxoollllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllokXWMMMMMMMMMMM
MMMMMMMMMMWKxollllllllllllllllllllllokNMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWKxollllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllloxKWMMMMMMMMMM
MMMMMMMMMWKxollllllllllllodxkkkkkkkO0XWMMMMMMMMMMMMMMMMMMMMMMMMMMMMNKOkO0KK0OkdolllllloolllllllllllloooollllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllloxKWMMMMMMMMM
MMMMMMMMWKxllllllllllolodOXWWWWWWWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMXxolloooollllllllllllllllloollllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllxKWMMMMMMMM
MMMMMMMWKxlllllllllollokXWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMXxololllllllooolloollllloolloooolllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllxKWMMMMMMM
MMMMMMWXxllllllllooodkKNWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMKdloollllllllllololodxxddddk0KK0kxxxdollolllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllxXWMMMMMM
MMMMMMXkolllllodk0KXNWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMKdllollllllllllllodOXWWNXXNWMMMMWWWNX0xolollllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllokNMMMMMM
MMMMMNOollllodONWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMW0dooollllllllllllodOXNWWWWWWMMMMMMMMMWXOddxxddolllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllloONMMMMM
MMMMW0dllllodKWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWNKKK0kdlllllllllllloodxxxxkkOOKNWMMMMMMWNNNNNXKOkdooooollllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllld0WMMMM
MMMWKxllllloOWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNkolllllollllllllllllllllodOKXWMMMMMMMMMMMMWNXKK0OOkkkxdooolllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllxKWMMM
MMMNkollllokXMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWXOdlllllolllllllllllloloolllooxKWMMMMMMMMMMMMMMMMMMMMWWWNXKOxoollllllllllllllllllllllllllllllllllllllllllllllllllllllllllolokNMMM
MMW0ollllldKWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWNKOOkxdollllllllllllllllllllllllllllox0NWMMMMWWNNXXKKXNWMMMMMMMMMWNKOxolllolllllllllllllllllllllllllllllllllllllllllllllllllllllllo0WMM
MMXxllllloONMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWXkolllllllllllllllllllllllllllllllllllooxO000OkxdddoooodkKWMMMMMMMMMMWXxllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllxXWM
MWOollllldKWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMXkollllllllllllllllllllllllllllllllllllllllllllllllllllllld0WMMMMMMMMMWKdlllllllllllllllllllllllllllllllllllllllllllllllllllllllllllloOWM
MXxllllloONMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWXkollllllllllllllllllllllllllllllllllooollllllllllllllllllold0WMMMMMMWN0dolllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllxXM
W0ollllld0WMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNKkdolllllllllllllllllllllllllllllllllllllllllllllllllolllllllllokKXNWWNKkollllllllloxdollllllllolllllllllllllllllllllllllolllllllllllllolo0W
NkllllloxXMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNkollllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllodxkkdoolollllllllxKOolllllllllllllllllllllllollooollllllloolllllloolllllkN
KxllllloONMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMW0doolllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllkX0dlllllllllllllllllllloollloOKKOkxdddoollllllllllllllxK
Oolllllo0WMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMXxllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllxXXkollllooolllllllllllllllloONMMMWNNNXX0xolllllllllolloO
kolllllo0WMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNOollllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllxXWXkollollllllllllllllllllodKMMMMMMMMMMWKxollllolollolok
kllllllo0WMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMW0dlllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllloolllllllllxXWWXkolllllllllllllllolllloONMMMMMMMMMMMW0dllllllllllllk
xollolld0WMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMXxllllolllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllloollllllolloONMMN0xoolllllllolllllllloxXWMMMMMMMMMMMMXxollllllloollx
dollllld0WMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMXxlllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllloollld0WMMWWXOdollollollllllloxXWMMMMMMMMMMMMMNOollllllokkold
olllllld0WMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNxlllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllldONMMMMWXxollllolllllox0NWMMMMMMMMMMMMMMNOollllllxXOolo
llllllld0WMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWXxllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllloONMMMMMXxddxxxxkkO0XWMMMMMMMMMMMMMMMMMNOolllllxKW0olo
llllllld0WMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWKdlllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllldONWMMMWNXNNWWWMMMMMMMMMMMMMMMMMMMMMMMW0dllollOWW0oll
llllllld0WMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNOolllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllloxO0KXXXXKKKXNWMMMMMMMMMMMMMMMMMMMMMMMNOdolllkNWOolo
ollllllo0WMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMW0dllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllooooddooloodkKWMMMMMMMMMMMMMMMMMMMMMMWXOolldKNOooo
dollllloONMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWKxolllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllloollllo0WMMMMMMMMMMMMMMMMMMMMMMMMXkold0Nkold
xollllloxXMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMXxllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllollokNMMMMMMMMMMMMMMMMMMMMMMMMMWOookXXxolx
xolllllloONWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWKxolllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllokXWMMMMMMMMMMMMMMMMMMMMMMMMMN00XW0dlox
kollllllloxOKXXNNWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWXOxollllllllllllllllllllllllllllllolllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllolllllolo0WMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWOollk
OolllllllllloodddkKWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWNKOkkxddooooollllllllllooodxxdollolllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllokXWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNkoloO
KdllllllllllllllllxXMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWNXXXK0OOkkkkkkkkOKXXXNNX0xolllllllllllllllllllllllllllllllllllllllllllllllllllllllloollllllllloox0NMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMKdlldK
NkllllollloolllllldKWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWWWWMMMMMMMMMWNOdlllllllllllllllllllllllllllllllllllllllllllllllllllllllollllllllodOKNWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWOolokN
WOolllllllllllolllokXWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWKxollllllllllllllllllllllllllllllllllllllllllllllllllllllllllllod0NWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWXxolo0W
WXxllllllllllllllllox0NWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWKxollllllllllllllllllllllllllllllllllllllllllllllllllllllllllokXWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNOollxXM
MWOollllllllllllllooloxKWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWKdllllllllllllllllllllllllllllllllllllllllllllllllllllloolld0NMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWKdlloOWM
MWXxllolllllllllllllllldOXWWNNK00KXWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMW0dllllllllllllllllllllllllllllllllllllllllllllllllllllllod0WMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWKxollxXWM
MMWOollllllllloollllllolodxkxdollodk0XWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNOollllllllllllllllllllllllllllllllllllllllllllllllllodxOXWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWN0dlllo0WMM
MMMXxllolllllllllllllllllllllllllllloox0NMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMN0dooollllllllllllllllllllllllllllllllllllllllllllodOXNWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWKOkxxolllokNMMM
MMMW0dlllllllllllllllllllolllllllollollokXMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWXOdoolllllllllllllllllllllllllllllllllllllllllllxKWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWNOoollllllldKWMMM
MMMMNOollllllllllllllllllllllllllllllllloOWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWXKOdolllllllllllllllllllllllllllllllllllllllloONMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNOolllllllloOWMMMM
MMMMMXkollllllllllllllllllllllllllllllllokNMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMW0dlllllllllllllllllllllllllllllllllllllllld0WMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMW0dllolllllokNMMMMM
MMMMMWXxlllllllllllllllllllllllllllllllloxXMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMW0ollllllllllllllllllllllllllllllllllllllldKWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWOollllllllxXWMMMMM
MMMMMMWKdlllllllllllllllllllllllllllllllokNMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMXxolllllllllllllllllllllllllllllllllllllloONWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNOolllllllxKWMMMMMM
MMMMMMMW0dlllllllllllllllllllllllllllllloOWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMW0dlllloollllllllllllllllllllllllllllllllloxkOKKXXKKXNMMMMMMMMMMMMMMMMMMMMMMMMNOolllllldKWMMMMMMM
MMMMMMMMW0dllllllllllllllllllllllllllllldKMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWKdlllllllllllllllllllllllllllllllllllllllllllloooood0WMMMMMMMMMMMMMMMMMMMMMMMNOollolldKWMMMMMMMM
MMMMMMMMMW0dlllllllllllllllllllllllllllokXMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMW0dllllllllllllllllllllllllllllllolllllllllllllllllld0WMMMMMMMMMMMMMMMMMMMMMMWKxllllldKWMMMMMMMMM
MMMMMMMMMMW0dlllllllllllllllllllllllllloxXMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNkolllllllllllllllllllllllllllllllllllllllllllllllllxXMMMMMMMMMMMMMMMMMMMMMWXOdolllldKWMMMMMMMMMM
MMMMMMMMMMMWKxollllllllllllllllllllllllloOWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMW0dlllllllllllllllllllllllllllllllloolllllllolllollllkNMMMMMMMMMMMMMMMMMMMWXOdolllloxKWMMMMMMMMMMM
MMMMMMMMMMMMWKxollllllllllllllllllllllllod0WMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNkoloollllllllllllllllllllllllllllloddollllllllllllld0WMMMMMMMMMMMMMMMWWNKOdolllllokXWMMMMMMMMMMMM
MMMMMMMMMMMMMWXkollllllllllllllllllllllllldKMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMW0dllollllllllllllllllllllllllllllld0XOollllllllllllkNMMMMMMMMMMMMWNK0OkxollllllloONWMMMMMMMMMMMMM
MMMMMMMMMMMMMMMNOdlllllllllllllllllllllllokXMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMN0dlllllllllllllllllllllllllolllld0NWN0dlllllloodxkKWMMMMMMMMMMMMNOollllllllllld0NMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMWKxolollllllllllllllllllokXWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNOolllllllllllllllllllllllllllldONMMMWKkdoooxOXNNWMMMMMMMMMMMMMNOollllllllllokXWMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMWXOdlllllllllllllllllloONWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWKdllllllllllllllllllllllllllld0NMMMMMWWXXXXNWMMMMMMMMMMMMMMMMW0dlllllllllod0NMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMWKxollolllllllllllloONMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMXxllllllllllllllllllllllllloxKWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMW0dlllllllllokXWMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMWNOdollllllloolllldKWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNkolllloollllooolllllllllodONWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNkllllllolox0NMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMWXkollllllolllllox0NMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWKdlllllollllllllllllllodkKWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMW0dllllllodOXWMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMWKxoolllllllllllokXWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMN0dollllllllllooddxxk0KNWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWXOdollllldOXWMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMN0xolllllllllllokXWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWKxolllllodk0KXNNWWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWNKkdollolodkXWMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMNKxoolllllllllodOKNMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWXOdolldOXWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWX0xoollllodkXWMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNKkolllollllllloxOXWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWNKOx0WMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWNKOdolllllldOXWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWKOdollllllllllodx0XWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWNKOdoollllloxOXWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWX0xollollollollodxOXNWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWX0kdooollllodk0NWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWNKkdooolllllllllooxOKNWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWNKOxdollllllloxOXWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWN0kdllllllllollllodkOKNWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWXKOkdoolllllloodOKNMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWX0kdolllllllllllllodxO0XNWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWNX0OxdollloolllloxOKNWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWX0kdoolllllllllllllooxkO0XNWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWNX0OkxoololllllllooxOKNWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWNK0kdoolllllllllllloooodxkO0KXNWWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWNXK0Okxdoolllllollllloxk0XWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWNKOkdoollllllllloolllllloodxkkO00KXXNNWWWWWWMMMMMMMMMWWWWWWWNNXXK00Okxxdoolllllllllllloooxk0KNWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWNK0kxdoollllllllllllllllllllloodddxxxkkOOOOOOOOOOOkkkxxxdddoollllllllllllllllloodxO0XNWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWNXK0OxdooollllllllllllooolllllllllllllllllllllllllllllllllllllllllllooodkO0KNWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWNXK0OkxdooollllllllllllllllllllllllllllllllllllllllllloooddxkO0KXNWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWNNXK0OOkkxdddoooooollllllllllllllllooooooddxxkOO0KKXNWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWNNXXXKK00OOOOOOOOOOOOOOOO00KKXXXNNWWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
 */

