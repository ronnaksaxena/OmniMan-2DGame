package util;

public class PlayerObject extends GameObject {
	//health of player
	public static int health = 100;

	private int direction = 0; //0 == not moving, 1 == up, 2 == right, 3 == down, 4 == left
	
	public PlayerObject (String textureLocation,int width,int height,Point3f centre) {
		super(textureLocation,width, height,centre);
		
	}
	
	public void setHealth(int newHealth) {
		PlayerObject.health = newHealth;
	}
	

	public int getHealth() {
		return PlayerObject.health;
	}
	
	public int getDirection() {
		return direction;
	}
	
	public void setDirection(int x) {
		direction = x;
	}

}