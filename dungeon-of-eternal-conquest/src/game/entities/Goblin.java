package game.entities;

import java.awt.Color;
import java.io.IOException;

public class Goblin extends Enemy {

	public Goblin() throws IOException {
		super(0, 0, 16, 16, 1, 1, 1, 0, 1, 5, 6, "/sprites/entities/goblin.png");
	}

	@Override
	public Color getColor() {
		return new Color(6, 39, 92);
	}

}
