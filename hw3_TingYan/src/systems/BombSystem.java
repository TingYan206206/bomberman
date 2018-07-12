import java.util.List;
import java.util.LinkedList;
import java.util.Random;

// todo: make BombSystem implement GameObjectListener

public class BombSystem extends GameSystem implements UpdateListener, GameObjectListener
{
	private Game m_game;
	private Board m_board = null;
	private List<Bomb> m_bombs = new LinkedList<>();
	//private Random random;
	// todo: make a list of ExplosionListeners here
	private List<ExplosionListener> m_explosionListeners = new LinkedList<>();


	public BombSystem(Game game)
	{
		m_game = game;
	}

	// todo: implement GameObjectListener here
	//
	// pseudo-code for gameObjectAdded:
	// if e.object() is a Bomb, add e.object() to m_bombs AND run the following code:
	//     int row = e.object().getY() / m_board.TILE_SIZE;
	//     int col = e.object().getX() / m_board.TILE_SIZE;
	//     m_board.currentAnim(row, col, "bomb");
	// if e.object() is an ExplosionListener, add e.object() to the list of explosion listeners
	// if e.object() is a Board, set m_board to e.object()
	//
	@Override
	public void gameObjectAdded(GameObjectEvent e)
	{
		GameObject obj = e.object();
		if(obj instanceof Bomb)
		{
			m_bombs.add((Bomb) obj);
			int row = obj.getY() / m_board.TILE_SIZE;
			int col = obj.getX() / m_board.TILE_SIZE;
			m_board.currentAnim(row, col, "bomb");

		}
		if (obj instanceof ExplosionListener)
		{
			m_explosionListeners.add((ExplosionListener) obj);
		}
		if (obj instanceof Board)
		{
			m_board = (Board) obj;
		}
	}

	// pseudo-code for gameObjectRemoved:
	// remove e.object() from m_bombs
	// remove e.object() from the list of explosion listeners
	// if e.object() equals m_board, set m_board to null
	@Override
	public void gameObjectRemoved(GameObjectEvent e){
		GameObject obj = e.object();
		if(obj instanceof Bomb)
		{
			m_bombs.remove( obj);

		}
		if (obj instanceof ExplosionListener)
		{
			m_explosionListeners.remove(obj);
		}
		if (obj instanceof Board)
		{
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
		
		// todo: notify all ExplosionListeners that an explosion occurred at row+dy, col+dx
		for (ExplosionListener listener : m_explosionListeners)
		{
			listener.explosionOccurs(new ExplosionEvent(bomb,row+dy,col+dx));
		}

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
		{

			Random rand = new Random();
			int n = rand.nextInt(10);
			if (n < 7) {
				m_board.currentAnim(row + dy, col + dx, "explodingBlock");
			}
			else if (n == 7) {
				m_board.currentAnim(row + dy, col + dx, "powerUpFire");
			}
			else if (n == 8){
				m_board.currentAnim(row + dy, col + dx, "powerUpBomb");

			}
			else {
				m_board.currentAnim(row + dy, col + dx, "powerUpSpeed");
			}

		}
		//else if

		return false;
	}
}
