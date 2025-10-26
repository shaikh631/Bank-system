public class CurrentAccount extends BankAccount {
    private double overdraftLimit = 5000; // Maximum overdraft allowed

    // Constructor for creating new Current Account
    public CurrentAccount(String name, String username, String password, double balance) {
        super(name, username, password, balance, "Current");
    }

    // Constructor for loading existing Current Account from DB
    public CurrentAccount(String name, String username, String password, String accountNumber, double balance) {
        super(name, username, password, accountNumber, balance, "Current");
    }

    // Override withdraw method to allow overdraft
    @Override
    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid withdrawal amount!");
            return;
        }

        if (amount <= balance + overdraftLimit) {
            balance -= amount;
            updateBalanceDB();
            addTransaction("Withdraw", amount);
            System.out.println("Withdrawn ₹" + amount + " successfully.");
            if (balance < 0) {
                System.out.println("⚠️ Your account is in overdraft! Current balance: ₹" + balance);
            }
        } else {
            System.out.println("❌ Exceeded overdraft limit! Maximum allowed withdrawal: ₹" + (balance + overdraftLimit));
        }
    }

    // Optional: Setter to adjust overdraft limit
    public void setOverdraftLimit(double limit) {
        overdraftLimit = limit;
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }
}
