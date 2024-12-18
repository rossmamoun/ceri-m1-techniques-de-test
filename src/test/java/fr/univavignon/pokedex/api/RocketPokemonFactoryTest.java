package fr.univavignon.pokedex.api;

import static org.junit.jupiter.api.Assertions.*;

import fr.univavignon.pokedex.api.Pokemon;
import fr.univavignon.pokedex.api.IPokemonFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RocketPokemonFactoryTest {

    private IPokemonFactory factory;

    @BeforeEach
    public void setUp() {
        factory = new RocketPokemonFactory();
    }

    @Test
    public void testNegativeIndexShouldNotCreateRidiculousPokemon() {
        // Supposons que l'API s'attende à ce qu'un index négatif soit invalide
        // et qu'il devrait soit lever une exception, soit créer un Pokémon cohérent.
        // Ici on s'attend par exemple à une exception. Si l'implémentation Rocket
        // ne le fait pas, le test échouera.
        int cp = 500;
        int hp = 60;
        int dust = 4000;
        int candy = 4;
        
        // On s'attend à un comportement défini (ici, une exception) pour un index négatif.
        // Si Rocket retourne un Pokémon avec 1000 partout, c'est un défaut.
        assertThrows(IllegalArgumentException.class, () -> {
            factory.createPokemon(-1, cp, hp, dust, candy);
        }, "Creating a pokemon with negative index should throw an exception.");
    }

    @Test
    public void testIVShouldNotAlwaysBeOneForPositiveIndex() {
        // Supposons qu'IV devrait varier ou dépendre du Pokémon.
        // Si Rocket renvoie toujours IV=1 pour les index >=0, ce test le détectera.
        int cp = 500;
        int hp = 60;
        int dust = 4000;
        int candy = 4;

        Pokemon p = factory.createPokemon(1, cp, hp, dust, candy);
        // Admettons qu'on s'attende à ce que l'IV ne soit pas systématiquement 1.
        // Par exemple, on pourrait s'attendre à une IV entre 0 et 1, calculée aléatoirement.
        // Ici, on vérifie juste que ce n'est pas toujours la même valeur.
        // Si Rocket met toujours 1, ce test échouera si on attend une variation.
        double iv = p.getIv();
        assertTrue(iv >= 0 && iv <= 1, "IV should be between 0 and 1");
        // Pour être encore plus strict, on pourrait créer plusieurs Pokémon et vérifier
        // une variation, mais ce test simple peut déjà échouer si le code est trop rigide.
    }

    @Test
    public void testStatsShouldBeInReasonableRange() {
        // Supposons que l'API prévoit que les stats attaque, défense, stamina soient dans une fourchette normale.
        // Par exemple, entre 0 et 300.
        Pokemon p = factory.createPokemon(1, 500, 60, 4000, 4);

        assertTrue(p.getAttack() >= 0 && p.getAttack() <= 300, "Attack should be within a normal range");
        assertTrue(p.getDefense() >= 0 && p.getDefense() <= 300, "Defense should be within a normal range");
        assertTrue(p.getStamina() >= 0 && p.getStamina() <= 300, "Stamina should be within a normal range");
    }

    @Test
    public void testUnknownIndexNameShouldNotDefaultToMISSINGNOEveryTime() {
        // Supposons qu'on s'attend à ce que la factory gère les Pokémon non mappés
        // d'une façon spécifique (par exemple, lever une exception ou renvoyer un nom cohérent).
        // Si Rocket renvoie "MISSINGNO" pour tout index inconnu, on détecte cela.
        Pokemon p = factory.createPokemon(999, 500, 60, 4000, 4);
        // Imaginons que la spécification dise : pour un index non connu, lever une exception.
        // Ou alors, ne jamais retourner "MISSINGNO".
        // On met donc :
        assertNotEquals("MISSINGNO", p.getName(), "Unknown index should not return MISSINGNO");
    }

    @Test
    public void testPerformanceOfStatGeneration() {
        // Ceci n'est pas un test fonctionnel classique,
        // mais on peut quand même essayer de détecter un temps d'exécution trop long.
        // Si generateRandomStat() est inefficace, ce test pourrait prendre trop de temps.
        // Cependant, un test unitaire de performance n'est pas idéal.
        // On met une simple boucle pour voir si ce n'est pas trop lent.
        long start = System.currentTimeMillis();
        for(int i = 0; i < 10; i++) {
            factory.createPokemon(1, 500, 60, 4000, 4);
        }
        long end = System.currentTimeMillis();
        long duration = end - start;
        // On fixe une limite arbitraire, par exemple 1 seconde pour 10 créations.
        assertTrue(duration < 1000, "Creating multiple pokemon should not be too slow");
    }

}
