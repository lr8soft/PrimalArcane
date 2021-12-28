package net.lrsoft.primalarcane.gui;

import net.lrsoft.primalarcane.PrimalArcane;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

public class GuiArcaneEnvironment extends Gui
{
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
        
        StringBuilder builder = new StringBuilder();
        builder.append("+:");
        builder.append(postiveMana);
        builder.append(" -:");
        builder.append(negativeMana);
        builder.append(" max:");
        builder.append(maxMana);
        
        String result = builder.toString();
        
        drawString(mc.fontRenderer, result, 30, 60, Integer.parseInt("FFAA00", 16));
    }
    
    public static void updateManaInfo(float p, float n, float max) {
    	postiveMana = p;
    	negativeMana = n;
    	maxMana = max;
    }
}