package net.lrsoft.primalarcane.network;

import io.netty.buffer.ByteBuf;
import net.lrsoft.primalarcane.mana.ManaDataManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class Message {
    public static class MessageMana implements IMessage {

        public ManaDataManager.ChunkManaData data;

        // unpack
        public MessageMana() {}

        // pack
        public MessageMana(ManaDataManager.ChunkManaData data) {
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
            this.data = new ManaDataManager.ChunkManaData();
            this.data.maxMana = buf.readFloat();
            this.data.negativeMana = buf.readFloat();
            this.data.positiveMana = buf.readFloat();
            this.data.positiveNegativeRatio = buf.readFloat();
            this.data.recoverySpeed = buf.readFloat();
        }


    }

    public static class MessageGuiButton implements IMessage {
        public int buttonId;

        public MessageGuiButton() {}
        public MessageGuiButton(int buttonId) {
            this.buttonId = buttonId;
        }

        @Override
        public void fromBytes(ByteBuf buf) {
            buttonId = buf.readInt();
        }

        @Override
        public void toBytes(ByteBuf buf) {
            buf.writeInt(buttonId);
        }
    }
}
