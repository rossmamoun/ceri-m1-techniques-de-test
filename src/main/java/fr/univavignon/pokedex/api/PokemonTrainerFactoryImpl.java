package fr.univavignon.pokedex.api;

public class PokemonTrainerFactoryImpl implements IPokemonTrainerFactory {

	private final IPokemonMetadataProvider metadataProvider;
	private final IPokemonFactory pokemonFactory;

	public PokemonTrainerFactoryImpl(IPokemonMetadataProvider metadataProvider, IPokemonFactory pokemonFactory) {
		this.metadataProvider = metadataProvider;
		this.pokemonFactory = pokemonFactory;
	}

	@Override
	public PokemonTrainer createTrainer(String name, Team team, IPokedexFactory pokedexFactory) {
		IPokedex pokedex = pokedexFactory.createPokedex(metadataProvider, pokemonFactory);
		return new PokemonTrainer(name, team, pokedex);
	}
}