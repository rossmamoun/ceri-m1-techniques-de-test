package fr.univavignon.pokedex.api;

public class PokedexFactoryImpl implements IPokedexFactory {

    @Override
    public IPokedex createPokedex(IPokemonMetadataProvider metadataProvider, IPokemonFactory pokemonFactory) {
        // Create and return a new instance of PokedexImpl, providing it with the metadata provider and pokemon factory
        return new PokedexImpl(metadataProvider, pokemonFactory);
    }
}
