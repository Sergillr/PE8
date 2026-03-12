import java.util.Random;
import java.util.Scanner;

public abstract class Personatge {
    protected final String nom;
    protected int edat;
    protected double salut, mana, energia;
    protected final Raca raca;
    protected final Caracteristica stats;
    protected final Inventari inventari = new Inventari();
    protected Arma armaEquipada;
    private final Random random = new Random();
    // Constructor de la classe Personatge, inicialitzant tots els atributs
    public Personatge(String nom, int edat, double salut, double mana, double energia, Raca raca,
            Caracteristica stats) {
        this.nom = nom;
        this.edat = edat;
        this.salut = salut;
        this.mana = mana;
        this.energia = energia;
        this.raca = raca;
        this.stats = stats;
    }
// Getters per accedir als atributs del personatge
    public String getNom() {
        return nom;
    }
    public double getSalut() {
        return salut;
    }

    public double getMana() {
        return mana;
    }

    public double getEnergia() {
        return energia;
    }

    public Arma getArmaEquipada() {
        return armaEquipada;
    }

    public Inventari getInventari() {
        return inventari;
    }
// Mètode per equipar una arma, verificando requisits i actualitzant l'estat del personatge
    public boolean equiparArma(Arma arma) {
        if (arma.getTipusDany() == TipusDany.MAGIC && stats.getInteligencia() < 10) {
            System.out.println("Intel·ligència insuficient.");
            return false;
        }
        this.armaEquipada = arma;
        System.out.println(nom + " ha equipat " + arma.getNom());
        return true;
    }
    // Mètode per atacar a un altre personatge, calculant el dany basat en les estadístiques i l'arma equipada
    public double atacar(Personatge objetivo) {
        if (armaEquipada == null || armaEquipada.getTipusDany() == TipusDany.FISIC) {
            return stats.getForca() * (1 + (armaEquipada != null ? armaEquipada.getDany() / 100.0 : 0));
        }
        return armaEquipada.getDany() * stats.getInteligencia() / 100.0;
    }
    // Mètode per rebre dany, aplicant esquiva i reduccions basades en si el personatge està defensant
    public void rebreDany(double dany, boolean defensant) {
        if (esquivar()) {
            System.out.println(nom + " esquiva!");
            return;
        }
        double danyFinal = defensant ? dany / 2 : dany;
        System.out.println(nom + " rep " + danyFinal + " de dany.");
        salut = Math.max(salut - danyFinal, 0);
    }
    // Mètode per intentar esquivar un atac, basat en la destresa del personatge i un factor aleatori
    private boolean esquivar() {
        return random.nextDouble() * 100 < Math.max(0, (stats.getDestresa() - 5) * 3.33);
    }
    // Mètode per descansar, recuperant energia, salut i mana basats en les característiques del personatge
    public void descansar() {
        energia = Math.min(energia + 20, 100);
        salut = Math.min(salut + stats.getConstitucio() * 3, 100);
        mana = Math.min(mana + stats.getInteligencia() * 2, 100);
    }
    // Mètode per mostrar les estadístiques del personatge, incloent nom, raça, HP, MP, energia i característiques
    public void mostrarStats() {
        System.out.printf("Nom: %s | Raça: %s | HP: %.1f | MP: %.1f | EN: %.1f%n", nom, raca, salut, mana, energia);
        System.out.printf("Stats: STR:%d DEX:%d CON:%d INT:%d WIS:%d CHA:%d%n",
                stats.getForca(), stats.getDestresa(), stats.getConstitucio(),
                stats.getInteligencia(), stats.getSaviesa(), stats.getCarisma());
        if (armaEquipada != null)
            System.out.println("Arma: " + armaEquipada.getNom());
    }
    // Mètode per modificar un atribut del personatge, permetent al jugador seleccionar quin atribut vol canviar i introduir el nou valor
    public void modificarAtribut(Scanner scanner) {
        System.out.println("Selecciona el atribut a modificar (1. edat,2. salut, 3. mana, 4. energia): ");
        int opcio = LlegirInt.llegirInt(scanner, "Opció: ");
        switch (opcio) {
            case 1:
                System.out.println("Introdueix la nova edat: ");
                this.edat = LlegirInt.llegirInt(scanner, "Edat: ");
                System.out.println("Edat actualitzada a: " + this.edat);
                break;
            case 2:
                System.out.println("Introdueix la nova salut: ");
                this.salut = Math.max(0, LlegirInt.llegirInt(scanner, "Salut: "));
                System.out.println("Salut actualitzada a: " + this.salut);
                break;
            case 3:
                System.out.println("Introdueix el nou mana: ");
                this.mana = Math.max(0, LlegirInt.llegirInt(scanner, "Mana: "));
                System.out.println("Mana actualitzat a: " + this.mana);
                break;
            case 4:
                System.out.println("Introdueix la nova energia: ");
                this.energia = Math.max(0, LlegirInt.llegirInt(scanner, "Energia: "));
                System.out.println("Energia actualitzada a: " + this.energia);
                break;
            default:
                System.out.println("L'opció'" + opcio + "' no existeix o no es pot modificar.");
                break;
        }
    }
}