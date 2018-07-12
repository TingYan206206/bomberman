import java.net.Socket;
import java.net.ServerSocket;
import java.util.List;
import java.util.LinkedList;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.IOException;

public class ClientThread extends Thread
{
    private Socket m_socket;
    private Scanner m_scanner;
    private PrintWriter m_writer;
    private GameStateListener m_listener;
    private int m_id;

    public ClientThread(GameStateListener listener) throws IOException
    {
        m_socket = new Socket(ServerThread.HOST, ServerThread.PORT);
        m_scanner = new Scanner(m_socket.getInputStream());
        m_writer = new PrintWriter(m_socket.getOutputStream());
        m_listener = listener;
        m_id = m_scanner.nextInt();
        m_scanner.nextLine();
    }

    public int id()
    {
        return m_id;
    }

    public void send(GameStateEvent e)
    {
        m_writer.println(e.toString());
        m_writer.flush();
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
