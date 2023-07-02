package game.screens;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import game.main.Game;

public class FinalScreen {
	
	public void render(Graphics graphics) {
		graphics.setColor(new Color(0, 0, 0, 200));
		graphics.fillRect(0, 0, Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE);

		graphics.setColor(Color.RED);
		graphics.setFont(new Font("arial", Font.BOLD, 36));
		graphics.drawString(">Dungeon of Eternal Conquest<", Game.WIDTH * Game.SCALE / 2 - 330, Game.HEIGHT * Game.SCALE / 2 - 160);

		graphics.setColor(Color.WHITE);
		graphics.setFont(new Font("arial", Font.BOLD, 16));
		
		graphics.drawString("Wow! You're a game master!", Game.WIDTH * Game.SCALE / 2 - 330, 160);
		graphics.drawString("Congratulations on overcoming all the challenges", Game.WIDTH * Game.SCALE / 2 - 330, 180);
		graphics.drawString("and reaching the end.", Game.WIDTH * Game.SCALE / 2 - 330, 200);
		graphics.drawString("Your determination and skill are impressive.", Game.WIDTH * Game.SCALE / 2 - 330, 220);
		graphics.drawString("Now it's time to celebrate this epic victory.", Game.WIDTH * Game.SCALE / 2 - 330, 240);
		graphics.drawString("Enjoy the moment and celebrate your achievement. You're amazing!", Game.WIDTH * Game.SCALE / 2 - 330, 260);
		
		graphics.setColor(Color.GREEN);
		graphics.setFont(new Font("arial", Font.BOLD, 24));
		graphics.drawString("Press ENTER to continue", Game.WIDTH * Game.SCALE / 2 - 150, 410);
		
		graphics.setColor(Color.WHITE);
		graphics.setFont(new Font("arial", Font.BOLD, 12));
		graphics.drawString("Thanks for playing!", Game.WIDTH * Game.SCALE / 2 - 75, 450);
	}
	
	public void renderFullscreen(Graphics graphics, int width, int height) {
		graphics.setColor(new Color(0, 0, 0, 200));
		graphics.fillRect(0, 0, width, height);

		graphics.setColor(Color.RED);
		graphics.setFont(new Font("arial", Font.BOLD, 36));
		graphics.drawString(">Dungeon of Eternal Conquest<", width / 2 - 330, height / 2 - 160);

		graphics.setColor(Color.WHITE);
		graphics.setFont(new Font("arial", Font.BOLD, 16));
		
		graphics.drawString("Wow! You're a game master!", width / 2 - 300, height / 2 - 60);
		graphics.drawString("Congratulations on overcoming all the challenges and reaching the end.", width / 2 - 300, height / 2 - 40);
		graphics.drawString("Your determination and skill are impressive.", width / 2 - 300, height / 2 - 20);
		graphics.drawString("Now it's time to celebrate this epic victory.", width / 2 - 300, height / 2);
		graphics.drawString("Enjoy the moment and celebrate your achievement. You're amazing!", width / 2 - 300, height / 2 + 20);
		
		graphics.setColor(Color.GREEN);
		graphics.setFont(new Font("arial", Font.BOLD, 24));
		graphics.drawString("Press ENTER to continue", width / 2 - 150, height / 2 + 180);
		
		graphics.setColor(Color.WHITE);
		graphics.setFont(new Font("arial", Font.BOLD, 15));
		graphics.drawString("Thanks for playing!", width / 2 - 75, height / 2 + 210);
	}
	
}
