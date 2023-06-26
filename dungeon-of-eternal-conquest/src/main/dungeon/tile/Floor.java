package main.dungeon.tile;

import main.util.Spritesheet;

public class Floor extends Tile {

	public Floor(int x, int y, int width, int height, Spritesheet spritesheet) {
		super(x, y, width, height, spritesheet.getSprite(16, 0, 16, 16));
	}

}
