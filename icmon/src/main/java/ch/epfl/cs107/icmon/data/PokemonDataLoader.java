package ch.epfl.cs107.icmon.data;

import ch.epfl.cs107.icmon.actor.pokemon.Bulbizarre;
import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.area.maps.Pokeball;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class PokemonDataLoader {

    private String pokemonName;
    private Document document;

    public PokemonDataLoader(String pokemonName) throws IOException, SAXException, ParserConfigurationException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream file = classloader.getResourceAsStream("pokedex/pokemon_xml/pikachu.xml");

        // Set up the document builder
        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();

        // Change the entity resolver
        db.setEntityResolver((publicId, systemId) -> new InputSource(new StringReader("")));

        // Parse the xml document
        Document document = db.parse(file);
        // optional, but recommended
        // read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
        document.getDocumentElement().normalize();

        this.pokemonName = pokemonName;
        this.document = document;
    }

    public BasePokemonStats parseBaseStats() {
        NodeList list = document.getElementsByTagName("base_stats");
        Element element = (Element) list.item(0);

        String hpText = element.getElementsByTagName("hp").item(0).getTextContent();
        String attackText = element.getElementsByTagName("atk").item(0).getTextContent();
        String defenseText = element.getElementsByTagName("def").item(0).getTextContent();

        return new BasePokemonStats(Integer.parseInt(hpText), Integer.parseInt(attackText), Integer.parseInt(defenseText));
    }

    public List<String> parseAbilities() {
        NodeList list = document.getElementsByTagName("abilities");
        List<String> abilities = new ArrayList<>();

        for (int temp = 0; temp < list.getLength(); ++temp) {
            Node node = list.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String name = element.getElementsByTagName("name").item(0).getTextContent();
                abilities.add(name);
            }
        }

        return abilities;
    }

}
