public class ExplosionEvent
{
	Bomb m_bomb;
	int m_row, m_col;
	
	public ExplosionEvent(Bomb bomb, int row, int col)
	{
		m_bomb = bomb;
		m_row = row;
		m_col = col;
	}
	
	public Bomb bomb()
	{
		return m_bomb;
	}
	
	public int row()
	{
		return m_row;
	}
	
	public int col()
	{
		return m_col;
	}
}
