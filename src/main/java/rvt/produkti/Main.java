package rvt.produkti;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            DatabaseConnection.initializeDatabase();
        } catch (SQLException e) {
            System.out.println("Kļūda inicializējot datubāzi: " + e.getMessage());
        }
        Scanner scanner = new Scanner(System.in);


        while (true) {
            System.out.println("Produktu un kategoriju sistema");
            System.out.println("1 Pievienot kategoriju");
            System.out.println("2 Pievienot produktu");
            System.out.println("3 Paradit visas kategorijas");
            System.out.println("4 Paradit visus produktus");
            System.out.println("5 Meklet produktus pec kategorijas ID");
            System.out.println("0 Iziet");
            System.out.println("Izveleties darbibu: ");


            int izvele = scanner.nextInt();
            scanner.nextLine(); 

            switch (izvele) {
                case 1:
                pievienotKategoriju(scanner);
                    break;

                case 2:
                pievienotProduktu(scanner);
                    break;
                case 3:
                paraditKategorijas(scanner);
                    break;
                case 4:
                paraditProduktus(scanner);
                    break;
                case 5:
                mekletPecKategorijas(scanner);
                    break;
                case 0:
                System.out.println("Programma beidz darbu. Atā!");
                scanner.close();
                    return;
                default:
                    System.out.println("Nepareiza izvēle, mēģini vēlreiz!");
            }
        }
    }
    private static void pievienotKategoriju(Scanner scanner) {
        System.out.println("Ievadi kategorijas nosaukumu: ");
        String nosaukums = scanner.nextLine();

                String sql = "INSERT INTO categories (name) VALUES (?)";

                try (Connection conn = DatabaseConnection.getConnection();
                     PreparedStatement pstmt = conn.prepareStatement(sql)) {

                    pstmt.setString(1, nosaukums);
                    pstmt.executeUpdate();
                    System.out.println("Kategorija saglabāta!");

                } catch (SQLException e) {
                    System.out.println("Kļūda pievienojot kategoriju: " + e.getMessage());
            
                }
            } 
    
    private static void pievienotProduktu(Scanner scanner) {
        System.out.println("Ievadi produkta nosaukumu: ");
        String nosaukums = scanner.nextLine();
        System.out.println("Ievadi produkta cenu: ");
        double cena = scanner.nextDouble();
        System.out.println("Ievadi kategorijas ID: ");
        int kategorijasId = scanner.nextInt();

        String sql = "INSERT INTO products (name, price, category_id) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nosaukums);
            pstmt.setDouble(2, cena);
            pstmt.setInt(3, kategorijasId);
            pstmt.executeUpdate();
            System.out.println("Produkts saglabāts!");

        } catch (SQLException e) {
            System.out.println("Kļūda pievienojot produktu: " + e.getMessage());
        }
    } 

    private static void paraditKategorijas(Scanner scanner) {
        String sql = "SELECT id, name FROM categories";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            System.out.println("Kategorijas:");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + ", Nosaukums: " + rs.getString("name"));
            }

        } catch (SQLException e) {
            System.out.println("Kļūda parādot kategorijas: " + e.getMessage());
        }
    }

    private static void paraditProduktus(Scanner scanner) {
        String sql = "SELECT p.id, p.name, p.price, c.name AS kategorija FROM products p LEFT JOIN categories c ON p.category_id = c.id";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            System.out.println("Produkti:");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + ", Nosaukums: " + rs.getString("name") + ", Cena: " + rs.getDouble("price") + ", Kategorija: " + rs.getString("kategorija"));
            }

        } catch (SQLException e) {
            System.out.println("Kļūda parādot produktus: " + e.getMessage());
        }
    }

    private static void mekletPecKategorijas(Scanner scanner) {
        System.out.println("Ievadi kategorijas ID: ");
        int kategorijasId = scanner.nextInt();

        String sql = "SELECT id, name, price FROM products WHERE category_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, kategorijasId);
            ResultSet rs = pstmt.executeQuery();

            System.out.println("Produkti kategorijā ID " + kategorijasId + ":");
            boolean atrasts = false;
            while (rs.next()) {
                atrasts = true;
                System.out.println("ID: " + rs.getInt("id") + ", Nosaukums: " + rs.getString("name") + ", Cena: " + rs.getDouble("price"));
            }

            if (!atrasts) {
                System.out.println("Nav atrasti produkti šajā kategorijā.");
            }

        } catch (SQLException e) {
            System.out.println("Kļūda meklējot produktus pēc kategorijas: " + e.getMessage());
        }
    }
}
