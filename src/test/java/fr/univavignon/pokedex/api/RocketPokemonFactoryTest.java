package fr.univavignon.pokedex.api;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.univavignon.pokedex.api.IPokemonFactory;
import fr.univavignon.pokedex.api.Pokemon;

public class RocketPokemonFactoryTest {

    private IPokemonFactory factory;

    @BeforeEach
    public void setUp() {
        factory = new RocketPokemonFactory();
    }

    @Test
    public void testCreatePokemonWithMappedIndex() {
        // Index 1 est "Bulbasaur" selon la map
        int index = 1;
        int cp = 500;
        int hp = 60;
        int dust = 4000;
        int candy = 4;

        Pokemon result = factory.createPokemon(index, cp, hp, dust, candy);

        // Vérification de base, on sait que la map a l'entrée 1 -> "Bulbasaur"
        assertEquals("Bulbasaur", result.getName(), "Should return Bulbasaur for index 1");
        assertEquals(index, result.getIndex(), "Index should match");
        assertEquals(cp, result.getCp(), "CP should match input");
        assertEquals(hp, result.getHp(), "HP should match input");
        assertEquals(dust, result.getDust(), "Dust should match input");
        assertEquals(candy, result.getCandy(), "Candy should match input");
    }

    @Test
    public void testCreatePokemonWithNonMappedIndex() {
        // Index 999 n'est pas dans la map, cela doit retourner "MISSINGNO"
        int index = 999;
        int cp = 300;
        int hp = 50;
        int dust = 2000;
        int candy = 2;

        Pokemon result = factory.createPokemon(index, cp, hp, dust, candy);

        // On sait que si l'index n'est pas trouvé, Rocket retourne "MISSINGNO"
        assertEquals("MISSINGNO", result.getName(), "Should return MISSINGNO for unknown index");
        // Vérifier que le reste des attributs sont bien ceux passés en paramètres
        assertEquals(index, result.getIndex());
        assertEquals(cp, result.getCp());
        assertEquals(hp, result.getHp());
        assertEquals(dust, result.getDust());
        assertEquals(candy, result.getCandy());
    }

    @Test
    public void testCreatePokemonNoExceptionForNormalIndex() {
        // On ne s'attend pas à d'exception pour un index standard
        int index = 1;
        int cp = 500;
        int hp = 60;
        int dust = 4000;
        int candy = 4;

        assertDoesNotThrow(() -> {
            factory.createPokemon(index, cp, hp, dust, candy);
        }, "Should not throw an exception for a normal valid index");
    }
}
