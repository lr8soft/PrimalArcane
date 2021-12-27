package net.lrsoft.primalarcane.manager;

import net.lrsoft.primalarcane.PrimalArcane;
import net.lrsoft.primalarcane.entity.EntityFireball;
import net.lrsoft.primalarcane.entity.EntityShootSpell;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;

@Mod.EventBusSubscriber(modid = PrimalArcane.MODID)
public class EntityManager {
	private static int currentEntityId = 0;
	
	@SubscribeEvent
	public static void onEntityInit(RegistryEvent.Register<EntityEntry> event)
	{
		registerEntity(event, EntityFireball.class, "SpellFireBall", 200, 5);
	}
	
	private static void registerEntity(RegistryEvent.Register<EntityEntry> event, 
			Class<? extends Entity> entityClass, String entityName, int updateRange, int updateFrequency)
	{
	    event.getRegistry().register(EntityEntryBuilder.create()
	            .entity(entityClass)
	            .id(new ResourceLocation(PrimalArcane.MODID, entityName), currentEntityId++)
	            .name(entityName)
	            .tracker(updateRange, updateFrequency, true)
	            .build()
	    );
	}
}