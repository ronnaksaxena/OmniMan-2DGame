package util;

public class GunObject extends GameObject{
	
	public static double angle = 0.0;
	public static double scaleX = 0.0;
	public static double scaleY = 0.0;
	
	public GunObject (String textureLocation, Point3f centre, double scaleX, double scaleY, double angle) {
		super(textureLocation,50,50,centre);
		GunObject.scaleX = scaleX;
		GunObject.scaleY = scaleY;
		GunObject.angle = angle;
	}
	
	public void setAngle(double newAngle) {
		GunObject.angle = newAngle;
	}
	
	public double getScaleX() {
		return GunObject.scaleX;
	}
	
	public double getScaleY() {
		return GunObject.scaleY;
	}
	
	public double getAngle() {
		return GunObject.angle;
	}
	

}
