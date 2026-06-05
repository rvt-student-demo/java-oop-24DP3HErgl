package rvt.produkti;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {

private static final String DB_URL = "jdbc:sqlite:products.db";
private static Connection connection;

public static Connection getConnection() throws SQLException {
if (connection == null || connection.isClosed()) {
connection = DriverManager.getConnection(DB_URL);
}
return connection;
}

public static void initializeDatabase() throws SQLException {
String createCategories =
"CREATE TABLE IF NOT EXISTS categories (" +
"id INTEGER PRIMARY KEY AUTOINCREMENT, " +
"name TEXT NOT NULL UNIQUE)";

String createProducts =
"CREATE TABLE IF NOT EXISTS products (" +
"id INTEGER PRIMARY KEY AUTOINCREMENT, " +
"name TEXT NOT NULL, " +
"price REAL NOT NULL, " +
"category_id INTEGER NOT NULL, " +
"FOREIGN KEY (category_id) REFERENCES categories(id))";

try (Statement stmt = getConnection().createStatement()) {
stmt.execute(createCategories);
stmt.execute(createProducts);
System.out.println("Datubaze inicializeta !");
}
}

public static void closeConnection() {
try {
if (connection != null && !connection.isClosed()) {
connection.close();
}
} catch (SQLException e) {
System.err.println("Kļūda: " + e.getMessage());
}
}
}   

