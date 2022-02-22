package util;

public class GunObject extends GameObject{
	
	
	private double scaleX = 0.0;
	private double scaleY = 0.0;
	private double angle = 0.0;
	private double speed;
	
	public GunObject (String textureLocation, Point3f centre, double sX, double sY, double angle, double velocity) {
		super(textureLocation,50,50,centre);
		this.scaleX = sX;
		this.scaleY = sY;
		this.angle = angle;
		speed = velocity;
	}
	
	public void setAngle(double newAngle) {
		this.angle = newAngle;
	}
	
	public double getScaleX() {
		return scaleX;
	}
	
	public double getScaleY() {
		return scaleY;
	}
	
	public double getAngle() {
		return this.angle;
	}
	
	public double getSpeed() {
		return this.speed;
	}
	

}
