import java.util.Scanner;

public class LlegirString {
    public static String llegirString(Scanner scanner, String missatge) {
        System.out.print(missatge);
        return scanner.nextLine();
    }
}