package net.lrsoft.primalarcane.mana;

import java.util.LinkedList;
import java.util.List;

import net.lrsoft.primalarcane.PrimalArcane;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;

@Mod.EventBusSubscriber(modid = PrimalArcane.MODID)
public class ChunkManaListener {
	
	private static LinkedList<Chunk> activeChunks = new LinkedList<>();
	
	private static final int chunkManaUpdateTick = 5;
	private static int currentManaUpdateTick = 0;
	@SubscribeEvent
	public static void onChunkWatch(WorldTickEvent event) {
        if(event.world.isRemote)
            return;
        
        // update every 5 ticks
        if(currentManaUpdateTick < chunkManaUpdateTick)
        	currentManaUpdateTick++;
        else {
	        for(Chunk chunk : activeChunks)
	        	ManaHelper.updateChunkMana(chunk);
	        currentManaUpdateTick = 0;
        }
        
        List<EntityPlayerMP> players = event.world.getPlayers(EntityPlayerMP.class, (p) -> true);
        for(EntityPlayerMP player : players) {
        	Chunk playerChunk = event.world.getChunkFromBlockCoords(player.getPosition());
        	ManaHelper.sendManaDataToClient(playerChunk, player);
        	ManaHelper.updateChunkMana(playerChunk);
        }
	}
	
    @SubscribeEvent
    public static void onChunkLoad(ChunkDataEvent.Load event) {
        if(event.getWorld().isRemote)
            return;

        Chunk chunk = event.getChunk();
		activeChunks.add(chunk);
        ChunkManaManager.loadDataFromChunk(chunk, event.getData());
        

    }
    
    @SubscribeEvent
    public static void onChunkUnload(ChunkEvent.Unload event) {
        if(event.getWorld().isRemote)
            return;
        
        Chunk chunk = event.getChunk();
		activeChunks.remove(chunk);
        ChunkManaManager.removeChunkData(chunk);
    }
	
	
    @SubscribeEvent
    public static void onChunkSave(ChunkDataEvent.Save event) {
        if(event.getWorld().isRemote)
            return;
        
        ChunkManaManager.writeDataToChunk(event.getChunk(), event.getData());
    }
}
