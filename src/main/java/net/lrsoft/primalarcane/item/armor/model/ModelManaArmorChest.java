package net.lrsoft.primalarcane.item.armor.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelManaArmorChest extends ModelBiped {
	private final ModelRenderer LeftArm;
	private final ModelRenderer RightArm;
	private final ModelRenderer Body;

	public ModelManaArmorChest() {
		textureWidth = 64;
		textureHeight = 64;

		LeftArm = new ModelRenderer(this);
		LeftArm.setRotationPoint(6.0F, 3.0F, 0.0F);
		LeftArm.cubeList.add(new ModelBox(LeftArm, 18, 9, -2.0F, -4.0F, -3.0F, 5, 5, 6, 0.0F, false));
		LeftArm.cubeList.add(new ModelBox(LeftArm, 14, 31, -2.0F, 1.0F, -2.0F, 4, 4, 4, 0.0F, false));

		RightArm = new ModelRenderer(this);
		RightArm.setRotationPoint(-6.0F, 3.0F, 0.0F);
		RightArm.cubeList.add(new ModelBox(RightArm, 18, 9, -3.0F, -4.0F, -3.0F, 5, 5, 6, 0.0F, false));
		RightArm.cubeList.add(new ModelBox(RightArm, 14, 31, -2.0F, 1.0F, -2.0F, 4, 4, 4, 0.0F, false));

		Body = new ModelRenderer(this);
		Body.setRotationPoint(0.0F, 0.0F, 0.0F);
		Body.cubeList.add(new ModelBox(Body, 0, 0, -4.0F, 1.0F, -2.0F, 8, 11, 4, 0.0F, false));
		Body.cubeList.add(new ModelBox(Body, 0, 15, -1.0F, 2.0F, -4.0F, 2, 2, 1, 0.0F, false));
		Body.cubeList.add(new ModelBox(Body, 20, 0, 2.0F, 0.0F, -3.0F, 1, 1, 1, 0.0F, false));
		Body.cubeList.add(new ModelBox(Body, 0, 18, -3.0F, 0.0F, -3.0F, 1, 1, 1, 0.0F, false));
		Body.cubeList.add(new ModelBox(Body, 36, 21, -3.0F, 0.0F, -2.0F, 1, 1, 4, 0.0F, false));
		Body.cubeList.add(new ModelBox(Body, 36, 16, 2.0F, 0.0F, -2.0F, 1, 1, 4, 0.0F, false));
		Body.cubeList.add(new ModelBox(Body, 22, 20, -3.0F, 1.0F, -3.0F, 6, 10, 1, 0.0F, false));
		Body.cubeList.add(new ModelBox(Body, 0, 0, 2.0F, 0.0F, 2.0F, 1, 1, 1, 0.0F, false));
		Body.cubeList.add(new ModelBox(Body, 0, 2, -3.0F, 0.0F, 2.0F, 1, 1, 1, 0.0F, false));
		Body.cubeList.add(new ModelBox(Body, 22, 20, -3.0F, 1.0F, 2.0F, 6, 10, 1, 0.0F, false));

		this.bipedBody = Body;
		this.bipedBody.addChild(LeftArm);
		this.bipedBody.addChild(RightArm);

		this.bipedLeftArm = LeftArm;
		this.bipedRightArm = RightArm;
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}