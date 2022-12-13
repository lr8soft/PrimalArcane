package net.lrsoft.primalarcane.network;

import net.lrsoft.primalarcane.PrimalArcane;
import net.lrsoft.primalarcane.container.IButtonHandler;
import net.lrsoft.primalarcane.item.ItemWand;
import net.lrsoft.primalarcane.network.Message.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageHandler {
	// client receive
	public static class MessageManaHandler implements IMessageHandler<MessageMana, IMessage> {
		@Override
		public IMessage onMessage(MessageMana message, MessageContext ctx) {
			net.lrsoft.primalarcane.gui.GuiArcaneEnvironment.updateManaInfo(message.data.mana, message.data.maxMana);
			return null;
		}
	}

	public static class MessageGuiButtonHandler implements IMessageHandler<MessageGuiButton, IMessage>{
		@Override
		public IMessage onMessage(MessageGuiButton message, MessageContext ctx) {
			EntityPlayer player = PrimalArcane.Instance.getServerPlayer(ctx);
			if(player != null) {
				PrimalArcane.Instance.getServerThreadListener(ctx).addScheduledTask(() -> {
					// 触发container中按钮点击事件
					if(player.openContainer instanceof IButtonHandler) {
						((IButtonHandler) player.openContainer).onButtonPress(message.buttonId);
					}
				});
			}
			return null;
		}
	}

	public static class MessageScrollChangeSpellHandler implements IMessageHandler<MessageScrollChangeSpell, IMessage>{
		@Override
		public IMessage onMessage(MessageScrollChangeSpell message, MessageContext ctx) {
			EntityPlayer player = PrimalArcane.Instance.getServerPlayer(ctx);
			if(player != null) {
				PrimalArcane.Instance.getServerThreadListener(ctx).addScheduledTask(() -> {
					ItemStack currentStack = player.getHeldItemMainhand();
					if(currentStack.getItem() instanceof ItemWand) {

						ItemWand wand = (ItemWand)currentStack.getItem();
						int maxSlot = wand.getMaxSpellSlot();
						int currentSlot = wand.getCurrentSpellSlot(currentStack);

						if(message.scrollValue > 0) {
							if(currentSlot + 1 < maxSlot) {
								currentSlot++;
							}else {
								currentSlot = 0;
							}
						}else{
							if(currentSlot - 1 >= 0) {
								currentSlot--;
							}else {
								currentSlot = maxSlot - 1;
							}
						}
						// 更新当前手杖插槽
						wand.setCurrentSpellSlot(currentStack, currentSlot);
					}
				});
			}
			return null;
		}
	}
}
