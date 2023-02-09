package net.lrsoft.primalarcane.item.armor;

import net.lrsoft.primalarcane.PrimalArcane;
import net.lrsoft.primalarcane.item.armor.model.ModelManaArmor;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.EnumHelper;

import javax.annotation.Nullable;

public class ItemManaArmor extends ItemArmor {
    private static ArmorMaterial defaultMaterial = EnumHelper.addArmorMaterial(
            "mana_armor", PrimalArcane.MODID + ":mana_armor", 50,
            new int[]{7, 15, 9, 6}, 40, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 2);

    private static ResourceLocation armorTexture = new ResourceLocation(PrimalArcane.MODID, "textures/models/armor/mana_armor_layer_1.png");
    private EntityEquipmentSlot currentType;
    public ItemManaArmor(String itemName, EntityEquipmentSlot equipmentSlotIn) {
        super(defaultMaterial, 0, equipmentSlotIn);
        setUnlocalizedName("primalarcane." + itemName);
        setRegistryName(PrimalArcane.MODID, itemName);
        setCreativeTab(PrimalArcane.CREATIVE_TAB);
        currentType = equipmentSlotIn;
    }

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
        return armorTexture.toString();
    }

    @Nullable
    @Override
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped _default) {
        if(!itemStack.isEmpty())
        {
            if(itemStack.getItem() instanceof ItemArmor)
            {
                ModelManaArmor model = new ModelManaArmor();
                if(armorSlot == EntityEquipmentSlot.HEAD) {
                    model.bipedHead.showModel = true;
                }
                else if(armorSlot == EntityEquipmentSlot.CHEST) {
                    model.bipedBody.showModel = true;
                    //model.bipedLeftArm.showModel = true;
                    //model.bipedRightArm.showModel = true;
                }
                else if(armorSlot == EntityEquipmentSlot.LEGS) {
                    model.bipedLeftLeg.showModel = true;
                    model.bipedRightLeg.showModel = true;
                }
                else if(armorSlot == EntityEquipmentSlot.FEET) {
                    model.RightFoot.showModel = true;
                    model.LeftFoot.showModel = true;
                }

                model.leftArmPose = _default.leftArmPose;
                model.rightArmPose = _default.rightArmPose;
                model.isChild = entityLiving.isChild();
                model.isRiding= entityLiving.isRiding();
                model.isSneak = entityLiving.isSneaking();


                return model;
            }
        }
        return null;
    }
}
