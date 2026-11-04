public import java.util.Scanner;

public class CampusFinder {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        int choice;

        do {
            System.out.println("\n===== CAMPUS FINDER SYSTEM =====");
            System.out.println("1. View Campus Locations");
            System.out.println("2. Search for a Building");
            System.out.println("3. Get Directions");
            System.out.println("4. Exit");

            System.out.print("Enter your choice: ");
            choice = input.nextInt();
            input.nextLine();

            switch (choice) {

                case 1:
                    System.out.println("\n--- CAMPUS LOCATIONS ---");
                    System.out.println("1. Library");
                    System.out.println("2. Administration Block");
                    System.out.println("3. Computer Laboratory");
                    System.out.println("4. Lecture Hall");
                    System.out.println("5. Cafeteria");
                    break;

                case 2:
                    System.out.print("\nEnter building name: ");
                    String building = input.nextLine();

                    if (building.equalsIgnoreCase("Library")) {
                        System.out.println("Library is near the main entrance.");
                    } 
                    else if (building.equalsIgnoreCase("Administration Block")) {
                        System.out.println("Administration Block is beside the library.");
                    } 
                    else if (building.equalsIgnoreCase("Computer Laboratory")) {
                        System.out.println("Computer Laboratory is behind the lecture hall.");
                    } 
                    else if (building.equalsIgnoreCase("Lecture Hall")) {
                        System.out.println("Lecture Hall is near the computer laboratory.");
                    } 
                    else if (building.equalsIgnoreCase("Cafeteria")) {
                        System.out.println("Cafeteria is next to the student centre.");
                    } 
                    else {
                        System.out.println("Building not found!");
                    }
                    break;

                case 3:
                    System.out.println("\n--- CAMPUS DIRECTIONS ---");
                    System.out.println("Library: Follow the main entrance path.");
                    System.out.println("Administration: Walk past the library.");
                    System.out.println("Computer Lab: Walk behind the lecture hall.");
                    System.out.println("Lecture Hall: Follow the signs near the main block.");
                    System.out.println("Cafeteria: Follow the path to the student centre.");
                    break;

                case 4:
                    System.out.println("Thank you for using Campus Finder!");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 4);

        input.close();
    }
} Main {
    
}
