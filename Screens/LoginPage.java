package Screens;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginPage extends JFrame implements ActionListener {

    JRadioButton studentRadio, teacherRadio;
    JTextField emailField;
    JPasswordField passwordField;
    JButton loginButton;

    Connection con;

    public LoginPage() {

        setTitle("Student Management System - Login");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImageIcon icon = new ImageIcon(
                LoginPage.class.getResource("/Images/Appicon.png"));
        setIconImage(icon.getImage());

        JPanel backgroundPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon bg = new ImageIcon(
                        LoginPage.class.getResource("/Images/background.jpg"));
                g.drawImage(bg.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        backgroundPanel.setLayout(new GridBagLayout());
        setContentPane(backgroundPanel);

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(400, 320));
        panel.setBackground(new Color(255, 255, 255, 200));
        panel.setLayout(null);

        backgroundPanel.add(panel);

        // Radio Buttons
        studentRadio = new JRadioButton("Student");
        teacherRadio = new JRadioButton("Teacher");

        studentRadio.setBounds(50, 20, 100, 30);
        teacherRadio.setBounds(180, 20, 100, 30);

        ButtonGroup bg = new ButtonGroup();
        bg.add(studentRadio);
        bg.add(teacherRadio);

        panel.add(studentRadio);
        panel.add(teacherRadio);

        // Email
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(30, 70, 80, 25);
        panel.add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(110, 70, 200, 30);
        panel.add(emailField);

        // Password
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(30, 120, 80, 25);
        panel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(110, 120, 200, 30);
        panel.add(passwordField);

        // Login Button
        loginButton = new JButton("Login");
        loginButton.setBounds(120, 180, 100, 35);
        loginButton.setBackground(new Color(0, 102, 204));
        loginButton.setForeground(Color.WHITE);
        loginButton.addActionListener(this);
        panel.add(loginButton);

        connectDB();
        setVisible(true);
    }

    void connectDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/student_management",
                    "root",
                    "root");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Database Connection Failed");
        }
    }

    public void actionPerformed(ActionEvent e) {

        String email = emailField.getText().trim();
        String password = String.valueOf(passwordField.getPassword()).trim();

        if (!studentRadio.isSelected() && !teacherRadio.isSelected()) {
            JOptionPane.showMessageDialog(this, "Please Select Login Type");
            return;
        }

        try {

            if (teacherRadio.isSelected()) {

                PreparedStatement ps = con.prepareStatement(
                        "SELECT * FROM teachers WHERE email=? AND password=?");

                ps.setString(1, email);
                ps.setString(2, password);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {

                    int teacherId = rs.getInt("id");
                    String dept = rs.getString("department");

                    new TeacherDashboard(teacherId, dept);
                    dispose();

                } else {
                    JOptionPane.showMessageDialog(this, "Invalid Teacher Login ❌");
                }

            } else {

               PreparedStatement ps = con.prepareStatement(
        "SELECT * FROM students WHERE email=? AND password=?");

ps.setString(1, email);
ps.setString(2, password);

ResultSet rs = ps.executeQuery();

if (rs.next()) {

    String rollNo = rs.getString("roll_no");
    String name = rs.getString("name");

    new StudentDashboard(rollNo, name);
    dispose();

} else {
    JOptionPane.showMessageDialog(this, "Invalid Student Login ❌");
}
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Login Error ❌");
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new LoginPage();
    }
}