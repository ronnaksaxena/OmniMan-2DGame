package util;

public class BulletObject extends GameObject {
	public double angle = 0.0;
	public static double scaleX = 0.0;
	public static double scaleY = 0.0;
	
	public BulletObject (String textureLocation, Point3f centre, double scaleX, double scaleY, double angle) {
		super(textureLocation,50,50,centre);
		BulletObject.scaleX = scaleX;
		BulletObject.scaleY = scaleY;
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
}
