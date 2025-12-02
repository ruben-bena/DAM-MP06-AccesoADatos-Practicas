package pr210honor.model;

/**
 * Classe Model per representar un Personatge de la base de dades.
 */
public class Personatge {
    private final int id;
    private final String nom;
    private final double atac;
    private final double defensa;
    private final int idFaccio;
    // Camp opcional per emmagatzemar el nom de la facció en consultes amb JOIN
    private String nomFaccio;

    /**
     * Constructor per a un Personatge.
     * @param id Identificador únic.
     * @param nom Nom del personatge.
     * @param atac Valor d'atac.
     * @param defensa Valor de defensa.
     * @param idFaccio Clau forana de la facció.
     */
    public Personatge(int id, String nom, double atac, double defensa, int idFaccio) {
        this.id = id;
        this.nom = nom;
        this.atac = atac;
        this.defensa = defensa;
        this.idFaccio = idFaccio;
    }
    
    // Constructor addicional per a consultes que inclouen el nom de la facció
    public Personatge(int id, String nom, double atac, double defensa, int idFaccio, String nomFaccio) {
        this.id = id;
        this.nom = nom;
        this.atac = atac;
        this.defensa = defensa;
        this.idFaccio = idFaccio;
        this.nomFaccio = nomFaccio;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public double getAtac() {
        return atac;
    }

    public double getDefensa() {
        return defensa;
    }

    public int getIdFaccio() {
        return idFaccio;
    }
    
    public String getNomFaccio() {
        return nomFaccio;
    }
    
    /**
     * Retorna una representació en cadena del personatge.
     * @return Una cadena amb la informació del personatge.
     */
    @Override
    public String toString() {
        if (nomFaccio != null) {
             return String.format("| %-3d | %-15s | %-15s | %-5.1f | %-5.1f |", id, nom, nomFaccio, atac, defensa);
        }
        return String.format("| %-3d | %-15s | %-7.1f | %-8.1f | %-10d |", id, nom, atac, defensa, idFaccio);
    }
}
