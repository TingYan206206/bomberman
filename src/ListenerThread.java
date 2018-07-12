import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.util.Scanner;

/**
 * Created by tingyan on 7/22/17.
 */
public class ListenerThread extends Thread
{
    private int m_id;
    private Scanner m_scanner;
    private PrintWriter m_writer;
    private GameStateListener m_listener;

    public ListenerThread(int id, Scanner scanner, PrintWriter writer, GameStateListener listener)
    {
        m_id = id;
        m_scanner = scanner;
        m_writer = writer;
        m_listener = listener;
    }

    @Override
    public void run()
    {
        while(m_scanner.hasNextLine())
        {
            m_listener.gameStateChanged(new GameStateEvent(m_scanner.nextLine()));
        }
    }
}
