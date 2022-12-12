package net.lrsoft.primalarcane.mana;

import net.lrsoft.primalarcane.PrimalArcane;
import net.lrsoft.primalarcane.mana.ManaDataManager.ChunkManaData;
import net.lrsoft.primalarcane.network.NetworkHandler;
import net.lrsoft.primalarcane.network.Message.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class ManaHelper {

	public enum ManaType {
		POSITIVE, NEGATIVE, BOTH
	}
	
	public static void updateChunkMana(Chunk chunk) {
		ChunkManaData data = ManaDataManager.getChunkManaData(chunk);

		// calc as second
		long nowTime = chunk.getWorld().getTotalWorldTime();
		// 1s = 20tick
		float deltaTime = (nowTime - data.lastUpdateTime) / 20.0f;
		if(deltaTime < 0) {
			data.lastUpdateTime = nowTime - 1;
			ManaDataManager.setChunkManaData(chunk, data);
			return;
		}

		
		float maxPositiveMana = data.maxMana * data.positiveNegativeRatio;
		float maxNegativeMana = data.maxMana - maxPositiveMana;
		
		float currentMaxMana = data.negativeMana + data.positiveMana;
		
		float updateValue = deltaTime * data.recoverySpeed;
		
		boolean result = false;
		// consume none mana
		if(data.maxMana - currentMaxMana > 1e-2) {
			;
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
						data.positiveMana += updateValue;
						data.negativeMana -= updateValue;
					}else {
						data.positiveMana -= updateValue;
						data.negativeMana += updateValue;
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
			ManaDataManager.setChunkManaData(chunk, data);
		}
	}
	
	public static boolean canConsumeMana(World world, Chunk chunk, ManaType type, float cost) {
		boolean result = false;
		ChunkManaData data = ManaDataManager.getChunkManaData(chunk);
		PrimalArcane.logger.info(data.positiveMana + " " + data.negativeMana + " " + cost);
		switch(type) {
		case POSITIVE:
			return data.positiveMana - cost >= 0;
		case NEGATIVE:
			return data.negativeMana - cost >= 0;
		default:
			return false;
		}
	}

	public static boolean consumeMana(World world, Chunk chunk, ManaType type, float cost) {
		boolean result = false;
		ChunkManaData data = ManaDataManager.getChunkManaData(chunk);
		
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
			
		}
		
		// update mana data
		if(result) {
			ManaDataManager.setChunkManaData(chunk, data);
		}
		
		return result;
	}
	
	public static void sendManaDataToClient(World world, Chunk chunk, EntityPlayerMP player) {
		ChunkManaData data = ManaDataManager.getChunkManaData(chunk);
		NetworkHandler.INSTANCE.sendMessageToPlayer(new MessageMana(data), player);
	}
}
