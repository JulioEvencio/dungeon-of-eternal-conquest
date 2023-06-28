package main.entities;

import java.io.IOException;

import main.dungeon.Dungeon;

public class Slime extends Entity {

	public Slime() throws IOException {
		super(0, 0, 16, 16, 0.5, 1, 1, 0, 1, 5, 6, "/sprites/entities/slime.png");
	}
	
	@Override
	protected void updateMask() {
		this.maskX = x;
		this.maskY = y + 5;
		this.maskW = width;
		this.maskH = height - 7;
	}

	@Override
	public void tick(Dungeon dungeon) {
		moved = false;
		
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
	}

}
