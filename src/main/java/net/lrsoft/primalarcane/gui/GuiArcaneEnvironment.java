package net.lrsoft.primalarcane.gui;

import net.lrsoft.primalarcane.item.ItemWand;
import net.lrsoft.primalarcane.spell.Spell;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

import net.lrsoft.primalarcane.PrimalArcane;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class GuiArcaneEnvironment extends Gui {
	private static Map<String, ResourceLocation> spellIcons = new HashMap<>();
	private static ResourceLocation spellUiTexture = new ResourceLocation(PrimalArcane.MODID, "textures/gui/wand_spell.png");
	private static ResourceLocation hudTexture = new ResourceLocation(PrimalArcane.MODID, "textures/gui/new_hud.png");
	private static final int hudTexWidth = 160, hudTexHeight = 160;
	private static final int spellTexWidth = 128, spellTexHeight = 64;
	private static final int spellIconWidth = 16, spellIconHeight = 16;
	static float currentMana = 0.0f;
	static float maxMana = 0.0f;
	private ItemStack currentWand;

	public GuiArcaneEnvironment(Minecraft mc) {
		ItemStack mainStack = mc.player.getHeldItemMainhand();
		ItemStack offhandStack = mc.player.getHeldItemOffhand();

		if(mainStack.getItem() instanceof ItemWand) {
			currentWand = mainStack;
		}else if(offhandStack.getItem() instanceof ItemWand) {
			currentWand = offhandStack;
		}else{
			currentWand = ItemStack.EMPTY;
		}

		// 拿着法杖才渲染魔力ui和法杖信息ui
		if(currentWand != ItemStack.EMPTY) {
			ScaledResolution scaled = new ScaledResolution(mc);
			renderWandSpell(mc, scaled);
			renderChunkManaValue(mc, scaled);
		}
	}

	private void renderWandSpell(Minecraft mc, ScaledResolution resolution) {
		int width = resolution.getScaledWidth();
		int height = resolution.getScaledHeight();

		float xBase = 1.0f;
		float yBase = height;

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.8F);
		// 渲染spell背景图
		{
			mc.renderEngine.bindTexture(spellUiTexture);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_CLAMP);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_CLAMP);

			float xScale = 0.8f;
			float yScale = 0.8f;

			float translateX = xBase;
			float translateY = yBase - yScale * spellTexHeight;

			GlStateManager.pushMatrix();
			GlStateManager.translate(translateX, translateY, this.zLevel);
			GlStateManager.scale(xScale, yScale, 0.5f);

			this.drawModalRectWithCustomSizedTexture(0, 0, 0.0f, 0.0f, 64, spellTexHeight, spellTexWidth, spellTexHeight);
			GlStateManager.popMatrix();
		}
		// 渲染spell名称与icon
		{
			ItemWand wand = (ItemWand)currentWand.getItem();
			int slot = ItemWand.getCurrentSpellSlot(currentWand);

			Spell spell = ItemWand.getSlotSpell(currentWand, slot);
			String spellName;
			if(spell != null) {
				spellName = I18n.format("item.primalarcane.spell." + spell.getSpellName() + ".name");
				// 渲染spell的图标icon
				String spellIconPath = "textures/icons/spell/" + spell.getSpellName() + ".png";
				if (!spellIcons.containsKey(spellIconPath)) {
					spellIcons.put(spellIconPath, new ResourceLocation(PrimalArcane.MODID, spellIconPath));
				}

				{
					// 绑定图标icon
					mc.renderEngine.bindTexture(spellIcons.get(spellIconPath));
					GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_CLAMP);
					GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_CLAMP);

					float xScale = 1.0f;
					float yScale = 1.0f;

					float translateX = xBase + 17.0f;
					float translateY = yBase - yScale * spellIconHeight * 2.0f - 2.0f;

					GlStateManager.pushMatrix();
					GlStateManager.translate(translateX, translateY, this.zLevel);
					GlStateManager.scale(xScale, yScale, 0.5f);

					this.drawModalRectWithCustomSizedTexture(0, 0, 0.0f, 0.0f, 16, 16, 16, 16);
					GlStateManager.popMatrix();
				}
			}else{
				spellName = I18n.format("primalarcane.text.none");
			}
			{
				float translateX = xBase + 48.0f;
				float translateY = yBase - 32f;

				float fontSize = 1.5f;
				GlStateManager.pushMatrix();
				GlStateManager.translate(translateX, translateY, this.zLevel);
				GlStateManager.scale(fontSize, fontSize, fontSize);

				mc.fontRenderer.drawStringWithShadow(spellName, 0, 0, Integer.parseInt("00FFFF", 16));
				GlStateManager.popMatrix();
			}
		}

	}

	private void renderChunkManaValue(Minecraft mc, ScaledResolution resolution) {
		int width = resolution.getScaledWidth();
		int height = resolution.getScaledHeight();

		float xScale = 0.20f;//0.35f
		float yScale = 0.15f;//0.06f
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.6F);
		mc.renderEngine.bindTexture(hudTexture);

		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_CLAMP);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_CLAMP);

		float thirdViewOffset = Minecraft.getMinecraft().gameSettings.thirdPersonView > 0 ? 10.0f : 0.0f;

		float translateX = width / 2.0f + 10.0f + thirdViewOffset;
		float translateY = (height - yScale * hudTexHeight) / 2.0f;
		// render mana progress frame
		{
			GlStateManager.pushMatrix();
			GlStateManager.translate(translateX, translateY, this.zLevel);
			GlStateManager.scale(xScale, yScale, 0.5f);

			this.drawModalRectWithCustomSizedTexture(0, 0, 0.0f, 0.0f, 20, hudTexHeight, hudTexWidth, hudTexHeight);
			GlStateManager.popMatrix();
		}
		// render postive mana
		{
			float postiveRate = currentMana / maxMana;
			int postiveTexHeight = Math.round(postiveRate * (float) hudTexWidth);

			GlStateManager.pushMatrix();

			GlStateManager.translate(translateX, translateY, this.zLevel);
			GlStateManager.scale(xScale, yScale, 0.5f);

			this.drawModalRectWithCustomSizedTextureReverb(0, hudTexWidth - postiveTexHeight, 38.0f, 0.0f, 20,
					postiveTexHeight, hudTexWidth, hudTexHeight);

			GlStateManager.popMatrix();
		}
		/*
		// render negative mana
		{
			float negativeRate = negativeMana / maxMana;
			int negativeTexHeight = Math.round(negativeRate * (float) texWidth);

			GlStateManager.pushMatrix();

			GlStateManager.translate(translateX, translateY, this.zLevel);
			GlStateManager.scale(xScale, yScale, 0.5f);

			this.drawModalRectWithCustomSizedTexture(0, 0, 20.0f, 0.0f, 20, negativeTexHeight, texWidth, texHeight);

			GlStateManager.popMatrix();
		}
		*/
	}

	public static void drawModalRectWithCustomSizedTextureReverb(int x, int y, float u, float v, int width, int height,
			float textureWidth, float textureHeight) {
		float f = 1.0F / textureWidth;
		float f1 = 1.0F / textureHeight;
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
		bufferbuilder.pos((double) x, (double) (y + height), 0.0D).tex((double) (u * f), (double) (v * f1)).endVertex();
		bufferbuilder.pos((double) (x + width), (double) (y + height), 0.0D).tex((double) ((u + (float) width) * f), (double) (v * f1)).endVertex();
		bufferbuilder.pos((double) (x + width), (double) y, 0.0D).tex((double) ((u + (float) width) * f), (double) ((v + (float) height) * f1)).endVertex();
		bufferbuilder.pos((double) x, (double) y, 0.0D).tex((double) (u * f), (double) ((v + (float) height) * f1)).endVertex();
		tessellator.draw();
	}

	public static void updateManaInfo(float current, float max) {
		currentMana = current;
		maxMana = max;
	}
}