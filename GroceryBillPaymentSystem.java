import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class GroceryBillPaymentSystem {
    private static final double INITIAL_CARD_BALANCE = 100.0;
    private static Map<String, Double> cardBalances = new HashMap<>();
    private static Map<String, String> userPins = new HashMap<>();
    private static String userId;
    private static String userPin;
    private static String userName;
    private static String userMobile;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your name: ");
        userName = scanner.nextLine();

        while (true) {
            System.out.print("Enter your mobile number: ");
            userMobile = scanner.nextLine();
            if (userMobile.matches("\\d{10}")) {
                break;
            } else {
                System.out.println("Invalid mobile number. Please enter a 10-digit number.");
            }
        }

        System.out.println("Thank you for providing your details!");
        System.out.println("Enter 1 to open the menu or 0 to exit:");
        int initialChoice = scanner.nextInt();

        if (initialChoice == 0) {
            System.out.println("Thank you for visiting our grocery, exiting now.");
            scanner.close();
            return;
        } else if (initialChoice == 1) {
            boolean running = true;
            while (running) {
                System.out.println("\nEnter your choice:");
                System.out.println("1. View User Details");
                System.out.println("2. Purchase Grocery Shopping Card");
                System.out.println("3. Purchase Items Using Grocery Card");
                System.out.println("4. View Remaining Card Balance");
                System.out.println("5. Recharge Grocery Card Balance");
                System.out.println("6. Exit");

                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        viewUserDetails();
                        break;
                    case 2:
                        purchaseGroceryCard();
                        break;
                    case 3:
                        purchaseItems(scanner);
                        break;
                    case 4:
                        viewCardBalance();
                        break;
                    case 5:
                        rechargeCard(scanner);
                        break;
                    case 6:
                        System.out.println("Thank you for visiting our grocery, " + userName + "!");
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice, please try again.");
                }
            }
        } else {
            System.out.println("Invalid input. Exiting...");
        }
        scanner.close();
    }

    private static void viewUserDetails() {
        System.out.println("\nUser Details:");
        System.out.println("Name: " + userName);
        System.out.println("Mobile Number: " + userMobile);
        if (userId != null) {
            System.out.println("Grocery Card User ID: " + userId);
        } else {
            System.out.println("No Grocery Card purchased yet.");
        }
    }

    private static void purchaseGroceryCard() {
        if (userId == null) {
            userId = generateUserId();
            userPin = generatePin();
            cardBalances.put(userId, INITIAL_CARD_BALANCE);
            userPins.put(userId, userPin);
            System.out.println("\nGrocery Card purchased successfully!");
            System.out.println("User ID: " + userId);
            System.out.println("PIN: " + userPin);
            System.out.println("Initial Balance: $" + INITIAL_CARD_BALANCE);
        } else {
            System.out.println("You already have a Grocery Card with User ID: " + userId);
        }
    }

    private static void purchaseItems(Scanner scanner) {
        if (userId == null) {
            System.out.println("Please purchase a Grocery Card first.");
            return;
        }

        System.out.print("Enter User ID: ");
        String enteredUserId = scanner.next();
        System.out.print("Enter PIN: ");
        String enteredPin = scanner.next();

        if (!enteredUserId.equals(userId) || !enteredPin.equals(userPin)) {
            System.out.println("Invalid User ID or PIN. Access denied.");
            return;
        }

        System.out.print("Enter the number of items purchased: ");
        int itemCount = scanner.nextInt();

        double totalCost = 0;
        for (int i = 1; i <= itemCount; i++) {
            System.out.print("Enter the cost of item " + i + ": ");
            double cost = scanner.nextDouble();
            totalCost += cost;
        }

        System.out.println("Total cost of items: $" + totalCost);

        double currentBalance = cardBalances.get(userId);
        if (currentBalance >= totalCost) {
            cardBalances.put(userId, currentBalance - totalCost);
            System.out.println("Payment successful! Remaining balance: $" + (currentBalance - totalCost));
        } else {
            System.out.println("Insufficient card balance. Please recharge your card.");
        }
    }

    private static void viewCardBalance() {
        if (userId != null) {
            System.out.println("Remaining Card Balance: $" + cardBalances.get(userId));
        } else {
            System.out.println("No Grocery Card purchased yet.");
        }
    }

    private static void rechargeCard(Scanner scanner) {
        if (userId == null) {
            System.out.println("Please purchase a Grocery Card first.");
            return;
        }

        System.out.print("Enter recharge amount: ");
        double rechargeAmount = scanner.nextDouble();
        double currentBalance = cardBalances.get(userId);
        cardBalances.put(userId, currentBalance + rechargeAmount);
        System.out.println("Recharge successful! New balance: $" + (currentBalance + rechargeAmount));
    }

    private static String generateUserId() {
        Random random = new Random();
        return String.valueOf(1000 + random.nextInt(9000));
    }

    private static String generatePin() {
        Random random = new Random();
        return String.format("%04d", random.nextInt(10000));
    }
}
