package main.util;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Spritesheet {
	
	private BufferedImage spritesheet;

	public Spritesheet(String path) throws IOException {
		spritesheet = ImageIO.read(this.getClass().getResource(path));
	}

	public BufferedImage getSprite(int x, int y, int width, int height) {
		return spritesheet.getSubimage(x, y, width, height);
	}

}
