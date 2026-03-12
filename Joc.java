import java.util.*;

// Classe principal del joc
public class Joc {
    private final List<Personatge> personatges = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);
    private final Random random = new Random();
    // Mètode per mostrar el menú principal i gestionar les opcions
    public void mostrarMenu() {
        while (true) {
            System.out.println("\n1.Crear Personatge | 2.Crear Arma | 3.Combat | 4.Modificar Atributs | 5.Sortir");
            int opcio = LlegirInt.llegirInt(scanner, "Opció: ");

            switch (opcio) {
                case 1 -> crearPersonatge();
                case 2 -> crearArma();
                case 3 -> iniciarCombatSimple();
                case 4 -> modificarPersonatege();
                case 5 -> {
                    System.out.println("Sortint...");
                    return;
                }
                default -> System.out.println("Opció invàlida");
            }
        }
    }
    // Mètode per modificar els atributs d'un personatge existent
    private void modificarPersonatege() {
        if (personatges.isEmpty()) {
            System.out.println("No hi ha personatges creats!");
        } else {
            Personatge p = seleccionarPersonatge();
            p.modificarAtribut(scanner);
        }
    }
    // Mètode per crear un personatge, ja sigui manualment o automàticament
    private void crearPersonatge() {
        int tipus = LlegirInt.llegirInt(scanner, "1.Manual 2.Auto: ");
        Personatge personatge = (tipus == 1) ? crearManual() : crearAuto();
        personatges.add(personatge);
        personatge.mostrarStats();
    }
    // Mètode per crear un personatge manualment, permetent al jugador personalitzar els atributs
    private Personatge crearManual() {
        String nom = LlegirString.llegirString(scanner, "Nom: ");
        int edat = LlegirInt.llegirInt(scanner, "Edat: ");
        Raca raca = seleccionarEnum("Raça", Raca.values());

        int[] puntsAtribuibles = new int[6];
        System.out.println(
                "Tens 60 punts per repartir entre Força, Destreza, Constitució, Intel·ligència, Saviesa i Carisma (mínim 5 cada un).");
        int punts = 60;
        String[] labels = { "For", "Des", "Con", "Int", "Sav", "Car" };
        for (int i = 0; i < 6; i++) {
            System.out.println("Punts restants: " + punts);
            puntsAtribuibles[i] = llegirAtribut(labels[i], punts, 6 - i);
            punts -= puntsAtribuibles[i];
        }
        return new PersonatgeBase(nom, edat, 100, 100, 100, raca,
                new Caracteristica(puntsAtribuibles[0], puntsAtribuibles[1], puntsAtribuibles[2], puntsAtribuibles[3],
                        puntsAtribuibles[4], puntsAtribuibles[5]));
    }
    // Mètode per crear un personatge automàticament
    private Personatge crearAuto() {
        String nom = LlegirString.llegirString(scanner, "Nom: ");
        int edat = LlegirInt.llegirInt(scanner, "Edat: ");
        int[] puntsAtribuibles = { 5, 5, 5, 5, 5, 5 };
        int punts = 30;
        while (punts > 0) {
            int idx = random.nextInt(6);
            if (puntsAtribuibles[idx] < 20) {
                puntsAtribuibles[idx]++;
                punts--;
            }
        }
        return new PersonatgeBase(nom, edat, 100, 100, 100, Raca.values()[random.nextInt(Raca.values().length)],
                new Caracteristica(puntsAtribuibles[0], puntsAtribuibles[1], puntsAtribuibles[2], puntsAtribuibles[3],
                        puntsAtribuibles[4], puntsAtribuibles[5]));
    }
    // Mètode per crear una arma i assignar-la a un personatge
    private void crearArma() {
        if (personatges.isEmpty())
            return;
        String nom = LlegirString.llegirString(scanner, "Nom arma: ");
        TipusArma tipusArma = seleccionarEnum("Tipus", TipusArma.values());
        TipusDany tipusDany = seleccionarEnum("Dany", TipusDany.values());
        int dany = Math.min(100, Math.max(1, LlegirInt.llegirInt(scanner, "Dany (1-100): ")));

        Arma arma = new Arma(nom, dany, tipusArma, tipusDany);
        Personatge personatge = seleccionarPersonatge();
        personatge.getInventari().afegirObjecte(arma);
    }
    // Mètode per seleccionar un valor d'un enum a través del menú
    private <T extends Enum<T>> T seleccionarEnum(String titol, T[] valors) {
        for (int i = 0; i < valors.length; i++)
            System.out.println((i + 1) + ". " + valors[i]);
        int seleccio = LlegirInt.llegirInt(scanner, titol + ": ") - 1;
        return valors[Math.max(0, Math.min(seleccio, valors.length - 1))];
    }
    // Mètode per seleccionar un personatge de la llista de personatges creats
    private Personatge seleccionarPersonatge() {
        for (int i = 0; i < personatges.size(); i++)
            System.out.println((i + 1) + ". " + personatges.get(i).getNom());
        return personatges.get(LlegirInt.llegirInt(scanner, "Sel. personatge: ") - 1);
    }
    // Mètode per iniciar un combat simple entre dos personatges seleccionats
    public void iniciarCombatSimple() {
        if (personatges.size() < 2)
            return;
        Personatge p1 = seleccionarPersonatge();
        Personatge p2 = seleccionarPersonatge();

        while (p1.getSalut() > 0 && p2.getSalut() > 0) {
            executarTorn(p1, p2);
            if (p2.getSalut() > 0)
                executarTorn(p2, p1);
        }
        System.out.println("Combat finalitzat.");
    }
    // Mètode per executar un torn de combat, permetent al jugador seleccionar una acció
    private void executarTorn(Personatge atacant, Personatge defensor) {
        System.out.println("Es el torn de " + atacant.getNom());
        atacant.mostrarStats();
        System.out.println("\nTorn de " + atacant.getNom());
        int opcions = LlegirInt.llegirInt(scanner, "1.Atac 2.Def 3.Desc 4.Arma: ");
        switch (opcions) {
            case 1 -> defensor.rebreDany(atacant.atacar(defensor), false);
            case 2 -> defensor.rebreDany(atacant.atacar(defensor), true);
            case 3 -> atacant.descansar();
            case 4 -> {
                List<Objecte> objecte = atacant.getInventari().getObjectes();
                for (int i = 0; i < objecte.size(); i++)
                    if (objecte.get(i) instanceof Arma)
                        System.out.println(i + ". " + objecte.get(i).getNom());
                int i = LlegirInt.llegirInt(scanner, "Que vols equipar ?: ");
                if (objecte.get(i) instanceof Arma arma)
                    atacant.equiparArma(arma);
            }
        }
    }
    // Mètode per llegir un atribut amb validació, assegurant que el valor estigui dins dels límits permesos
    private int llegirAtribut(String n, int p, int r) {
        int max = Math.min(20, p - (r - 1) * 5);
        int v;
        do {
            v = LlegirInt.llegirInt(scanner, n + " (5-" + max + "): ");
        } while (v < 5 || v > max);
        return v;
    }
}