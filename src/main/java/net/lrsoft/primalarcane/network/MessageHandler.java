package net.lrsoft.primalarcane.network;

import net.lrsoft.primalarcane.PrimalArcane;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageHandler {
	// client receive
	public static class MessageManaHandler implements IMessageHandler<MessageMana, IMessage> {
		@Override
		public IMessage onMessage(MessageMana message, MessageContext ctx) {
			net.lrsoft.primalarcane.gui.GuiArcaneEnvironment.updateManaInfo(message.data.positiveMana, message.data.negativeMana, message.data.maxMana);
			return null;
		}
	}
}
