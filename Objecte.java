public abstract class Objecte {
    protected final String nom;
    protected float pes;

    public Objecte(String nom) {
        this.nom = nom;
        this.pes = 1.0f;
    }

    public String getNom() { return nom; }
}