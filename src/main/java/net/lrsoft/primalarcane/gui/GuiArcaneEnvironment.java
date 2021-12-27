package net.lrsoft.primalarcane.gui;

import net.lrsoft.primalarcane.PrimalArcane;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

public class GuiArcaneEnvironment extends Gui
{
    String text = "PRIMAL ARCANE DEV EDITION";
 
    public GuiArcaneEnvironment(Minecraft mc)
    {
        ScaledResolution scaled = new ScaledResolution(mc);
        int width = scaled.getScaledWidth();
        int height = scaled.getScaledHeight();
 
        drawCenteredString(mc.fontRenderer, text, 2 * text.length(), 30, Integer.parseInt("FFAA00", 16));
    }
}