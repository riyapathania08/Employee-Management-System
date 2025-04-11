package employee.management.system;

import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class View_Employee extends JFrame implements ActionListener {

    JTable table;
    Choice choiceEMP;
    JButton searchbtn, print, update, back;

    View_Employee() {

        getContentPane().setBackground(new Color(220, 220, 220));
        setLayout(null);

        JLabel searchLabel = new JLabel("Search by employee id");
        searchLabel.setBounds(20, 20, 150, 20);
        add(searchLabel);

        choiceEMP = new Choice();
        choiceEMP.setBounds(180, 20, 150, 20);
        add(choiceEMP);

        try {
            conn c = new conn();
            ResultSet rs = c.statement.executeQuery("SELECT * FROM employee");
            while (rs.next()) {
                choiceEMP.add(rs.getString("empID"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        table = new JTable();
        JScrollPane jp = new JScrollPane(table);
        jp.setBounds(0, 110, 900, 500);
        add(jp);

        searchbtn = new JButton("Search");
        searchbtn.setBounds(20, 70, 80, 30);
        searchbtn.addActionListener(this);  // ✅ You forgot this
        add(searchbtn);

        print = new JButton("Print");
        print.setBounds(120, 70, 80, 30);
        print.addActionListener(this);
        add(print);

        update = new JButton("Update");
        update.setBounds(220, 70, 80, 30);
        update.addActionListener(this);
        add(update);

        back = new JButton("Back");
        back.setBounds(320, 70, 80, 30);
        back.addActionListener(this);
        add(back);

        // Show all employees by default
        try {
            conn c = new conn();
            ResultSet rs = c.statement.executeQuery("SELECT * FROM employee");
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            e.printStackTrace();
        }

        setSize(900, 700);
        setLocation(300, 100);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchbtn) {
            String query = "SELECT * FROM employee WHERE empID = '" + choiceEMP.getSelectedItem() + "'";
            try {
                conn c = new conn();
                ResultSet rs = c.statement.executeQuery(query);
                table.setModel(DbUtils.resultSetToTableModel(rs));
            } catch (Exception e1) {
                e1.printStackTrace();
            }

        } else if (e.getSource() == print) {
            try {
                table.print();
            } catch (Exception e1) {
                e1.printStackTrace();
            }

        } else if (e.getSource() == update) {
            setVisible(false);
            new UpdateEmployee(choiceEMP.getSelectedItem());
            // Open update window here if needed

        } else  {
            setVisible(false);
            new Main_class();

        }
    }

    public static void main(String[] args) {
        new View_Employee();
    }
}
