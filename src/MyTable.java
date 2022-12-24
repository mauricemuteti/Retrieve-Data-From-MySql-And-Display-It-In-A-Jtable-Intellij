/**
 *  https://mauricemuteti.info/retrieve-data-from-mysql-and-display-it-in-a-jtable-in-java-using-intellij/
 *  Retrieve Data From MySql And Display It In A Jtable In Java Using Intellij
 */

import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MyTable {

    public static void main(String[] args) {
        // Create a connection to the database
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/test", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Create a table model
        DefaultTableModel model = new DefaultTableModel();

        // Execute a SELECT query and get the result set
        String query = "SELECT * FROM users";
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);

            // Get the column names
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            String[] columnNames = new String[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                columnNames[i - 1] = metaData.getColumnName(i);
            }
            model.setColumnIdentifiers(columnNames);

            // Add the rows to the table model
            while (rs.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = rs.getObject(i);
                }
                model.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Create the JTable and set the model
        JTable table = new JTable(model);

        // Add the table to a scroll pane
// Create the scroll pane and add the table to it
        JScrollPane scrollPane = new JScrollPane(table);

// Create the frame and add the scroll pane to it
        JFrame frame = new JFrame("Table Example");
        frame.add(scrollPane);

// Set the size and location of the frame
        frame.setSize(500, 300);
        frame.setLocationRelativeTo(null);

// Make the frame visible
        frame.setVisible(true);
    }
}