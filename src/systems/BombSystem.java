import java.util.List;
import java.util.LinkedList;
import java.util.Random;

public class BombSystem extends GameSystem implements GameObjectListener, UpdateListener, AnimationListener
{
	private Game m_game;
	private Board m_board = null;
	private Random m_rand = new Random();
	
	private List<Bomb> m_bombs = new LinkedList<>();
	private List<ExplosionListener> m_listeners = new LinkedList<>();
	
	public BombSystem(Game game)
	{
		m_game = game;
	}
	
	@Override
	public void gameObjectAdded(GameObjectEvent e)
	{
		if(e.object() instanceof Bomb)
		{
			m_bombs.add((Bomb) e.object());
			m_board.currentAnim(
				e.object().getY() / Board.TILE_SIZE,
				e.object().getX() / Board.TILE_SIZE,
				"bomb"
			);
		}
		
		if(e.object() instanceof ExplosionListener)
			m_listeners.add((ExplosionListener) e.object());
		
		if(e.object() instanceof Board)
		{
			m_board = (Board) e.object();
			m_board.addAnimationListener(this);
		}
	}
	
	@Override
	public void gameObjectRemoved(GameObjectEvent e)
	{
		m_bombs.remove(e.object());
		m_listeners.remove(e.object());
		if(e.object() == m_board)
		{
			m_board.removeAnimationListener(this);
			m_board = null;
		}
	}
	
	@Override
	public void update(UpdateEvent e)
	{
		for(Bomb bomb : m_bombs)
		{
			bomb.tick();
			if(bomb.exploded())
			{
				int row = bomb.getY() / m_board.TILE_SIZE;
				int col = bomb.getX() / m_board.TILE_SIZE;
				
				explode(bomb, row, col, 0, 0);
				for(int i = 1; i <= bomb.range() && explode(bomb, row, col, -i,  0); ++i);
				for(int i = 1; i <= bomb.range() && explode(bomb, row, col,  i,  0); ++i);
				for(int i = 1; i <= bomb.range() && explode(bomb, row, col,  0, -i); ++i);
				for(int i = 1; i <= bomb.range() && explode(bomb, row, col,  0,  i); ++i);
				
				m_game.remove(bomb);
			}
		}
	}
	
	public boolean explode(Bomb bomb, int row, int col, int dy, int dx)
	{
		if(!m_board.inBounds(row + dy, col + dx))
			return false;
		
		ExplosionEvent e = new ExplosionEvent(bomb, row + dy, col + dx);
		for(ExplosionListener listener : m_listeners)
			listener.explosionOccurred(e);
		
		if(dx == 0 && dy == 0)
		{
			m_board.currentAnim(row + dy, col + dx, "middleExplode");
			return true;
		}
		
		if(m_board.emptyAt(row + dy, col + dx))
		{
			if(dy != 0)
			{
				if(Math.abs(dy) < bomb.range())
					m_board.currentAnim(row + dy, col + dx, "verticalExplode");
				else if(dy > 0)
					m_board.currentAnim(row + dy, col + dx, "bottomEndExplode");
				else
					m_board.currentAnim(row + dy, col + dx, "topEndExplode");
			}
			
			if(dx != 0)
			{
				if(Math.abs(dx) < bomb.range())
					m_board.currentAnim(row + dy, col + dx, "horizontalExplode");
				else if(dx > 0)
					m_board.currentAnim(row + dy, col + dx, "rightEndExplode");
				else
					m_board.currentAnim(row + dy, col + dx, "leftEndExplode");
			}
			
			return true;
		}
		else if(m_board.currentAnim(row + dy, col + dx) == "block")
			m_board.currentAnim(row + dy, col + dx, "explodingBlock");
		
		return false;
	}
	
	@Override
	public void animationStarted(AnimationEvent e)
	{}
	
	@Override 
	public void animationEnded(AnimationEvent e)
	{
		if(e.name() == "explodingBlock")
		{
			switch(m_rand.nextInt(10))
			{
			case 0:
				m_game.add(new PowerUp(m_game, "powerUpBomb", e.sprite().getX(), e.sprite().getY()));
				break;
			case 1:
				m_game.add(new PowerUp(m_game, "powerUpFire", e.sprite().getX(), e.sprite().getY()));
				break;
			case 2:
				m_game.add(new PowerUp(m_game, "powerUpSpeed", e.sprite().getX(), e.sprite().getY()));
				break;
			}
		}
	}
}
