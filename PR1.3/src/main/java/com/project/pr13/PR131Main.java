package com.project.pr13;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

/**
 * Classe principal que crea un document XML amb informació de llibres i el guarda en un fitxer.
 * 
 * Aquesta classe permet construir un document XML, afegir elements i guardar-lo en un directori
 * especificat per l'usuari.
 */
public class PR131Main {

    private File dataDir;

    /**
     * Constructor de la classe PR131Main.
     * 
     * @param dataDir Directori on es guardaran els fitxers de sortida.
     */
    public PR131Main(File dataDir) {
        this.dataDir = dataDir;
    }

    /**
     * Retorna el directori de dades actual.
     * 
     * @return Directori de dades.
     */
    public File getDataDir() {
        return dataDir;
    }

    /**
     * Actualitza el directori de dades.
     * 
     * @param dataDir Nou directori de dades.
     */
    public void setDataDir(File dataDir) {
        this.dataDir = dataDir;
    }

    /**
     * Mètode principal que inicia l'execució del programa.
     * 
     * @param args Arguments passats a la línia de comandament (no s'utilitzen en aquest programa).
     */
    public static void main(String[] args) {
        String userDir = System.getProperty("user.dir");
        File dataDir = new File(userDir, "data" + File.separator + "pr13");

        PR131Main app = new PR131Main(dataDir);
        app.processarFitxerXML("biblioteca.xml");
    }

    /**
     * Processa el document XML creant-lo, guardant-lo en un fitxer i comprovant el directori de sortida.
     * 
     * @param filename Nom del fitxer XML a guardar.
     */
    public void processarFitxerXML(String filename) {
        if (comprovarIDirCrearDirectori(dataDir)) {
            Document doc = construirDocument();
            File fitxerSortida = new File(dataDir, filename);
            guardarDocument(doc, fitxerSortida);
        }
    }

    /**
     * Comprova si el directori existeix i, si no és així, el crea.
     * 
     * @param directori Directori a comprovar o crear.
     * @return True si el directori ja existeix o s'ha creat amb èxit, false en cas contrari.
     */
    private boolean comprovarIDirCrearDirectori(File directori) {
        if (!directori.exists()) {
            return directori.mkdirs();
        }
        return true;
    }

    /**
     * Crea un document XML amb l'estructura d'una biblioteca i afegeix un llibre amb els seus detalls.
     * 
     * @return Document XML creat o null en cas d'error.
     */
    private static Document construirDocument() {
        // *************** CODI PRÀCTICA **********************/
        try {
            // Crear documento
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();

            // Elemento raiz biblioteca
            Element elmBiblioteca = doc.createElement("biblioteca");
            doc.appendChild(elmBiblioteca);

            // Elemento llibre
            Element elmLlibre = doc.createElement("llibre");
            Attr attrId = doc.createAttribute("id");
            attrId.setValue("001");
            elmBiblioteca.appendChild(attrId);

            // Elemento titol
            Element elmTitol = doc.createElement("titol");
            Text nodeTextTitol = doc.createTextNode("El viatge dels venturons");
            elmTitol.appendChild(nodeTextTitol);
            elmLlibre.appendChild(elmTitol);

            // Elemento autor
            Element elmAutor = doc.createElement("autor");
            Text nodeTextAutor = doc.createTextNode("Joan Pla");
            elmAutor.appendChild(nodeTextAutor);
            elmLlibre.appendChild(elmAutor);

            // Elemento anyPublicacio
            Element elmAnyPublicacio = doc.createElement("anyPublicacio");
            Text nodeTextAnyPublicacio = doc.createTextNode("1998");
            elmAnyPublicacio.appendChild(nodeTextAnyPublicacio);
            elmLlibre.appendChild(elmAnyPublicacio);

            // Elemento editorial
            Element elmEditorial = doc.createElement("editorial");
            Text nodeTextEditorial = doc.createTextNode("Edicions Mar");
            elmEditorial.appendChild(nodeTextEditorial);
            elmLlibre.appendChild(elmEditorial);

            // Elemento genere
            Element elmGenere = doc.createElement("genere");
            Text nodeTextGenere = doc.createTextNode("Aventura");
            elmGenere.appendChild(nodeTextGenere);
            elmLlibre.appendChild(elmGenere);
            
            // Elemento pagines
            Element elmPagines = doc.createElement("pagines");
            Text nodeTextPagines = doc.createTextNode("320");
            elmPagines.appendChild(nodeTextPagines);
            elmLlibre.appendChild(elmPagines);

            // Elemento disponible
            Element elmDisponible = doc.createElement("disponible");
            Text nodeTextDisponible = doc.createTextNode("true");
            elmDisponible.appendChild(nodeTextDisponible);
            elmLlibre.appendChild(elmDisponible);

            return doc;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Guarda el document XML proporcionat en el fitxer especificat.
     * 
     * @param doc Document XML a guardar.
     * @param fitxerSortida Fitxer de sortida on es guardarà el document.
     */
    private static void guardarDocument(Document doc, File fitxerSortida) {
        // *************** CODI PRÀCTICA **********************/
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(fitxerSortida);
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
