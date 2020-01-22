package online_chat;
import java.io.*;
import java.util.*;
import java.net.*;

public class  ChatServer 
{
  ArrayList<String> users = new ArrayList<>();
  ArrayList<HandleClient> clients = new ArrayList<>();
  String gto;
 public void process() throws Exception  
 {
      ServerSocket server = new ServerSocket(9999,10);
      System.out.println("Server Started...");
      while( true) 
      {
 		 Socket client = server.accept();
 		 HandleClient c = new HandleClient(client);
  		 clients.add(c);
     }  
  }
  
  public void boradcast(String gto,String user, String message)  
  {
      for ( HandleClient c : clients )
            {
	           if (c.getUserName().equals(gto))
                   {
                    c.sendMessage(gto,user,message);
                   }
            }
  }

  class  HandleClient extends Thread 
  {
        String name = "";
	BufferedReader input;
	PrintWriter output;

	public HandleClient(Socket  client) throws Exception 
        {
	 input = new BufferedReader( new InputStreamReader( client.getInputStream())) ;
	 output = new PrintWriter ( client.getOutputStream(),true);
	 name  = input.readLine();
	 users.add(name); 
	 start();
        }

        public void sendMessage(String gto,String uname,String  msg)  
        {
            output.println( uname + ":" + msg);
	}
		
        public String getUserName() 
        {  
            return name; 
        }
        public void run()  
        {
    	     String line;
	     try    
             {
                while(true)   
                {
		 line = input.readLine();
                 gto=input.readLine();
		 if ( line.equals("end") ) 
                 {
		   clients.remove(this);
		   users.remove(name);
		   break;
                 }
		 boradcast(gto,name,line); 
	       } 
	     } 
	     catch(Exception ex) {
	       System.out.println(ex);
	     }
        } 
   }
  public static void main(String args[]) throws Exception 
  {
      ChatServer cs=new ChatServer();
      cs.process();
  } 
} 