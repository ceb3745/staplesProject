import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to the Staples Multi-Purpose Database Application! Are you a...\n\n" +
                           "1. Administrator?\n" +
                           "2. Cashier?\n" +
                           "3. Customer?\n" +
                           "4. Store Manager?\n" +
                           "5. Vendor?");

        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();

        switch (choice) {
            case 1:
                // Run Admin UI
                break;
            case 2:
                // Run Cashier UI
                break;
            case 3:
                // Run Customer UI
                break;
            case 4:
                // Run Store Manager UI
                break;
            case 5:
                // Run Vendor UI
                break;
            default:
                System.out.println("Invalid choice, try again: ");
        }
    }
}
