package net.lrsoft.primalarcane.manager;

import net.lrsoft.primalarcane.PrimalArcane;
import net.lrsoft.primalarcane.item.ItemWand;
import net.lrsoft.primalarcane.network.Message;
import net.lrsoft.primalarcane.network.NetworkHandler;
import net.minecraft.client.Minecraft;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(modid = PrimalArcane.MODID)
public class InputManager {
    private static long lastScrollTime = System.currentTimeMillis();
    // 当客户端玩家蹲下并滚动滚轮时，切换法杖法术
    @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
    public static void onEvent(MouseEvent event) {
        int dWheel = event.getDwheel();
        if(dWheel == 0) return;

        // 检测间隔300ms
        long currentTime = System.currentTimeMillis();
        if(currentTime - lastScrollTime >= 300) {
            EntityPlayerSP player = Minecraft.getMinecraft().player;
            if(!player.isSneaking()) return;

            ItemStack heldItem = player.getHeldItemMainhand();
            if(!(heldItem.getItem() instanceof ItemWand)) return;

            NetworkHandler.INSTANCE.sendMessageToServer(new Message.MessageScrollChangeSpell(dWheel));
            lastScrollTime = currentTime;
        }

        event.setCanceled(true);
    }
}
