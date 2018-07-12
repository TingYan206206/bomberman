public class Weapon
{
	public Weapon(WeaponType type)
	{
		m_type = type.name();
		m_attack = type.nextAttack();
	}
	
	public String type()
	{
		return m_type;
	}
	
	public double attack()
	{
		return m_attack;
	}
	
	private String m_type;
	private double m_attack;
}
