package fr.univavignon.pokedex.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class IPokemonMetadataProviderTest {
	@Mock
	private IPokemonMetadataProvider metadataProvider;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testGetPokemonMetadata() throws PokedexException {
		PokemonMetadata bulbizarre = new PokemonMetadata(0, "bulbizarre", 126, 126, 90);

		when(metadataProvider.getPokemonMetadata(0)).thenReturn(bulbizarre);

		PokemonMetadata resultat = metadataProvider.getPokemonMetadata(0);

		assertEquals(0, resultat.getIndex());
		assertEquals("bulbizarre", resultat.getName());
		assertEquals(126, resultat.getAttack());
		assertEquals(126, resultat.getDefense());
		assertEquals(90, resultat.getStamina());

	}
}