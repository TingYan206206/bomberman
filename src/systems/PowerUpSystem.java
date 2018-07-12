import java.util.List;
import java.util.LinkedList;
import java.util.Random;

public class PowerUpSystem extends GameSystem implements GameObjectListener, UpdateListener
{
	private Game m_game;
	private Board m_board = null;
	
	private List<Character> m_characters = new LinkedList<>();
	private List<PowerUp> m_powerUps = new LinkedList<>();
	
	public PowerUpSystem(Game game)
	{
		m_game = game;
	}
	
	@Override
	public void gameObjectAdded(GameObjectEvent e)
	{
		if(e.object() instanceof Character)
			m_characters.add((Character) e.object());
		
		if(e.object() instanceof PowerUp)
		{
			m_powerUps.add((PowerUp) e.object());
			m_board.currentAnim(
				e.object().getRow(),
				e.object().getCol(),
				((PowerUp) e.object()).type()
			);
		}
		
		if(e.object() instanceof Board)
			m_board = (Board) e.object();
	}
	
	@Override
	public void gameObjectRemoved(GameObjectEvent e)
	{
		m_characters.remove(e.object());
		
		if(e.object() instanceof PowerUp)
		{
			m_powerUps.remove(e.object());
			if(!m_board.currentAnim(e.object().getRow(), e.object().getCol()).equals("empty"))
			{
				m_board.currentAnim(
					e.object().getRow(),
					e.object().getCol(),
					"explosion"
				);
			}
		}
		if(e.object() == m_board)
			m_board = null;
	}
	
	@Override
	public void update(UpdateEvent e)
	{
		for(PowerUp powerUp : m_powerUps)
		{
			powerUp.tick();
			if(powerUp.ticks() == 0)
				m_game.remove(powerUp);
			else
			{
				for(Character character : m_characters)
				{
					if(character.getRow() == powerUp.getRow() && character.getCol() == powerUp.getCol())
					{
						switch(powerUp.type())
						{
						case "powerUpSpeed":
							character.increaseSpeed();
							break;
						case "powerUpFire":
							character.increaseBombRange();
							break;
						case "powerUpBomb":
							character.increaseBombCount();
							break;
						}
						
						m_board.currentAnim(powerUp.getRow(), powerUp.getCol(), "empty");
						m_game.remove(powerUp);
						break;
					}
				}
			}
		}
	}
}
