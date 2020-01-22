package online_chat;
import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class  ChatClient extends JFrame implements ActionListener 
{
    String uname;
    PrintWriter pw;
    BufferedReader br;
    JTextArea  taMessages;
    JTextField tfInput;
    JTextField giveto;
    JButton btnSend,btnExit;
    Socket client;
    
    public ChatClient(String uname,String servername) throws Exception 
    {
        super(uname);  
        this.uname = uname;
        client  = new Socket(servername,9999);
        br = new BufferedReader( new InputStreamReader( client.getInputStream()) ) ;
        pw = new PrintWriter(client.getOutputStream(),true);
        pw.println(uname);  
        buildInterface();
        MessagesThread mt=new MessagesThread();
        mt.start();  
    }
    
    public void buildInterface() 
    {
        btnSend = new JButton("Send");
        btnExit = new JButton("Exit");
        taMessages = new JTextArea();
        taMessages.setRows(10);
        taMessages.setColumns(50);
        taMessages.setEditable(false);
        tfInput  = new JTextField(50);
        giveto=new JTextField(15);
        JScrollPane sp = new JScrollPane(taMessages, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(sp,"Center");
        JPanel bp = new JPanel( new FlowLayout());
        bp.add(tfInput);
        bp.add(btnSend);
        bp.add(btnExit);
        bp.add(giveto);
        add(bp,"South");
        btnSend.addActionListener(this);
        btnExit.addActionListener(this);
        setSize(500,300);
        setVisible(true);
        pack();
    }
    
    @Override
    public void actionPerformed(ActionEvent evt) 
    {
        if ( evt.getSource() == btnExit ) 
        {
            pw.println("end");  
            System.exit(0);
        } 
        else 
        {
            pw.println(tfInput.getText());
            pw.println(giveto.getText());
            tfInput.setText("");
            giveto.setText("");
        }
    }
    
    public static void main(String args[]) 
    {
        String servername = "localhost";  
        try 
        {
          ChatClient cc=new ChatClient( Login.usrid ,servername);
        } 
        catch(Exception ex) 
        {
           System.out.println(ex);
        }      
    } 
    class  MessagesThread extends Thread 
    {
        public void run() 
        {
            String line;
            try 
            {
                while(true) 
                {
                    {
                    line = br.readLine();
                    taMessages.append(line + "\n");
                    }
                } 
            } 
            catch(Exception ex) 
            {
                System.out.println(ex);
            }
        }
    }
} 

