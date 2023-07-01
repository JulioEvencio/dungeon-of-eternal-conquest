package game.entities;

import java.awt.Color;
import java.io.IOException;

import game.resources.Spritesheet;

public class FlyingEye extends Enemy {

	public FlyingEye() throws IOException {
		super(0, 0, 16, 16, 1, 1, 1, 0, 1, 5, 4, "/sprites/entities/flying-eye.png");
	}
	
	@Override
	protected void loadSpritesheet(String spritePath) throws IOException {
		Spritesheet spritesheet = new Spritesheet(spritePath);

		for (int i = 0; i < idleRightEntity.length; i++) {
			idleRightEntity[i] = spritesheet.getSprite(0 + (i * 16), 0, 16, 16);
		}

		for (int i = 0; i < idleLeftEntity.length; i++) {
			idleLeftEntity[i] = spritesheet.getSprite(0 + (i * 16), 16, 16, 16);
		}

		for (int i = 0; i < movingRightEntity.length; i++) {
			movingRightEntity[i] = spritesheet.getSprite(0 + (i * 16), 0, 16, 16);
		}

		for (int i = 0; i < movingLeftEntity.length; i++) {
			movingLeftEntity[i] = spritesheet.getSprite(0 + (i * 16), 16, 16, 16);
		}
	}

	@Override
	public Color getColor() {
		return new Color(179, 29, 12);
	}

}
