public class SavingsAccount extends BankAccount {
    private double interestRate = 0.04; // 4% annual

    public SavingsAccount(String name, String username, String password, double balance) {
        super(name, username, password, balance, "Savings");
    }

    public SavingsAccount(String name, String username, String password, String accountNumber, double balance) {
        super(name, username, password, accountNumber, balance, "Savings");
    }

    public void addInterest() {
        double interest = getBalance() * interestRate;
        deposit(interest);
        System.out.println("Interest of ₹" + interest + " added!");
    }
}
