package main.dungeon.tile;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

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

	public void render(Graphics g) {
		g.drawImage(sprite, x, y, width, height, null);
	}
	
}
