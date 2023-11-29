package ch.epfl.cs107.play.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


public final class XMLTexts {

    /// Private map containing all the strings. Use getText(String key) to get a value
    private static final Map<String, String> strings = new HashMap<>();

    /** Default empty constructor : must be here to confirm the private*/
    private XMLTexts(){}

    /**
     * Initialize the static strings map with strings contained into the given xml file
     * @param fileSystem (FileSystem): given file system used to read the given file name. Not null
     * @param textFileName (String): full file name in the format (path/name.xml). Not null
     *                     - assume the file is a XML one
     */
    public static void initialize(FileSystem fileSystem, String textFileName){

        try {
            InputStream input = fileSystem.read(textFileName);

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            db.setEntityResolver((publicId, systemId) -> new InputSource(new StringReader("")));

            Document doc = db.parse(input);

            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("string");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    // System.out.println("name : " + eElement.getAttribute("name") + " , value: "+eElement.getTextContent());
                    strings.put(eElement.getAttribute("name"), eElement.getTextContent());
                }
            }
        }
        catch (IOException e) {
            // Empty on purpose, will return null as an error
            System.out.println("Text File :"+ textFileName +" not found");
        } catch (SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    /**
     * XMLTexts map accessor for the given key String
     * @param key (String): the key we want the value for
     * @return (String): the value corresponding to the given key if exists or an empty string otherwise.
     */
    public static String getText(String key){

        String s = strings.get(key);
        if(s == null){
            System.out.println("String for key='"+key+"' not found, empty string returned instead");
            return "";
        }
        return s;
    }
}

