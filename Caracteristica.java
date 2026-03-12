public class Caracteristica {
    private final int forca, destresa, constitucio, inteligencia, saviesa, carisma;
    // Constructor de la classe Caracteristica, inicialitzant tots els atributs
    public Caracteristica(int forca, int destresa, int constitucio, int inteligencia, int saviesa, int carisma) {
        this.forca = forca;
        this.destresa = destresa;
        this.constitucio = constitucio;
        this.inteligencia = inteligencia;
        this.saviesa = saviesa;
        this.carisma = carisma;
    }
    // Getters per accedir als atributs de les característiques
    public int getForca() { return forca; }
    public int getDestresa() { return destresa; }
    public int getConstitucio() { return constitucio; }
    public int getInteligencia() { return inteligencia; }
    public int getSaviesa() { return saviesa; }
    public int getCarisma() { return carisma; }
}