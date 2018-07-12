// todo: make this implement ControlListener

public class Player extends Character implements ControlListener
{
	public Player(Game game, int x, int y)
	{
		super(game, x, y, "player4");
	}
	@Override
	public void controlPressed(ControlEvent e)
	{
		if (e.isM_pressed())
		{
			String direction = e.getM_key();
			switch (direction){
				case "up":    setM_up(true);
					break;
				case "down":  setM_down(true);
					break;
				case "left":  setLeft(true);
					break;
				case "right": setM_right(true);
					break;

				case "b": 	setM_bomb(true);
							int num = this.getM_bombNum();
							this.setM_bombNum(num--);

					         break;

			}
		}
	}

	public void controlReleased(ControlEvent e)
	{
		if (!e.isM_pressed())
		{
			String direction = e.getM_key();
			switch (direction){
				case "up":    setM_up(false);
					break;
				case "down":  setM_down(false);
					break;
				case "left":  setLeft(false);
					break;
				case "right": setM_right(false);
					break;
				case "b": setM_bomb(false);
					break;
			}
		}
	}

}
