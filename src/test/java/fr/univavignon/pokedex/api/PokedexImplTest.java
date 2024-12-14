package fr.univavignon.pokedex.api;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Comparator;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class PokedexImplTest {

    private PokedexImpl pokedex;

    @Mock
    private IPokemonMetadataProvider metadataProvider;

    @Mock
    private IPokemonFactory pokemonFactory;

    @BeforeEach
    public void setup() throws PokedexException {
        MockitoAnnotations.openMocks(this);
        pokedex = new PokedexImpl(metadataProvider, pokemonFactory);

        // Mocking metadata for the pokemon with index 0
        when(metadataProvider.getPokemonMetadata(0)).thenReturn(new PokemonMetadata(0, "Bulbasaur", 100, 100, 100));
    }

    @Test
    public void testAddPokemon() {
        Pokemon pokemon = new Pokemon(0, "Bulbasaur", 115, 115, 115, 500, 100, 2000, 3, 100.0);
        int index = pokedex.addPokemon(pokemon);
        
        assertEquals(0, index, "The index of the first added Pokemon should be 0.");
        assertEquals(1, pokedex.size(), "The size should be 1 after adding one Pokemon.");
    }

    @Test
    public void testGetPokemon() throws PokedexException {
        Pokemon pokemon = new Pokemon(0, "Bulbasaur", 115, 115, 115, 500, 100, 2000, 3, 100.0);
        pokedex.addPokemon(pokemon);

        Pokemon retrievedPokemon = pokedex.getPokemon(0);
        assertNotNull(retrievedPokemon, "Retrieved Pokemon should not be null.");
        assertEquals(pokemon, retrievedPokemon, "The retrieved Pokemon should match the added Pokemon.");
    }

    @Test
    public void testGetPokemonWithInvalidIndex() {
        assertThrows(PokedexException.class, () -> pokedex.getPokemon(-1), "Should throw exception for negative index.");
        assertThrows(PokedexException.class, () -> pokedex.getPokemon(1), "Should throw exception for out-of-bounds index.");
    }

    @Test
    public void testGetPokemons() {
        Pokemon pokemon1 = new Pokemon(0, "Bulbasaur", 115, 115, 115, 500, 100, 2000, 3, 100.0);
        Pokemon pokemon2 = new Pokemon(1, "Ivysaur", 130, 130, 130, 800, 150, 3000, 3, 80.0);
        
        pokedex.addPokemon(pokemon1);
        pokedex.addPokemon(pokemon2);

        List<Pokemon> pokemons = pokedex.getPokemons();
        assertEquals(2, pokemons.size(), "There should be 2 Pokemons in the list.");
        assertTrue(pokemons.contains(pokemon1) && pokemons.contains(pokemon2), "Both added Pokemons should be in the list.");
    }

    @Test
    public void testSize() {
        assertEquals(0, pokedex.size(), "Size should be 0 initially.");
        
        pokedex.addPokemon(new Pokemon(0, "Bulbasaur", 115, 115, 115, 500, 100, 2000, 3, 100.0));
        assertEquals(1, pokedex.size(), "Size should be 1 after adding one Pokemon.");
    }

    @Test
    public void testGetPokemonMetadata() throws PokedexException {
        PokemonMetadata metadata = pokedex.getPokemonMetadata(0);
        assertNotNull(metadata, "Metadata should not be null.");
        assertEquals("Bulbasaur", metadata.getName(), "The Pokemon name should match the mocked metadata.");
    }

    @Test
    public void testGetPokemonMetadataWithInvalidIndex() {
        assertThrows(PokedexException.class, () -> pokedex.getPokemonMetadata(-1), "Should throw exception for negative index.");
    }

    @Test
    public void testCreatePokemon() throws PokedexException {
        when(metadataProvider.getPokemonMetadata(0)).thenReturn(new PokemonMetadata(0, "Bulbasaur", 100, 100, 100));
        
        Pokemon pokemon = pokedex.createPokemon(0, 500, 100, 2000, 3);
        
        assertNotNull(pokemon, "Created Pokemon should not be null.");
        assertEquals("Bulbasaur", pokemon.getName(), "Pokemon name should match metadata.");
        assertEquals(0, pokemon.getIndex(), "Pokemon index should be 0.");
        assertTrue(pokemon.getAttack() >= 100 && pokemon.getAttack() <= 115, "Attack should be within base + bonus range.");
        assertTrue(pokemon.getDefense() >= 100 && pokemon.getDefense() <= 115, "Defense should be within base + bonus range.");
        assertTrue(pokemon.getStamina() >= 100 && pokemon.getStamina() <= 115, "Stamina should be within base + bonus range.");
        assertTrue(pokemon.getIv() >= 0 && pokemon.getIv() <= 100, "IV should be within valid percentage range.");
    }

    @Test
    public void testGetPokemonsSorted() {
        Pokemon pokemon1 = new Pokemon(0, "Bulbasaur", 100, 100, 100, 500, 100, 2000, 3, 50.0);
        Pokemon pokemon2 = new Pokemon(1, "Ivysaur", 130, 130, 130, 800, 150, 3000, 3, 80.0);
        
        pokedex.addPokemon(pokemon2);
        pokedex.addPokemon(pokemon1);

        List<Pokemon> sortedPokemons = pokedex.getPokemons(Comparator.comparing(Pokemon::getName));
        
        assertEquals(pokemon1, sortedPokemons.get(0), "Pokemons should be sorted by name with Bulbasaur first.");
        assertEquals(pokemon2, sortedPokemons.get(1), "Ivysaur should be second in sorted list.");
    }
}
