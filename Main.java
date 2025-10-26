import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int mainChoice;

        do {
            System.out.println("\n==== Welcome to MySQL Banking System ====");
            System.out.println("1. Create New Account");
            System.out.println("2. Login");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            mainChoice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (mainChoice) {
                case 1:
                    System.out.print("Enter Full Name: ");
                    String name = sc.nextLine();

                    System.out.print("Create Username: ");
                    String username = sc.nextLine();

                    System.out.print("Create Password: ");
                    String password = sc.nextLine();

                    System.out.print("Enter Initial Deposit: ");
                    double balance = sc.nextDouble();
                    sc.nextLine();

                    System.out.println("Choose Account Type: 1. Savings  2. Current");
                    int typeChoice = sc.nextInt();
                    sc.nextLine();

                    BankAccount newAcc;
                    if (typeChoice == 1) {
                        newAcc = new SavingsAccount(name, username, password, balance);
                    } else {
                        newAcc = new CurrentAccount(name, username, password, balance);
                    }

                    if (newAcc.saveToDB()) {
                        System.out.println("\n✅ Account Created Successfully!");
                        newAcc.displayAccountInfo();
                    }
                    break;

                case 2:
                    System.out.print("Enter Username: ");
                    String user = sc.nextLine();
                    System.out.print("Enter Password: ");
                    String pass = sc.nextLine();

                    BankAccount loggedIn = BankAccount.login(user, pass);
                    if (loggedIn != null) {
                        System.out.println("\n✅ Login Successful! Welcome " + loggedIn.username + "!");
                        int choice;
                        do {
                            System.out.println("\n--- Banking Menu ---");
                            System.out.println("1. Deposit");
                            System.out.println("2. Withdraw");
                            System.out.println("3. Check Balance / Account Info");
                            System.out.println("4. Transaction History");
                            if (loggedIn instanceof SavingsAccount) {
                                System.out.println("5. Add Interest");
                            }
                            System.out.println("6. Reset Password");
                            System.out.println("0. Logout");
                            System.out.print("Enter your choice: ");
                            choice = sc.nextInt();
                            sc.nextLine();

                            switch (choice) {
                                case 1:
                                    System.out.print("Enter amount to deposit: ");
                                    double dep = sc.nextDouble();
                                    sc.nextLine();
                                    loggedIn.deposit(dep);
                                    break;
                                case 2:
                                    System.out.print("Enter amount to withdraw: ");
                                    double wit = sc.nextDouble();
                                    sc.nextLine();
                                    loggedIn.withdraw(wit);
                                    break;
                                case 3:
                                    loggedIn.displayAccountInfo();
                                    break;
                                case 4:
                                    loggedIn.showTransactions();
                                    break;
                                case 5:
                                    if (loggedIn instanceof SavingsAccount) {
                                        ((SavingsAccount) loggedIn).addInterest();
                                    } else {
                                        System.out.println("Invalid choice!");
                                    }
                                    break;
                                case 6:
                                    System.out.print("Enter current password: ");
                                    String currPass = sc.nextLine();
                                    System.out.print("Enter new password: ");
                                    String newPass = sc.nextLine();
                                    loggedIn.resetPassword(currPass, newPass);
                                    break;
                                case 0:
                                    System.out.println("Logging out...");
                                    break;
                                default:
                                    System.out.println("Invalid choice!");
                            }
                        } while (choice != 0);
                    } else {
                        System.out.println("❌ Invalid username or password!");
                    }
                    break;

                case 0:
                    System.out.println("Thank you for using the Banking System!");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (mainChoice != 0);

        sc.close();
    }
}
