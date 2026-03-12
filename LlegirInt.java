import java.util.Scanner;

public class LlegirInt {
    public static int llegirInt(Scanner scanner, String missatge) {
        int valor = -1;
        boolean valid = false;

        while (!valid) {
            System.out.print(missatge);
            try {
                valor = Integer.parseInt(scanner.nextLine());
                valid = true;
            } catch (NumberFormatException e) {
                System.out.println("Entrada no vàlida. Intenta-ho de nou.");
            }
        }

        return valor;
    }
}

