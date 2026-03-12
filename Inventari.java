import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
public class Inventari {
    private final List<Objecte> objectes = new ArrayList<>();

    public void afegirObjecte(Objecte objecte) {
        objectes.add(objecte);
        System.out.println(objecte.getNom() + " afegit.");
    }

    public List<Objecte> getObjectes() { return objectes; }

    public Optional<Objecte> getObjectePerNom(String nom) {
        return objectes.stream().filter(o -> o.getNom().equalsIgnoreCase(nom)).findFirst();
    }
    // Mètode per mostrar el contingut de l'inventari, indicant si està buit o llistant els objectes disponibles
    public void mostrarInventari() {
        System.out.println("----- Inventari -----");
        if (objectes.isEmpty()) {
            System.out.println("Buit.");
        } else {
            for (int i = 0; i < objectes.size(); i++) {
                System.out.println((i + 1) + ". " + objectes.get(i).getNom());
            }
        }
        System.out.println("--------------------");
    }
}