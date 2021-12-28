package net.lrsoft.primalarcane.mana;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
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

public class ManaDataManager extends WorldSavedData {
	public static final String DATA_NAME = PrimalArcane.MODID + "_manadata";
	
	private static NoiseGenerator generator = new NoiseGenerator();
	
	private HashMap<ChunkPos, ChunkManaData> chunkData = new HashMap<>();
	
	private World world;

	public static ManaDataManager getManager(World world) {
		MapStorage storage = world.getMapStorage();
		ManaDataManager manager = (ManaDataManager) storage.getOrLoadData(ManaDataManager.class, DATA_NAME);

		if (manager == null) {
			manager = new ManaDataManager();
			storage.setData(DATA_NAME, manager);
		}
		manager.setWorld(world);
		return manager;
	}

	public ManaDataManager(String name) {
		super(name);
	}

	public ManaDataManager() {
		this(DATA_NAME);
	}

	public ChunkManaData getChunkManaData(ChunkPos pos) {
		return chunkData.getOrDefault(pos, null);
	}

	public void setChunkManaData(ChunkManaData data, ChunkPos pos) {
		chunkData.put(pos, data);
		this.markDirty();
	}
	
	public ChunkManaData initChunkManaData(Chunk chunk) {
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
		data.recoverySpeed = 7.0f * (recoveryRate / biomeCount);
		
		ChunkPos pos = chunk.getPos();
		data.maxMana = generator.generateHeight(pos.x, pos.z);
		
		data.positiveMana = data.positiveNegativeRatio * data.maxMana;
		data.negativeMana = data.maxMana - data.positiveMana;
		data.lastUpdateTime = world.getTotalWorldTime();
		
		this.setChunkManaData(data, chunk.getPos());
		return data;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		String info = compound.getString("ChunkManaData");
		if (info == null || info.length() == 0)
			return;

		Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
		chunkData.clear();
		chunkData.putAll(gson.fromJson(info, new TypeToken<HashMap<ChunkPos, ChunkManaData>>() {}.getType()));//HashMap.class));


	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
		String data = gson.toJson(chunkData);

		compound.setString("ChunkManaData", data);
		return compound;
	}
	
	private void setWorld(World world) {
		this.world = world;
	}
	
	public World getWorld() {
		return this.world;
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
