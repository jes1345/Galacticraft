package micdoodle8.mods.galacticraft.core.tick;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import micdoodle8.mods.galacticraft.api.world.IOrbitDimension;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.dimension.SpaceRaceManager;
import micdoodle8.mods.galacticraft.core.dimension.WorldDataSpaceRaces;
import micdoodle8.mods.galacticraft.core.network.PacketSimple;
import micdoodle8.mods.galacticraft.core.network.PacketSimple.EnumSimplePacket;
import micdoodle8.mods.galacticraft.core.util.WorldUtil;
import micdoodle8.mods.galacticraft.core.wrappers.Footprint;
import micdoodle8.mods.galacticraft.core.wrappers.ScheduledBlockChange;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ServerTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;

/**
 * GCCoreTickHandlerServer.java
 * 
 * This file is part of the Galacticraft project
 * 
 * @author micdoodle8
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class TickHandlerServer
{
	private static Map<Integer, List<ScheduledBlockChange>> scheduledBlockChanges = new ConcurrentHashMap<Integer, List<ScheduledBlockChange>>();
	private static Map<Integer, List<Footprint>> footprintList = new HashMap<Integer, List<Footprint>>();
	public static WorldDataSpaceRaces spaceRaceData = null;
	private long tickCount;

	public static void scheduleNewBlockChange(int dimID, ScheduledBlockChange change)
	{
		List<ScheduledBlockChange> changeList = TickHandlerServer.scheduledBlockChanges.get(dimID);

		if (changeList == null)
		{
			changeList = new ArrayList<ScheduledBlockChange>();
		}

		changeList.add(change);
		TickHandlerServer.scheduledBlockChanges.put(dimID, changeList);
	}
	
	public static void addFootprint(Footprint print, int dimID)
	{
		List<Footprint> footprints = TickHandlerServer.footprintList.get(dimID);
		
		if (footprints == null)
		{
			footprints = new ArrayList<Footprint>();
		}
		
		footprints.add(print);
		footprintList.put(dimID, footprints);
	}
	
	@SubscribeEvent
	public void onServerTick(ServerTickEvent event)
	{
		if (spaceRaceData == null)
		{
			World world = FMLCommonHandler.instance().getMinecraftServerInstance().worldServerForDimension(0);
			spaceRaceData = (WorldDataSpaceRaces) world.mapStorage.loadData(WorldDataSpaceRaces.class, WorldDataSpaceRaces.saveDataID);
			
	        if (spaceRaceData == null)
	        {
	        	spaceRaceData = new WorldDataSpaceRaces(WorldDataSpaceRaces.saveDataID);
	            world.mapStorage.setData(WorldDataSpaceRaces.saveDataID, this.spaceRaceData);
	        }
		}
		
		SpaceRaceManager.tick();
		
		if (tickCount % 100 == 0)
		{
			WorldServer[] worlds = FMLCommonHandler.instance().getMinecraftServerInstance().worldServers;
			
			for (int i = 0; i < worlds.length; i++)
			{
				List<Footprint> footprints = footprintList.get(worlds[i].provider.dimensionId);
				
				if (footprints != null)
				{
					List<Footprint> toRemove = new ArrayList<Footprint>();
					
					for (int j = 0; j < footprints.size(); j++)
					{
						footprints.get(j).age += 100;
						
						if (footprints.get(j).age >= Footprint.MAX_AGE)
						{
							toRemove.add(footprints.get(j));
						}
					}
					
					footprints.removeAll(toRemove);
					footprintList.put(worlds[i].provider.dimensionId, footprints);	
					
					GalacticraftCore.packetPipeline.sendToDimension(new PacketSimple(EnumSimplePacket.C_UPDATE_FOOTPRINT_LIST, new Object[] { footprints.toArray(new Footprint[footprints.size()]) }), worlds[i].provider.dimensionId);
				}
			}
		}
		
		tickCount++;
		
		if (tickCount >= Long.MAX_VALUE)
		{
			tickCount = 0;
		}
	}

	@SubscribeEvent
	public void onWorldTick(WorldTickEvent event)
	{
		final WorldServer world = (WorldServer) event.world;

		List<ScheduledBlockChange> scheduledChanges = TickHandlerServer.scheduledBlockChanges.get(world.provider.dimensionId);

		if (scheduledChanges != null && !scheduledChanges.isEmpty())
		{
			for (Iterator<ScheduledBlockChange> it = scheduledChanges.iterator(); it.hasNext();)
			{
				ScheduledBlockChange change = it.next();
				world.setBlock(change.getChangePosition().intX(), change.getChangePosition().intY(), change.getChangePosition().intZ(), change.getChangeBlock(), change.getChangeMeta(), change.getChangeFlag());
				it.remove();
			}
		}

		if (world.provider instanceof IOrbitDimension)
		{
			final Object[] entityList = world.loadedEntityList.toArray();

			for (final Object o : entityList)
			{
				if (o instanceof Entity)
				{
					final Entity e = (Entity) o;

					if (e.worldObj.provider instanceof IOrbitDimension)
					{
						final IOrbitDimension dimension = (IOrbitDimension) e.worldObj.provider;

						if (e.posY <= dimension.getYCoordToTeleportToPlanet())
						{
							final Integer dim = WorldUtil.getProviderForName(dimension.getPlanetToOrbit()).dimensionId;

							WorldUtil.transferEntityToDimension(e, dim, world, false, null);
						}
					}
				}
			}
		}
	}
}
