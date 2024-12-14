package fr.univavignon.pokedex.api;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class IPokedexFactoryTest {

    @Mock
    private IPokemonMetadataProvider metadataProvider;

    @Mock
    private IPokemonFactory pokemonFactory;

    private IPokedexFactory pokedexFactory;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        // Instantiate the real PokedexFactoryImpl
        pokedexFactory = new PokedexFactoryImpl();
    }

    @Test
    public void testCreatePokedex() {
        // Call the method under test
        IPokedex result = pokedexFactory.createPokedex(metadataProvider, pokemonFactory);

        // Assert that the result is not null and is an instance of PokedexImpl
        assertNotNull(result, "The created Pokedex should not be null.");
        assertTrue(result instanceof PokedexImpl, "The created Pokedex should be an instance of PokedexImpl.");
    }
}
