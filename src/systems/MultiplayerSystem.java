import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by tingyan on 7/15/17.
 */
public class MultiplayerSystem extends GameSystem implements  GameObjectListener, GameStateListener
{
//    private Server m_server;
//
//    //private List<Player> playerList = new LinkedList<>();
//    private PrintWriter writer = null;
//    private Scanner scanner = null;
//    public static int MAX_PACKET_LENGTH = 1024;


    private Game m_game;
    //private List<Player> m_characters = new LinkedList<>();
    private Board m_board = null;
    private Thread m_thread = null;
    private boolean m_isServer;
    private Character m_p1 = null;
    private Character m_p2 = null;
    private Character m_p3 = null;






    public MultiplayerSystem (Game game,boolean isServer) {
        m_game = game;
        m_isServer = isServer;
    }

    @Override
    public void start()
    {

            if(m_isServer)
            {
                m_game.add(m_p1 = new Character(m_game,32, 32,"player1"));
                m_game.add(m_p2 = new Character(m_game,416, 32, "player2"));
                m_game.add(m_p3 = new Character(m_game,32, 360, "player3"));
                System.out.println("p1 object Id : "+ m_p1.id());
                System.out.println("p2 object Id : "+ m_p2.id());
                System.out.println("p3 object Id : "+ m_p3.id());
                ServerThread thread = new ServerThread(this);
                thread.start();

                m_thread = thread;

            }


            else
            {
                try{
                    ClientThread thread = new ClientThread(this);
                    // System.out.println("thread Id is " + thread.id());

                if(thread.id() == 0)
                {
                    m_game.add(m_p1 = new Player(m_game,32, 32,"player1"));
                    m_game.add(m_p2 = new Character(m_game,416, 32, "player2"));
                    m_game.add(m_p3 = new Character(m_game,32, 360, "player3"));
                    m_p1.setID(1);
                    //System.out.println("p1 object Id : "+ m_p1.id());

                }
                else if(thread.id() == 1)
                {

                    m_game.add(m_p2 = new Player(m_game,416, 32, "player2"));
                    m_game.add(m_p1 = new Character(m_game,32, 32,"player1"));
                    m_game.add(m_p3 = new Character(m_game,32, 360, "player3"));
                    m_p2.setID(2);
                    // System.out.println("p2 object Id : "+ m_p2.id());
                }
                else if(thread.id() == 2)
                {
                    m_game.add(m_p3 = new Player(m_game,32, 360, "player3"));
                    m_game.add(m_p1 = new Character(m_game,32, 32,"player1"));
                    m_game.add(m_p2 = new Character(m_game,416, 32, "player2"));
                    m_p3.setID(3);
                }
                else
                {
                    throw new IOException("Too many clients connected!");
                }
                thread.start();

                m_thread = thread;


            }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
        }
    }

    @Override
    public void gameObjectAdded(GameObjectEvent e)
   {
       if(e.object() instanceof Board)
           m_board = (Board) e.object();
   }

    @Override
    public void gameObjectRemoved(GameObjectEvent e)
    {
        if(e.object() == m_board)
            m_board = null;
    }


    @Override
    public void gameStateChanged(GameStateEvent e)
    {
        if(e.local())
        {
            if(!m_isServer)
            {
                // client sends local events to the server
                if(e.prop().equals("down") || e.prop().equals("up") || e.prop().equals("left") || e.prop().equals("right") || e.prop().equals("a"))
                {
                    ClientThread thread = (ClientThread) m_thread;
                    thread.send(e);
                }
            }
            else
            {
                // server sends all local events to clients
                ServerThread thread = (ServerThread) m_thread;
                thread.send(e);
            }
        }

        // handle remote events (from client or from server)
        if(!e.local())
        {
            // server re-sends the event to the other clients
            if(m_isServer)
            {
                ServerThread thread = (ServerThread) m_thread;
                thread.send(e);
            }

            // both client and server update the game state
//            if(e.objectId() == 0)
//            {
////                if(e.prop().equals("empty")) {
////                    m_board.currentAnim(e.value(), e.value(), "empty");
////                    System.out.println("BBBB");
////                }
//                System.out.println("AAAAA");
////                else if(e.prop().equals("y"))
////                    m_ball.y(e.value());
////                else if(e.prop().equals("vx"))
////                    m_ball.vx(e.value());
////                else if(e.prop().equals("vy"))
////                    m_ball.vy(e.value());
//            }

            if(e.objectId() == 1)
            {
                if(e.prop().equals("up"))
                    m_p1.setUp(e.value() == 1);
                else if(e.prop().equals("down"))
                    m_p1.setDown(e.value() == 1);
                else if(e.prop().equals("left"))
                    m_p1.setLeft(e.value() == 1);
                else if(e.prop().equals("right"))
                    m_p1.setRight(e.value() == 1);
                else if(e.prop().equals("a"))
                    m_p1.setBomb(e.value() == 1);
            }
             if(e.objectId() == 2)
            {
                if(e.prop().equals("up"))
                    m_p2.setUp(e.value() == 1);
                else if(e.prop().equals("down"))
                    m_p2.setDown(e.value() == 1);
                else if(e.prop().equals("left"))
                    m_p2.setLeft(e.value() == 1);
                else if(e.prop().equals("right"))
                    m_p2.setRight(e.value() == 1);
                else if(e.prop().equals("a"))
                    m_p2.setBomb(e.value() == 1);
            }
            if(e.objectId() == 3)
            {
                if(e.prop().equals("up"))
                    m_p3.setUp(e.value() == 1);
                else if(e.prop().equals("down"))
                    m_p3.setDown(e.value() == 1);
                else if(e.prop().equals("left"))
                    m_p3.setLeft(e.value() == 1);
                else if(e.prop().equals("right"))
                    m_p3.setRight(e.value() == 1);
                else if(e.prop().equals("a"))
                    m_p3.setBomb(e.value() == 1);
            }
        }
    }
}
