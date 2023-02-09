package net.lrsoft.primalarcane.item.armor.model;// Made with Blockbench 4.5.2
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports


import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class ModelManaArmor extends ModelBiped {
	private final ModelRenderer Head;
	private final ModelRenderer HeadNode_r1;
	private final ModelRenderer TopPlate2_r1;
	private final ModelRenderer TopPlate1_r1;
	private final ModelRenderer SidePlate2_r1;
	private final ModelRenderer SidePlate1_r1;
	private final ModelRenderer FrontPanel_r1;
	private final ModelRenderer LeftArm;
	private final ModelRenderer RightArm;
	private final ModelRenderer Body;
	private final ModelRenderer Belt;
	private final ModelRenderer RightLeg;
	private final ModelRenderer LeftLeg;
	public final ModelRenderer RightFoot;
	public final ModelRenderer LeftFoot;

	public ModelManaArmor() {
		textureWidth = 64;
		textureHeight = 64;

		Head = new ModelRenderer(this);
		Head.setRotationPoint(0.0F, 0.0F, -1.75F);
		Head.cubeList.add(new ModelBox(Head, 0, 40, -4.0F, -8.0F, 0.75F, 8, 8, 5, 0.0F, false));

		HeadNode_r1 = new ModelRenderer(this);
		HeadNode_r1.setRotationPoint(-4.5F, -4.5F, 2.25F);
		Head.addChild(HeadNode_r1);
		setRotationAngle(HeadNode_r1, -0.5672F, 0.0F, 0.0F);
		HeadNode_r1.cubeList.add(new ModelBox(HeadNode_r1, 24, 44, 1.5F, -4.8F, -0.9F, 6, 1, 3, 0.0F, false));

		TopPlate2_r1 = new ModelRenderer(this);
		TopPlate2_r1.setRotationPoint(-4.5F, -4.5F, 2.25F);
		Head.addChild(TopPlate2_r1);
		setRotationAngle(TopPlate2_r1, 0.0436F, 0.0F, 0.0F);
		TopPlate2_r1.cubeList.add(new ModelBox(TopPlate2_r1, 24, 44, 1.5F, -4.5F, -1.3F, 6, 1, 3, 0.0F, false));

		TopPlate1_r1 = new ModelRenderer(this);
		TopPlate1_r1.setRotationPoint(-4.5F, -4.5F, 2.25F);
		Head.addChild(TopPlate1_r1);
		setRotationAngle(TopPlate1_r1, 0.2182F, 0.0F, 0.0F);
		TopPlate1_r1.cubeList.add(new ModelBox(TopPlate1_r1, 24, 44, 1.5F, -4.5F, -3.5F, 6, 1, 3, 0.0F, false));

		SidePlate2_r1 = new ModelRenderer(this);
		SidePlate2_r1.setRotationPoint(0.0F, -4.0F, 2.25F);
		Head.addChild(SidePlate2_r1);
		setRotationAngle(SidePlate2_r1, 0.5251F, -1.1243F, -0.4815F);
		SidePlate2_r1.cubeList.add(new ModelBox(SidePlate2_r1, 46, 20, -2.5F, -4.8F, -4.3F, 3, 6, 1, 0.0F, false));

		SidePlate1_r1 = new ModelRenderer(this);
		SidePlate1_r1.setRotationPoint(0.0F, -4.0F, 2.25F);
		Head.addChild(SidePlate1_r1);
		setRotationAngle(SidePlate1_r1, 0.4173F, 1.0075F, 0.3587F);
		SidePlate1_r1.cubeList.add(new ModelBox(SidePlate1_r1, 46, 20, -1.2F, -4.8F, -4.5F, 3, 6, 1, 0.0F, false));

		FrontPanel_r1 = new ModelRenderer(this);
		FrontPanel_r1.setRotationPoint(0.0F, -4.0F, 2.25F);
		Head.addChild(FrontPanel_r1);
		setRotationAngle(FrontPanel_r1, 0.2182F, 0.0F, 0.0F);
		FrontPanel_r1.cubeList.add(new ModelBox(FrontPanel_r1, 46, 13, -3.0F, -4.8F, -4.1F, 6, 6, 1, 0.0F, false));

		LeftArm = new ModelRenderer(this);
		LeftArm.setRotationPoint(6.0F, 3.0F, 0.0F);
		LeftArm.cubeList.add(new ModelBox(LeftArm, 18, 9, -2.0F, -4.0F, -3.0F, 5, 5, 6, 0.0F, false));

		RightArm = new ModelRenderer(this);
		RightArm.setRotationPoint(-6.0F, 3.0F, 0.0F);
		RightArm.cubeList.add(new ModelBox(RightArm, 18, 9, -3.0F, -4.0F, -3.0F, 5, 5, 6, 0.0F, false));

		Body = new ModelRenderer(this);
		Body.setRotationPoint(0.0F, 0.0F, 0.0F);
		Body.cubeList.add(new ModelBox(Body, 0, 0, -4.0F, 1.0F, -2.0F, 8, 11, 4, 0.0F, false));
		Body.cubeList.add(new ModelBox(Body, 0, 15, -1.0F, 2.0F, -4.0F, 2, 2, 1, 0.0F, false));
		Body.cubeList.add(new ModelBox(Body, 20, 0, 2.0F, 0.0F, -3.0F, 1, 1, 1, 0.0F, false));
		Body.cubeList.add(new ModelBox(Body, 0, 18, -3.0F, 0.0F, -3.0F, 1, 1, 1, 0.0F, false));
		Body.cubeList.add(new ModelBox(Body, 36, 21, -3.0F, 0.0F, -2.0F, 1, 1, 4, 0.0F, false));
		Body.cubeList.add(new ModelBox(Body, 36, 16, 2.0F, 0.0F, -2.0F, 1, 1, 4, 0.0F, false));
		Body.cubeList.add(new ModelBox(Body, 22, 20, -3.0F, 1.0F, -3.0F, 6, 6, 1, 0.0F, false));
		Body.cubeList.add(new ModelBox(Body, 23, 27, -2.0F, 7.0F, -3.0F, 4, 1, 1, 0.0F, false));
		Body.cubeList.add(new ModelBox(Body, 0, 38, -1.0F, 8.0F, -3.0F, 2, 1, 1, 0.0F, false));
		Body.cubeList.add(new ModelBox(Body, 0, 0, 2.0F, 0.0F, 2.0F, 1, 1, 1, 0.0F, false));
		Body.cubeList.add(new ModelBox(Body, 0, 2, -3.0F, 0.0F, 2.0F, 1, 1, 1, 0.0F, false));
		Body.cubeList.add(new ModelBox(Body, 50, 0, -3.0F, 1.0F, 2.0F, 6, 10, 1, 0.0F, false));

		Belt = new ModelRenderer(this);
		Belt.setRotationPoint(0.0F, 0.0F, 0.0F);
		Body.addChild(Belt);
		Belt.cubeList.add(new ModelBox(Belt, 8, 57, -2.0F, 11.0F, -3.0F, 4, 2, 1, 0.0F, false));
		Belt.cubeList.add(new ModelBox(Belt, 19, 57, 2.0F, 11.5F, -2.5F, 3, 1, 0, 0.0F, false));
		Belt.cubeList.add(new ModelBox(Belt, 19, 57, -5.0F, 11.5F, -2.5F, 3, 1, 0, 0.0F, false));
		Belt.cubeList.add(new ModelBox(Belt, 23, 57, -5.0F, 11.5F, -3.0F, 1, 1, 6, 0.0F, false));
		Belt.cubeList.add(new ModelBox(Belt, 23, 57, 4.0F, 11.5F, -3.0F, 1, 1, 6, 0.0F, false));
		Belt.cubeList.add(new ModelBox(Belt, 46, 32, -4.0F, 11.5F, 2.0F, 8, 1, 1, 0.0F, false));

		RightLeg = new ModelRenderer(this);
		RightLeg.setRotationPoint(-1.9F, 12.0F, 0.0F);
		RightLeg.cubeList.add(new ModelBox(RightLeg, 0, 57, -3.1F, 2.0F, -1.0F, 1, 3, 2, 0.0F, false));
		RightLeg.cubeList.add(new ModelBox(RightLeg, 48, 48, -2.1F, 0.0F, -2.0F, 4, 7, 4, 0.0F, false));
		RightLeg.cubeList.add(new ModelBox(RightLeg, 41, 60, -1.1F, 2.5F, -2.5F, 2, 2, 1, 0.0F, false));

		LeftLeg = new ModelRenderer(this);
		LeftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
		LeftLeg.cubeList.add(new ModelBox(LeftLeg, 48, 48, -1.9F, 0.0F, -2.0F, 4, 7, 4, 0.0F, false));
		LeftLeg.cubeList.add(new ModelBox(LeftLeg, 0, 57, 2.1F, 2.0F, -1.0F, 1, 3, 2, 0.0F, false));
		LeftLeg.cubeList.add(new ModelBox(LeftLeg, 41, 60, -0.9F, 2.5F, -2.5F, 2, 2, 1, 0.0F, false));

		RightFoot = new ModelRenderer(this);
		RightFoot.setRotationPoint(0.0F, 32.0F, 0.0F);
		RightFoot.cubeList.add(new ModelBox(RightFoot, 42, 36, -5.0F, -13.0F, -3.0F, 5, 5, 6, 0.0F, false));

		LeftFoot = new ModelRenderer(this);
		LeftFoot.setRotationPoint(5.0F, 24.0F, 0.0F);
		LeftFoot.cubeList.add(new ModelBox(LeftFoot, 42, 36, -5.0F, -5.0F, -3.0F, 5, 5, 6, 0.0F, false));

		this.bipedHead = Head;
		this.bipedBody = Body;

		this.bipedLeftArm = LeftArm;
		this.bipedRightArm = RightArm;

		this.bipedLeftLeg = LeftLeg;
		this.bipedRightLeg = RightLeg;
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