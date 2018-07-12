import java.util.Queue;
import java.util.List;
import java.util.LinkedList;
import javax.swing.JFrame;

public class Game
{
	private static final int FRAME_RATE = 1000 / 60;
	
	private List<GameObject> m_objects = new LinkedList<>();
	private List<GameSystem> m_systems = new LinkedList<>();
	
	private List<GameObjectListener> m_gameObjectListeners = new LinkedList<>();
	private List<UpdateListener> m_updateListeners = new LinkedList<>();
	
	private Queue<GameObject> m_addQueue = new LinkedList<>();
	private Queue<GameObject> m_removeQueue = new LinkedList<>();
	
	public Game()
	{
		addSystems();
		addGameObjects();
		processAddQueue();
	}
	
	private void addSystems()
	{
		// todo: if needed, add constructor parameters to the systems
		
		JFrame frame = new JFrame();
		add(new RenderingSystem(frame));
		add(new ControlSystem(frame));
		add(new MovementSystem());
		add(new BombSystem(this));
	}
	
	private void addGameObjects()
	{
		add(new Player(this, 32, 32));
		// add two enemies
		add(new Enemy(this, 384,32 ,"player2"));
		add(new Enemy(this, 32,352 ,"player3"));
		add(new Board(this));
	}
	
	public void add(GameSystem system)
	{
		m_systems.add(system);
		if(system instanceof GameObjectListener)
			m_gameObjectListeners.add((GameObjectListener) system);
		if(system instanceof UpdateListener)
			m_updateListeners.add((UpdateListener) system);
	}
	
	public void add(GameObject object)
	{
		m_addQueue.add(object);
	}
	
	public void processAddQueue()
	{
		while(!m_addQueue.isEmpty())
		{
			GameObject object = m_addQueue.remove();
			
			m_objects.add(object);
			if(object instanceof UpdateListener)
				m_updateListeners.add((UpdateListener) object);
			
			for(GameObjectListener listener : m_gameObjectListeners)
				listener.gameObjectAdded(new GameObjectEvent(object));
		}
	}
	
	public void remove(GameObject object)
	{
		m_removeQueue.add(object);
	}
	
	public void processRemoveQueue()
	{
		while(!m_removeQueue.isEmpty())
		{
			GameObject object = m_removeQueue.remove();
			m_objects.remove(object);
			for(GameObjectListener listener : m_gameObjectListeners)
				listener.gameObjectRemoved(new GameObjectEvent(object));
		}
	}
	
	public void run()
	{
		for(GameSystem system : m_systems)
			system.start();
		
		while(true)
		{
			sleep();
			
			for(UpdateListener listener : m_updateListeners)
				listener.update(new UpdateEvent());
			
			processAddQueue();
			processRemoveQueue();
		}
	}
	
	private void sleep()
	{
		try
		{
			Thread.sleep(FRAME_RATE);
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
	}
}
