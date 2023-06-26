package main.dungeon.tile;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.dungeon.Camera;

public class Tile {

	private final int x;
	private final int y;
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

	public Rectangle getRectangle() {
		return new Rectangle(x, y, width, height);
	}

	public void render(Graphics g) {
		g.drawImage(sprite, x - Camera.x, y - Camera.y, width, height, null);
	}

}
