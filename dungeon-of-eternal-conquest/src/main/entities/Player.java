package main.entities;

import java.io.IOException;

import main.Game;
import main.dungeon.Camera;
import main.dungeon.Dungeon;

public class Player extends Entity {

	public Player() throws IOException {
		super(0, 0, 16, 16, 1, 100, 5, 6, "/entities/player.png");
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

}
