package util;

import java.sql.*;

public class SQLiteUtil {
    private static final String DB_URL = "jdbc:sqlite:database.db";

    static {
        try {
            Class.forName("org.sqlite.JDBC");
            initializeDatabase();
        } catch (ClassNotFoundException e) {
            System.out.println("SQLite JDBC driver not found: " + e.getMessage());
        }
    }

    public static void initializeDatabase() {
        String sql = "CREATE TABLE IF NOT EXISTS tasks (" +
                "id TEXT PRIMARY KEY," +
                "title TEXT NOT NULL," +
                "description TEXT);";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Database initialized or already set up.");
        } catch (SQLException e) {
            System.out.println("Error initializing the database: " + e.getMessage());
        }
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public static void insertTask(String id, String title, String description) {
        if (!taskExists(id)) {
            String sql = "INSERT INTO tasks (id, title, description) VALUES (?, ?, ?)";
            try (Connection conn = getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, id);
                pstmt.setString(2, title);
                pstmt.setString(3, description);
                pstmt.executeUpdate();
                System.out.println("Task created: " + id);
            } catch (SQLException e) {
                System.out.println("Error inserting task: " + e.getMessage());
            }
        } else {
            System.out.println("Task with ID " + id + " already exists.");
        }
    }

    public static boolean taskExists(String id) {
        String sql = "SELECT 1 FROM tasks WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Error checking task existence: " + e.getMessage());
            return false;
        }
    }

    public static String readTask(String id) {
        String sql = "SELECT id, title, description FROM tasks WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("id") + ": " + rs.getString("title") + " - " + rs.getString("description");
                } else {
                    return "Task not found";
                }
            }
        } catch (SQLException e) {
            System.out.println("Error reading task: " + e.getMessage());
            return "Error retrieving task";
        }
    }

    public static void updateTask(String id, String title, String description) {
        String sql = "UPDATE tasks SET title = ?, description = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setString(2, description);
            pstmt.setString(3, id);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Task updated: " + id);
            } else {
                System.out.println("No task found with ID " + id);
            }
        } catch (SQLException e) {
            System.out.println("Error updating task: " + e.getMessage());
        }
    }

    public static void deleteTask(String id) {
        String sql = "DELETE FROM tasks WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Task deleted: " + id);
            } else {
                System.out.println("No task found with ID " + id);
            }
        } catch (SQLException e) {
            System.out.println("Error deleting task: " + e.getMessage());
        }
    }
}
