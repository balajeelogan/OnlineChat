package online_chat;
import java.util.*;
import java.sql.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.regex.*;
public class Register 
{
    String fn,ln,usr,pwd,cnpwd,mail,mob;
    long phno;
    boolean r1,r2,r3,r31,r4,r5,r6,r7;
    ArrayList<String> al=new ArrayList();
    Register()
    {
        try
        {
        JFrame jf=new JFrame("REGISTER PAGE");
        JLabel jl=new JLabel("WELCOME TO MUG ONLINE CHAT");
        jl.setBounds(650,20,500,60);
        jf.add(jl);
        JLabel jl1=new JLabel("FIRST_NAME :");
        jl1.setBounds(600,100,200,30);
        jf.add(jl1);
        JLabel jl2=new JLabel("LAST_NAME :");
        jl2.setBounds(600,150,200,30);
        jf.add(jl2);
        JLabel jl3=new JLabel("USER_ID :");
        jl3.setBounds(600,200,200,30);
        jf.add(jl3);
        JLabel jl4=new JLabel("EMAIL : ");
        jl4.setBounds(600,250,200,30);
        jf.add(jl4);
        JLabel jl5=new JLabel("PHONE_NO :");
        jl5.setBounds(600,300,200,30);
        jf.add(jl5);
        JLabel jl6=new JLabel("PASSWORD  :");
        jl6.setBounds(600,350,200,30);
        jf.add(jl6);    
        JLabel jl7=new JLabel("RETYPE PASSWORD :");
        jl7.setBounds(560,400,200,30);
        jf.add(jl7);
        JTextField jtf1=new JTextField();
        jtf1.setBounds(700, 100, 200, 30);
        jf.add(jtf1);
        JTextField jtf2=new JTextField();
        jtf2.setBounds(700, 150, 200, 30);
        jf.add(jtf2);
        JTextField jtf3=new JTextField();
        jtf3.setBounds(700, 200, 200, 30);
        jf.add(jtf3);
        JTextField jtf4=new JTextField();
        jtf4.setBounds(700, 250, 200, 30);
        jf.add(jtf4);
        JTextField jtf5=new JTextField();
        jtf5.setBounds(700, 300, 200, 30);
        jf.add(jtf5);
        JPasswordField jtf6=new JPasswordField();
        jtf6.setBounds(700, 350, 200, 30);
        jf.add(jtf6);
        JPasswordField jtf7=new JPasswordField();
        jtf7.setBounds(700, 400, 200, 30);
        jf.add(jtf7);
        JButton jb1=new JButton("REGISTER");
        jb1.setBounds(650,500,100,30);
        jf.add(jb1);
        jb1.addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e)
        {
            fn=jtf1.getText();
            ln=jtf2.getText();
            usr=jtf3.getText();
            mail=jtf4.getText();
            mob=jtf5.getText();
            phno=Long.parseLong(mob);
            pwd=jtf6.getText();
            cnpwd=jtf7.getText();
            r1=(Pattern.matches("[a-zA-Z]{1,12}",fn));
            r2=(Pattern.matches("[a-zA-Z]{1,12}",ln));
            r3=(Pattern.matches("[a-zA-Z][a-zA-Z0-9]{1,12}",usr));
            r4=(Pattern.matches("^(.+)@(.+)$",mail));
            r5=(Pattern.matches("[6789][0-9]{9}",mob));
            r6=(Pattern.matches("[a-zA-Z][a-zA-Z_0-9]{7,12}",pwd));
            if(pwd.equals(cnpwd))
            {
                r7=true;
            }
            JLabel jr1=new JLabel("*ENTER FIRST NAME WITHIN 12");
            jf.add(jr1);
            JLabel jr2=new JLabel("*ENTER LAST NAME WITHIN 12");
            jf.add(jr2);
            JLabel jr3=new JLabel("*ENTER USER NAME WITHIN 12 AND START WITH ALPHABETS");
            jf.add(jr3);
            JLabel jr31=new JLabel("*USERNAME ALREADY REGISTERED");
            jf.add(jr31);
            JLabel jr4=new JLabel("*ENTER THE VALID MAIL ID");
            jf.add(jr4);
            JLabel jr5=new JLabel("*ENTER THE VALID MOBILE NUMBER");
            jf.add(jr5);
            JLabel jr6=new JLabel("*ENTER PASSWORD WITHIN 8-12 AND STARTS WITH ALPHABETS");
            jf.add(jr6);
            JLabel jr7=new JLabel("*PASSWORD DOESN'T MATCH");
            jf.add(jr7);
            chkusr();
            if(r1&&r2&&r3&r31&&r4&&r5&&r6&&r7==true)
            {
            register();
            jf.setVisible(false);
            Login.main(null);
            }
            if(r1==false)
            {
                jr1.setBounds(950,100,500,20); 
            }
            
            if(r2==false)
            {
                jr2.setBounds(950,150,500,20); 
            }
            if(r3==false)
            {
                jr3.setBounds(950,200,500,20); 
            }
            if(r31==false)
            {
                jr31.setBounds(950,200,500,20); 
            }
            if(r4==false)
            {
                jr4.setBounds(950,250,500,20); 
            }
            if(r5==false)
            {
                jr5.setBounds(950,300,500,20); 
            }
            if(r6==false)
            {
                jr6.setBounds(950,350,500,20); 
            }
            if(r7==false)
            {
                jr7.setBounds(950,400,500,20); 
            }
        }
    });
        JLabel jl8=new JLabel("NOTE:User_ID and Password must start with alaphabets\n");
        JLabel jl9=new JLabel("NOTE:All the values should be with in 12\n");
        jl8.setBounds(50,550,700,20);
        jl9.setBounds(50,570,700,20);
        jf.add(jl8);
        jf.add(jl9);
        jf.setLayout(null);
        jf.setSize(1366,768);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
    }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
public void chkusr()
{
    try
        {
            r31=true;
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/practice","root","rootroot");
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select user_name from chat");
            while(rs.next())
            {
                al.add(rs.getString("user_name"));
            }
            if(al.contains(usr))
            {
                r31=false;
                System.out.println(usr);
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
}
public void register()
{
    try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/practice","root","rootroot");
            Statement stmt=con.createStatement();
            stmt.executeUpdate("insert into chat values('"+fn+"','"+ln+"','"+usr+"','"+mail+"','"+mob+"','"+pwd+"','"+cnpwd+"')");
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
}
    public static void main(String args[])
    {
        Register r=new Register();
    }
}
