package micdoodle8.mods.galacticraft.core.blocks;

import java.util.Random;

import universalelectricity.api.vector.Vector3;
import micdoodle8.mods.galacticraft.core.tile.TileEntityMulti;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * BlockMulti.java
 * 
 * This file is part of the Galacticraft project
 * 
 * @author micdoodle8
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class BlockMulti extends BlockContainer
{
	public String textureName = null;
	public String channel = "";

	public BlockMulti(int id)
	{
		super(id, GCCoreBlocks.machine);
		this.setHardness(0.8F);
		this.setUnlocalizedName("multiBlock");
	}

	public BlockMulti setChannel(String channel)
	{
		this.channel = channel;
		return this;
	}

	@Override
	public BlockMulti setTextureName(String name)
	{
		this.textureName = name;
		return this;
	}

	public void makeFakeBlock(World worldObj, Vector3 position, Vector3 mainBlock)
	{
		worldObj.setBlock(position.intX(), position.intY(), position.intZ(), this.blockID);
		((TileEntityMulti) worldObj.getBlockTileEntity(position.intX(), position.intY(), position.intZ())).setMainBlock(mainBlock);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IconRegister iconRegister)
	{
		if (this.textureName != null)
		{
			this.blockIcon = iconRegister.registerIcon(this.textureName);
		}
		else
		{
			super.registerIcons(iconRegister);
		}
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, int par5, int par6)
	{
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

		if (tileEntity instanceof TileEntityMulti)
		{
			((TileEntityMulti) tileEntity).onBlockRemoval();
		}

		super.breakBlock(world, x, y, z, par5, par6);
	}

	/**
	 * Called when the block is right clicked by the player. This modified
	 * version detects electric items and wrench actions on your machine block.
	 * Do not override this function. Use machineActivated instead! (It does the
	 * same thing)
	 */
	@Override
	public boolean onBlockActivated(World par1World, int x, int y, int z, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
	{
		TileEntityMulti tileEntity = (TileEntityMulti) par1World.getBlockTileEntity(x, y, z);
		return tileEntity.onBlockActivated(par1World, x, y, z, par5EntityPlayer);
	}

	/**
	 * Returns the quantity of items to drop on block destruction.
	 */
	@Override
	public int quantityDropped(Random par1Random)
	{
		return 0;
	}

	@Override
	public int getRenderType()
	{
		return -1;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World var1)
	{
		return new TileEntityMulti(this.channel);
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World par1World, int x, int y, int z)
	{
		TileEntity tileEntity = par1World.getBlockTileEntity(x, y, z);
		Vector3 mainBlockPosition = ((TileEntityMulti) tileEntity).mainBlockPosition;

		if (mainBlockPosition != null)
		{
			int mainBlockID = par1World.getBlockId(mainBlockPosition.intX(), mainBlockPosition.intY(), mainBlockPosition.intZ());

			if (mainBlockID > 0)
			{
				return Block.blocksList[mainBlockID].getPickBlock(target, par1World, mainBlockPosition.intX(), mainBlockPosition.intY(), mainBlockPosition.intZ());
			}
		}

		return null;
	}
}
