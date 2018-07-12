import java.util.List;
import java.util.Random;
import java.util.Collections;

// immutable; thread-safe
public class WeaponType
{
	public WeaponType(String name, List<WeaponPartType> parts, double mean, double stdv)
	{
		m_name	= name;
		m_parts	= Collections.unmodifiableList(parts);
		m_mean	= mean;
		m_stdv	= stdv;
	}
	
	public String name()
	{
		return m_name;
	}
	
	public List<WeaponPartType> parts()
	{
		return m_parts;
	}
	
	public double nextAttack()
	{
		double delay = rand.nextGaussian() * m_stdv + m_mean;
		if(delay < 1.0)
			delay = 1.0;
		return delay;
	}
	
	private static final Random rand = new Random();
	private final String m_name;
	private final List<WeaponPartType> m_parts;
	private final double m_mean, m_stdv;
}
