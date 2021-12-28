package net.lrsoft.primalarcane.network;

import java.util.UUID;

import io.netty.buffer.ByteBuf;
import net.lrsoft.primalarcane.PrimalArcane;
import net.lrsoft.primalarcane.mana.ManaDataManager;
import net.lrsoft.primalarcane.mana.ManaDataManager.ChunkManaData;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageMana implements IMessage {

	public ChunkManaData data;

	// unpack
	public MessageMana() {
	}

	// pack
	public MessageMana(ChunkManaData data) {
		this.data = data;
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeFloat(this.data.maxMana);
		buf.writeFloat(this.data.negativeMana);
		buf.writeFloat(this.data.positiveMana);
		buf.writeFloat(this.data.positiveNegativeRatio);
		buf.writeFloat(this.data.recoverySpeed);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.data = new ChunkManaData();
		this.data.maxMana = buf.readFloat();
		this.data.negativeMana = buf.readFloat();
		this.data.positiveMana = buf.readFloat();
		this.data.positiveNegativeRatio = buf.readFloat();
		this.data.recoverySpeed = buf.readFloat();
	}


}
