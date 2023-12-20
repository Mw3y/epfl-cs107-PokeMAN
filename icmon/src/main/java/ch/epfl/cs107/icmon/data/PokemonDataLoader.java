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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PokemonDataLoader {

    public static final int POKEDEX_SIZE = 493;

    public PokemonDataLoader() {}

    private Document openDataFile(String path) {
        try {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream file = classloader.getResourceAsStream("pokedex/" + path + ".xml");

            if (file == null)
                throw new FileNotFoundException("The pokemon data file was not found! " + path);

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
        } catch (ParserConfigurationException | IOException | SAXException e) {
            System.err.println(e);
            return null;
        }
    }

    public Pokemon loadRandom(Area area, Orientation orientation, DiscreteCoordinates coordinates) {
        return load(RandomGenerator.getInstance().nextInt(1, POKEDEX_SIZE), area, orientation, coordinates);
    }

    public Pokemon load(int pokedexId, Area area, Orientation orientation, DiscreteCoordinates coordinates) {
            Document document = openDataFile("pokemon/" + pokedexId);
            // Pokémon data
            BasePokemonStats stats = parsePokemonBaseStats(document);
            String name = parsePokemonName(document).toLowerCase();
            List<PokemonType> types = parsePokemonTypes(document);
            List<ICMonFightAction> actions = new ArrayList<>();
            List<PokemonMove> moves = parsePokemonMoves(document);

            for (PokemonMove move : moves) {
                actions.add(new Attack(move.name(), move.power()));
            }
            // Each Pokémon has a run-away attack but only the player can use it.
            actions.add(new RunAway());
            // Create the new Pokémon
            return new Pokemon(area, orientation, coordinates, name, pokedexId, types, stats.hp(), stats.attack(), stats.defense(), actions);
    }

    private BasePokemonStats parsePokemonBaseStats(Document document) {
        NodeList list = document.getElementsByTagName("base_stats");
        Element element = (Element) list.item(0);

        String hpText = element.getElementsByTagName("hp").item(0).getTextContent();
        String attackText = element.getElementsByTagName("atk").item(0).getTextContent();
        String defenseText = element.getElementsByTagName("def").item(0).getTextContent();

        return new BasePokemonStats(hpText, attackText, defenseText);
    }

    private String parsePokemonName(Document document) {
        NodeList list = document.getElementsByTagName("names");
        Element element = (Element) list.item(0);
        return element.getElementsByTagName("en").item(0).getTextContent();
    }

    private List<PokemonType> parsePokemonTypes(Document document) {
        Element typesTag = (Element) document.getElementsByTagName("types").item(0);
        NodeList list = typesTag.getElementsByTagName("item");
        List<PokemonType> types = new ArrayList<>();

        for (int temp = 0; temp < list.getLength(); ++temp) {
            Node node = list.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                String typeName = node.getTextContent().toLowerCase();
                Document type = openDataFile("type/" + typeName);

                if (type != null)
                    types.add(new PokemonType(typeName, parseEffectivenessAgainstOtherTypes(type)));
            }
        }
        return types;
    }

    private Map<String, Float> parseEffectivenessAgainstOtherTypes(Document document) {
        NodeList effectivenessTags = document.getElementsByTagName("effectivness").item(0).getChildNodes();
        Map<String, Float> effectivenessMap = new HashMap<>();

        for (int temp = 0; temp < effectivenessTags.getLength(); ++temp) {
            Node node = effectivenessTags.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String typeName = element.getTagName().toLowerCase();
                Float effectiveness = Float.parseFloat(element.getTextContent());
                effectivenessMap.put(typeName, effectiveness);
            }
        }

        return effectivenessMap;
    }

    private List<PokemonMove> parsePokemonMoves(Document document) {
        NodeList list = document.getElementsByTagName("move");
        List<PokemonMove> moves = new ArrayList<>();

        for (int temp = 0; temp < list.getLength(); ++temp) {
            Node node = list.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String name = element.getTextContent();
                // Convert the move name to snake case to load the corresponding move
                String fileName = name.replaceAll(" ", "_")
                        .replaceAll("-", "_").toLowerCase();

                Document move = openDataFile("move/" + fileName);
                if (move != null) {
                    int power = parseMovePower(move);
                    if (power > 0)
                        moves.add(new PokemonMove(name, power));
                }
            }
        }
        return moves;
    }

    private int parseMovePower(Document document) {
        NodeList list = document.getElementsByTagName("power");
        Element element = (Element) list.item(0);
        return Integer.parseInt(element.getTextContent());
    }

    private record BasePokemonStats(int hp, int attack, int defense) {
        private BasePokemonStats(String hp, String attack, String defense) {
            this(Integer.parseInt(hp), Integer.parseInt(attack), Integer.parseInt(defense));
        }
    }

}
