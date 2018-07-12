public class ExplosionEvent
{
    // todo: fill this in
    private int row;
    private int col;
    private Bomb m_bomb;

    public ExplosionEvent (Bomb bomb, int row, int col)
    {
        m_bomb = bomb;
        this.row = row;
        this.col = col;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public Bomb getM_bomb() {
        return m_bomb;
    }
}
