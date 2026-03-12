public class Arma extends Objecte {
    // Atributs de l'arma
    private final int dany;
    private final TipusArma tipus;
    private final TipusDany tipusDany;
    // Constructor de l'arma, inicialitzant els atributs
    public Arma(String nom, int dany, TipusArma tipus, TipusDany tipusDany) {
        super(nom);
        this.dany = dany;
        this.tipus = tipus;
        this.tipusDany = tipusDany;
    }
    // Getters per accedir als atributs de l'arma
    public int getDany() { return dany; }
    public TipusArma getTipus() { return tipus; }
    public TipusDany getTipusDany() { return tipusDany; }
}