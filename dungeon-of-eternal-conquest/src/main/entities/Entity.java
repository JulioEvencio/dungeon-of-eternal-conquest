package main.entities;

import java.awt.Graphics;
import java.awt.Rectangle;

public class Entity {
	
	protected double x;
	protected double y;
	protected final int width;
	protected final int height;

	protected boolean up;
	protected boolean down;
	protected boolean right;
	protected boolean left;
	
	protected double speed;
	
	protected double life;
	protected final double maxLife;

	protected int maskX;
	protected int maskY;
	protected int maskW;
	protected int maskH;

	public Entity(double x, double y, int width, int height, double speed, double maxLife) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

		this.speed = speed;

		this.maskX = 0;
		this.maskY = 0;
		this.maskW = width;
		this.maskH = height;
		
		this.maxLife = maxLife;
		this.life = maxLife;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public double getSpeed() {
		return speed;
	}

	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public boolean isMoveUp() {
		return up;
	}
	
	public void moveUp() {
		up = true;
	}
	
	public void stopUp() {
		up = false;
	}
	
	public boolean isMoveDown() {
		return down;
	}
	
	public void moveDown() {
		down = true;
	}
	
	public void stopDown() {
		down = false;
	}
	
	public boolean isMoveRight() {
		return right;
	}
	
	public void moveRight() {
		right = true;
	}
	
	public void stopRight() {
		right = false;
	}
	
	public boolean isMoveLeft() {
		return left;
	}
	
	public void moveLeft() {
		left = true;
	}
	
	public void stopLeft() {
		left = false;
	}

	public void setMask(int maskX, int maskY, int maskW, int maskH) {
		this.maskX = maskX;
		this.maskY = maskY;
		this.maskW = maskW;
		this.maskH = maskH;
	}
	
	public boolean isColidding(Entity e) {
		Rectangle e1Mask = new Rectangle((int) this.x + this.maskX, (int) this.y + this.maskY, this.maskW, this.height);
		Rectangle e2Mask = new Rectangle((int) e.x + e.maskX, (int) e.y + e.maskY, e.maskW, e.height);

		return e1Mask.intersects(e2Mask);
	}

	public void tick() {
		// Code
	}

	public void render(Graphics g) {
		// Code
	}
	
}
