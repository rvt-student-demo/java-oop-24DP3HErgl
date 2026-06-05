package rvt;
import java.util.Scanner;

public class DivisionPractice {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter the numerator: ");
            String numeratorInput = scanner.next();

            
            if (numeratorInput.length() > 0) {
                char firstChar = numeratorInput.charAt(0);
                if (firstChar == 'q' || firstChar == 'Q') {
                    break; 
                }
            }

            try {
                
                int numerator = Integer.parseInt(numeratorInput);

                
                System.out.print("Enter the divisor: ");
                String divisorInput = scanner.next();
                int divisor = Integer.parseInt(divisorInput);

                
                if (divisor == 0) {
                    System.out.println("You can't divide " + numerator + " by 0");
                } else {
                    int result = numerator / divisor;
                    System.out.println(numerator + " / " + divisor + " is " + result);
                }

            } catch (NumberFormatException e) {
                
                System.out.println("You entered bad data.");
                System.out.println("Please try again.");
            }

            System.out.println();
        }

        scanner.close();
    }
}
