package pr210honor;

import java.util.List;
import java.util.Scanner;

import pr210honor.ddbb.DatabaseManager;
import pr210honor.model.Faccio;
import pr210honor.model.Personatge;

/**
 * Aplicació principal PR210Honor. Gestiona el menú i les operacions.
 */
public class PR210Honor {

    private final DatabaseManager dbManager;
    private final Scanner scanner;

    public PR210Honor() {
        this.dbManager = new DatabaseManager();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Mètode principal per iniciar l'aplicació.
     * @param args Arguments de la línia d'ordres (no utilitzats).
     */
    public static void main(String[] args) {
        PR210Honor app = new PR210Honor();
        app.start();
    }

    /**
     * Inicialitza la BD i executa el bucle del menú.
     */
    public void start() {
        // Exercici 0: Inicialització i Població de la BD
        dbManager.initialize();

        int opcio;
        do {
            mostrarMenu();
            opcio = llegirOpcio();

            switch (opcio) {
                case 1:
                    mostrarTaulaSeleccionada();
                    break;
                case 2:
                    mostrarPersonatgesPerFaccio();
                    break;
                case 3:
                    // Es crida al mètode que demana l'ID de la facció i mostra un sol resultat
                    mostrarMillorAtacantPerFaccio();
                    break;
                case 4:
                    // Es crida al mètode que demana l'ID de la facció i mostra un sol resultat
                    mostrarMillorDefensorPerFaccio();
                    break;
                case 5:
                    System.out.println("Sortint de l'aplicació. Fins aviat!");
                    break;
                default:
                    System.out.println("Opció no vàlida. Si us plau, tria una opció del 1 al 5.");
            }
        } while (opcio != 5);
        scanner.close();
    }

    /**
     * Mostra les opcions del menú a l'usuari.
     */
    private void mostrarMenu() {
        System.out.println("\n--- Menú Principal For Honor (PR210Honor) ---");
        System.out.println("1. Mostrar una taula (Facció o Personatge)");
        System.out.println("2. Mostrar personatges per facció");
        System.out.println("3. Mostrar el millor atacant per facció");
        System.out.println("4. Mostrar el millor defensor per facció");
        System.out.println("5. Sortir");
        System.out.print("Tria una opció: ");
    }

    /**
     * Llegeix l'opció del menú introduïda per l'usuari.
     * @return L'opció triada com a enter.
     */
    private int llegirOpcio() {
        if (scanner.hasNextInt()) {
            int opcio = scanner.nextInt();
            scanner.nextLine(); // Consumir el salt de línia
            return opcio;
        } else {
            System.out.println("Entrada no vàlida. Si us plau, introdueix un número.");
            scanner.nextLine(); // Consumir l'entrada incorrecta
            return 0;
        }
    }

    /**
     * Demana a l'usuari quina taula vol visualitzar i la mostra.
     */
    private void mostrarTaulaSeleccionada() {
        System.out.println("\n--- Selecció de Taula ---");
        System.out.println("a) Taula Facció");
        System.out.println("b) Taula Personatge");
        System.out.print("Tria (a/b): ");

        String opcioTaula = scanner.nextLine().trim().toLowerCase();

        if (opcioTaula.equals("a")) {
            mostrarTaulaFaccio();
        } else if (opcioTaula.equals("b")) {
            mostrarTaulaPersonatge();
        } else {
            System.out.println("Opció no vàlida. Torna al menú.");
        }
    }

    /**
     * Mostra tots els registres de la taula Facció.
     */
    private void mostrarTaulaFaccio() {
        List<Faccio> faccions = dbManager.getAllFaccions();

        if (faccions.isEmpty()) {
            System.out.println("No hi ha faccions registrades.");
            return;
        }

        System.out.println("\n--- TAULA FACCIO ---");
        System.out.println("--------------------------------------------------------------------------------------------------");
        System.out.println("| ID  | Nom             | Resum                                              ... |");
        System.out.println("--------------------------------------------------------------------------------------------------");
        for (Faccio f : faccions) {
            System.out.println(f);
        }
        System.out.println("--------------------------------------------------------------------------------------------------");
    }

    /**
     * Mostra tots els registres de la taula Personatge.
     */
    private void mostrarTaulaPersonatge() {
        List<Personatge> personatges = dbManager.getAllPersonatges();

        if (personatges.isEmpty()) {
            System.out.println("No hi ha personatges registrats.");
            return;
        }

        System.out.println("\n--- TAULA PERSONATGE ---");
        System.out.println("---------------------------------------------------------");
        System.out.println("| ID  | Nom             | Facció          | Atac  | Defensa |");
        System.out.println("---------------------------------------------------------");
        for (Personatge p : personatges) {
            System.out.println(p);
        }
        System.out.println("---------------------------------------------------------");
    }

    /**
     * Demana l'ID de la facció i mostra els personatges que hi pertanyen.
     */
    private void mostrarPersonatgesPerFaccio() {
        List<Faccio> faccions = dbManager.getAllFaccions();
        if (faccions.isEmpty()) {
            System.out.println("No hi ha faccions per buscar.");
            return;
        }

        System.out.println("\n--- Cerca de Personatges per Facció ---");
        System.out.println("Faccions disponibles (ID | Nom):");
        faccions.forEach(f -> System.out.printf("  - %d | %s\n", f.getId(), f.getNom()));
        System.out.print("Introdueix l'ID de la facció: ");

        if (scanner.hasNextInt()) {
            int idFaccio = scanner.nextInt();
            scanner.nextLine();

            List<Personatge> personatges = dbManager.getPersonatgesByFaccio(idFaccio);

            if (personatges.isEmpty()) {
                System.out.println("No s'han trobat personatges per a la Facció amb ID " + idFaccio + " o la Facció no existeix.");
            } else {
                System.out.printf("\n--- PERSONATGES de la FACCIÓ: %s ---\n", personatges.get(0).getNomFaccio());
                System.out.println("---------------------------------------------------------");
                System.out.println("| ID  | Nom             | Facció          | Atac  | Defensa |");
                System.out.println("---------------------------------------------------------");
                for (Personatge p : personatges) {
                    System.out.println(p);
                }
                System.out.println("---------------------------------------------------------");
            }
        } else {
            System.out.println("Entrada no vàlida. Cal introduir un ID numèric.");
            scanner.nextLine();
        }
    }

    /**
     * Mètode auxiliar per demanar l'ID de la facció.
     * @return L'ID de la facció introduït.
     */
    private int demanarIdFaccio() {
        List<Faccio> faccions = dbManager.getAllFaccions();
        if (faccions.isEmpty()) {
            System.out.println("No hi ha faccions registrades.");
            return -1;
        }

        System.out.println("\nFaccions disponibles (ID | Nom):");
        faccions.forEach(f -> System.out.printf("  - %d | %s\n", f.getId(), f.getNom()));
        System.out.print("Introdueix l'ID de la facció: ");

        if (scanner.hasNextInt()) {
            int id = scanner.nextInt();
            scanner.nextLine();
            return id;
        } else {
            System.out.println("Entrada no vàlida. Cal introduir un ID numèric.");
            scanner.nextLine();
            return -1;
        }
    }


    /**
     * Mostra el personatge amb el millor atac de la facció seleccionada. (Opció 3, CORREGIDA per selecció individual)
     */
    private void mostrarMillorAtacantPerFaccio() {
        System.out.println("\n--- Millor Atacant per Facció ---");
        int idFaccio = demanarIdFaccio();

        if (idFaccio != -1) {
            Personatge millorAtacant = dbManager.getBestAttacker(idFaccio);

            if (millorAtacant != null) {
                System.out.printf("\nEl millor atacant de la facció %s és:\n", millorAtacant.getNomFaccio());
                System.out.println("---------------------------------------------------------");
                System.out.println("| ID  | Nom             | Facció          | Atac  | Defensa |");
                System.out.println("---------------------------------------------------------");
                System.out.println(millorAtacant);
                System.out.println("---------------------------------------------------------");
            } else {
                System.out.println("No s'ha trobat cap personatge en aquesta facció.");
            }
        }
    }

    /**
     * Mostra el personatge amb la millor defensa de la facció seleccionada. (Opció 4, CORREGIDA per selecció individual)
     */
    private void mostrarMillorDefensorPerFaccio() {
        System.out.println("\n--- Millor Defensor per Facció ---");
        int idFaccio = demanarIdFaccio();

        if (idFaccio != -1) {
            Personatge millorDefensor = dbManager.getBestDefender(idFaccio);

            if (millorDefensor != null) {
                System.out.printf("\nEl millor defensor de la facció %s és:\n", millorDefensor.getNomFaccio());
                System.out.println("---------------------------------------------------------");
                System.out.println("| ID  | Nom             | Facció          | Atac  | Defensa |");
                System.out.println("---------------------------------------------------------");
                System.out.println(millorDefensor);
                System.out.println("---------------------------------------------------------");
            } else {
                System.out.println("No s'ha trobat cap personatge en aquesta facció.");
            }
        }
    }
}