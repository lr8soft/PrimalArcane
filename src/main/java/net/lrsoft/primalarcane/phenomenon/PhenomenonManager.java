package net.lrsoft.primalarcane.phenomenon;

import net.lrsoft.primalarcane.PrimalArcane;
import net.lrsoft.primalarcane.mana.ChunkManaManager;
import net.lrsoft.primalarcane.mana.ManaHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = PrimalArcane.MODID)
public class PhenomenonManager {
    @SubscribeEvent
    public static void OnChunkUpdate(ChunkEvent.Load event) {
        World world = event.getWorld();
        if(world.isRemote) return;
    }
}
