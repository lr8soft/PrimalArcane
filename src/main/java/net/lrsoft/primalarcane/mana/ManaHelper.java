package net.lrsoft.primalarcane.mana;

import net.lrsoft.primalarcane.PrimalArcane;
import net.lrsoft.primalarcane.mana.ManaDataManager.ChunkManaData;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class ManaHelper {

	public enum ManaType {
		POSITIVE, NEGATIVE, NONE
	}
	
	public static void updateMana(World world, Chunk chunk) {
		ManaDataManager manager = ManaDataManager.getManager(world);
		ChunkManaData data = manager.getChunkManaData(chunk.getPos());
		if(data == null)
			data = manager.initChunkManaData(chunk);
		
		// calc as second
		long nowTime = System.currentTimeMillis();
		float deltaTime = (System.currentTimeMillis() - data.lastUpdateTime) / 1000.0f;
		
		float maxPositiveMana = data.maxMana * data.positiveNegativeRatio;
		float maxNegativeMana = data.maxMana - data.positiveMana;
		
		float currentMaxMana = data.negativeMana + data.positiveMana;
		
		float updateValue = deltaTime * data.recoverySpeed;
		
		
		
		boolean result = false;
		// consume none mana
		if(data.maxMana - currentMaxMana > 1e-2) {
			float delta = data.maxMana - currentMaxMana;
			if(delta >= updateValue) {
				float newMax = currentMaxMana + updateValue;
				data.positiveMana = newMax * data.positiveNegativeRatio;
				data.negativeMana = newMax - data.positiveMana;
				result = true;
			}
			
		}else {
			// consume NP mana
			float delta = 0.0f;
			boolean addPositive = false;
			
			// consume +mana
			if(maxPositiveMana - data.positiveMana > 1e-4) {
				delta = maxPositiveMana - data.positiveMana;
				addPositive = true;
			}
			// consume -mana
			else if(data.positiveMana - maxPositiveMana > 1e-4) {
				delta = data.positiveMana - maxPositiveMana;
			}

			if(delta != 0.0f) {
				if(delta >= updateValue) {
					if(addPositive) {
						data.positiveMana += delta;
						data.negativeMana -= delta;
					}else {
						data.positiveMana -= delta;
						data.negativeMana += delta;
					}
				}else {
					data.positiveMana = maxPositiveMana;
					data.negativeMana = maxNegativeMana;
				}
				result = true;
			}
		}
		
		if(result) {
			data.lastUpdateTime = nowTime;
			PrimalArcane.logger.info("recovery:" + updateValue + " +:" + data.positiveMana + "-:" + data.negativeMana);
			manager.setChunkManaData(data, chunk.getPos());
		}
	}

	public static boolean consumeMana(World world, Chunk chunk, ManaType type, float cost) {
		//updateMana(world, chunk);
		boolean result = false;
		ManaDataManager manager = ManaDataManager.getManager(world);
		ChunkManaData data = manager.getChunkManaData(chunk.getPos());
		if(data == null)
			data = manager.initChunkManaData(chunk);
		
		PrimalArcane.logger.info("consume +:" + data.positiveMana + " -:" + data.negativeMana 
				+ " max:" + data.maxMana + " s:" + data.recoverySpeed + " r:" + data.positiveNegativeRatio);
		
		if(type == ManaType.POSITIVE) {
			if(data.positiveMana - cost >= 0) {
				data.positiveMana -= cost;
				data.negativeMana += cost;
				result = true;
			}

		}else if(type == ManaType.NEGATIVE){
			if(data.negativeMana - cost >= 0) {
				data.positiveMana += cost;
				data.negativeMana -= cost;
				result = true;
			}
		}else {
			float totalMana = data.positiveMana + data.negativeMana;
			float newTotal = totalMana - cost;
			if(newTotal >= 0) {
				data.positiveMana = newTotal * data.positiveNegativeRatio;
				data.negativeMana = newTotal - data.positiveMana;
				result = true;
			}
		}
		
		// update mana data
		if(result) {
			manager.setChunkManaData(data, chunk.getPos());
		}
		
		return result;
	}
}