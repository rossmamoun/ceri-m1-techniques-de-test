package fr.univavignon.pokedex.api;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Comparator;

public class IPokedexTest {

    @Mock
    private IPokedex pokedex;

    private Pokemon pokemon1;
    private Pokemon pokemon2;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        pokemon1 = new Pokemon(0, "Bulbizarre", 126, 126, 90, 613, 64, 4000, 4, 56.0);
        pokemon2 = new Pokemon(133, "Aquali", 186, 168, 260, 2729, 202, 5000, 4, 100.0);
    }

    @Test
    public void testAddPokemon() {
        // Setup mocks for addPokemon and size
        when(pokedex.size()).thenReturn(0);
        when(pokedex.addPokemon(pokemon1)).thenAnswer(invocation -> {
            when(pokedex.size()).thenReturn(1);
            return 0;
        });

        int index = pokedex.addPokemon(pokemon1);

        assertEquals(0, index, "The index should be 0 for the first Pokemon added.");
        assertEquals(1, pokedex.size(), "The size should be 1 after adding one Pokemon.");
    }

    @Test
    public void testGetPokemon() throws PokedexException {
        when(pokedex.addPokemon(pokemon1)).thenReturn(0);
        when(pokedex.getPokemon(0)).thenReturn(pokemon1);

        pokedex.addPokemon(pokemon1);
        Pokemon result = pokedex.getPokemon(0);

        assertNotNull(result, "The retrieved Pokemon should not be null.");
        assertEquals(pokemon1, result, "The retrieved Pokemon should match the expected Pokemon.");
    }

    @Test
    public void testGetPokemonInvalidIndex() {
        assertThrows(PokedexException.class, () -> pokedex.getPokemon(-1), 
                     "Retrieving a Pokemon with an invalid index should throw a PokedexException.");
    }

    @Test
    public void testGetPokemons() {
        when(pokedex.getPokemons()).thenReturn(Arrays.asList(pokemon1, pokemon2));

        List<Pokemon> pokemons = pokedex.getPokemons();

        assertEquals(2, pokemons.size(), "The list of Pokemons should contain two Pokémon.");
        assertTrue(pokemons.containsAll(Arrays.asList(pokemon1, pokemon2)), "The list should contain both Pokemons.");
    }

    @Test
    public void testGetPokemonsWithOrder() {
        Comparator<Pokemon> cpComparator = PokemonComparators.CP;
        when(pokedex.getPokemons(cpComparator)).thenReturn(Arrays.asList(pokemon2, pokemon1));

        List<Pokemon> sortedByCP = pokedex.getPokemons(cpComparator);

        assertEquals(2, sortedByCP.size(), "The sorted list of Pokemons should contain two Pokémon.");
        assertEquals(pokemon2, sortedByCP.get(0), "Aquali should come first by CP.");
        assertEquals(pokemon1, sortedByCP.get(1), "Bulbizarre should come second by CP.");

        Comparator<Pokemon> nameComparator = PokemonComparators.NAME;
        when(pokedex.getPokemons(nameComparator)).thenReturn(Arrays.asList(pokemon1, pokemon2));

        List<Pokemon> sortedByName = pokedex.getPokemons(nameComparator);

        assertEquals(2, sortedByName.size(), "The sorted list of Pokemons should contain two Pokémon.");
        assertEquals(pokemon1, sortedByName.get(0), "Bulbizarre should come first by name.");
        assertEquals(pokemon2, sortedByName.get(1), "Aquali should come second by name.");
    }

    // Additional test for createPokemon if applicable
    @Test
    public void testCreatePokemon() throws PokedexException {
        Pokemon createdPokemon = new Pokemon(0, "Bulbizarre", 126, 126, 90, 613, 64, 4000, 4, 56.0);
        when(pokedex.createPokemon(0, 613, 64, 4000, 4)).thenReturn(createdPokemon);

        Pokemon result = pokedex.createPokemon(0, 613, 64, 4000, 4);

        assertNotNull(result, "Created Pokemon should not be null.");
        assertEquals(createdPokemon, result, "Created Pokemon should match the expected Pokemon.");
    }
}
