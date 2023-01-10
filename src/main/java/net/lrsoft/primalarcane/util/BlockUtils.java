package net.lrsoft.primalarcane.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockUtils {
    public static BlockPos getPlaceablePos(BlockPos pos, EntityPlayer player) {
        EnumFacing facing = EnumFacing.getDirectionFromEntityLiving(pos, player).getOpposite();
        BlockPos result = pos;
        switch (facing) {
            case UP:
                result.add(0, 1, 0); break;
            case DOWN:
                result.add(0, -1, 0); break;
            case EAST:
                result.add(1, 0, 0); break;
            case WEST:
                result.add(-1, 0 ,0); break;
            case NORTH:
                result.add(0, 0, 1); break;
            case SOUTH:
                result.add(0, 0, -1);break;
        }

        return result;
    }

    public static boolean hasNeighbourBlock(BlockPos pos, World world) {
        BlockPos up = pos.add(0 ,1, 0);
        BlockPos down = pos.add(0 ,-1, 0);
        BlockPos north = pos.add(1 ,0, 0);
        BlockPos south = pos.add(-1 ,0, 0);
        BlockPos west = pos.add(0 ,0, 1);
        BlockPos east = pos.add(0 ,0, -1);

        return IsAir(up, world) || IsAir(down, world) || IsAir(north, world) || IsAir(south, world)
                || IsAir(west, world) || IsAir(east, world);
    }

    public static boolean IsAir(BlockPos pos, World world) {
        return world.getBlockState(pos) == Blocks.AIR.getDefaultState();
    }
}
