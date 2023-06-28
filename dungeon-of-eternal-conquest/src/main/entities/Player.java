package main.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import main.Game;
import main.dungeon.Camera;
import main.dungeon.Dungeon;
import main.util.Spritesheet;

public class Player extends Entity {

	private final BufferedImage spriteLife;
	
	public Player() throws IOException {
		super(0, 0, 16, 16, 1, 1, 5, 2, 4, 5, 6, "/sprites/entities/player.png");

		Spritesheet spritesheet = new Spritesheet("/sprites/entities/player.png");

		spriteLife = spritesheet.getSprite(0, 64, 80, 16);
	}

	@Override
	protected void updateMask() {
		this.maskX = x + 4;
		this.maskY = y + 4;
		this.maskW = width - 8;
		this.maskH = height - 6;
	}

	@Override
	public void tick(Dungeon dungeon) {
		moved = false;

		if (up && dungeon.isFree(this, dungeon.UP)) {
			moved = true;
			y -= speed;
			maskY -= speed;
		}

		if (down && dungeon.isFree(this, dungeon.DOWN)) {
			moved = true;
			y += speed;
			maskY += speed;
		}

		if (right && dungeon.isFree(this, dungeon.RIGHT)) {
			moved = true;
			x += speed;
			maskX += speed;
			dir = dirRight;
		}

		if (left && dungeon.isFree(this, dungeon.LEFT)) {
			moved = true;
			x -= speed;
			maskX -= speed;
			dir = dirLeft;
		}

		frames++;

		if (frames == maxFrames) {
			frames = 0;
			index++;

			if (index == maxIndex) {
				index = 0;
			}
		}

		Camera.x = Camera.clamp((int) (x - (Game.WIDTH / 2)), 0, dungeon.WIDTH * 16 - Game.WIDTH);
		Camera.y = Camera.clamp((int) (y - (Game.HEIGHT / 2)), 0, dungeon.HEIGHT * 16 - Game.HEIGHT);
	}
	
	private void showLife(Graphics graphics) {
		graphics.setColor(Color.RED);
		
		if (life >= 1) {
			graphics.fillRect(17, 2, 15, 11);
		}
		
		if (life >= 2) {
			graphics.fillRect(33, 2, 15, 11);
		}
		
		if (life >= 3) {
			graphics.fillRect(47, 2, 15, 11);
		}
		
		if (life == 4) {
			graphics.fillRect(63, 2, 15, 11);
		}

		graphics.drawImage(spriteLife, 0, 0, 80, 16, null);
	}
	
	@Override
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
		
		this.showLife(graphics);
	}

}
