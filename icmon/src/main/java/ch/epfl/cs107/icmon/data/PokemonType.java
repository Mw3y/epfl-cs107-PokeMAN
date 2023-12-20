package ch.epfl.cs107.icmon.data;

import java.util.Map;

/**
 * Represents a type of Pokémon.
 * @param name - The name of the type
 * @param effectiveness - The effectiveness of the type against all other types
 */
public record PokemonType(String name, Map<String, Float> effectiveness) {}

