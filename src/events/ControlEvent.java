public class ControlEvent
{
	private String m_key;
	private boolean m_pressed, m_firstTime;
	
	public ControlEvent(String key, boolean pressed, boolean firstTime)
	{
		m_key = key;
		m_pressed = pressed;
		m_firstTime = firstTime;
	}
	
	public String key()
	{
		return m_key;
	}
	
	public boolean pressed()
	{
		return m_pressed;
	}
	
	public boolean firstTime()
	{
		return m_firstTime;
	}
}
