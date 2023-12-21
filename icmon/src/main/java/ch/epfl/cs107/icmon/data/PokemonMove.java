package ch.epfl.cs107.icmon.data;

/**
 * Represents a move that a Pokémon can execute.
 * @param name - The name of the move
 * @param power - The power of the move
 */
public record PokemonMove(String name, int power) {}
