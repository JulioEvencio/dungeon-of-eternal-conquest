package main.dungeon;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.dungeon.tile.Floor;
import main.dungeon.tile.Tile;
import main.dungeon.tile.Wall;
import main.entities.Player;
import main.util.Spritesheet;

public class Dungeon {

	private final int WIDTH;
	private final int HEIGHT;

	private final Tile[] tiles;

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
		for (int i = 0; i < tiles.length; i++) {
			tiles[i].render(g);
		}

		player.render(g);
	}

}
