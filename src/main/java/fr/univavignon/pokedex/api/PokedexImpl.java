package fr.univavignon.pokedex.api;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class PokedexImpl implements IPokedex {

    private final List<Pokemon> pokemons;
    private final IPokemonMetadataProvider metadataProvider;
    private final IPokemonFactory pokemonFactory;

    public PokedexImpl(IPokemonMetadataProvider metadataProvider, IPokemonFactory pokemonFactory) {
        this.pokemons = new ArrayList<>();
        this.metadataProvider = metadataProvider;
        this.pokemonFactory = pokemonFactory;
    }

    @Override
    public int addPokemon(Pokemon pokemon) {
        pokemons.add(pokemon);
        return pokemons.size() - 1; // Return the index of the added Pokémon
    }

    @Override
    public Pokemon getPokemon(int index) throws PokedexException {
        if (index < 0 || index >= pokemons.size()) {
            throw new PokedexException("Invalid Pokemon index: " + index);
        }
        return pokemons.get(index);
    }

    @Override
    public List<Pokemon> getPokemons() {
        return new ArrayList<>(pokemons); // Return a copy of the list
    }

    @Override
    public int size() {
        return pokemons.size();
    }

    @Override
    public PokemonMetadata getPokemonMetadata(int index) throws PokedexException {
        if (index < 0) {
            throw new PokedexException("Invalid Pokemon index: " + index);
        }
        return metadataProvider.getPokemonMetadata(index); // Retrieve metadata via the provider
    }

    @Override
    public Pokemon createPokemon(int index, int cp, int hp, int dust, int candy) {
        // Retrieve metadata for the given index to create the Pokemon instance
        try {
            PokemonMetadata metadata = metadataProvider.getPokemonMetadata(index);
            Random random = new Random();
            int attackBonus = random.nextInt(16); // Random number between 0 and 15
            int defenseBonus = random.nextInt(16);
            int staminaBonus = random.nextInt(16);

            // Calculate the actual stats with the random bonus added to the base stats
            int actualAttack = metadata.getAttack() + attackBonus;
            int actualDefense = metadata.getDefense() + defenseBonus;
            int actualStamina = metadata.getStamina() + staminaBonus;
            // Compute IVs based on metadata and provided values
            double iv = ((attackBonus + defenseBonus + staminaBonus)/45)*100;
            return new Pokemon(index, metadata.getName(), actualAttack, actualDefense, 
                               actualStamina, cp, hp, dust, candy, iv);
        } catch (PokedexException e) {
            System.err.println("Error creating Pokemon: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Pokemon> getPokemons(Comparator<Pokemon> order) {
        List<Pokemon> sortedPokemons = new ArrayList<>(pokemons);
        sortedPokemons.sort(order);
        return sortedPokemons;
    }
}
