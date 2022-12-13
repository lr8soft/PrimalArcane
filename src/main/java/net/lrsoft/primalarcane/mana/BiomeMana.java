package net.lrsoft.primalarcane.mana;

import java.util.HashMap;

import net.minecraft.init.Biomes;
import net.minecraft.world.biome.Biome;

public class BiomeMana {
	private static HashMap<Biome, Float> biomeManaPNRate = new HashMap<>();
	private static HashMap<Biome, Float> biomeManaRecoverySpeed = new HashMap<>();
	static {
		// postive-negative mana rate
		addBiomeManaMultiple(Biomes.BIRCH_FOREST_HILLS, 0.6f);
		addBiomeManaMultiple(Biomes.COLD_BEACH, 0.3f);
		addBiomeManaMultiple(Biomes.COLD_TAIGA, 0.3f);
		addBiomeManaMultiple(Biomes.COLD_TAIGA_HILLS, 0.25f);
		addBiomeManaMultiple(Biomes.DEEP_OCEAN, 0.1f);
		addBiomeManaMultiple(Biomes.DEFAULT, 0.6f);
		addBiomeManaMultiple(Biomes.DESERT, 0.8f);
		addBiomeManaMultiple(Biomes.DESERT_HILLS, 0.85f);
		addBiomeManaMultiple(Biomes.EXTREME_HILLS, 0.6f);
		addBiomeManaMultiple(Biomes.EXTREME_HILLS_EDGE, 0.6f);
		addBiomeManaMultiple(Biomes.EXTREME_HILLS_WITH_TREES, 0.6f);
		addBiomeManaMultiple(Biomes.FOREST_HILLS, 0.6f);
		addBiomeManaMultiple(Biomes.FROZEN_OCEAN, 0.2f);
		addBiomeManaMultiple(Biomes.FROZEN_RIVER, 0.2f);
		addBiomeManaMultiple(Biomes.HELL, 1.0f);
		addBiomeManaMultiple(Biomes.ICE_MOUNTAINS, 0.2f);
		addBiomeManaMultiple(Biomes.ICE_PLAINS, 0.2f);
		addBiomeManaMultiple(Biomes.JUNGLE_EDGE, 0.6f);
		addBiomeManaMultiple(Biomes.JUNGLE_HILLS, 0.6f);
		addBiomeManaMultiple(Biomes.MESA, 0.8f);
		addBiomeManaMultiple(Biomes.MESA_CLEAR_ROCK, 0.8f);
		addBiomeManaMultiple(Biomes.MESA_ROCK, 0.8f);
		addBiomeManaMultiple(Biomes.MUTATED_BIRCH_FOREST_HILLS, 0.6f);
		addBiomeManaMultiple(Biomes.MUTATED_EXTREME_HILLS_WITH_TREES, 0.6f);
		addBiomeManaMultiple(Biomes.MUTATED_ICE_FLATS, 0.2f);
		addBiomeManaMultiple(Biomes.MUTATED_MESA, 0.8f);
		addBiomeManaMultiple(Biomes.MUTATED_MESA_CLEAR_ROCK, 0.8f);
		addBiomeManaMultiple(Biomes.MUTATED_MESA_ROCK, 0.8f);
		addBiomeManaMultiple(Biomes.OCEAN, 0.1f);
		addBiomeManaMultiple(Biomes.RIVER, 0.3f);
		addBiomeManaMultiple(Biomes.REDWOOD_TAIGA_HILLS, 0.6f);
		addBiomeManaMultiple(Biomes.ROOFED_FOREST, 0.5f);
		addBiomeManaMultiple(Biomes.VOID, 0.0f);
		
		// mana recovery speed
		addBiomeManaRecoverySpeed(Biomes.VOID, 10.0f);
		addBiomeManaRecoverySpeed(Biomes.HELL, 10.0f);
		addBiomeManaRecoverySpeed(Biomes.OCEAN, 6.0f);
		addBiomeManaRecoverySpeed(Biomes.BIRCH_FOREST, 5.0f);
		addBiomeManaRecoverySpeed(Biomes.BIRCH_FOREST_HILLS, 5.0f);
		addBiomeManaRecoverySpeed(Biomes.FOREST, 5.0f);
		addBiomeManaRecoverySpeed(Biomes.JUNGLE, 5.0f);
		addBiomeManaRecoverySpeed(Biomes.JUNGLE_EDGE, 5.0f);
		addBiomeManaRecoverySpeed(Biomes.JUNGLE_HILLS, 5.0f);
		addBiomeManaRecoverySpeed(Biomes.REDWOOD_TAIGA, 5.0f);
		addBiomeManaRecoverySpeed(Biomes.REDWOOD_TAIGA_HILLS, 5.0f);
		addBiomeManaRecoverySpeed(Biomes.ROOFED_FOREST, 5.0f);
	}
	
	public static void addBiomeManaMultiple(Biome biome, float positiveNegativeRatio) {
		biomeManaPNRate.put(biome, positiveNegativeRatio);
	}
	
	
	public static void addBiomeManaRecoverySpeed(Biome biome, float speed) {
		biomeManaRecoverySpeed.put(biome, speed);
	}
	
	/**
	 * Return the positive-negative ratio of the mana, default 0.5f
	 * @param biome
	 * @return The positive-negative ratio of the mana
	 */
	public static float getBiomeManaPNRate(Biome biome) {
		return biomeManaPNRate.getOrDefault(biome, 0.5f);
	}
	
	/**
	 * Return the mana recovery speed of the biome, default 1.0f
	 * @param biome
	 * @return The positive-negative ratio of the mana
	 */
	public static float getBiomeManaRecoverySpeed(Biome biome) {
		return biomeManaRecoverySpeed.getOrDefault(biome, 1.0f);
	}
}
