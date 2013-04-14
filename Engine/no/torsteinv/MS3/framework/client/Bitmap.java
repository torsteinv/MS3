package no.torsteinv.MS3.framework.client;

import static java.lang.Math.PI;
import static java.lang.Math.atan;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

import java.util.ArrayList;
import java.util.Random;

import no.torsteinv.MS3.framework.Game;

public class Bitmap
{
	public int		width;
	public int		height;
	public int[]	pixels;

	public Bitmap(int width, int height)
	{
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
	}

	public static Bitmap createSolidColor(int width, int height, int col)
	{
		Bitmap map = new Bitmap(width, height);
		for (int i = 0; i < width * height; i++)
			map.pixels[i] = col;
		return map;
	}

	public static Bitmap createRandomColor(int width, int height, int... list)
	{
		if ((int) (list.length / 2f) - (float) (list.length / 2f) != 0)
			throw new IllegalArgumentException(
					"every color must have a frequency!");
		if (list.length <= 1)
			throw new IllegalArgumentException("must have atleast one color!");
		Bitmap map = new Bitmap(width, height);
		ArrayList<Integer> colors = new ArrayList<Integer>();
		for (int i = 0; i < list.length; i += 2)
			for (int it = 0; it < list[i + 1]; it++)
				colors.add(list[i]);
		for (int i = 0; i < width * height; i++)
			map.pixels[i] = list.length == 2 ? list[0] : colors
					.get(new Random().nextInt(colors.size()));
		return map;
	}

	public static Bitmap createRandomColorBorder(int width, int height,
			int borderColor, int thickness, int... list)
	{
		return Bitmap.createRandomColor(width, height, list).draw(
				Bitmap.createSolidColorBorder(width, height, 0, borderColor,
						thickness), 0, 0);
	}

	public static Bitmap createSolidColorBorder(int width, int height, int col,
			int colBorder, int borderThickness)
	{
		Bitmap map = new Bitmap(width, height);
		for (int i = 0; i < width; i++)
			for (int j = 0; j < height; j++)
				if (borderThickness >= 0)
					map.pixels[i + j * width] = i <= borderThickness - 1
							|| j <= borderThickness - 1
							|| i >= width - borderThickness
							|| j >= height - borderThickness ? colBorder : col;
				else
					map.pixels[i + j * width] = i >= width + borderThickness

					|| j >= height + borderThickness ? colBorder : col;
		return map;
	}

	public static Bitmap createSolidColorCircle(int size, int col)
	{
		Bitmap map = new Bitmap(size, size);
		for (int i = 0; i < size * size; i++) {
			int xo = (int) (size / 2 - (i % size));
			int yo = (int) (size / 2 - ((i - (size / 2 - xo)) / size));
			if (Math.hypot(xo, yo) < size / 2)
				map.pixels[i] = col;
		}
		return map;
	}

	public Bitmap HorisontalShift(float shift)
	{
		shift = -shift;
		float bitwidth = shift > 0 ? (float) (width + shift * height)
				: (float) (width - shift * height);

		Bitmap Nbit = new Bitmap((int) bitwidth, (int) height);
		for (float x = 0f; x < bitwidth; x++)
			for (float y = 0f; y < height; y++) {

				float yy = y;
				float xx = (x + (y * shift)) % width;
				boolean transp = x < (height - y) * shift - 1
						|| x > (height - y) * shift + width;
				transp = shift < 0 ? (x < y * -shift - 1)
						|| (x > ((y) * -shift) + width) : transp;

				Nbit.pixels[(int) (x) + (int) (y) * (int) (bitwidth)] = transp ? 0
						: pixels[(int) (xx) + (int) (yy) * width];
			}
		return Nbit;
	}

	public Bitmap HorisontalRotatingShift(float shift)
	{
		return scale((float) width, (float) (height * cos(atan(shift))))
				.HorisontalShift(shift);
	}

	public Bitmap scale(float bitwidth, float bitheight)
	{
		Bitmap Nbit = new Bitmap((int) bitwidth, (int) bitheight);
		for (int x = 0; x < (int) bitwidth; x++)
			for (int y = 0; y < (int) bitheight; y++)
				Nbit.pixels[x + y * (int) (bitwidth)] = pixels[(int) ((int) (x * (float) ((int) bitwidth / width)) + (int) (y * (float) ((int) bitheight / height))
						* width)];
		return Nbit;
	}

	/**
	 * Radians
	 */
	public Bitmap Rotate(float angle)
	{
		return RotateAround(angle, width / 2, height / 2);
	}

	/**
	 * Radians
	 */
	public Bitmap RotateAround(float angle, int x, int y)
	{
		x %= width;
		y %= height;
		int bitwidth = ;
		int bitheight = ;
	}

	public Bitmap trim()
	{
		int pixToL = 0;
		int pixToR = 0;
		int pixToT = 0;
		int pixToB = 0;

	}

	public Bitmap trim(int s)
	{
		Bitmap nMap = new Bitmap(width-2,height-2);
		for(int i = 0;i < (width - 2) * (height - 2);i++)
			nMap.pixels[i]=;
		return nMap;
	}

	public int getX(int i)
	{
		return i % width;
	}

	public int getY(int i)
	{
		return (i - getX(i)) / width;
	}

	public int getXY(int x, int y)
	{
		return x + y * width;
	}

	public Bitmap VerticalShift(float shift)
	{
		int bitwidth = (int) (height * sin(PI / 4));
		int bitheight = (int) (height + shift);
		Bitmap Nbit = new Bitmap(bitwidth, bitheight);
		for (int x = 0; x < bitwidth; x++)
			for (int y = 0; y < bitheight; y++) {
				float xx = x - y * shift;
				float yy = y - x * shift;
				try {
					Nbit.pixels[x + y * bitwidth] = pixels[(int) (xx + yy
							* width)];
				} catch (ArrayIndexOutOfBoundsException e) {
					e.printStackTrace();
				}
			}
		return Nbit;
	}

	public void render(Game game)
	{

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {

				float w = 20f;
				float h = (float) (w * sin(PI / 4));

				float xc = x % w;
				float yc = y % h;
				float xyc = y % w;

				float yy = (float) (((yc + n(game.yOffs, h) + n(game.zOffs, h)) % h))
						* (Art.tile.height / h);
				float xx = (float) (((xc + (xyc * 1) + n(game.xOffs, w) + n(
						game.zOffs, w)) % w)) * (Art.tile.width / w);

				// try {
				pixels[x + y * width] = // ((int) (xx * (256 / w)) << 16)
				// + ((int) (yy * (256 / h)));
				Art.tile.pixels[(int) (xx) + (int) (yy) * Art.tile.width];
				/*
				 * } catch (Exception e) { e.printStackTrace(); }
				 */

			}
		}
	}

	private float n(float n, float freq)
	{
		return n < 0 ? (n % freq) + freq : (n % freq);
	}

	public Bitmap clone()
	{
		return new Bitmap(width, height).setPixels(pixels);
	}

	private Bitmap setPixels(int[] pix)
	{
		pixels = pix.clone();
		return this;
	}

	public Bitmap draw(Bitmap b, int xOffs, int yOffs)
	{
		for (int x = 0; x < b.width; x++) {
			int xPix = x + xOffs;
			if (xPix >= width || xPix <= 0)
				continue;
			for (int y = 0; y < b.height; y++) {
				int yPix = y + yOffs;
				if (yPix >= height || yPix <= 0)
					continue;
				int src = b.pixels[x + y * b.width];
				if (src > 0)
					pixels[xPix + yPix * width] = src;
			}
		}
		return this;
	}

}
