package net.lrsoft.primalarcane.mana;

import java.util.HashMap;

import net.minecraft.init.Biomes;
import net.minecraft.world.biome.Biome;

public class BiomeMana {
	private static HashMap<Biome, Float> biomeManaPNRate = new HashMap<>();
	private static HashMap<Biome, Float> biomeManaRecoverySpeed = new HashMap<>();
	static {
		// postive-negative mana rate
		addBiomeManaPNRate(Biomes.BIRCH_FOREST_HILLS, 0.6f);
		addBiomeManaPNRate(Biomes.COLD_BEACH, 0.3f);
		addBiomeManaPNRate(Biomes.COLD_TAIGA, 0.3f);
		addBiomeManaPNRate(Biomes.COLD_TAIGA_HILLS, 0.25f);
		addBiomeManaPNRate(Biomes.DEEP_OCEAN, 0.1f);
		addBiomeManaPNRate(Biomes.DEFAULT, 0.6f);
		addBiomeManaPNRate(Biomes.DESERT, 0.8f);
		addBiomeManaPNRate(Biomes.DESERT_HILLS, 0.85f);
		addBiomeManaPNRate(Biomes.EXTREME_HILLS, 0.6f);
		addBiomeManaPNRate(Biomes.EXTREME_HILLS_EDGE, 0.6f);
		addBiomeManaPNRate(Biomes.EXTREME_HILLS_WITH_TREES, 0.6f);
		addBiomeManaPNRate(Biomes.FOREST_HILLS, 0.6f);
		addBiomeManaPNRate(Biomes.FROZEN_OCEAN, 0.2f);
		addBiomeManaPNRate(Biomes.FROZEN_RIVER, 0.2f);
		addBiomeManaPNRate(Biomes.HELL, 1.0f);
		addBiomeManaPNRate(Biomes.ICE_MOUNTAINS, 0.2f);
		addBiomeManaPNRate(Biomes.ICE_PLAINS, 0.2f);
		addBiomeManaPNRate(Biomes.JUNGLE_EDGE, 0.6f);
		addBiomeManaPNRate(Biomes.JUNGLE_HILLS, 0.6f);
		addBiomeManaPNRate(Biomes.MESA, 0.8f);
		addBiomeManaPNRate(Biomes.MESA_CLEAR_ROCK, 0.8f);
		addBiomeManaPNRate(Biomes.MESA_ROCK, 0.8f);
		addBiomeManaPNRate(Biomes.MUTATED_BIRCH_FOREST_HILLS, 0.6f);
		addBiomeManaPNRate(Biomes.MUTATED_EXTREME_HILLS_WITH_TREES, 0.6f);
		addBiomeManaPNRate(Biomes.MUTATED_ICE_FLATS, 0.2f);
		addBiomeManaPNRate(Biomes.MUTATED_MESA, 0.8f);
		addBiomeManaPNRate(Biomes.MUTATED_MESA_CLEAR_ROCK, 0.8f);
		addBiomeManaPNRate(Biomes.MUTATED_MESA_ROCK, 0.8f);
		addBiomeManaPNRate(Biomes.OCEAN, 0.1f);
		addBiomeManaPNRate(Biomes.RIVER, 0.3f);
		addBiomeManaPNRate(Biomes.REDWOOD_TAIGA_HILLS, 0.6f);
		addBiomeManaPNRate(Biomes.ROOFED_FOREST, 0.5f);
		addBiomeManaPNRate(Biomes.VOID, 0.0f);
		
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
	
	public static void addBiomeManaPNRate(Biome biome, float positiveNegativeRatio) {
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
