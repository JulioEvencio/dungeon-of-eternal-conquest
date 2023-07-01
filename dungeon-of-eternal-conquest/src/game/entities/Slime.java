package game.entities;

import java.awt.Color;
import java.io.IOException;

public class Slime extends Enemy {

	public Slime() throws IOException {
		super(0, 0, 16, 16, 0.5, 1, 1, 0, 1, 5, 6, "/sprites/entities/slime.png");
	}

	@Override
	public Color getColor() {
		return new Color(51, 112, 59);
	}

}
