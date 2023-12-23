package ch.epfl.cs107.icmon.data;

import ch.epfl.cs107.icmon.actor.pokemon.Pokemon;
import ch.epfl.cs107.icmon.actor.pokemon.actions.Attack;
import ch.epfl.cs107.icmon.actor.pokemon.actions.RunAway;
import ch.epfl.cs107.icmon.area.ICMonArea;
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
import java.util.*;

public class PokemonDataLoader {

    public static final int POKEDEX_SIZE = 493;

    private Document pokemonData;
    private int pokedexId;
    private String nameInPokedex;
    private String customName;
    private BasePokemonStats stats;
    private List<PokemonType> types;
    private List<ICMonFightAction> actions;
    private List<PokemonMove> moves;

    public PokemonDataLoader() {
    }

    /**
     * Opens a data file of the Pokédex.
     *
     * @param path - The path of the file, relative to the Pokédex folder
     * @return the document content.
     */
    private static Document openDataFile(String path) {
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
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Loads a random Pokémon from the Pokédex.
     * @return the data loader instance.
     */
    public PokemonDataLoader loadRandom() {
        return load(RandomGenerator.getInstance().nextInt(1, POKEDEX_SIZE + 1));
    }

    /**
     * Loads a random Pokémon from the Pokédex based on its national id with a custom name.
     *
     * @param pokedexId   - The national id of the Pokémon
     * @return the data loader instance.
     */
    public PokemonDataLoader load(int pokedexId) {
        this.pokemonData = openDataFile("pokemon/" + pokedexId);
        // Pokémon data
        this.pokedexId = pokedexId;
        this.stats = parsePokemonBaseStats();
        this.nameInPokedex = parsePokemonName().toLowerCase();
        this.types = parsePokemonTypes();
        this.actions = new ArrayList<>();
        this.moves = parsePokemonMoves();
        // Convert moves into attacks
        for (PokemonMove move : moves) {
            actions.add(new Attack(move.name(), move.power()));
        }
        // Each Pokémon has a run-away attack but only the player can use it.
        actions.add(new RunAway());

        return this;
    }



    public PokemonDataLoader putCustomActionName(String newName) {
        if (!actions.isEmpty()) {
            ICMonFightAction action = actions.get(0);
            actions.add(new Attack(newName, action.power()));
            actions.remove(0);
        }
        return this;
    }

    public Pokemon toPokemon(Area area, Orientation orientation, DiscreteCoordinates coordinates) {
        String definitiveName = customName == null ? nameInPokedex : customName;
        return new Pokemon(area, orientation, coordinates, definitiveName, pokedexId, types, stats.attack, stats.defense, stats.hp(), actions);
    }

    public PokemonDataLoader multiplyHealthStatBy(float multiplier) {
        this.stats = new BasePokemonStats((int) (stats.hp() * multiplier), (int) (stats.attack()), (int) (stats.defense()));
        return this;
    }


    /**
     * Extracts the base stats of the Pokémon from the Pokédex file.
     *
     * @return the base stats of the Pokémon.
     */
    private BasePokemonStats parsePokemonBaseStats() {
        NodeList list = pokemonData.getElementsByTagName("base_stats");
        Element element = (Element) list.item(0);

        String hpText = element.getElementsByTagName("hp").item(0).getTextContent();
        String attackText = element.getElementsByTagName("atk").item(0).getTextContent();
        String defenseText = element.getElementsByTagName("def").item(0).getTextContent();

        return new BasePokemonStats(hpText, attackText, defenseText);
    }

    /**
     * Extracts the name of the Pokémon from the Pokédex file.
     *
     * @return the Pokémon name.
     */
    private String parsePokemonName() {
        NodeList list = pokemonData.getElementsByTagName("names");
        Element element = (Element) list.item(0);
        return element.getElementsByTagName("en").item(0).getTextContent();
    }

    /**
     * Extracts the types of the Pokémon from the Pokédex.
     *
     * @return a list of Pokémon types with their effectiveness against other types.
     */
    private List<PokemonType> parsePokemonTypes() {
        Element typesTag = (Element) pokemonData.getElementsByTagName("types").item(0);
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

    /**
     * Extracts the effectiveness of a type against another from the Pokédex.
     *
     * @return the effectiveness against all Pokémon types for this type.
     */
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

    /**
     * Extracts the possible moves for this Pokémon from the Pokédex.
     *
     * @return a list of moves for this Pokémon.
     */
    private List<PokemonMove> parsePokemonMoves() {
        NodeList list = pokemonData.getElementsByTagName("move");
        Set<String> alreadyLoadedMoves = new HashSet<>();
        List<PokemonMove> moves = new ArrayList<>();

        for (int temp = 0; temp < list.getLength(); ++temp) {
            Node node = list.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String name = element.getTextContent();
                // Convert the move name to snake case to load the corresponding move
                String fileName = name.replaceAll(" ", "_")
                        .replaceAll("-", "_").toLowerCase();

                // Only add 3 moves for the Pokémon
                if (!alreadyLoadedMoves.contains(fileName) && alreadyLoadedMoves.size() <= 3) {
                    Document move = openDataFile("move/" + fileName);
                    if (move != null) {
                        int power = parseMovePower(move);
                        if (power > 0) {
                            moves.add(new PokemonMove(name, power));
                            alreadyLoadedMoves.add(fileName);
                        }
                    }
                }
            }
        }
        return moves;
    }

    /**
     * Extracts the power of a move from the Pokédex.
     *
     * @param document - The Pokémon file for the move
     * @return the power of the move.
     */
    private int parseMovePower(Document document) {
        NodeList list = document.getElementsByTagName("power");
        Element element = (Element) list.item(0);
        return Integer.parseInt(element.getTextContent());
    }

    /**
     * Represents the base stats of a Pokémon.
     *
     * @param hp      - The base hp
     * @param attack  - The base attack
     * @param defense - The base defense
     */
    private record BasePokemonStats(int hp, int attack, int defense) {
        private BasePokemonStats(String hp, String attack, String defense) {
            this(Integer.parseInt(hp), Integer.parseInt(attack), Integer.parseInt(defense));
        }
    }

}
