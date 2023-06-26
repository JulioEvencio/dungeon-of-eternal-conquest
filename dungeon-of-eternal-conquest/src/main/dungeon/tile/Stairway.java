package main.dungeon.tile;

import main.util.Spritesheet;

public class Stairway extends Tile {

	public Stairway(int x, int y, int width, int height, Spritesheet spritesheet) {
		super(x, y, width, height, spritesheet.getSprite(32, 0, 16, 16));
	}

}
