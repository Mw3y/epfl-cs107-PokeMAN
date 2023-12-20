package ch.epfl.cs107.icmon.data;

import java.util.Map;

public record PokemonType(String name, Map<String, Float> effectiveness) {}

