package fr.univavignon.pokedex.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class IPokemonFactoryTest {

    @Mock
    private IPokemonFactory pokemonFactory;

    @BeforeEach
    public void setup() {
        // Initialize the mocks
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreatePokemon() {
        // Example data for a Pokemon (let's say Bulbizarre)
        int index = 0;  // Bulbizarre index
        String name = "Bulbizarre";
        int attack = 126;
        int defense = 126;
        int stamina = 90;
        int cp = 613;
        int hp = 64;
        int dust = 4000;
        int candy = 4;
        double iv = 0.56;  // IV is given as 56%

        // Expected Pokemon instance
        Pokemon expectedPokemon = new Pokemon(index, name, attack, defense, stamina, cp, hp, dust, candy, iv);

        // Stub the createPokemon() method to return the expected Pokemon instance
        when(pokemonFactory.createPokemon(index, cp, hp, dust, candy))
                .thenReturn(expectedPokemon);

        // Call the method under test
        Pokemon result = pokemonFactory.createPokemon(index, cp, hp, dust, candy);

        // Assert that the result matches the expected Pokemon
        assertEquals(expectedPokemon.getIndex(), result.getIndex());
        assertEquals(expectedPokemon.getName(), result.getName());
        assertEquals(expectedPokemon.getAttack(), result.getAttack());
        assertEquals(expectedPokemon.getDefense(), result.getDefense());
        assertEquals(expectedPokemon.getStamina(), result.getStamina());
        assertEquals(expectedPokemon.getCp(), result.getCp());
        assertEquals(expectedPokemon.getHp(), result.getHp());
        assertEquals(expectedPokemon.getDust(), result.getDust());
        assertEquals(expectedPokemon.getCandy(), result.getCandy());
        assertEquals(expectedPokemon.getIv(), result.getIv());
    }
}
