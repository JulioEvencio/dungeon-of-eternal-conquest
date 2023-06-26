package main.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import main.Game;
import main.dungeon.Camera;
import main.dungeon.Dungeon;
import main.util.Spritesheet;

public class Player extends Entity {

	private int dirRight;
	private int dirLeft;
	private int dir;

	private int frames;
	private final int maxFrames;

	private int index;
	private final int maxIndex;

	private boolean moved;

	private final BufferedImage[] idleRightPlayer;
	private final BufferedImage[] idleLeftPlayer;

	private final BufferedImage[] movingRightPlayer;
	private final BufferedImage[] movingLeftPlayer;

	public Player(double x, double y, int width, int height, double speed, double maxLife) throws IOException {
		super(x, y, width, height, speed, maxLife);

		dirRight = 1;
		dirLeft = 0;
		dir = dirRight;

		frames = 0;
		maxFrames = 5;

		index = 0;
		maxIndex = 6;

		moved = false;

		idleRightPlayer = new BufferedImage[6];
		idleLeftPlayer = new BufferedImage[6];

		movingRightPlayer = new BufferedImage[6];
		movingLeftPlayer = new BufferedImage[6];

		Spritesheet spritesheet = new Spritesheet("/spritesheet-player.png");

		for (int i = 0; i < idleRightPlayer.length; i++) {
			idleRightPlayer[i] = spritesheet.getSprite(0 + (i * 16), 0, 16, 16);
		}

		for (int i = 0; i < idleLeftPlayer.length; i++) {
			idleLeftPlayer[i] = spritesheet.getSprite(0 + (i * 16), 16, 16, 16);
		}

		for (int i = 0; i < movingRightPlayer.length; i++) {
			movingRightPlayer[i] = spritesheet.getSprite(0 + (i * 16), 32, 16, 16);
		}

		for (int i = 0; i < movingLeftPlayer.length; i++) {
			movingLeftPlayer[i] = spritesheet.getSprite(0 + (i * 16), 48, 16, 16);
		}
	}

	@Override
	public void tick() {
		moved = false;

		if (up && Dungeon.isFree((int) x, (int) (y - speed))) {
			moved = true;
			y -= speed;
		}

		if (down && Dungeon.isFree((int) x, (int) (y + speed))) {
			moved = true;
			y += speed;
		}

		if (right && Dungeon.isFree((int) (x + speed), (int) y)) {
			moved = true;
			x += speed;
			dir = dirRight;
		}

		if (left && Dungeon.isFree((int) (x - speed), (int) y)) {
			moved = true;
			x -= speed;
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

		Camera.x = Camera.clamp((int) (x - (Game.WIDTH / 2)), 0, Dungeon.WIDTH * 16 - Game.WIDTH);
		Camera.y = Camera.clamp((int) (y - (Game.HEIGHT / 2)), 0, Dungeon.HEIGHT * 16 - Game.HEIGHT);
	}

	@Override
	public void render(Graphics g) {
		if (dir == dirRight) {
			if (moved) {
				g.drawImage(movingRightPlayer[index], (int) (x - Camera.x), (int) (y - Camera.y), width, height, null);
			} else {
				g.drawImage(idleRightPlayer[index], (int) (x - Camera.x), (int) (y - Camera.y), width, height, null);
			}
		} else {
			if (moved) {
				g.drawImage(movingLeftPlayer[index], (int) (x - Camera.x), (int) (y - Camera.y), width, height, null);
			} else {
				g.drawImage(idleLeftPlayer[index], (int) (x - Camera.x), (int) (y - Camera.y), width, height, null);
			}
		}
	}

}
