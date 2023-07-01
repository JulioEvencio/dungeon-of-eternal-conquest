package game.tiles;

import game.resources.Spritesheet;

public class Wall extends Tile {

	public Wall(int x, int y, int width, int height, Spritesheet spritesheet) {
		super(x, y, width, height, spritesheet.getSprite(0, 0, 16, 16));
	}

}
