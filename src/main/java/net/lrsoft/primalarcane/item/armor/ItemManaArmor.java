package net.lrsoft.primalarcane.item.armor;

import net.lrsoft.primalarcane.PrimalArcane;
import net.lrsoft.primalarcane.item.armor.model.ModelManaArmorChest;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.common.util.EnumHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ItemManaArmor extends ItemArmor {
    private static ArmorMaterial defaultMaterial = EnumHelper.addArmorMaterial(
            "mana_armor", PrimalArcane.MODID + ":mana_armor", 50,
            new int[]{7, 15, 9, 6}, 40, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 2);

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
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped _default) {
        if(!itemStack.isEmpty())
        {
            if(itemStack.getItem() instanceof ItemArmor)
            {
                //使用我们刚刚的模型
                ModelManaArmorChest model = new ModelManaArmorChest();
                //模型应用在胸甲上armorSlot.CHEST，其他的分别是HEAD(头盔),LEGS(护腿),FEET(靴子)
                model.Root.showModel = armorSlot == armorSlot.CHEST;

                model.leftArmPose = _default.leftArmPose;
                model.rightArmPose = _default.rightArmPose;
                model.isChild = _default.isChild;
                model.isRiding= _default.isRiding;
                model.isSneak = _default.isSneak;


                return model;
            }
        }
        return null;
    }
}
