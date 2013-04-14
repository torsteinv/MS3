package no.torsteinv.MS3.framework.client;

import no.torsteinv.MS3.framework.Component;
import no.torsteinv.MS3.framework.Game;

public class Screen extends Bitmap
{
	public static final int	statheight	= (int) (184 / Component.SCALE);
	public Bitmap			view		= new Bitmap(Component.WIDTH,
												Component.HEIGHT - statheight);
	public Bitmap			stat		= Bitmap.createSolidColor(
												Component.WIDTH, statheight,
												0xeeffaa);
	public Bitmap			black		= Bitmap.createSolidColor(20, 20, 0);
	public Bitmap			test		= Bitmap.createSolidColorBorder(20, 20,
												0xFF00FF, 0x00FF00, 1);

	public Screen(int width, int height)
	{
		super(width, height);
	}

	public void render(Game game)
	{
		for (int i = 0; i < width * height; i++) {
			pixels[i] = 0;
		}
		stat = Bitmap.createSolidColor(Component.WIDTH, statheight, 0xeeffaa);
		view.render(game);
		draw(view, 0, 0);
		stat.draw(test.HorisontalRotatingShift(0), 10 - game.xOffs, 25);
		stat.draw(test.HorisontalRotatingShift(1f), 10, 10);
		stat.draw(test.HorisontalRotatingShift(-1f), 75, 10);
		draw(stat, 0, Component.HEIGHT - statheight);
	}
}
