// todo: make this implement ExplosionListener

public class Bomb extends GameObject implements ExplosionListener
{
	private int m_ticks;
	private int m_range;
	
	public Bomb(int x, int y, int ticks, int range)
	{
		super(x, y);
		m_ticks = ticks;
		m_range = range;
	}
	
	public int ticks()
	{
		return m_ticks;
	}
	
	public int range()
	{
		return m_range;
	}
	
	public void tick()
	{
		--m_ticks;
	}
	
	public boolean exploded()
	{
		return m_ticks == 0;
	}

	@Override
	public void explosionOccurs (ExplosionEvent e)
	{
		// location of the explosion
		int c = e.getCol();
		int r = e.getRow();

		// location of the bomb
		int rBomb = getY()/ Board.TILE_SIZE;
		int cBomb = getX()/ Board.TILE_SIZE;

		if(rBomb == r && cBomb == c)
		{
			m_ticks = 2;
		}

	}

}
