package main.dungeon;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.Game;
import main.dungeon.tile.Floor;
import main.dungeon.tile.Stairway;
import main.dungeon.tile.Tile;
import main.dungeon.tile.Wall;
import main.entities.Player;
import main.util.Spritesheet;

public class Dungeon {

	public static int WIDTH;
	public static int HEIGHT;

	private static Tile[] tiles;

	private Player player;

	public Dungeon(String path, Spritesheet spritesheet, Player player) throws IOException {
		this.player = player;

		BufferedImage map = ImageIO.read(this.getClass().getResource(path));

		int[] pixels = new int[map.getWidth() * map.getHeight()];

		WIDTH = map.getWidth();
		HEIGHT = map.getHeight();

		tiles = new Tile[map.getWidth() * map.getHeight()];

		map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());

		for (int x = 0; x < map.getWidth(); x++) {
			for (int y = 0; y < map.getHeight(); y++) {
				int currentPixel = pixels[x + (y * map.getWidth())];

				tiles[x + (y * WIDTH)] = new Floor(x * 16, y * 16, 16, 16, spritesheet);

				switch (currentPixel) {
					case 0xFF000000:
						tiles[x + (y * WIDTH)] = new Wall(x * 16, y * 16, 16, 16, spritesheet);
						break;
					case 0xFFFF0000:
						tiles[x + (y * WIDTH)] = new Stairway(x * 16, y * 16, 16, 16, spritesheet);
						break;
					case 0xFF0000FF:
						this.player.setPosition(x * 16, y * 16);
						break;
				}
			}
		}
	}

	public void tick() {
		player.tick();
	}

	public void render(Graphics g) {
		int xStart = Camera.x >> 4;
		int yStart = Camera.y >> 4;

		int xFinal = xStart + (Game.WIDTH >> 4);
		int yFinal = yStart + (Game.HEIGHT >> 4);

		for (int x = xStart; x <= xFinal; x++) {
			for (int y = yStart; y <= yFinal; y++) {
				if (x < 0 || y < 0 || x >= WIDTH || y >= HEIGHT) {
					continue;
				}

				Tile tile = tiles[x + (y * WIDTH)];
				tile.render(g);
			}
		}

		player.render(g);
	}

	public static boolean isFree(int xNext, int yNext) {
		int tileSize = 16;
		
		int x1 = xNext / tileSize;
		int y1 = yNext / tileSize;

		int x2 = (xNext + tileSize - 1) / tileSize;
		int y2 = yNext / tileSize;

		int x3 = xNext / tileSize;
		int y3 = (yNext + tileSize - 1) / tileSize;

		int x4 = (xNext + tileSize - 1) / tileSize;
		int y4 = (yNext + tileSize - 1) / tileSize;

		return !(tiles[x1 + (y1 * Dungeon.WIDTH)] instanceof Wall || tiles[x2 + (y2 * Dungeon.WIDTH)] instanceof Wall
				|| tiles[x3 + (y3 * Dungeon.WIDTH)] instanceof Wall || tiles[x4 + (y4 * Dungeon.WIDTH)] instanceof Wall);
	}
	
}
