package main.dungeon.tile;

import main.util.Spritesheet;

public class Wall extends Tile {

	public Wall(int x, int y, int width, int height, Spritesheet spritesheet) {
		super(x, y, width, height, spritesheet.getSprite(0, 0, 16, 16));
	}

}
