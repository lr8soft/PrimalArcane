package net.lrsoft.primalarcane.gui;

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

public class GuiArcaneEnvironment extends Gui
{
	private static ResourceLocation hudTexture = new ResourceLocation(PrimalArcane.MODID,"textures/gui/hud.png");
	private static final int texWidth = 384, texHeight = 384;
	private static final float designWidth = 640f, designHeight = 480f;
	
    String text = "PrimalArcane INDEV";
    
    static float postiveMana = 0.0f;
    static float negativeMana = 0.0f;
    static float maxMana = 0.0f;
    
    public GuiArcaneEnvironment(Minecraft mc)
    {
        ScaledResolution scaled = new ScaledResolution(mc);
        int width = scaled.getScaledWidth();
        int height = scaled.getScaledHeight();
 
        drawString(mc.fontRenderer, text, 30, 30, Integer.parseInt("FFFFFF", 16));

        float xScale = 0.5f;
        float yScale = 0.4f;
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(hudTexture);
        
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_CLAMP);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_CLAMP);
        
        float translateX = 15.0f;
        float translateY = (height - yScale * texHeight) / 2.0f;
        // render mana progress frame
        {
    		GlStateManager.pushMatrix();
            GlStateManager.translate(translateX, translateY, this.zLevel);
            GlStateManager.scale(xScale, yScale, 0.5f);

            this.drawModalRectWithCustomSizedTexture(0, 0, 0.0f, 0.0f, 20, texHeight, texWidth, texHeight);
            GlStateManager.popMatrix();
        }
        // render postive mana
        {
        	float postiveRate = postiveMana / maxMana;
        	int postiveTexHeight = Math.round(postiveRate * (float)texWidth);
        	
    		GlStateManager.pushMatrix();
    		
            GlStateManager.translate(translateX, translateY, this.zLevel);
            GlStateManager.scale(xScale, yScale, 0.5f);
            
            this.drawModalRectWithCustomSizedTextureReverb(0, texWidth - postiveTexHeight,
            		38.0f,  0.0f,
            		20, postiveTexHeight,
            		texWidth, texHeight);
            
            GlStateManager.popMatrix();
        }
        
        // render negative mana
        {
        	float negativeRate = negativeMana / maxMana;
        	int negativeTexHeight = Math.round(negativeRate * (float)texWidth);
        	
    		GlStateManager.pushMatrix();
    		
            GlStateManager.translate(translateX, translateY, this.zLevel);
            GlStateManager.scale(xScale, yScale, 0.5f);
    		
            this.drawModalRectWithCustomSizedTexture(0, 0,
            		20.0f,  0.0f,
            		20, negativeTexHeight,
            		texWidth, texHeight);

            GlStateManager.popMatrix();
        }
        



    }
    
    public static void drawModalRectWithCustomSizedTextureReverb(int x, int y, float u, float v, int width, int height, float textureWidth, float textureHeight)
    {
        float f = 1.0F / textureWidth;
        float f1 = 1.0F / textureHeight;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos((double)x, (double)(y + height), 0.0D).tex((double)(u * f), (double)(v * f1)).endVertex();
        bufferbuilder.pos((double)(x + width), (double)(y + height), 0.0D).tex((double)((u + (float)width) * f), (double)(v * f1)).endVertex();
        bufferbuilder.pos((double)(x + width), (double)y, 0.0D).tex((double)((u + (float)width) * f), (double)((v + (float)height) * f1)).endVertex();
        bufferbuilder.pos((double)x, (double)y, 0.0D).tex((double)(u * f), (double)((v + (float)height) * f1)).endVertex();
        tessellator.draw();
    }
    
    
    
    public static void updateManaInfo(float p, float n, float max) {
    	postiveMana = p;
    	negativeMana = n;
    	maxMana = max;
    }
}