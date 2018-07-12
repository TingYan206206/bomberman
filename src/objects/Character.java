import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.List;
import java.util.LinkedList;
import java.awt.Color;
import java.awt.Graphics;

public class Character extends Sprite implements UpdateListener, AnimationListener, ExplosionListener
{
	private Game m_game = null;
	private List<Bomb> m_bombs = new LinkedList<>();
	
	private int m_velocityX = 0, m_velocityY = 0, m_speed = 2;
	private int m_width = 25, m_height = 25;
	
	protected boolean m_left	= false;
	protected boolean m_right	= false;
	protected boolean m_up	    = false;
	protected boolean m_down	= false;
	protected boolean m_bomb	= false;
	protected boolean m_dead	= false;
	
	private int m_bombTicks = 100;
	private int m_bombRange = 1;
	private int m_bombCount = 1;
	
	public Character(Game game, int x, int y, String spriteSheet)
	{
		super(x, y, 1, spriteSheet);
		m_game = game;
		addAnimationListener(this);
	}
	
	public void setLeft(boolean left)
	{
		m_left = left;
	}
	
	public void setRight(boolean right)
	{
		m_right = right;
	}
	
	public void setUp(boolean up)
	{
		m_up = up;
	}
	
	public void setDown(boolean down)
	{
		m_down = down;
	}
	
	public void setBomb(boolean bomb)
	{
		m_bomb = bomb;
	}
	
	public void die()
	{
		if(!m_dead)
		{
			m_dead = true;
			currentAnim("die");
		}
	}
	
	public void increaseSpeed()
	{
		if(m_speed < 5)
			++m_speed;
	}
	
	public void increaseBombRange()
	{
		++m_bombRange;
	}
	
	public void increaseBombCount()
	{
		++m_bombCount;
	}
	
	public int velocityX()
	{
		return m_velocityX;
	}
	
	public void velocityX(int direction)
	{
		m_velocityX = m_speed * direction;
	}
	
	public int velocityY()
	{
		return m_velocityY;
	}
	
	public void velocityY(int direction)
	{
		m_velocityY = m_speed * direction;
	}
	
	public int width()
	{
		return m_width;
	}
	
	public int height()
	{
		return m_height;
	}
	
	@Override
	public int dx()
	{
		return (m_width - SCALE * image().getWidth()) / 2;
	}
	
	@Override
	public int dy()
	{
		return -SCALE * image().getHeight() / 2;
	}
	
	@Override
	public void update(UpdateEvent e)
	{
		super.update(e);
		
		if(m_dead)
		{
			velocityX(0);
			velocityY(0);
			return;
		}
		
		if(m_left && !m_right)
			velocityX(-1);
		else if(m_right && !m_left)
			velocityX(1);
		else
			velocityX(0);
		
		if(m_up && !m_down)
			velocityY(-1);
		else if(m_down && !m_up)
			velocityY(1);
		else
			velocityY(0);
		
		if(velocityY() > 0)
			currentAnim("walkDown");
		else if(velocityX() < 0)
			currentAnim("walkLeft");
		else if(velocityX() > 0)
			currentAnim("walkRight");
		else if(velocityY() < 0)
			currentAnim("walkUp");
		else
		{
			switch(currentAnim())
			{
			case "walkLeft":
				currentAnim("idleLeft");
				break;
			case "walkRight":
				currentAnim("idleRight");
				break;
			case "walkUp":
				currentAnim("idleUp");
				break;
			case "walkDown":
				currentAnim("idleDown");
				break;
			}
		}
		
		if(m_bomb)
		{
			if(m_bombCount > 0)
			{
				Bomb bomb = new Bomb(
					getX() + width() / 2,
					getY() + height() / 2,
					m_bombTicks,
					m_bombRange
				);
				
				m_game.add(bomb);
				m_bombs.add(bomb);
				
				--m_bombCount;
			}
			
			m_bomb = false;
		}
	}
	
	@Override
	public void animationStarted(AnimationEvent e)
	{}
	
	@Override
	public void animationEnded(AnimationEvent e)
	{
		if(e.name() == "die")
			m_game.remove(this);
	}
	
	@Override
	public void explosionOccurred(ExplosionEvent e)
	{
		for(Bomb bomb : m_bombs)
		{
			if(bomb == e.bomb())
			{
				++m_bombCount;
				m_bombs.remove(bomb);
				break;
			}
		}
		
		if(e.row() == getRow() && e.col() == getCol())
			die();
	}


}
