package online_chat;

import javax.swing.*;
import java.sql.*;
import java.awt.event.*;

interface db {

    public void checklogin();
}

public class Login implements db {

    static int alive;
    static String usrid, pwd, chpwd = "";
    static int flag = 0;
    Thread T2 = new Thread() {
        @Override
        public void run() {
            ChatClient.main(null);
        }
    };

    public void login() {
        JFrame jf = new JFrame("LOGIN PAGE");
        JLabel jl = new JLabel("WELCOME TO MUG ONLINE CHAT");
        jl.setBounds(650, 100, 500, 60);
        jf.add(jl);
        JLabel jl1 = new JLabel(" USER_ID : ");
        jl1.setBounds(600, 200, 200, 30);
        jf.add(jl1);
        JLabel jl2 = new JLabel("PASSWORD : ");
        jl2.setBounds(600, 250, 200, 30);
        jf.add(jl2);
        JTextField jtf1 = new JTextField();
        jtf1.setBounds(700, 200, 200, 30);
        jf.add(jtf1);
        JPasswordField jtf2 = new JPasswordField();
        jtf2.setBounds(700, 250, 200, 30);
        jf.add(jtf2);
        JButton jb1 = new JButton("LOGIN");
        jb1.setBounds(600, 300, 100, 60);
        jb1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usrid = jtf1.getText();
                pwd = jtf2.getText();
                checklogin();
                if (flag == 1) {
                    JOptionPane.showMessageDialog(jf, "LOGIN SUCCESSFULLY");
                    T2.start();
                    jf.setVisible(false);
                } else if (flag == 2) {
                    JOptionPane.showMessageDialog(jf, "ENTER USERNAME AND PASSWORD");
                } else {
                    JOptionPane.showMessageDialog(jf, "INCORRECT USERNAME AND PASSWORD");
                }
            }
        });
        jf.add(jb1);
        JButton jb2 = new JButton("REGISTER");
        jb2.setBounds(800, 300, 100, 60);
        jf.add(jb2);
        jb2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Register.main(null);
                jf.setVisible(false);
            }
        });
        jf.setLayout(null);
        jf.setSize(1366, 768);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void checklogin() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/practice", "root", "rootroot");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select password from chat where user_name='" + usrid + "'");
            while (rs.next()) {
                chpwd = rs.getString("password");
            }
            if (usrid.length() == 0 && pwd.length() == 0) {
                flag = 2;
            } else if (pwd.equals(chpwd)) {
                flag = 1;
            } else {
                flag = 0;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String args[]) {
        Login L = new Login();
        Thread T1 = new Thread() {
            @Override
            public void run() {
                L.login();
            }
        };
        T1.start();
    }
}
