package net.lrsoft.primalarcane.spell;

import net.lrsoft.primalarcane.util.MathUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class FlyingSpell implements Spell {
    @Override
    public boolean onSpell(World worldIn, EntityPlayer player, ItemStack stack) {
        Vec3d vec3d = player.getLookVec();
        player.motionX += vec3d.x * 0.4D + (vec3d.x * 1.5D - player.motionX) * 0.5D;
        player.motionY += vec3d.y * 0.4D + (vec3d.y * 1.5D - player.motionY) * 0.5D;
        player.motionZ += vec3d.z * 0.4D + (vec3d.z * 1.5D - player.motionZ) * 0.5D;

        float pitch = player.rotationPitch, yaw = player.rotationYaw;
        for (int p = 0; p < 3; p++) {
            float newYaw = yaw + MathUtils.getRandomFromRange(40, -40);
            float newPitch = pitch + MathUtils.getRandomFromRange(40, -40);
            Vec3d shootPosition = new Vec3d(
                    -MathHelper.sin(newYaw * 0.0174F) * MathHelper.cos(newPitch * 0.0174F),
                    -MathHelper.sin(newPitch * 0.0174F),
                    MathHelper.cos(newYaw * 0.0174F) * MathHelper.cos(newPitch * 0.0174F));
            player.world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, player.posX,
                    player.posY + player.getEyeHeight() - 0.2f, player.posZ, shootPosition.x, shootPosition.y , shootPosition.z , 1);
        }
        return true;
    }

    @Override
    public String getSpellName() {
        return "spell_flying";
    }

    @Override
    public float getSpellCost() {
        return 10.0f;
    }

    @Override
    public int getSpellInterval() {
        return 1;
    }
}
