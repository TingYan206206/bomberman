/**
 * Created by tingyan on 7/22/17.
 */
public class GameStateEvent
{
    private int m_objectId;
    private String m_prop;
    private int m_value;
    //private int m_yValue;
    private boolean m_local;

    public GameStateEvent(int objectId, String prop, int value)
    {
        m_objectId = objectId;
        m_prop = prop;
        m_value = value;
       // m_yValue = yValue;
        m_local = true;
    }

    public GameStateEvent(String serialized)
    {
        int first = serialized.indexOf(" ");
        int last = serialized.lastIndexOf(" ");
        m_objectId = Integer.parseInt(serialized.substring(0, first));
        m_prop = serialized.substring(first + 1, last);
        //m_xValue = Integer.parseInt(serialized.substring(last - 1));
        m_value = Integer.parseInt(serialized.substring(last + 1));
        m_local = false;
    }



    public int objectId()
    {
        return m_objectId;
    }

    public String prop()
    {
        return m_prop;
    }

    public int value()
    {
        return m_value;
    }
//    public int xValue()
//    {
//        return m_xValue;
//    }

    public boolean local()
    {
        return m_local;
    }

    public String toString()
    {
        return m_objectId + " " + m_prop + " " + m_value ;
    }
}

