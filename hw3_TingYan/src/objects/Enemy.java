import java.util.Random;

/**
 * Created by tingyan on 7/12/17.
 */
public class Enemy extends Character {


    private int delay = 20;
    private int currentDelay;
    private int actionType; // action type
    private int count;

    public Enemy(Game game, int x, int y, String spriteSheet)
    {
        super(game,x, y,  spriteSheet);
        currentDelay = 0;
        actionType = 0;
        count = 0;

    }
    @Override
    public void update(UpdateEvent e)
    {
        super.update(e);
        currentDelay++;

        if (currentDelay == delay)
        {
            currentDelay = 0;
            count ++;
            if (count == 5)
             {
                Random rand = new Random();
                actionType = rand.nextInt(5);
                count = 0;
            }
            if (actionType == 0) // moving left
            {
                this.setLeft(true);
                this.setM_right(false);
                this.setM_up(false);
                this.setM_down(false);
                this.setM_bomb(false);
            }
            else if (actionType == 1) // moving right
            {
                this.setLeft(false);
                this.setM_right(true);
                this.setM_up(false);
                this.setM_down(false);
                this.setM_bomb(false);
            }
            else if (actionType == 2) // moving up
            {
                this.setLeft(false);
                this.setM_right(false);
                this.setM_up(true);
                this.setM_down(false);
                this.setM_bomb(false);
            }
            else if (actionType == 3) // moving down
            {
                this.setLeft(false);
                this.setM_right(false);
                this.setM_up(false);
                this.setM_down(true);
                this.setM_bomb(false);
            }
            else // throw bomb
            {
                this.setLeft(false);
                this.setM_right(false);
                this.setM_up(false);
                this.setM_down(false);
                this.setM_bomb(true);
                currentDelay = delay-1;
            }


        }
    }
}
