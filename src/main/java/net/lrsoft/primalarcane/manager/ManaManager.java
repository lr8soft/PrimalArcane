package net.lrsoft.primalarcane.manager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import net.lrsoft.primalarcane.PrimalArcane;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.storage.WorldSavedData;

public class ManaManager {
	public class ManaWorldSaveData extends WorldSavedData {
		private HashMap<ChunkPos, ChunkManaData> chunkData = new HashMap<>();
		
		public ManaWorldSaveData() {
			super(PrimalArcane.MODID + "_manadata");
		}
		
		public ChunkManaData getChunkManaData(ChunkPos pos) {
			return chunkData.getOrDefault(pos, null);
		}
		
		public void setChunkManaData(ChunkManaData data, ChunkPos pos) {
			chunkData.put(pos, data);
		}
		
		

		@Override
		public void readFromNBT(NBTTagCompound compound) {
			String info = compound.getString("ChunkManaData");
			if(info == null || info.length() == 0)
				return;
			
			Gson gson = new Gson();
			chunkData.putAll(gson.fromJson(info, HashMap.class));
			
			PrimalArcane.logger.info(info);
		}

		@Override
		public NBTTagCompound writeToNBT(NBTTagCompound compound) {
			Gson gson = new Gson();
			String data = gson.toJson(chunkData);
			
			PrimalArcane.logger.info(data);
			compound.setString("ChunkManaData", data);
			return compound;
		}
		
		public class ChunkManaData implements Serializable {
			public float positiveMana;
			public float negativeMana;
			public float maxMana;
			public float recoverySpeed;
			public long lastUpdateTime;
		}
		
	}
}
