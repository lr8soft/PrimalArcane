package net.lrsoft.primalarcane.mana;

import net.lrsoft.primalarcane.mana.ChunkManaManager.ChunkManaData;
import net.lrsoft.primalarcane.network.NetworkHandler;
import net.lrsoft.primalarcane.network.Message.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.chunk.Chunk;

public class ManaHelper {
    public static void updateChunkMana(Chunk chunk) {
        ChunkManaData data = ChunkManaManager.getChunkManaData(chunk);
        // 按照秒为单位计算
        // 一秒为20ticks
        long nowTime = chunk.getWorld().getTotalWorldTime();
        float deltaTime = (nowTime - data.lastUpdateTime) / 20.0f;
        if (deltaTime < 0) {
            data.lastUpdateTime = nowTime - 1;
            ChunkManaManager.setChunkManaData(chunk, data);
            return;
        }

        float maxMana = data.maxMana;
        float updateValue = deltaTime * data.recoverySpeed;

        boolean result = false;
        // 需要恢复的魔力数量
        float deltaMana = 0.0f;

        if (maxMana - data.mana > 1e-4) {
            deltaMana = maxMana - data.mana;
        }

        if (deltaMana != 0.0f) {
            if (deltaMana >= updateValue) {
                data.mana += updateValue;
            } else {
                data.mana = maxMana;
            }
            result = true;
        }

        if (result) {
            data.lastUpdateTime = nowTime;
            ChunkManaManager.setChunkManaData(chunk, data);
        }
    }

    public static boolean canConsumeMana(Chunk chunk, float cost) {
        ChunkManaData data = ChunkManaManager.getChunkManaData(chunk);
        return data.mana - cost >= 0;
    }

    public static boolean consumeMana(Chunk chunk, float cost) {
        boolean result = false;
        ChunkManaData data = ChunkManaManager.getChunkManaData(chunk);

        if (data.mana - cost >= 0) {
            data.mana -= cost;
            // 更新消耗后区块的mana值
            ChunkManaManager.setChunkManaData(chunk, data);
            return true;
        }
        return false;
    }

    public static void sendManaDataToClient(Chunk chunk, EntityPlayerMP player) {
        ChunkManaData data = ChunkManaManager.getChunkManaData(chunk);
        NetworkHandler.INSTANCE.sendMessageToPlayer(new MessageMana(data), player);
    }
}
