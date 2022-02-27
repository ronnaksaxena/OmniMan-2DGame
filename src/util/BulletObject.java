package util;

/*

Created by Ronnak Saxena on 27/02/2022 (Student ID: 21210758)

*/
public class BulletObject extends GameObject implements Cloneable {
	private double  angle = 0.0;
	private double scaleX = 0.0; //change back to static
	private double scaleY = 0.0;
	
	public BulletObject (String textureLocation, Point3f centre, double scaleX, double scaleY, double angle) {
		super(textureLocation,50,50,centre);
		this.scaleX = scaleX;
		this.scaleY = scaleY;
		this.angle = angle;
	}
	
	public void setAngle(double newAngle) {
		this.angle = newAngle;
	}
	
	public double getScaleX() {
		return this.scaleX;
	}
	
	public double getScaleY() {
		return this.scaleY;
	}
	
	public double getAngle() {
		return this.angle;
	}
	
	public Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }
}
