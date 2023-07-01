package game.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.IOException;

import game.scenarios.Camera;
import game.scenarios.Dungeon;

public class Slime extends Entity {
	
	private double maskVisionX;
	private double maskVisionY;
	private int maskVisionW;
	private int maskVisionH;

	public Slime() throws IOException {
		super(0, 0, 16, 16, 0.5, 1, 1, 0, 1, 5, 6, "/sprites/entities/slime.png");
	}
	
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
		if (!dungeon.player.getRectangle().intersects(this.getRectangleVision())) {
			return;
		}
		
		moved = false;
		
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

		frames++;

		if (frames == maxFrames) {
			frames = 0;
			index++;

			if (index == maxIndex) {
				index = 0;
			}
		}
		
		this.updateMask();
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
