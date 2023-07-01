package game.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.IOException;

import game.scenarios.Dungeon;
import game.util.Camera;

public abstract class Enemy extends Entity {
	
	private double maskVisionX;
	private double maskVisionY;
	private int maskVisionW;
	private int maskVisionH;

	public Enemy(double x, double y, int width, int height, double speed, double minDamage, double maxDamage, long shieldTime, double maxLife, int maxFrames, int maxIndex, String spritePath) throws IOException {
		super(x, y, width, height, speed, minDamage, maxDamage, shieldTime, maxLife, maxFrames, maxIndex, spritePath);
	}
	
	public abstract Color getColor();
	
	protected void updateMaskVision() {
		this.maskVisionX = x - 60;
		this.maskVisionY = y - 60;
		this.maskVisionW = 150;
		this.maskVisionH = 150;
	}
	
	public Rectangle getRectangleVision() {
		return new Rectangle((int) maskVisionX, (int) maskVisionY, maskVisionW, maskVisionH);
	}
	
	@Override
	protected void updateMask() {
		this.maskX = x;
		this.maskY = y + 5;
		this.maskW = width;
		this.maskH = height - 7;
		
		this.updateMaskVision();
	}

	@Override
	public void tick(Dungeon dungeon) {
		moved = false;
		
		if (dungeon.player.getRectangle().intersects(this.getRectangleVision())) {
			if (dungeon.player.isAttacking() && dungeon.player.getMaskAttack().intersects(this.getRectangle())) {
				this.takeDamage(dungeon.player.dealDamage());
			}
			
			if (dungeon.player.getRectangle().intersects(this.getRectangle())) {
				dungeon.player.takeDamage(this.dealDamage());
			} else {
				if (maskX < dungeon.player.getX() && dungeon.isFree(this, dungeon.RIGHT)) {
					moved = true;
					x += speed;
					maskX += speed;
					dir = dirRight;
				} else if (maskX > dungeon.player.getX() && dungeon.isFree(this, dungeon.LEFT)) {
					moved = true;
					x -= speed;
					maskX -= speed;
					dir = dirLeft;
				}
				
				if (maskY < dungeon.player.getY() && dungeon.isFree(this, dungeon.DOWN)) {
					moved = true;
					y += speed;
					maskY += speed;
				} else if (maskY > dungeon.player.getY() && dungeon.isFree(this, dungeon.UP)) {
					moved = true;
					y -= speed;
					maskY -= speed;
				}
			}
			
			this.updateMask();
		}
		
		frames++;

		if (frames == maxFrames) {
			frames = 0;
			index++;

			if (index == maxIndex) {
				index = 0;
			}
		}
	}

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
