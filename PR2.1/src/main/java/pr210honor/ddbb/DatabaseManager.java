package pr210honor.ddbb;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import pr210honor.model.Faccio;
import pr210honor.model.Personatge;

/**
 * Classe per gestionar la connexió i les operacions amb la base de dades SQLite.
 */
public class DatabaseManager {

    private static final String DB_FILE = "pr210honor.db";
    private static final String DB_URL = "jdbc:sqlite:" + DB_FILE;

    /**
     * Inicialitza la base de dades: verifica l'existència, crea taules i pobla dades.
     */
    public void initialize() {
        File dbFile = new File(DB_FILE);
        boolean exists = dbFile.exists();

        if (!exists) {
            System.out.println("Fitxer de base de dades no trobat. S'inicialitzarà la BD.");
            createTables();
            populateData();
        } else {
            System.out.println("Base de dades trobada. Connexió establerta.");
        }
    }

    /**
     * Obre una connexió a la base de dades.
     * @return Objecte Connection.
     */
    private Connection connect() {
        Connection conn = null;
        try {
            // Aquesta línia carrega el driver JDBC per SQLite (és automàtic en versions modernes de Java)
            conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            System.err.println("Error de connexió a la BD: " + e.getMessage());
        }
        return conn;
    }

    /**
     * Crea les taules Facció i Personatge si no existeixen.
     */
    private void createTables() {
        String createFaccio = "CREATE TABLE IF NOT EXISTS Faccio ("
                + "id INTEGER PRIMARY KEY,"
                + "nom VARCHAR(15) NOT NULL UNIQUE,"
                + "resum VARCHAR(500)"
                + ");";

        String createPersonatge = "CREATE TABLE IF NOT EXISTS Personatge ("
                + "id INTEGER PRIMARY KEY,"
                + "nom VARCHAR(15) NOT NULL UNIQUE,"
                + "atac REAL NOT NULL,"
                + "defensa REAL NOT NULL,"
                + "idFaccio INTEGER NOT NULL,"
                + "FOREIGN KEY (idFaccio) REFERENCES Faccio(id)"
                + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(createFaccio);
            stmt.execute(createPersonatge);
            System.out.println("Taules creades correctament.");
        } catch (SQLException e) {
            System.err.println("Error en crear les taules: " + e.getMessage());
        }
    }

    /**
     * Insereix les dades inicials a les taules.
     */
    private void populateData() {
        // Les claus primàries s'assignen automàticament per INTEGER PRIMARY KEY
        String[] sqlInserts = {
                "INSERT INTO Faccio (nom, resum) VALUES ('Cavallers', 'Though seen as a single group, the Knights are hardly unified. There are many Legions in Ashfeld, the most prominent being The Iron Legion.');",
                "INSERT INTO Faccio (nom, resum) VALUES ('Vikings',   'The Vikings are a loose coalition of hundreds of clans and tribes, the most powerful being The Warborn.');",
                "INSERT INTO Faccio (nom, resum) VALUES ('Samurais',  'The Samurai are the most unified of the three factions, though this does not say much as the Daimyos were often battling each other for dominance.');",

                "INSERT INTO Personatge (nom, atac, defensa, idFaccio) VALUES ('Warden',      1, 3, 1);",
                "INSERT INTO Personatge (nom, atac, defensa, idFaccio) VALUES ('Conqueror',   2, 2, 1);",
                "INSERT INTO Personatge (nom, atac, defensa, idFaccio) VALUES ('Peacekeep',   2, 3, 1);",

                "INSERT INTO Personatge (nom, atac, defensa, idFaccio) VALUES ('Raider',    3, 3, 2);",
                "INSERT INTO Personatge (nom, atac, defensa, idFaccio) VALUES ('Warlord',   2, 2, 2);",
                "INSERT INTO Personatge (nom, atac, defensa, idFaccio) VALUES ('Berserker', 1, 1, 2);",

                "INSERT INTO Personatge (nom, atac, defensa, idFaccio) VALUES ('Kensei',    3, 2, 3);",
                "INSERT INTO Personatge (nom, atac, defensa, idFaccio) VALUES ('Shugoki',   2, 1, 3);",
                "INSERT INTO Personatge (nom, atac, defensa, idFaccio) VALUES ('Orochi',    3, 2, 3);"
        };

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {

            conn.setAutoCommit(false); // Iniciem una transacció
            for (String sql : sqlInserts) {
                stmt.addBatch(sql);
            }
            stmt.executeBatch();
            conn.commit(); // Confirmem tots els canvis
            System.out.println("Dades inserides correctament.");

        } catch (SQLException e) {
            System.err.println("Error en inserir dades: " + e.getMessage());
            // En un entorn real, aquí s'hauria de fer un rollback.
        }
    }

    /**
     * Recupera totes les faccions de la base de dades.
     * @return Llista de Facció.
     */
    public List<Faccio> getAllFaccions() {
        String sql = "SELECT id, nom, resum FROM Faccio";
        List<Faccio> faccions = new ArrayList<>();

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                faccions.add(new Faccio(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("resum")));
            }
        } catch (SQLException e) {
            System.err.println("Error en obtenir les faccions: " + e.getMessage());
        }
        return faccions;
    }

    /**
     * Recupera tots els personatges de la base de dades.
     * @return Llista de Personatge.
     */
    public List<Personatge> getAllPersonatges() {
        // Fem un JOIN per incloure el nom de la facció en la llista
        String sql = "SELECT p.id, p.nom, p.atac, p.defensa, p.idFaccio, f.nom AS nomFaccio " +
                     "FROM Personatge p JOIN Faccio f ON p.idFaccio = f.id";
        List<Personatge> personatges = new ArrayList<>();

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                personatges.add(new Personatge(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getDouble("atac"),
                        rs.getDouble("defensa"),
                        rs.getInt("idFaccio"),
                        rs.getString("nomFaccio")));
            }
        } catch (SQLException e) {
            System.err.println("Error en obtenir els personatges: " + e.getMessage());
        }
        return personatges;
    }

    /**
     * Llista els personatges d'una facció específica.
     * @param idFaccio ID de la facció a cercar.
     * @return Llista de Personatge.
     */
    public List<Personatge> getPersonatgesByFaccio(int idFaccio) {
        // Consulta amb JOIN per obtenir el nom de la facció i filtrar per idFaccio.
        String sql = "SELECT p.id, p.nom, p.atac, p.defensa, p.idFaccio, f.nom AS nomFaccio " +
                     "FROM Personatge p JOIN Faccio f ON p.idFaccio = f.id " +
                     "WHERE p.idFaccio = ?";
        List<Personatge> personatges = new ArrayList<>();

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idFaccio); // Substitueix el primer '?' amb l'idFaccio
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    personatges.add(new Personatge(
                            rs.getInt("id"),
                            rs.getString("nom"),
                            rs.getDouble("atac"),
                            rs.getDouble("defensa"),
                            rs.getInt("idFaccio"),
                            rs.getString("nomFaccio")));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error en llistar personatges per facció: " + e.getMessage());
        }
        return personatges;
    }

    /**
     * Obté el personatge amb el màxim valor d'atac d'una facció.
     * @param idFaccio ID de la facció.
     * @return Objecte Personatge o null si no es troba.
     */
    public Personatge getBestAttacker(int idFaccio) { // Tipus de retorn Personatge (correcte)
        // S'utilitza ORDER BY i LIMIT 1 per gestionar l'empat, mostrant només 1.
        String sql = "SELECT p.id, p.nom, p.atac, p.defensa, p.idFaccio, f.nom AS nomFaccio " +
                     "FROM Personatge p JOIN Faccio f ON p.idFaccio = f.id " +
                     "WHERE p.idFaccio = ? " +
                     "ORDER BY p.atac DESC, p.defensa DESC " + // Desempat per defensa
                     "LIMIT 1";

        return getSinglePersonatgeQuery(sql, idFaccio);
    }

    /**
     * Obté el personatge amb el màxim valor de defensa d'una facció.
     * @param idFaccio ID de la facció.
     * @return Objecte Personatge o null si no es troba.
     */
    public Personatge getBestDefender(int idFaccio) { // Tipus de retorn Personatge (correcte)
        // S'utilitza ORDER BY i LIMIT 1 per gestionar l'empat, mostrant només 1.
        String sql = "SELECT p.id, p.nom, p.atac, p.defensa, p.idFaccio, f.nom AS nomFaccio " +
                     "FROM Personatge p JOIN Faccio f ON p.idFaccio = f.id " +
                     "WHERE p.idFaccio = ? " +
                     "ORDER BY p.defensa DESC, p.atac DESC " + // Desempat per atac
                     "LIMIT 1";

        return getSinglePersonatgeQuery(sql, idFaccio);
    }

    /**
     * Mètode auxiliar per executar consultes que retornen un únic personatge.
     * @param sql Consulta SQL a executar.
     * @param idFaccio ID de la facció per al PreparedStatement.
     * @return Personatge o null.
     */
    private Personatge getSinglePersonatgeQuery(String sql, int idFaccio) { // Tipus de retorn Personatge (correcte)
        Personatge personatge = null;
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idFaccio);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    personatge = new Personatge(
                            rs.getInt("id"),
                            rs.getString("nom"),
                            rs.getDouble("atac"),
                            rs.getDouble("defensa"),
                            rs.getInt("idFaccio"),
                            rs.getString("nomFaccio"));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error en obtenir el personatge: " + e.getMessage());
        }
        return personatge;
    }
}