import java.sql.*;
import java.util.Random;

public class BankAccount {
    protected String name;
    protected String username;
    protected String password;
    protected String accountNumber;
    protected double balance;
    protected String accountType;

    public BankAccount(String name, String username, String password, double balance, String accountType) {
        this.name = name;
        this.username = username;
        this.password = PasswordUtils.hashPassword(password);
        this.balance = balance;
        this.accountType = accountType;
        this.accountNumber = generateAccountNumber();
    }

    public BankAccount(String name, String username, String password, String accountNumber, double balance, String accountType) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.accountType = accountType;
    }

    private String generateAccountNumber() {
        Random rand = new Random();
        StringBuilder accNo = new StringBuilder();
        for (int i = 0; i < 12; i++) accNo.append(rand.nextInt(10));
        return accNo.toString();
    }

    public boolean saveToDB() {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO accounts (name, username, password, account_number, balance, account_type) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, username);
            ps.setString(3, password);
            ps.setString(4, accountNumber);
            ps.setDouble(5, balance);
            ps.setString(6, accountType);
            ps.executeUpdate();
            addTransaction("Deposit", balance); // initial deposit
            return true;
        } catch (SQLException e) {
            System.out.println("Error saving account: " + e.getMessage());
            return false;
        }
    }

    public static BankAccount login(String username, String password) {
        String hashedPassword = PasswordUtils.hashPassword(password);
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM accounts WHERE username=? AND password=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, hashedPassword);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String type = rs.getString("account_type");
                if (type.equalsIgnoreCase("Savings")) {
                    return new SavingsAccount(
                            rs.getString("name"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("account_number"),
                            rs.getDouble("balance")
                    );
                } else {
                    return new CurrentAccount(
                            rs.getString("name"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("account_number"),
                            rs.getDouble("balance")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Login Error: " + e.getMessage());
        }
        return null;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            updateBalanceDB();
            addTransaction("Deposit", amount);
            System.out.println("Deposited ₹" + amount + " successfully.");
        } else {
            System.out.println("Invalid deposit amount!");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            updateBalanceDB();
            addTransaction("Withdraw", amount);
            System.out.println("Withdrawn ₹" + amount + " successfully.");
        } else {
            System.out.println("Insufficient balance!");
        }
    }

    protected void updateBalanceDB() {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE accounts SET balance=? WHERE account_number=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDouble(1, balance);
            ps.setString(2, accountNumber);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating balance: " + e.getMessage());
        }
    }

    protected void addTransaction(String type, double amount) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO transactions (account_number, type, amount) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, accountNumber);
            ps.setString(2, type);
            ps.setDouble(3, amount);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error adding transaction: " + e.getMessage());
        }
    }

    public void showTransactions() {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM transactions WHERE account_number=? ORDER BY date_time";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, accountNumber);
            ResultSet rs = ps.executeQuery();
            System.out.println("\n--- Transaction History ---");
            while (rs.next()) {
                System.out.println(rs.getString("date_time") + " | " + rs.getString("type") + " | ₹" + rs.getDouble("amount"));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching transactions: " + e.getMessage());
        }
    }

    public void displayAccountInfo() {
        System.out.println("\n--- Account Information ---");
        System.out.println("Name: " + name);
        System.out.println("Username: " + username);
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Account Type: " + accountType);
        System.out.println("Balance: ₹" + balance);
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void resetPassword(String currentPassword, String newPassword) {
        if (PasswordUtils.hashPassword(currentPassword).equals(password)) {
            password = PasswordUtils.hashPassword(newPassword);
            try (Connection conn = DBConnection.getConnection()) {
                String sql = "UPDATE accounts SET password=? WHERE account_number=?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, password);
                ps.setString(2, accountNumber);
                ps.executeUpdate();
                System.out.println("Password updated successfully!");
            } catch (SQLException e) {
                System.out.println("Error updating password: " + e.getMessage());
            }
        } else {
            System.out.println("Current password is incorrect!");
        }
    }
}
