package net.lrsoft.primalarcane.manager;

import net.lrsoft.primalarcane.PrimalArcane;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

@Mod.EventBusSubscriber(modid = PrimalArcane.MODID)
public class SoundManager {
	//public static SoundEvent plasma_charge_sound = new SoundEvent(new ResourceLocation(PrimalArcane.MODID, "plasma_charge_sound"));
	//laser_bullet_shoot
	@SubscribeEvent
	public static void onSoundEvenrRegistration(RegistryEvent.Register<SoundEvent> event) 
	{
	    //event.getRegistry().register(plasma_charge_sound.setRegistryName("plasma_charge_sound"));
	}
	
}
