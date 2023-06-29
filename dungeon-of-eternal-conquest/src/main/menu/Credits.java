package main.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import main.Game;

public class Credits {
	
	public void render(Graphics graphics) {
		graphics.setColor(new Color(0, 0, 0, 200));
		graphics.fillRect(0, 0, Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE);

		graphics.setColor(Color.RED);
		graphics.setFont(new Font("arial", Font.BOLD, 36));
		graphics.drawString(">Credits<", Game.WIDTH * Game.SCALE / 2 - 130, Game.HEIGHT * Game.SCALE / 2 - 160);

		graphics.setColor(Color.WHITE);
		graphics.setFont(new Font("arial", Font.BOLD, 16));
		
		graphics.drawString("> Programmer: Júlio Igreja", Game.WIDTH * Game.SCALE / 2 - 330, 160);
		graphics.drawString("> Access: https://github.com/JulioEvencio", Game.WIDTH * Game.SCALE / 2 - 330, 180);
		
		graphics.drawString("> Sprites: o_lobster", Game.WIDTH * Game.SCALE / 2 - 330, 260);
		graphics.drawString("> Access: https://o-lobster.itch.io/", Game.WIDTH * Game.SCALE / 2 - 330, 280);
		
		graphics.setColor(Color.GREEN);
		graphics.setFont(new Font("arial", Font.BOLD, 24));
		graphics.drawString("Press ENTER to go back to main menu", Game.WIDTH * Game.SCALE / 2 - 230, 410);
		
		graphics.setColor(Color.WHITE);
		graphics.setFont(new Font("arial", Font.BOLD, 12));
		graphics.drawString("Source code: https://github.com/JulioEvencio/dungeon-of-eternal-conquest", Game.WIDTH * Game.SCALE / 2 - 240, 450);
	}
	
	public void renderFullscreen(Graphics graphics, int width, int height) {
		graphics.setColor(new Color(0, 0, 0, 200));
		graphics.fillRect(0, 0, width, height);

		graphics.setColor(Color.RED);
		graphics.setFont(new Font("arial", Font.BOLD, 36));
		graphics.drawString(">Credits<", width / 2 - 130, height / 2 - 160);

		graphics.setColor(Color.WHITE);
		graphics.setFont(new Font("arial", Font.BOLD, 16));
		
		graphics.drawString("> Programmer: Júlio Igreja", width / 2 - 300, height / 2 - 60);
		graphics.drawString("> Access: https://github.com/JulioEvencio", width / 2 - 300, height / 2 - 40);
		
		graphics.drawString("> Sprites: o_lobster", width / 2 - 300, height / 2 + 60);
		graphics.drawString("> Access: https://o-lobster.itch.io/", width / 2 - 300, height / 2 + 80);
		
		graphics.setColor(Color.GREEN);
		graphics.setFont(new Font("arial", Font.BOLD, 24));
		graphics.drawString("Press ENTER to go back to main menu", width / 2 - 250, height / 2 + 180);
		
		graphics.setColor(Color.WHITE);
		graphics.setFont(new Font("arial", Font.BOLD, 15));
		graphics.drawString("Source code: https://github.com/JulioEvencio/dungeon-of-eternal-conquest", width / 2 - 300, height / 2 + 210);
	}
	
}
