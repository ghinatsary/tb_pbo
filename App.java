import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class App {

    private static void displayMenu() {
        System.out.println("\n========================================");
        System.out.println("                Menu");
        System.out.println("========================================");
        System.out.println("1. Add Customer Data");
        System.out.println("2. View Customer Data");
        System.out.println("3. Update Customer Data");
        System.out.println("4. Delete Customer Data");
        System.out.println("5. Exit");
        System.out.println("========================================");
    }

    public static void main(String[] args) {
        Pedicure pedicure = new Pedicure("Pedicure House Cantik Berseri", "Jl. Sungai Balang");
        pedicure.displayInfo();

        try (Scanner scanner = new Scanner(System.in); Connection conn = DatabaseConnection.connectToDatabase()) {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS customers (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(100), service VARCHAR(50), date DATE)");

            boolean running = true;

            while (running) {
                displayMenu();
                System.out.print("Masukkan Pilihan: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1 -> {
                        System.out.print("Masukkan nama pelanggan: ");
                        String name = scanner.nextLine();

                        pedicure.displayServices();
                        System.out.print("Pilih Layanan (ID): ");
                        int serviceId = scanner.nextInt();
                        System.out.print("Jumlah: ");
                        int quantity = scanner.nextInt();
                        scanner.nextLine();

                        double price = pedicure.calculatePrice(serviceId, quantity);
                        System.out.println("Total Harga: $" + price);

                        String service = pedicure.services.get(serviceId);
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        String date = formatter.format(new Date());

                        String query = "INSERT INTO customers (name, service, date) VALUES (?, ?, ?)";
                        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                            pstmt.setString(1, name);
                            pstmt.setString(2, service);
                            pstmt.setString(3, date);
                            pstmt.executeUpdate();
                            System.out.println("Data pelanggan berhasil ditambahkan....");
                        }
                    }

                    case 2 -> {
                        String query = "SELECT * FROM customers";
                        try (ResultSet rs = stmt.executeQuery(query)) {
                            System.out.println("\n========================================");
                            System.out.println("              Data Pelanggan");
                            System.out.println("========================================");
                            System.out.printf("%-5s %-25s %-20s %-15s%n", "ID", "Name", "Service", "Date");
                            System.out.println("========================================");
                            while (rs.next()) {
                                System.out.printf("%-5d %-25s %-20s %-15s%n",
                                        rs.getInt("id"), rs.getString("name"), rs.getString("service"), rs.getDate("date"));
                            }
                            System.out.println("========================================");
                        }
                    }

                    case 3 -> {
                        System.out.print("Masukkan data pelanggan: ");
                        int id = scanner.nextInt();
                        scanner.nextLine();

                        System.out.print("Masukkan nama baru: ");
                        String name = scanner.nextLine();
                        String query = "UPDATE customers SET name = ? WHERE id = ?";
                        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                            pstmt.setString(1, name);
                            pstmt.setInt(2, id);
                            int rows = pstmt.executeUpdate();
                            if (rows > 0) {
                                System.out.println("Data pelanggan berhasil diubah...");
                            } else {
                                System.out.println("ID pelanggan tidak ditemukan.");
                            }
                        }
                    }

                    case 4 -> {
                        System.out.print("Masukkan ID pelanggan: ");
                        int id = scanner.nextInt();
                        scanner.nextLine();

                        String query = "DELETE FROM customers WHERE id = ?";
                        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                            pstmt.setInt(1, id);
                            int rows = pstmt.executeUpdate();
                            if (rows > 0) {
                                System.out.println("Data pelanggan telah dihapus.");
                            } else {
                                System.out.println("Data pelanggan tidak ditemukan.");
                            }
                        }
                    }

                    case 5 -> {
                        running = false;
                        System.out.println("Terima kasih sudah menggunakan layanan!");
                    }

                    default -> System.out.println("Invalid choice.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.err.println("Salah input, masukkan angka.");
        }
    }
}
