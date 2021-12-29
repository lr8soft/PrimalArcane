package net.lrsoft.primalarcane.mana;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import net.lrsoft.primalarcane.PrimalArcane;
import net.lrsoft.primalarcane.util.MathUtils;
import net.lrsoft.primalarcane.util.Noise;
import net.lrsoft.primalarcane.util.Noise.NoiseGenerator;
import net.minecraft.init.Biomes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;

public class ManaDataManager {

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
		// new dimension have not init
		if(chunksData == null) {
			// init chunk
			ChunkManaData data = initChunkManaData(chunk);
			setChunkManaData(chunk, data);
			return data;
		}
		
		ChunkPos pos = chunk.getPos();
		ChunkManaData storageData = chunksData.getOrDefault(pos, null);
		// chunk have not load
		if(storageData == null) {
			// init chunk
			storageData = initChunkManaData(chunk);
			chunksData.put(chunk.getPos(), storageData);
		}
		
		return storageData;
	}
	
	private static boolean checkChunkLoad(Chunk chunk) {
		int dimensionId = chunk.getWorld().provider.getDimension();
		
		HashMap<ChunkPos, ChunkManaData> chunksData = chunkManaData.getOrDefault(dimensionId, null);
		// no dimension data
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
		
		// calc biome mana ratio
		float totalRate = 0.0f;
		float recoveryRate = 0.0f;
		byte[] biomes = chunk.getBiomeArray();
		float biomeCount = biomes.length;
		for(int i = 0; i < biomeCount; i++) {
			Biome biome = Biome.getBiome(biomes[i]);
			totalRate += BiomeMana.getBiomeManaPNRate(biome);
			recoveryRate += BiomeMana.getBiomeManaRecoverySpeed(biome);
		}
		data.positiveNegativeRatio = totalRate / biomeCount;
		data.recoverySpeed = 15.0f * (recoveryRate / biomeCount);
		
		ChunkPos pos = chunk.getPos();
		data.maxMana = generator.generateHeight(pos.x, pos.z);
		
		data.positiveMana = data.positiveNegativeRatio * data.maxMana;
		data.negativeMana = data.maxMana - data.positiveMana;
		
		data.lastUpdateTime = chunk.getWorld().getTotalWorldTime();
		
		//this.setChunkManaData(data, chunk.getPos());
		return data;
	}

	private static ChunkManaData readFromNBT(NBTTagCompound compound) {
		if(!compound.hasKey("maxMana") || !compound.hasKey("positiveNegativeRatio") || !compound.hasKey("recoverySpeed"))
			return null;
		
		ChunkManaData data = new ChunkManaData();
		data.positiveMana = compound.getFloat("positiveMana");
		data.negativeMana = compound.getFloat("negativeMana");
		data.maxMana = compound.getFloat("maxMana");
		data.positiveNegativeRatio = compound.getFloat("positiveNegativeRatio");
		data.recoverySpeed = compound.getFloat("recoverySpeed");
		data.lastUpdateTime = compound.getLong("lastUpdateTime");
		return data;
	}


	private static NBTTagCompound writeToNBT(ChunkManaData data, NBTTagCompound compound) {
		if(data == null)
			return compound;
		
		compound.setFloat("positiveMana", data.positiveMana);
		compound.setFloat("negativeMana", data.negativeMana);
		compound.setFloat("maxMana", data.maxMana);
		compound.setFloat("positiveNegativeRatio", data.positiveNegativeRatio);
		compound.setFloat("recoverySpeed", data.recoverySpeed);
		compound.setLong("lastUpdateTime", data.lastUpdateTime);
		
		return compound;
	}
	
	public static class ChunkManaData implements Serializable {
		public float positiveMana;
		public float negativeMana;
		public float maxMana;
		public float positiveNegativeRatio;
		public float recoverySpeed;
		public long lastUpdateTime;
	}
}
