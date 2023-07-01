package game.screens;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import game.main.Game;

public class GameOver {
	
	public void render(Graphics graphics) {
		graphics.setColor(new Color(0, 0, 0, 200));
		graphics.fillRect(0, 0, Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE);

		graphics.setColor(Color.RED);
		graphics.setFont(new Font("arial", Font.BOLD, 36));
		graphics.drawString(">Game Over<", Game.WIDTH * Game.SCALE / 2 - 130, Game.HEIGHT * Game.SCALE / 2 - 160);

		graphics.setColor(Color.GREEN);
		graphics.setFont(new Font("arial", Font.BOLD, 24));
		
		graphics.drawString("Press ENTER to continue", Game.WIDTH * Game.SCALE / 2 - 150, 160);
	}
	
	public void renderFullscreen(Graphics graphics, int width, int height) {
		graphics.setColor(new Color(0, 0, 0, 200));
		graphics.fillRect(0, 0, width, height);

		graphics.setColor(Color.RED);
		graphics.setFont(new Font("arial", Font.BOLD, 36));
		graphics.drawString(">Game Over<", width / 2 - 130, height / 2 - 160);

		graphics.setColor(Color.GREEN);
		graphics.setFont(new Font("arial", Font.BOLD, 24));
		graphics.drawString("Press ENTER to continue", width / 2 - 150, height / 2 - 100);
	}
	
}
