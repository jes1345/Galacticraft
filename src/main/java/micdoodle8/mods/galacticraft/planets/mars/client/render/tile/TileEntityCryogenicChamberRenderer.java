package micdoodle8.mods.galacticraft.planets.mars.client.render.tile;

import micdoodle8.mods.galacticraft.planets.mars.MarsModule;
import micdoodle8.mods.galacticraft.planets.mars.blocks.BlockMachineMars;
import micdoodle8.mods.galacticraft.planets.mars.tile.TileEntityCryogenicChamber;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * GCMarsTileEntityCryogenicChamberRenderer.java
 * 
 * This file is part of the Galacticraft project
 * 
 * @author micdoodle8
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
@SideOnly(Side.CLIENT)
public class TileEntityCryogenicChamberRenderer extends TileEntitySpecialRenderer
{
	private static final ResourceLocation chamberTexture0 = new ResourceLocation(MarsModule.TEXTURE_DOMAIN, "textures/model/chamber_dark.png");
	private static final ResourceLocation chamberTexture1 = new ResourceLocation(MarsModule.TEXTURE_DOMAIN, "textures/model/chamber2_dark.png");

	private final IModelCustom model;

	public TileEntityCryogenicChamberRenderer(IModelCustom model)
	{
		this.model = model;
	}

	public void renderCryogenicChamber(TileEntityCryogenicChamber chamber, double par2, double par4, double par6, float par8)
	{
		GL11.glPushMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glTranslatef((float) par2 + 0.5F, (float) par4, (float) par6 + 0.5F);

		float rotation = 0.0F;

		switch (chamber.getBlockMetadata() - BlockMachineMars.CRYOGENIC_CHAMBER_METADATA)
		{
		case 0:
			rotation = 180.0F;
			break;
		case 1:
			rotation = 0.0F;
			break;
		case 2:
			rotation = 270.0F;
			break;
		case 3:
			rotation = 90.0F;
			break;
		}

		GL11.glRotatef(rotation, 0, 1, 0);
		GL11.glTranslatef(-0.5F, 0.0F, 0.0F);

		this.bindTexture(TileEntityCryogenicChamberRenderer.chamberTexture0);
		this.model.renderPart("Main_Cylinder");
		this.bindTexture(TileEntityCryogenicChamberRenderer.chamberTexture1);
		this.model.renderPart("Shield_Torus");

		GL11.glPopMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8)
	{
		this.renderCryogenicChamber((TileEntityCryogenicChamber) par1TileEntity, par2, par4, par6, par8);
	}
}
