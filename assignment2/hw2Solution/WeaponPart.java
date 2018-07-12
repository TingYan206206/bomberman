public class WeaponPart
{
	public WeaponPart(WeaponPartType type)
	{
		m_type = type.name();
		m_delay = type.nextDelay();
	}
	
	public String type()
	{
		return m_type;
	}
	
	public int delay()
	{
		return m_delay;
	}
	
	private String m_type;
	private int m_delay;
}
