package net.lrsoft.primalarcane.network;

import net.lrsoft.primalarcane.PrimalArcane;
import net.lrsoft.primalarcane.network.Message.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;


public enum NetworkHandler {
    INSTANCE;
	
    private final SimpleNetworkWrapper channel = NetworkRegistry.INSTANCE.newSimpleChannel(PrimalArcane.MODID);
	
	public void initNetworkHandler() {
	    this.channel.registerMessage(MessageHandler.MessageManaHandler.class, MessageMana.class, 0, Side.CLIENT);
        this.channel.registerMessage(MessageHandler.MessageGuiButtonHandler.class, MessageGuiButton.class, 1, Side.SERVER);
	}

    public void sendMessageToDim(IMessage msg, int dim) {
        channel.sendToDimension(msg, dim);
    }

    public void sendMessageAroundPos(IMessage msg, int dim, BlockPos pos) {
        channel.sendToAllAround(msg, new NetworkRegistry.TargetPoint(dim, pos.getX(), pos.getY(), pos.getZ(), 2.0D));
    }

    public void sendMessageToPlayer(IMessage msg, EntityPlayerMP player) {
        channel.sendTo(msg, player);
    }

    public void sendMessageToAll(IMessage msg) {
        channel.sendToAll(msg);
    }

    public void sendMessageToServer(IMessage msg) {
        channel.sendToServer(msg);
    }
}
