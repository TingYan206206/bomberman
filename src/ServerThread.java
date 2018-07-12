import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by tingyan on 7/22/17.
 */
import java.net.Socket;
import java.net.ServerSocket;


public class ServerThread extends Thread
{
    public static final String HOST = "localhost";
    public static final int PORT = 1337;

    private MultiplayerSystem m_system;
    private List<Socket> m_clients = new LinkedList<>();
    private List<Scanner> m_scanners = new LinkedList<>();
    private List<PrintWriter> m_writers = new LinkedList<>();
    private List<ListenerThread> m_threads = new LinkedList<>();

    public ServerThread(MultiplayerSystem system)
    {
        m_system = system;
    }

    public void send(GameStateEvent e)
    {
        String serialized = e.toString();
        for(PrintWriter writer : m_writers)
        {
            writer.println(serialized);
            writer.flush();
        }
    }

    @Override
    public void run()
    {
        try
        {
            ServerSocket server = new ServerSocket(PORT);
            while(true)
            {
                Socket client = server.accept();
                int clientId = m_clients.size();

                Scanner scanner = new Scanner(client.getInputStream());
                PrintWriter writer = new PrintWriter(client.getOutputStream());
                ListenerThread thread = new ListenerThread(clientId, scanner, writer, m_system);

                writer.println(clientId);
                writer.flush();

                m_clients.add(client);
                m_scanners.add(scanner);
                m_writers.add(writer);
                m_threads.add(thread);

                //m_system.syncNewClient(writer);
                thread.start();
            }
        }
        catch(IOException e)
        {
            System.out.println("Shutting down server.");
            for(Socket s : m_clients)
            {
                try
                {
                    s.close();
                }
                catch(IOException ex)
                {
                    // intentionally empty
                }
            }
        }
    }
}
