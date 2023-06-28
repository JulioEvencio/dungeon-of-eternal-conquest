package main.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import main.Game;

public class Menu {

	private final String[] options;
	private int currentOption;
	private int selectedOption;
	private int maxOption;

	private boolean up;
	private boolean down;
	private boolean space;

	public Menu() {
		options = new String[2];
		currentOption = 0;
		selectedOption = Game.GAME_MENU;
		maxOption = options.length - 1;

		up = false;
		down = false;
		space = false;

		options[0] = "New Game";
		options[1] = "Exit";
	}
	
	public void menuUp() {
		up = true;
	}
	
	public void menuDown() {
		down = true;
	}
	
	public void menuSpace() {
		space = true;
	}
	
	public int getOption() {
		return selectedOption;
	}
	
	public void tick() {
		selectedOption = Game.GAME_MENU;
		
		if (up) {
			up = false;
			currentOption--;

			if (currentOption < 0) {
				currentOption = 0;
			}
		}

		if (down) {
			down = false;
			currentOption++;

			if (currentOption > maxOption) {
				currentOption = maxOption;
			}
		}

		if (space) {
			space = false;
			
			if (currentOption == 0) {
				selectedOption = Game.GAME_RUN;
			} else if (currentOption == 1) {
				selectedOption = Game.GAME_EXIT;
			}
		}
	}

	public void render(Graphics graphics) {
		graphics.setColor(new Color(0, 0, 0, 200));
		graphics.fillRect(0, 0, Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE);

		graphics.setColor(Color.RED);
		graphics.setFont(new Font("arial", Font.BOLD, 36));
		graphics.drawString(">Dungeon of Eternal Conquest<", Game.WIDTH * Game.SCALE / 2 - 330, Game.HEIGHT * Game.SCALE / 2 - 160);

		graphics.setColor(Color.WHITE);
		graphics.setFont(new Font("arial", Font.BOLD, 24));
		graphics.drawString(options[0], Game.WIDTH * Game.SCALE / 2 - 50, 160);
		graphics.drawString(options[1], Game.WIDTH * Game.SCALE / 2 - 50, 204);

		if (currentOption == 0) {
			graphics.drawString("-> ", Game.WIDTH * Game.SCALE / 2 - 90, 160);
		} else if (currentOption == 1) {
			graphics.drawString("-> ", Game.WIDTH * Game.SCALE / 2 - 90, 204);
		}
		
		graphics.drawString("Use W and S keys to move and SPACE to select", Game.WIDTH * Game.SCALE / 2 - 305, 304);
		graphics.drawString("Press f2 to enable fullscreen", Game.WIDTH * Game.SCALE / 2 - 305, 354);
		graphics.drawString("Press f3 to enable/disable FPS", Game.WIDTH * Game.SCALE / 2 - 305, 404);
	}
	
	public void renderFullscreen(Graphics graphics, int width, int height) {
		graphics.setColor(new Color(0, 0, 0, 200));
		graphics.fillRect(0, 0, width, height);

		graphics.setColor(Color.RED);
		graphics.setFont(new Font("arial", Font.BOLD, 36));
		graphics.drawString(">Dungeon of Eternal Conquest<", width / 2 - 330, height / 2 - 160);

		graphics.setColor(Color.WHITE);
		graphics.setFont(new Font("arial", Font.BOLD, 24));
		graphics.drawString(options[0], width / 2 - 50, height / 2 - 100);
		graphics.drawString(options[1], width / 2 - 50, height / 2 - 50);

		if (currentOption == 0) {
			graphics.drawString("-> ", width / 2 - 90, height / 2 - 100);
		} else if (currentOption == 1) {
			graphics.drawString("-> ", width / 2 - 90, height / 2 - 50);
		}
		
		graphics.drawString("Use W and S keys to move and SPACE to select", width / 2 - 300, height / 2 + 50);
		graphics.drawString("Press f2 to disable fullscreen", width / 2 - 300, height / 2 + 100);
		graphics.drawString("Press f3 to enable/disable FPS", width / 2 - 300, height / 2 + 150);
	}

}
