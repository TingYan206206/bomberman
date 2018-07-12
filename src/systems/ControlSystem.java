import java.util.Queue;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import java.util.concurrent.CopyOnWriteArrayList;

public class ControlSystem extends GameSystem implements KeyListener, GameObjectListener, UpdateListener
{
	private static Map<Integer, String> CODES = initializeCodes();
	private static Map<Integer, String> initializeCodes()
	{
		Map<Integer, String> map = new HashMap<>();
		map.put(KeyEvent.VK_LEFT,	"left");			// note: change VK_LEFT to VK_A for WASD
		map.put(KeyEvent.VK_RIGHT,	"right");			// note: change VK_RIGHT to VK_D for WASD
		map.put(KeyEvent.VK_UP,		"up");				// note: change VK_UP to VK_W for WASD
		map.put(KeyEvent.VK_DOWN,	"down");			// note: change VK_DOWN to VK_S for WASD
		map.put(KeyEvent.VK_Z,		"a");
		map.put(KeyEvent.VK_X,		"b");
		map.put(KeyEvent.VK_ENTER,	"start");
		map.put(KeyEvent.VK_ESCAPE,	"select");
		return map;
	}
	
	private List<ControlListener> m_listeners = new LinkedList<>();
	private Map<String, Boolean> m_keys = new HashMap<>();
	private List<ControlEvent> m_events = new CopyOnWriteArrayList<>();
	
	public ControlSystem(JFrame frame)
	{
		frame.addKeyListener(this);
		for(String value : CODES.values())
			m_keys.put(value, false);
	}
	
	@Override
	public void keyPressed(KeyEvent e)
	{
		String key = CODES.get(e.getKeyCode());
		if(key != null)
		{
			m_events.add(new ControlEvent(key, true, !m_keys.get(key)));
			m_keys.put(key, true);
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e)
	{
		String key = CODES.get(e.getKeyCode());
		if(key != null)
		{
			m_events.add(new ControlEvent(key, false, false));
			m_keys.put(key, false);
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e)
	{}
	
	@Override
	public void gameObjectAdded(GameObjectEvent e)
	{
		if(e.object() instanceof ControlListener)
			m_listeners.add((ControlListener) e.object());
	}
	
	@Override
	public void gameObjectRemoved(GameObjectEvent e)
	{
		m_listeners.remove(e.object());
	}
	
	@Override
	public void update(UpdateEvent e)
	{
		for(ControlEvent event : m_events)
		{
			for(ControlListener listener : m_listeners)
			{
				if(event.pressed())
					listener.controlPressed(event);
				else
					listener.controlReleased(event);
			}
		}
		
		m_events.clear();
	}
}
