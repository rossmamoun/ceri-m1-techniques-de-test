package fr.univavignon.pokedex.api;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class IPokedexFactoryTest {

    @Mock
    private IPokemonMetadataProvider metadataProvider;

    @Mock
    private IPokemonFactory pokemonFactory;

    @Mock
    private IPokedexFactory pokedexFactory;

    @Mock
    private IPokedex pokedex;  // Mocking the resulting IPokedex instance

    @BeforeEach
    public void setup() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreatePokedex() {
        // Stub the createPokedex() method to return a mock IPokedex instance
        when(pokedexFactory.createPokedex(metadataProvider, pokemonFactory)).thenReturn(pokedex);

        // Call the method under test
        IPokedex result = pokedexFactory.createPokedex(metadataProvider, pokemonFactory);

        // Verify that the result is not null (i.e., a valid IPokedex is returned)
        assertNotNull(result, "The created Pokedex should not be null.");
    }
}
