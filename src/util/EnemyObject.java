package util;

public class EnemyObject extends GameObject {
	//direction the enemy is moving towards or away
	public double curAngle = 0.0;
	
	public EnemyObject (String textureLocation,int width,int height,Point3f centre, double curAngle) {
		super(textureLocation,width, height,centre);
		this.curAngle = curAngle;
	}
	
	public void setcurAngle(double newAngle) {
		this.curAngle = newAngle;
	}
	

	public double getcurAngle() {
		return this.curAngle;
	}
}

