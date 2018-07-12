// todo: make this implement ExplosionListener

import java.awt.Color;
import java.awt.Graphics;

public class Character extends Sprite implements UpdateListener, AnimationListener,ExplosionListener
{
	private Game m_game = null;
	
	private int m_velocityX = 0, m_velocityY = 0, m_speed = 3;
	private int m_width = 25, m_height = 25;
	private int m_bombNum = 20;
	
	// todo: add getters and setters for control variables (left, right, up, down, and bomb)
	
	private boolean m_left	= false;
	private boolean m_right	= false;
	private boolean m_up	= false;
	private boolean m_down	= false;
	private boolean m_bomb	= false;
	private boolean m_dead	= false;
	
	private int m_bombTicks = 100;
	private int m_bombRange = 2;
	
	public Character(Game game, int x, int y)
	{
		super(x, y, 1, "player1");
		
		// save the game so we can add bombs to it
		m_game = game;
	}
	
	public Character(Game game, int x, int y, String spriteSheet)
	{
		super(x, y, 1, spriteSheet);
		m_game = game;
	}

	// todo: add getters and setters for control variables (left, right, up, down, and bomb)
	public boolean getLeft()
	{
		return m_left;
	}
	public boolean getRight()
	{
		return m_right;
	}
	public boolean getUp()
	{
		return m_up;
	}
	public boolean getDown()
	{
		return m_down;
	}
	public boolean getBomb () {return m_bomb;}

	public int getM_speed()		{return m_speed;}
	public int getM_bombRange()  {return m_bombRange;}
	public int getM_bombNum()   {return m_bombNum;}

	public void setM_bombNum(int n)		{m_bombNum = n;}
	public void setLeft(boolean l)  {m_left = l;}
	public void setM_right(boolean r) {m_right = r;}
	public void setM_up(boolean u)	{m_up = u;}
	public void setM_down (boolean d)	{m_down = d;}
	public void setM_bomb (boolean b)	{m_bomb = b;}

	public void setM_bombRange (int r)	{m_bombRange =r;}
	public void setM_speed (int s)		{m_speed = s;}

	public void die()
	{
		if(!m_dead)
		{
			m_dead = true;
			addAnimationListener(this);
			currentAnim("die");
		}
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
			// todo: add a new Bomb to m_game at the appropriate location
			if (m_bombNum > 0) {
				m_game.add(new Bomb(getX(), getY(), m_bombTicks, m_bombRange));
				m_bomb = false;
			}
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
	public void explosionOccurs (ExplosionEvent e) {
		// location of the explosion
		int c = e.getCol();
		int r = e.getRow();

		// location of the character
		int rChar = getY() / Board.TILE_SIZE;
		int cChar = getX() / Board.TILE_SIZE;

		if (rChar == r && cChar == c) {
			die();

		}
	}
}
