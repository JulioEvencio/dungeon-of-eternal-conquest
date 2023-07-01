package game.screens;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import game.main.Game;

public class Tutorial {
	
	public void render(Graphics graphics) {
		graphics.setColor(new Color(0, 0, 0, 200));
		graphics.fillRect(0, 0, Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE);

		graphics.setColor(Color.RED);
		graphics.setFont(new Font("arial", Font.BOLD, 36));
		graphics.drawString(">Tutorial<", Game.WIDTH * Game.SCALE / 2 - 130, Game.HEIGHT * Game.SCALE / 2 - 160);

		graphics.setColor(Color.WHITE);
		graphics.setFont(new Font("arial", Font.BOLD, 16));
		
		graphics.drawString("> W, A, S, D -> use to move", Game.WIDTH * Game.SCALE / 2 - 330, 160);
		graphics.drawString("> SPACE -> use to attack", Game.WIDTH * Game.SCALE / 2 - 330, 180);
		graphics.drawString("> ESC or P -> use to pause the game", Game.WIDTH * Game.SCALE / 2 - 330, 200);
		graphics.drawString("> F2 -> to enable fullscreen", Game.WIDTH * Game.SCALE / 2 - 330, 220);
		graphics.drawString("> F3 -> to enable/disable FPS", Game.WIDTH * Game.SCALE / 2 - 330, 240);
		
		graphics.setColor(Color.GREEN);
		graphics.setFont(new Font("arial", Font.BOLD, 24));
		
		graphics.drawString("Press ENTER to go back to main menu", Game.WIDTH * Game.SCALE / 2 - 230, 410);
	}
	
	public void renderFullscreen(Graphics graphics, int width, int height) {
		graphics.setColor(new Color(0, 0, 0, 200));
		graphics.fillRect(0, 0, width, height);

		graphics.setColor(Color.RED);
		graphics.setFont(new Font("arial", Font.BOLD, 36));
		graphics.drawString(">Tutorial<", width / 2 - 130, height / 2 - 160);

		graphics.setColor(Color.WHITE);
		graphics.setFont(new Font("arial", Font.BOLD, 16));
		
		graphics.drawString("> W, A, S, D -> use to move", width / 2 - 300, height / 2 - 60);
		graphics.drawString("> SPACE -> use to attack", width / 2 - 300, height / 2 - 40);
		graphics.drawString("> ESC or P -> use to pause the game", width / 2 - 300, height / 2 - 20);
		graphics.drawString("> F2 -> to disable fullscreen", width / 2 - 300, height / 2 - 00);
		graphics.drawString("> F3 -> to enable/disable FPS", width / 2 - 300, height / 2 + 20);
		
		graphics.setColor(Color.GREEN);
		graphics.setFont(new Font("arial", Font.BOLD, 24));
		
		graphics.drawString("Press ENTER to go back to main menu", width / 2 - 250, height / 2 + 210);
	}
	
}
