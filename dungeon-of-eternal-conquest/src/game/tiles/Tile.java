package game.tiles;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import game.scenarios.Camera;

public abstract class Tile {

	private int x;
	private int y;
	private final int width;
	private final int height;

	private final BufferedImage sprite;

	public Tile(int x, int y, int width, int height, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

		this.sprite = sprite;
	}

	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Rectangle getRectangle() {
		return new Rectangle(x, y, width, height);
	}

	public void render(Graphics g) {
		g.drawImage(sprite, x - Camera.x, y - Camera.y, width, height, null);
	}

}
