package fr.univavignon.pokedex.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class IPokemonTrainerFactoryTest {

    // The factory to be tested
    private IPokemonTrainerFactory pokemonTrainerFactory;

    // Mock dependencies
    @Mock
    private IPokedexFactory pokedexFactory;

    @Mock
    private IPokedex pokedex;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize the actual trainer factory (assuming concrete implementation exists)
        pokemonTrainerFactory = mock(IPokemonTrainerFactory.class); // Replace this with actual implementation if available

        // Mock the behavior of pokedexFactory
        when(pokedexFactory.createPokedex(any(), any())).thenReturn(pokedex);
    }

    @Test
    public void testCreateTrainer() {
        // Arrange
        String trainerName = "Ash";
        Team team = Team.MYSTIC;
        PokemonTrainer expectedTrainer = new PokemonTrainer(trainerName, team, pokedex);

        // Mock behavior of createTrainer
        when(pokemonTrainerFactory.createTrainer(trainerName, team, pokedexFactory)).thenAnswer(invocation -> {
            String name = invocation.getArgument(0);
            Team trainerTeam = invocation.getArgument(1);
            IPokedexFactory factory = invocation.getArgument(2);

            // Call pokedexFactory to create the pokedex
            IPokedex pokedex = factory.createPokedex(null, null);

            // Return a new PokemonTrainer with the created pokedex
            return new PokemonTrainer(name, trainerTeam, pokedex);
        });

        // Act
        PokemonTrainer trainer = pokemonTrainerFactory.createTrainer(trainerName, team, pokedexFactory);

        // Assert
        assertEquals(trainerName, trainer.getName());
        assertEquals(team, trainer.getTeam());
        assertEquals(pokedex, trainer.getPokedex());

        // Verify interactions with mocks
        verify(pokedexFactory).createPokedex(any(), any());
    }
}
