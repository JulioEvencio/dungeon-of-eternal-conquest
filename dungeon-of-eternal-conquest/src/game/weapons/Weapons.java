package game.weapons;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import game.resources.Spritesheet;
import game.scenarios.Camera;

public abstract class Weapons {

	protected double x;
	protected double y;
	protected int width;
	protected int height;

	protected double maskX;
	protected double maskY;
	protected int maskW;
	protected int maskH;

	protected double minDamage;
	protected double maxDamage;
	
	protected boolean isAttacking;

	protected int dir;
	protected final int dirRight;
	protected final int dirLeft;

	protected int frames;
	protected final int maxFrames;

	protected int index;
	protected final int maxIndex;

	private final BufferedImage[] spriteRightAttack;
	private final BufferedImage[] spriteLeftAttack;

	public Weapons(double x, double y, int width, int height, double minDamage, double maxDamage, int maxFrames, int maxIndex, String spritePath) throws IOException {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		this.updateMask();
		
		this.minDamage = minDamage;
		this.maxDamage = maxDamage;
		
		this.isAttacking = false;
		
		this.dirRight = 1;
		this.dirLeft = 0;
		this.dir = this.dirRight;

		this.frames = 0;
		this.maxFrames = maxFrames;

		this.index = 0;
		this.maxIndex = maxIndex;

		spriteRightAttack = new BufferedImage[this.maxIndex];
		spriteLeftAttack = new BufferedImage[this.maxIndex];
		
		Spritesheet spritesheet = new Spritesheet(spritePath);
		
		for (int i = 0; i < spriteRightAttack.length; i++) {
			spriteRightAttack[i] = spritesheet.getSprite(0 + (i * 16), 80, 16, 16);
		}
		
		spriteLeftAttack[0] = spritesheet.getSprite(48, 80, 16, 16);
		spriteLeftAttack[1] = spritesheet.getSprite(64, 80, 16, 16);
		spriteLeftAttack[2] = spritesheet.getSprite(80, 80, 16, 16);
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

	public boolean isAttacking() {
		return isAttacking;
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
	
	public void setDirRight() {
		dir = dirRight;
	}
	
	public void setDirLeft() {
		dir = dirLeft;
	}
	
	public void resetAnimation() {
		index = 0;
		frames = 0;
	}
	
	public void initAttack() {
		this.resetAnimation();
		
		isAttacking = true;
	}
	
	public double dealDamage() {
		return minDamage + (maxDamage - minDamage) * new Random().nextDouble();
	}
	
	public void updateDamage(double minDamage, double maxDamage) {
		this.minDamage = minDamage;
		this.maxDamage = maxDamage;
	}

	public Rectangle getRectangle() {
		return new Rectangle((int) maskX, (int) maskY, maskW, maskH);
	}

	public void tick() {
		frames++;

		if (frames == maxFrames) {
			frames = 0;
			index++;

			if (index == maxIndex) {
				index = 0;

				isAttacking = false;
			}
		}
	}
	
	public void render(Graphics graphics) {
		if (isAttacking) {
			if (dir == dirRight) {
				graphics.drawImage(spriteRightAttack[index], (int) (x - Camera.x), (int) (y - Camera.y), width, height, null);
			} else {
				graphics.drawImage(spriteLeftAttack[index], (int) (x - Camera.x), (int) (y - Camera.y), width, height, null);
			}
		}
	}

}
