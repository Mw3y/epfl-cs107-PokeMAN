package ch.epfl.cs107.icmon.data;

import ch.epfl.cs107.icmon.actor.pokemon.Bulbizarre;
import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.actor.pokemon.actions.Attack;
import ch.epfl.cs107.icmon.actor.pokemon.actions.RunAway;
import ch.epfl.cs107.icmon.area.maps.Pokeball;
import ch.epfl.cs107.icmon.gamelogic.fights.ICMonFightAction;
import ch.epfl.cs107.play.areagame.area.Area;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Orientation;
import ch.epfl.cs107.play.math.random.RandomGenerator;
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

    public static final int POKEDEX_SIZE = 493;

    public PokemonDataLoader() {}

    private Document openDataFile(String path) throws ParserConfigurationException, IOException, SAXException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream file = classloader.getResourceAsStream(path + ".xml");

        // Set up the document builder
        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();

        // Change the entity resolver
        db.setEntityResolver((publicId, systemId) -> new InputSource(new StringReader("")));

        // Parse the xml document
        Document document = db.parse(file);
        // optional, but recommended
        // read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
        document.getDocumentElement().normalize();

        return document;
    }

    public Pokemon loadRandom(Area area, Orientation orientation, DiscreteCoordinates coordinates) {
        return load(RandomGenerator.getInstance().nextInt(1, POKEDEX_SIZE), area, orientation, coordinates);
    }

    public Pokemon load(int pokedexId, Area area, Orientation orientation, DiscreteCoordinates coordinates) {
        try {
            Document document = openDataFile("pokedex/pokemon/" + pokedexId);
            // Pokémon data
            BasePokemonStats stats = parseBaseStats(document);
            String name = parseName(document).toLowerCase();
            List<ICMonFightAction> actions = new ArrayList<>();
            actions.add(new Attack());
            actions.add(new RunAway());
            // Create the new Pokémon
            return new Pokemon(area, orientation, coordinates, name, pokedexId, stats.hp(), stats.attack(), stats.defense(), actions);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    private BasePokemonStats parseBaseStats(Document document) {
        NodeList list = document.getElementsByTagName("base_stats");
        Element element = (Element) list.item(0);

        String hpText = element.getElementsByTagName("hp").item(0).getTextContent();
        String attackText = element.getElementsByTagName("atk").item(0).getTextContent();
        String defenseText = element.getElementsByTagName("def").item(0).getTextContent();

        return new BasePokemonStats(hpText, attackText, defenseText);
    }

    private int parsePokedexNationalId(Document document) {
        NodeList list = document.getElementsByTagName("national_id");
        Element element = (Element) list.item(0);
        return Integer.parseInt(element.getTextContent());
    }

    private String parseName(Document document) {
        NodeList list = document.getElementsByTagName("names");
        Element element = (Element) list.item(0);
        return element.getElementsByTagName("en").item(0).getTextContent();
    }

    private List<String> parseAbilities(Document document) {
        NodeList list = document.getElementsByTagName("abilities");
        List<String> abilities = new ArrayList<>();

        for (int temp = 0; temp < list.getLength(); ++temp) {
            Node node = list.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String name = element.getElementsByTagName("name").item(0).getTextContent();
                // Convert the ability name to snake case
                abilities.add(name.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase());
            }
        }

        return abilities;
    }

    private record BasePokemonStats(int hp, int attack, int defense) {
        private BasePokemonStats(String hp, String attack, String defense) {
            this(Integer.parseInt(hp), Integer.parseInt(attack), Integer.parseInt(defense));
        }
    }

}
