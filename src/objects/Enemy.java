import java.util.Arrays;
import java.util.Random;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Enemy extends Character implements UpdateListener
{
	private Random m_rand = new Random();
	private int m_current = 0;
	private int m_delay = 30;
	
	private int m_cycle = 1;
	
	private List<Integer> preamble = Arrays.asList(
		0, 4, 1, 3, 5, 4, 2, 0, 5, 4, 1, 1, 3, 5
	);
	private int m_preambleIndex = 0;
	
	private int m_lastRand = 0;
	
	public Enemy(Game game, int x, int y, String sprite)
	{
		super(game, x, y, sprite);
	}
	
	@Override
	public void update(UpdateEvent e)
	{
		super.update(e);
		
		++m_current;
		if(m_current >= m_delay * m_cycle)
		{
			m_current = 0;
			switch(m_lastRand++)
			{
			case 0:
				setUp(false);
				setLeft(true);
				break;
			case 1:
				setLeft(false);
				break;
			case 2:
				setBomb(true);
				setRight(true);
				break;
			case 3:
				setRight(false);
				setDown(true);
				break;
			case 4:
				break;
			case 5:
				setDown(false);
				break;
			case 6:
				setBomb(true);
				setUp(true);
				break;
			default:
				m_lastRand = 0;
				++m_cycle;
			}
		}
		
		/*
		++m_current;
		if(m_current >= m_delay)
		{
			m_current = 0;
			
			switch(m_lastRand)
			{
			case 0:
				setLeft(false);
				break;
			case 1:
				setRight(false);
				break;
			case 2:
				setUp(false);
				break;
			case 3:
				setDown(false);
				break;
			}
			
			if(m_preambleIndex < preamble.size())
				m_lastRand = preamble.get(m_preambleIndex++);
			else
				m_lastRand = m_rand.nextInt(6);
			
			switch(m_lastRand)
			{
			case 0:
				setLeft(true);
				break;
			case 1:
				setRight(true);
				break;
			case 2:
				setUp(true);
				break;
			case 3:
				setDown(true);
				break;
			case 4:
				setBomb(true);
				break;
			}
		}
		*/
	}
}
