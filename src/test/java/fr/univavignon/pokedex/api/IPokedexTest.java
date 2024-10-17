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
        // Initialize Mockito annotations
        MockitoAnnotations.openMocks(this);

        // Create real instances of Pokemon instead of mocking
        pokemon1 = new Pokemon(0, "Bulbizarre", 126, 126, 90, 613, 64, 4000, 4, 56.0);
        pokemon2 = new Pokemon(133, "Aquali", 186, 168, 260, 2729, 202, 5000, 4, 100.0);
    }

    @Test
    public void testAddPokemon() {
        // Mock initial size before adding any Pokemon
        when(pokedex.size()).thenReturn(0);
        
        // Mock the behavior of adding a Pokemon
        when(pokedex.addPokemon(pokemon1)).thenAnswer(invocation -> {
            // After adding the Pokemon, mock the size to return 1
            when(pokedex.size()).thenReturn(1);
            return 0;  // Return the index for the first Pokemon
        });

        // Add Pokemon
        int index = pokedex.addPokemon(pokemon1);

        // Verify the index returned is correct
        assertEquals(0, index, "The index should be 0 for the first Pokemon added.");
        
        // Verify that the size has been updated after adding the Pokemon
        assertEquals(1, pokedex.size(), "The size of the pokedex should be 1 after adding one Pokemon.");
    }

    @Test
    public void testGetPokemon() throws PokedexException {
        // Setup mock behavior
        when(pokedex.addPokemon(pokemon1)).thenReturn(0);
        when(pokedex.getPokemon(0)).thenReturn(pokemon1);

        // Add and retrieve Pokemon
        pokedex.addPokemon(pokemon1);
        Pokemon result = pokedex.getPokemon(0);

        // Verify
        assertNotNull(result, "The retrieved Pokemon should not be null.");
        assertEquals(pokemon1, result, "The retrieved Pokemon should match the expected Pokemon.");
    }

    @Test
    public void testGetPokemonInvalidIndex() {
        // Setup mock behavior
        try {
			when(pokedex.getPokemon(-1)).thenThrow(new PokedexException("Invalid Pokemon ID: -1"));
		} catch (PokedexException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        // Verify that an exception is thrown for an invalid index
        assertThrows(PokedexException.class, () -> {
            pokedex.getPokemon(-1);
        }, "Retrieving a Pokemon with an invalid index should throw a PokedexException.");
    }

    @Test
    public void testGetPokemons() {
        // Setup mock behavior
        when(pokedex.getPokemons()).thenReturn(Arrays.asList(pokemon1, pokemon2));

        // Get list of Pokemons
        List<Pokemon> pokemons = pokedex.getPokemons();

        // Verify
        assertEquals(2, pokemons.size(), "The list of Pokemons should contain two Pokémon.");
        assertTrue(pokemons.contains(pokemon1), "The list should contain the first Pokemon.");
        assertTrue(pokemons.contains(pokemon2), "The list should contain the second Pokemon.");
    }

    @Test
    public void testGetPokemonsWithOrder() {
        // Setup a comparator
        Comparator<Pokemon> comparator = Comparator.comparing(Pokemon::getCp);

        // Setup mock behavior
        when(pokedex.getPokemons(comparator)).thenReturn(Arrays.asList(pokemon2, pokemon1));

        // Get sorted list of Pokemons
        List<Pokemon> sortedPokemons = pokedex.getPokemons(comparator);

        // Verify
        assertEquals(2, sortedPokemons.size(), "The sorted list of Pokemons should contain two Pokémon.");
        assertEquals(pokemon2, sortedPokemons.get(0), "The first Pokemon should be the one with the higher CP.");
        assertEquals(pokemon1, sortedPokemons.get(1), "The second Pokemon should be the one with the lower CP.");
    }
}
