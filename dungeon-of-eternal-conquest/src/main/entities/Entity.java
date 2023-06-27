package main.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import main.dungeon.Camera;
import main.dungeon.Dungeon;
import main.util.Spritesheet;

public abstract class Entity {

	protected double x;
	protected double y;
	protected final int width;
	protected final int height;
	
	protected double maskX;
	protected double maskY;
	protected int maskW;
	protected int maskH;

	protected boolean up;
	protected boolean down;
	protected boolean right;
	protected boolean left;

	protected double speed;

	protected double life;
	protected double maxLife;

	protected int dir;
	protected final int dirRight;
	protected final int dirLeft;

	protected int frames;
	protected final int maxFrames;

	protected int index;
	protected final int maxIndex;

	protected boolean moved;

	protected final BufferedImage[] idleRightEntity;
	protected final BufferedImage[] idleLeftEntity;

	protected final BufferedImage[] movingRightEntity;
	protected final BufferedImage[] movingLeftEntity;

	public Entity(double x, double y, int width, int height, double speed, double maxLife, int maxFrames, int maxIndex, String spritePath) throws IOException {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		this.updateMask();

		this.speed = speed;

		this.maxLife = maxLife;
		this.life = maxLife;
		
		this.dirRight = 1;
		this.dirLeft = 0;
		this.dir = this.dirRight;

		this.frames = 0;
		this.maxFrames = maxFrames;

		this.index = 0;
		this.maxIndex = maxIndex;
		
		this.moved = false;

		idleRightEntity = new BufferedImage[this.maxIndex];
		idleLeftEntity = new BufferedImage[this.maxIndex];

		movingRightEntity = new BufferedImage[this.maxIndex];
		movingLeftEntity = new BufferedImage[this.maxIndex];
		
		Spritesheet spritesheet = new Spritesheet(spritePath);

		for (int i = 0; i < idleRightEntity.length; i++) {
			idleRightEntity[i] = spritesheet.getSprite(0 + (i * 16), 0, 16, 16);
		}

		for (int i = 0; i < idleLeftEntity.length; i++) {
			idleLeftEntity[i] = spritesheet.getSprite(0 + (i * 16), 16, 16, 16);
		}

		for (int i = 0; i < movingRightEntity.length; i++) {
			movingRightEntity[i] = spritesheet.getSprite(0 + (i * 16), 32, 16, 16);
		}

		for (int i = 0; i < movingLeftEntity.length; i++) {
			movingLeftEntity[i] = spritesheet.getSprite(0 + (i * 16), 48, 16, 16);
		}
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

		this.updateMask();
	}

	protected void updateMask() {
		this.maskX = x;
		this.maskY = y;
		this.maskW = width;
		this.maskH = height;
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

	public Rectangle getRectangle() {
		return new Rectangle((int) maskX, (int) maskY, maskW, maskH);
	}

	public abstract void tick(Dungeon dungeon);

	public void render(Graphics graphics) {
		if (dir == dirRight) {
			if (moved) {
				graphics.drawImage(movingRightEntity[index], (int) (x - Camera.x), (int) (y - Camera.y), width, height, null);
			} else {
				graphics.drawImage(idleRightEntity[index], (int) (x - Camera.x), (int) (y - Camera.y), width, height, null);
			}
		} else {
			if (moved) {
				graphics.drawImage(movingLeftEntity[index], (int) (x - Camera.x), (int) (y - Camera.y), width, height, null);
			} else {
				graphics.drawImage(idleLeftEntity[index], (int) (x - Camera.x), (int) (y - Camera.y), width, height, null);
			}
		}
	}

}
