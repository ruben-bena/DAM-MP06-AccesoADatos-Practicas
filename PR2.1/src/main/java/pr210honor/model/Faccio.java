package pr210honor.model;

/**
 * Classe Model per representar una Facció de la base de dades.
 */
public class Faccio {
private final int id;
    private final String nom;
    private final String resum;

    /**
     * Constructor complet per a una Facció.
     * @param id Identificador únic.
     * @param nom Nom de la facció.
     * @param resum Resum descriptiu.
     */
    public Faccio(int id, String nom, String resum) {
        this.id = id;
        this.nom = nom;
        this.resum = resum;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getResum() {
        return resum;
    }

    /**
     * Retorna una representació en cadena de la facció.
     * @return Una cadena amb la informació de la facció.
     */
    @Override
    public String toString() {
        return String.format("| %-3d | %-15s | %-50s... |", id, nom, resum.substring(0, Math.min(50, resum.length())));
    }
}
