package util;

/*

Created by Ronnak Saxena on 27/02/2022 (Student ID: 21210758)

*/
public class EnemyObject extends GameObject implements Cloneable {
	//direction the enemy is moving towards or away
	public double curAngle = 0.0;
	private double speed = 1.0;
	private int damage = 5;
	
	
	public EnemyObject (String textureLocation,int width,int height,Point3f centre, double curAngle, double sp, int dmg) {
		super(textureLocation,width, height,centre);
		this.curAngle = curAngle;
		this.speed = sp;
		this.damage = dmg;
	}
	
	public void setcurAngle(double newAngle) {
		this.curAngle = newAngle;
	}
	

	public double getcurAngle() {
		return this.curAngle;
	}
	public Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }
	public double getSpeed() {
		return this.speed;
	}
	public double getDamage() {
		return this.damage;
	}
	
}

