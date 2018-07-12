public class PowerUp extends GameObject implements ExplosionListener
{
	public static int LIFETIME = 300;
	
	private Game m_game;
	private String m_type;
	private int m_ticks;
	
	public PowerUp(Game game, String type, int x, int y)
	{
		super(x, y);
		m_game = game;
		m_type = type;
		m_ticks = LIFETIME;
	}
	
	public String type()
	{
		return m_type;
	}
	
	public int ticks()
	{
		return m_ticks;
	}
	
	public void tick()
	{
		--m_ticks;
	}
	
	@Override
	public void explosionOccurred(ExplosionEvent e)
	{
		if(e.row() == getRow() && e.col() == getCol())
			m_game.remove(this);
	}
}
