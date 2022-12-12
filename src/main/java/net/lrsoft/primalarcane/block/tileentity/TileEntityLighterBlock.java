package net.lrsoft.primalarcane.block.tileentity;

import net.lrsoft.primalarcane.PrimalArcane;
import net.lrsoft.primalarcane.util.MathUtils;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;

import java.util.Random;

public class TileEntityLighterBlock extends TileEntity implements ITickable{
    private static final float updateSpeed = 1.5f;
    private float red = 170.0f, green = 85.0f, blue = 255.0f;
    private boolean newLighter = true;
    @Override
    public void update() {
        // 客户端更新
        if(!world.isRemote) return;

        // 新亮点初始化
        if(newLighter) {
            red = MathUtils.getRandomFromRange(84, 1);
            blue = MathUtils.getRandomFromRange(170, 85);
            green = MathUtils.getRandomFromRange(255, 171);
            newLighter = false;
        }

        if(blue < 255.0f)
        {
            blue += updateSpeed;
        }else
        {
            blue = 0.0f;
        }

        if(green < 255.0f)
        {
            green += updateSpeed;
        }else
        {
            green = 0.0f;
        }

        if(red < 255.0f)
        {
            red += updateSpeed;
        }else
        {
            red = 0.0f;
        }
    }

    public float[] getColor()
    {
        return new float[]{ red,green, blue };
    }
}
