package micdoodle8.mods.galacticraft.planets.mars.tile;

import java.util.ArrayList;
import java.util.List;

import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.tile.TileEntityDungeonSpawner;
import micdoodle8.mods.galacticraft.planets.mars.entities.EntityCreeperBoss;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;

/**
 * GCMarsTileEntityDungeonSpawner.java
 * 
 * This file is part of the Galacticraft project
 * 
 * @author micdoodle8
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class TileEntityDungeonSpawnerMars extends TileEntityDungeonSpawner
{
	public TileEntityDungeonSpawnerMars()
	{
		super(EntityCreeperBoss.class);
	}

	@Override
	public List<Class<? extends EntityLiving>> getDisabledCreatures()
	{
		List<Class<? extends EntityLiving>> list = new ArrayList<Class<? extends EntityLiving>>();
		list.add(EntitySkeleton.class);
		list.add(EntityZombie.class);
		list.add(EntitySpider.class);
		return list;
	}

	@Override
	public void playSpawnSound(Entity entity)
	{
		this.worldObj.playSoundAtEntity(entity, GalacticraftCore.ASSET_PREFIX + "ambience.scaryscape", 9.0F, 1.4F);
	}
}
