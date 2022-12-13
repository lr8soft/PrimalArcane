package net.lrsoft.primalarcane.mana;

import java.io.Serializable;
import java.util.HashMap;

import net.lrsoft.primalarcane.util.Noise.NoiseGenerator;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;

public class ChunkManaManager {
	private static NoiseGenerator generator = new NoiseGenerator();
	
	// <dimId, <chunkPos, data>>
	private static HashMap<Integer, HashMap<ChunkPos, ChunkManaData>> chunkManaData = new HashMap<>();
	
	public static void loadDataFromChunk(Chunk chunk, NBTTagCompound nbt) {
		// 先从NBT读
		ChunkManaData data = readFromNBT(nbt);
		if(data == null)
			data = initChunkManaData(chunk);
		
		// 写入到map
		setChunkManaData(chunk, data);
	}
	
	public static void removeChunkData(Chunk chunk) {
		if(!checkChunkLoad(chunk))
			return;
		
		int dimensionId = chunk.getWorld().provider.getDimension();
		chunkManaData.get(dimensionId).remove(chunk.getPos());
	}
	
	public static void writeDataToChunk(Chunk chunk, NBTTagCompound nbt) {
		ChunkManaData data = getChunkManaData(chunk);
		writeToNBT(data, nbt);
	}
	
	
	public static void setChunkManaData(Chunk chunk, ChunkManaData data) {
		int dimensionId = chunk.getWorld().provider.getDimension();
		// 维度未初始化
		if(!chunkManaData.containsKey(dimensionId)) {
			HashMap<ChunkPos, ChunkManaData> worldManaData = new HashMap<>();
			chunkManaData.put(dimensionId, worldManaData);
		}
		
		chunkManaData.get(dimensionId).put(chunk.getPos(), data);
	}
	
	public static ChunkManaData getChunkManaData(Chunk chunk) {
		int dimensionId = chunk.getWorld().provider.getDimension();
		
		HashMap<ChunkPos, ChunkManaData> chunksData = chunkManaData.getOrDefault(dimensionId, null);
		// 未初始化的新维度
		if(chunksData == null) {
			// 初始化区块
			ChunkManaData data = initChunkManaData(chunk);
			setChunkManaData(chunk, data);
			return data;
		}
		
		ChunkPos pos = chunk.getPos();
		ChunkManaData storageData = chunksData.getOrDefault(pos, null);
		// 区块未加载
		if(storageData == null) {
			// 初始化区块
			storageData = initChunkManaData(chunk);
			chunksData.put(chunk.getPos(), storageData);
		}
		
		return storageData;
	}
	
	private static boolean checkChunkLoad(Chunk chunk) {
		int dimensionId = chunk.getWorld().provider.getDimension();
		
		HashMap<ChunkPos, ChunkManaData> chunksData = chunkManaData.getOrDefault(dimensionId, null);
		// 没有维度信息
		if(chunksData == null) {
			return false;
		}
		
		ChunkPos pos = chunk.getPos();
		ChunkManaData data = chunksData.getOrDefault(pos, null);
		// chunk have not load
		if(data == null) {
			return false;
		}
		
		return true;
	}
	
	
	public static ChunkManaData initChunkManaData(Chunk chunk) {
		ChunkManaData data = new ChunkManaData();

		// 计算区块mana恢复速率
		float recoveryRate = 0.0f;
		byte[] biomes = chunk.getBiomeArray();
		float biomeCount = biomes.length;
		for(int i = 0; i < biomeCount; i++) {
			Biome biome = Biome.getBiome(biomes[i]);
			recoveryRate += BiomeMana.getBiomeManaRecoverySpeed(biome);
		}
		data.recoverySpeed = 30.0f * (recoveryRate / biomeCount);
		
		ChunkPos pos = chunk.getPos();
		data.maxMana = generator.generateHeight(pos.x, pos.z);
		data.mana = data.maxMana;
		data.lastUpdateTime = chunk.getWorld().getTotalWorldTime();
		return data;
	}

	private static ChunkManaData readFromNBT(NBTTagCompound compound) {
		if(!compound.hasKey("maxMana") || !compound.hasKey("recoverySpeed"))
			return null;
		
		ChunkManaData data = new ChunkManaData();
		data.mana = compound.getFloat("mana");
		data.maxMana = compound.getFloat("maxMana");
		data.recoverySpeed = compound.getFloat("recoverySpeed");
		data.lastUpdateTime = compound.getLong("lastUpdateTime");
		return data;
	}


	private static NBTTagCompound writeToNBT(ChunkManaData data, NBTTagCompound compound) {
		if(data == null)
			return compound;
		
		compound.setFloat("mana", data.mana);
		compound.setFloat("maxMana", data.maxMana);
		compound.setFloat("recoverySpeed", data.recoverySpeed);
		compound.setLong("lastUpdateTime", data.lastUpdateTime);
		
		return compound;
	}
	
	public static class ChunkManaData implements Serializable {
		public float mana;
		public float maxMana;
		public float recoverySpeed;
		public long lastUpdateTime;
	}
}
