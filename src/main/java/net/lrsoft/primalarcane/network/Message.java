package net.lrsoft.primalarcane.network;

import io.netty.buffer.ByteBuf;
import net.lrsoft.primalarcane.PrimalArcane;
import net.lrsoft.primalarcane.mana.ChunkManaManager;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class Message {
    public static class MessageMana implements IMessage {
        public ChunkManaManager.ChunkManaData data;
        // unpack
        public MessageMana() {}
        // pack
        public MessageMana(ChunkManaManager.ChunkManaData data) {
            this.data = data;
        }
        @Override
        public void toBytes(ByteBuf buf) {
            buf.writeFloat(this.data.maxMana);
            buf.writeFloat(this.data.mana);
            buf.writeFloat(this.data.recoverySpeed);
        }
        @Override
        public void fromBytes(ByteBuf buf) {
            this.data = new ChunkManaManager.ChunkManaData();
            this.data.maxMana = buf.readFloat();
            this.data.mana = buf.readFloat();
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

    public static class MessageScrollChangeSpell implements IMessage {
        public int scrollValue;
        public MessageScrollChangeSpell() {}
        public MessageScrollChangeSpell(int switchValue) {
            scrollValue = switchValue;
        }
        @Override
        public void fromBytes(ByteBuf buf) {
            scrollValue = buf.readInt();
        }
        @Override
        public void toBytes(ByteBuf buf) {
            buf.writeInt(scrollValue);
        }
    }
}
