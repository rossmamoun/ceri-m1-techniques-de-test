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
    private IPokemonMetadataProvider metadataProvider;

    @Mock
    private IPokemonFactory pokemonFactory;

    @Mock
    private IPokedexFactory pokedexFactory;

    @Mock
    private IPokedex pokedex;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Instantiate the real PokemonTrainerFactoryImpl
        pokemonTrainerFactory = new PokemonTrainerFactoryImpl(metadataProvider, pokemonFactory);

        // Mock the behavior of pokedexFactory
        when(pokedexFactory.createPokedex(metadataProvider, pokemonFactory)).thenReturn(pokedex);
    }

    @Test
    public void testCreateTrainer() {
        // Arrange
        String trainerName = "Ash";
        Team team = Team.MYSTIC;
        PokemonTrainer expectedTrainer = new PokemonTrainer(trainerName, team, pokedex);

        // Act
        PokemonTrainer trainer = pokemonTrainerFactory.createTrainer(trainerName, team, pokedexFactory);

        // Assert
        assertEquals(trainerName, trainer.getName());
        assertEquals(team, trainer.getTeam());
        assertEquals(pokedex, trainer.getPokedex());

        // Verify interactions with mocks
        verify(pokedexFactory).createPokedex(metadataProvider, pokemonFactory);
    }
}
