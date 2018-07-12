import java.util.Random;

public class WeaponPartType
{
	public WeaponPartType(String name, double mean, double stdv)
	{
		m_name = name;
		m_mean = mean;
		m_stdv = stdv;
	}
	
	public String name()
	{
		return m_name;
	}
	
	public int nextDelay()
	{
		int delay = (int) (rand.nextGaussian() * m_stdv + m_mean);
		if(delay < 0)
			delay = 0;
		return delay;
	}
	
	private static Random rand = new Random();
	private String m_name;
	private double m_mean, m_stdv;
}
