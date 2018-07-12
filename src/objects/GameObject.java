public class GameObject
{
	private static int NEXT_ID = 0;
	private int m_id, m_x, m_y;
	private GameStateListener m_listener = null;
	
	public GameObject()
	{
		this(0, 0);
	}
	
	public GameObject(int x, int y)
	{
		m_id = NEXT_ID++;
		m_x = x;
		m_y = y;
	}
	public void setListener(GameStateListener listener)
	{
		m_listener = listener;
	}

	public GameStateListener listener()
	{
		return m_listener;
	}

	public int id()
	{
		return m_id;
	}
	public void setID (int id)
	{
		m_id = id;
	}

	public synchronized int getX()
	{
		return m_x;
	}
	
	public synchronized void setX(int x)
	{
		m_x = x;
		if(m_listener != null)
			m_listener.gameStateChanged(new GameStateEvent(m_id, "x", m_x));
	}
	
	public synchronized int getY()
	{
		return m_y;
	}
	
	public synchronized void setY(int y)
	{
		m_y = y;
		if(m_listener != null)
			m_listener.gameStateChanged(new GameStateEvent(m_id, "y", m_y));
	}
	
	public synchronized int getRow()
	{
		return m_y / Board.TILE_SIZE;
	}
	
	public synchronized int getCol()
	{
		return m_x / Board.TILE_SIZE;
	}
}
