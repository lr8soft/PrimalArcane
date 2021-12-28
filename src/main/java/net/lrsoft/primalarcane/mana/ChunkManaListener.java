package net.lrsoft.primalarcane.mana;

import net.lrsoft.primalarcane.PrimalArcane;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.world.ChunkWatchEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = PrimalArcane.MODID)
public class ChunkManaListener {

	@SubscribeEvent
	public static void onChunkWatch(ChunkWatchEvent event) {
		Chunk chunk = event.getChunkInstance();
		ManaHelper.updateChunkMana(chunk.getWorld(), chunk);
	}
}
