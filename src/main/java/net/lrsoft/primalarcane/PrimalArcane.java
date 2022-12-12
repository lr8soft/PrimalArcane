package net.lrsoft.primalarcane;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.lrsoft.primalarcane.gui.RenderGuiHandler;
import net.lrsoft.primalarcane.network.NetworkHandler;
import net.lrsoft.primalarcane.proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod(modid = PrimalArcane.MODID, name = PrimalArcane.NAME, version = PrimalArcane.VERSION)
public class PrimalArcane {
	public static final String MODID = "primalarcane", NAME = "Primal Arcane", VERSION = "0.0";
	public static final Logger logger = LogManager.getLogger();

	@SidedProxy(clientSide = "net.lrsoft.primalarcane.proxy.ClientProxy", serverSide = "net.lrsoft.primalarcane.proxy.CommonProxy")
	public static CommonProxy proxy;
	
	@Instance(PrimalArcane.MODID)
    public static PrimalArcane Instance;

	public static final CreativeTabs CREATIVE_TAB = new CreativeTabs("primalarcaneTabs") {
		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(Items.DIAMOND);
		}
	};

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		proxy.preInit(event);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(event);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
		NetworkRegistry.INSTANCE.registerGuiHandler(Instance, new RenderGuiHandler());
		NetworkHandler.INSTANCE.initNetworkHandler();
	}

	public IThreadListener getThreadListener(MessageContext context) {
		if (context.side.isServer())
			return context.getServerHandler().player.mcServer;
		return null;
	}

	public EntityPlayer getPlayer(MessageContext context) {
		if (context.side.isServer())
			return context.getServerHandler().player;
		return null;
	}

}
