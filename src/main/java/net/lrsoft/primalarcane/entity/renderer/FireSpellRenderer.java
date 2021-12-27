package net.lrsoft.primalarcane.entity.renderer;

import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL11;

import net.lrsoft.primalarcane.PrimalArcane;
import net.lrsoft.primalarcane.entity.EntityShootSpell;
import net.lrsoft.primalarcane.util.MathUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.SideOnly;


public class FireSpellRenderer<T extends EntityShootSpell> extends Render<T>{
	private final ResourceLocation texture;
	public FireSpellRenderer(RenderManager manager, ResourceLocation texture)
	{
		super(manager);
		this.texture = texture;
	}
	
	//From minecraft
	public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
		this.bindEntityTexture(entity);
		EntityPlayerSP viewer = (Minecraft.getMinecraft()).player;
		float[] info = MathUtils.getPlayerView(viewer, partialTicks);
		
		GlStateManager.pushMatrix();
        GlStateManager.translate((float)x, (float)y, (float)z);
        GL11.glDepthMask(true);

        GlStateManager.color(1.5f, 1.5f, 1.5f, 1.0f);
        GlStateManager.translate(0.0F, 0.0F, 0.0F);

        GlStateManager.rotate(180.0F - info[1], 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(
        		(float)(Minecraft.getMinecraft().gameSettings.thirdPersonView == 2 ? -1 : 1) * -info[0], 1.0f, 0.0f, 0.0f);
        GlStateManager.rotate(entity.getSpellRotationZ(), 0.0f, 0.0f, 1.0f);
        GlStateManager.scale(0.5f, 0.5f, 0.5f);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
        bufferbuilder.pos(-0.5D, -0.25D, 0.0D).tex(0.0d, 0.0d).normal(0.0F, 1.0F, 0.0F).endVertex();
        bufferbuilder.pos(0.5D, -0.25D, 0.0D).tex(1.0d, 0.0d).normal(0.0F, 1.0F, 0.0F).endVertex();
        bufferbuilder.pos(0.5D, 0.75D, 0.0D).tex(1.0d, 1.0d).normal(0.0F, 1.0F, 0.0F).endVertex();
        bufferbuilder.pos(-0.5D, 0.75D, 0.0D).tex(0.0d, 1.0d).normal(0.0F, 1.0F, 0.0F).endVertex();
        tessellator.draw();
        GlStateManager.disableBlend();
        GlStateManager.disableRescaleNormal();

        GlStateManager.popMatrix();
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityShootSpell entity)
	{
		return texture;
	}

}

