package net.lrsoft.primalarcane.gui;

import net.lrsoft.primalarcane.PrimalArcane;
import net.lrsoft.primalarcane.block.tileentity.TileEntityWandWorkBench;
import net.lrsoft.primalarcane.container.ContainerWandWorkbench;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.IGuiHandler;

@Mod.EventBusSubscriber(modid = PrimalArcane.MODID)
public class RenderGuiHandler implements IGuiHandler{
	public static final int WAND_WORKBENCH_ID = 0;
	
    @SubscribeEvent
    public static void onRenderGui(net.minecraftforge.client.event.RenderGameOverlayEvent.Post event)
    {
    	if (event.getType() != net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType.EXPERIENCE) return;
    	new GuiArcaneEnvironment(net.minecraft.client.Minecraft.getMinecraft());
    }
    

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
		switch(ID) {
		case WAND_WORKBENCH_ID:
			if(te instanceof TileEntityWandWorkBench) {
				return new ContainerWandWorkbench(player.inventory, (TileEntityWandWorkBench)te);
			}
			break;
		
		
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
		switch(ID) {
		case WAND_WORKBENCH_ID:
			if(te instanceof TileEntityWandWorkBench) {
				return new GuiWandWorkbench(player.inventory, (TileEntityWandWorkBench)te);
			}
			break;
		}
		return null;
	}
}