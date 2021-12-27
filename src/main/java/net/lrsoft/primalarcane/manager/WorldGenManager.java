package net.lrsoft.primalarcane.manager;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

public class WorldGenManager implements IWorldGenerator {
	//private WorldGenMinable niobiumOre;
	private WorldGenMinable titaniumOre;
	public WorldGenManager() {
		//niobiumOre = new WorldGenMinable(BlockManager.niobiumOre.getDefaultState(), 3);//Each chunk less than 10 blocks
	}
	private static void runGenerator(WorldGenMinable normalore, World world, Random rand, int chunk_X, int chunk_Z, int chancesToSpawn, int minHeight, int maxHeight) {
	    if (minHeight < 0 || maxHeight > 256 || minHeight > maxHeight)
	        throw new IllegalArgumentException("Invalid height");
	    int heightDiff = maxHeight - minHeight + 1;
	    for (int i = 0; i < chancesToSpawn; i ++) {
	        int x = chunk_X * 16 + rand.nextInt(16);
	        int y = minHeight + rand.nextInt(heightDiff);
	        int z = chunk_Z * 16 + rand.nextInt(16);
	        normalore.generate(world, rand, new BlockPos(x, y, z));
	    }
	}
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider) {
		switch (world.provider.getDimension()) {
		case 1:  break;
		case -1: break;
		default:
		//	this.runGenerator(this.niobiumOre, world, random, chunkX, chunkZ, 15, 0, 75);
		}

	}

}
