package no.torsteinv.MS3.framework.client;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Art {
	public static final Bitmap tile = load("/tex/tile.png", 0, 0, 20, 20);

	public static Bitmap load(String file, int xOffs, int yOffs, int width,
			int height) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(Art.class.getResource(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Bitmap n = new Bitmap(width, height);
		img.getRGB(xOffs, yOffs, width, height, n.pixels, 0, width);
		for (int i = 0; i < width * height; i++) {
			Color col = new Color(n.pixels[i]);
			n.pixels[i] = col.getBlue() | col.getGreen() << 8 | col.getRed() << 16;
		}
		return n;
	}
}