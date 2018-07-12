import java.util.List;
import java.util.LinkedList;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Board extends GameObject implements ExplosionListener
{
	public static final int ROWS = 13;
	public static final int COLS = 15;
	public static final int TILE_SIZE = 32;
	
	private List<Sprite> sprites = new LinkedList<>();
	
	public Board(Game g)
	{
		super(0, 0);
		System.out.println("board object id: " + this.id());
		
		String map =
			"222222222222222" +
			"200101111011002" +
			"202121202020202" +
			"211110111111112" +
			"212121212120212" +
			"211110111111012" +
			"212120212021212" +
			"211010110111112" +
			"212121202021212" +
			"211011111111102" +
			"202120212021202" +
			"200011111111002" +
			"222222222222222";
		
		for(int i = 0; i < ROWS; ++i)
		{
			for(int j = 0; j < COLS; ++j)
			{
				Sprite s = new Sprite(j * TILE_SIZE, i * TILE_SIZE, 0, "tiles");
				
				switch(map.charAt(i * COLS + j))
				{
				case '0':
					s.currentAnim("empty");
					break;
				case '1':
					s.currentAnim("block");
					break;
				case '2':
					s.currentAnim("solid");
					break;
				}
				
				sprites.add(s);
				g.add(s);
			}
		}
	}
	
	public boolean inBounds(int row, int col)
	{
		return row >= 0 && row < ROWS && col >= 0 && col < COLS;
	}
	
	public String currentAnim(int row, int col)
	{
		return sprites.get(row * COLS + col).currentAnim();
	}
	
	public void currentAnim(int row, int col, String anim)
	{
		sprites.get(row * COLS + col).currentAnim(anim);
	}
	
	public Sprite sprite(int row, int col)
	{
		return sprites.get(row * ROWS + col);
	}
	
	public boolean emptyAt(int row, int col)
	{
		if(!inBounds(row, col))
			return false;
		
		String anim = currentAnim(row, col);
		return anim != "block" && anim != "solid" && anim != "bomb";
	}
	
	public void addAnimationListener(AnimationListener listener)
	{
		for(Sprite s : sprites)
			s.addAnimationListener(listener);
	}
	
	public void removeAnimationListener(AnimationListener listener)
	{
		for(Sprite s : sprites)
			s.removeAnimationListener(listener);
	}

	@Override
	public void explosionOccurred(ExplosionEvent e)
	{
		if (this.currentAnim(e.row(),e.col())!= "solid")
		{
			this.currentAnim(e.row(), e.col(), "empty");
			if (listener() != null)
				listener().gameStateChanged(new GameStateEvent(id(), "empty",e.row()));

		}
	}
}