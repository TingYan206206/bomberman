public class Player extends Character implements ControlListener
{

	public Player(Game game, int x, int y)
	{
		super(game, x, y, "player1");
	}
	public Player(Game game, int x, int y, String spriteSheet)
	{
		super(game,  x,  y,  spriteSheet);
	}


	
	@Override
	public void controlPressed(ControlEvent e)
	{
		switch(e.key())
		{
		case "left":
			setLeft(true);
			if (listener() != null)
				listener().gameStateChanged(new GameStateEvent(id(), "left", 1));
			break;
		case "right":
			setRight(true);
			if (listener() != null)
				listener().gameStateChanged(new GameStateEvent(id(), "right", 1));
			break;
		case "up":
			setUp(true);
			if (listener() != null)
				listener().gameStateChanged(new GameStateEvent(id(), "up", 1));
			break;

			case "down":
			setDown(true);
			if (listener() != null)
				listener().gameStateChanged(new GameStateEvent(id(), "down", 1));
			break;
			case "a":
			if(e.firstTime()) {
				setBomb(true);
				if (listener() != null)
					listener().gameStateChanged(new GameStateEvent(id(), "a", 1));
			}
			break;
		}

	}
	
	@Override
	public void controlReleased(ControlEvent e)
	{
		switch(e.key())
		{
		case "left":
			setLeft(false);
			if (listener() != null)
				listener().gameStateChanged(new GameStateEvent(id(), "left", 0));
			break;
		case "right":
			setRight(false);
			if (listener() != null)
				listener().gameStateChanged(new GameStateEvent(id(), "right", 0));
			break;
		case "up":
			setUp(false);
			if (listener() != null)
				listener().gameStateChanged(new GameStateEvent(id(), "up", 0));
			break;
		case "down":
			setDown(false);
			if (listener() != null)
				listener().gameStateChanged(new GameStateEvent(id(), "down", 0));
			break;
		}
	}
}
