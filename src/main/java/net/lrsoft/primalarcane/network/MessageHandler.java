package net.lrsoft.primalarcane.network;

import net.lrsoft.primalarcane.PrimalArcane;
import net.lrsoft.primalarcane.container.IButtonHandler;
import net.lrsoft.primalarcane.network.Message.*;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
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

	public static class MessageGuiButtonHandler implements IMessageHandler<MessageGuiButton, IMessage>{
		@Override
		public IMessage onMessage(MessageGuiButton message, MessageContext ctx) {
			EntityPlayer player = PrimalArcane.Instance.getServerPlayer(ctx);
			if(player != null) {
				PrimalArcane.Instance.getServerThreadListener(ctx).addScheduledTask(new Runnable() {
					@Override
					public void run() {
						// 触发container中按钮点击事件
						if(player.openContainer instanceof IButtonHandler) {
							((IButtonHandler) player.openContainer).onButtonPress(message.buttonId);
						}
					}
				});
			}
			return null;
		}
	}
}
