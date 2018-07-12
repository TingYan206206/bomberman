public class ControlEvent
{
    // todo: fill this in
    private String m_key;
    private boolean m_pressed;
    private boolean m_pressedFirst;

    public ControlEvent (String key, boolean pressed, boolean pressedFirst)
    {
        m_key = key;
        m_pressed = pressed;
        m_pressedFirst = pressedFirst;
    }

    public boolean isM_pressed()  {return m_pressed;}

    public boolean isM_pressedFirst() {
        return m_pressedFirst;
    }

    public String getM_key() {
        return m_key;
    }
}

