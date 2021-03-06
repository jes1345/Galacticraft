package micdoodle8.mods.galacticraft.core.util;

import java.io.File;
import java.util.Arrays;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.config.Configuration;

/**
 * GCCoreConfigManager.java
 * 
 * This file is part of the Galacticraft project
 * 
 * @author micdoodle8
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class ConfigManagerCore
{
	public static boolean loaded;

	static Configuration configuration;

	public static int idDimensionOverworldOrbit;
	public static int idDimensionOverworldOrbitStatic;
	public static int idDimensionMoon;

	// SCHEMATICS
	public static int idSchematicRocketT1;
	public static int idSchematicMoonBuggy;
	public static int idSchematicAddSchematic;

	// GUI
	public static int idGuiRocketCraftingBench;
	public static int idGuiBuggyCraftingBench;
	public static int idGuiGalaxyMap;
	public static int idGuiSpaceshipInventory;
	public static int idGuiAddSchematic;
	public static int idGuiKnowledgeBook;
	public static int idGuiExtendedInventory;
	public static int idGuiNewSpaceRace;

	// ACHIEVEMENTS
	public static int idAchievBase;

	public static int idEntityEvolvedSpider;
	public static int idEntityEvolvedCreeper;
	public static int idEntityEvolvedZombie;
	public static int idEntityEvolvedSkeleton;
	public static int idEntityEvolvedSkeletonBoss;
	public static int idEntitySpaceship;
	public static int idEntityAntiGravityArrow;
	public static int idEntityMeteor;
	public static int idEntityBuggy;
	public static int idEntityFlag;
	public static int idEntityAstroOrb;
	public static int idEntityParaChest;
	public static int idEntityAlienVillager;
	public static int idEntityOxygenBubble;
	public static int idEntityLander;
	public static int idEntityLanderChest;
	public static int idEntityMeteorChunk;

	// GENERAL
	public static boolean moreStars;
	public static boolean wasdMapMovement;
	public static String[] sealableIDs;
	public static String[] detectableIDs;
	public static boolean disableSpaceshipParticles;
	public static boolean disableSpaceshipGrief;
	public static boolean oxygenIndicatorLeft;
	public static boolean oxygenIndicatorBottom;
	public static double oilGenFactor;
	public static boolean disableLeafDecay;
	public static boolean spaceStationsRequirePermission;
	public static boolean overrideCapes;
	public static double spaceStationEnergyScalar;
	public static boolean disableLander;
	public static double dungeonBossHealthMod;
	public static int suffocationCooldown;
	public static int suffocationDamage;
	public static int[] externalOilGen;
	public static boolean forceOverworldRespawn;
	public static boolean enableDebug;
	public static boolean enableCopperOreGen;
	public static boolean enableTinOreGen;
	public static boolean enableAluminumOreGen;
	public static boolean enableSiliconOreGen;
	public static int[] staticLoadDimensions = {};
	public static boolean disableCheeseMoon;
	public static boolean disableTinMoon;
	public static boolean disableCopperMoon;
	public static boolean disableMoonVillageGen;

	public static void setDefaultValues(File file)
	{
		if (!ConfigManagerCore.loaded)
		{
			ConfigManagerCore.configuration = new Configuration(file);
		}

		try
		{
			ConfigManagerCore.configuration.load();

			ConfigManagerCore.idDimensionMoon = ConfigManagerCore.configuration.get("Dimensions", "idDimensionMoon", -28).getInt(-28);
			ConfigManagerCore.idDimensionOverworldOrbit = ConfigManagerCore.configuration.get("DIMENSIONS", "idDimensionOverworldOrbit", -27).getInt(-27);
			ConfigManagerCore.idDimensionOverworldOrbitStatic = ConfigManagerCore.configuration.get("DIMENSIONS", "idDimensionOverworldOrbitStatic", -26, "Static Space Station ID").getInt(-26);
			ConfigManagerCore.staticLoadDimensions = ConfigManagerCore.configuration.get("DIMENSIONS", "Static Loaded Dimensions", ConfigManagerCore.staticLoadDimensions, "IDs to load at startup, and keep loaded until server stops. Can be added via /gckeeploaded").getIntList();
			
			ConfigManagerCore.idGuiRocketCraftingBench = ConfigManagerCore.configuration.get("GUI", "idGuiRocketCraftingBench", 130).getInt(130);
			ConfigManagerCore.idGuiBuggyCraftingBench = ConfigManagerCore.configuration.get("GUI", "idGuiBuggyCraftingBench", 131).getInt(131);
			ConfigManagerCore.idGuiGalaxyMap = ConfigManagerCore.configuration.get("GUI", "idGuiGalaxyMap", 132).getInt(132);
			ConfigManagerCore.idGuiSpaceshipInventory = ConfigManagerCore.configuration.get("GUI", "idGuiSpaceshipInventory", 133).getInt(133);
			ConfigManagerCore.idGuiAddSchematic = ConfigManagerCore.configuration.get("GUI", "idGuiAddSchematic", 138).getInt(138);
			ConfigManagerCore.idGuiKnowledgeBook = ConfigManagerCore.configuration.get("GUI", "idGuiKnowledgeBook", 140).getInt(140);
			ConfigManagerCore.idGuiExtendedInventory = ConfigManagerCore.configuration.get("GUI", "idGuiExtendedInventory", 145).getInt(145);
			ConfigManagerCore.idGuiNewSpaceRace = ConfigManagerCore.configuration.get("GUI", "idGuiNewSpaceRace", 146).getInt(146);

			ConfigManagerCore.idSchematicRocketT1 = ConfigManagerCore.configuration.get("Schematic", "idSchematicRocketT1", 0).getInt(0);
			ConfigManagerCore.idSchematicMoonBuggy = ConfigManagerCore.configuration.get("Schematic", "idSchematicMoonBuggy", 1).getInt(1);
			ConfigManagerCore.idSchematicAddSchematic = ConfigManagerCore.configuration.get("Schematic", "idSchematicAddSchematic", Integer.MAX_VALUE).getInt(Integer.MAX_VALUE);

			ConfigManagerCore.idAchievBase = ConfigManagerCore.configuration.get("Achievements", "idAchievBase", 1784).getInt(1784);

			ConfigManagerCore.idEntityEvolvedSpider = ConfigManagerCore.configuration.get("Entities", "idEntityEvolvedSpider", 155).getInt(155);
			ConfigManagerCore.idEntityEvolvedCreeper = ConfigManagerCore.configuration.get("Entities", "idEntityEvolvedCreeper", 156).getInt(156);
			ConfigManagerCore.idEntityEvolvedZombie = ConfigManagerCore.configuration.get("Entities", "idEntityEvolvedZombie", 157).getInt(157);
			ConfigManagerCore.idEntityEvolvedSkeleton = ConfigManagerCore.configuration.get("Entities", "idEntityEvolvedSkeleton", 158).getInt(158);
			ConfigManagerCore.idEntitySpaceship = ConfigManagerCore.configuration.get("Entities", "idEntitySpaceship", 159).getInt(159);
			ConfigManagerCore.idEntityAntiGravityArrow = ConfigManagerCore.configuration.get("Entities", "idEntityAntiGravityArrow", 160).getInt(160);
			ConfigManagerCore.idEntityMeteor = ConfigManagerCore.configuration.get("Entities", "idEntityMeteor", 161).getInt(161);
			ConfigManagerCore.idEntityBuggy = ConfigManagerCore.configuration.get("Entities", "idEntityBuggy", 162).getInt(162);
			ConfigManagerCore.idEntityFlag = ConfigManagerCore.configuration.get("Entities", "idEntityFlag", 163).getInt(163);
			ConfigManagerCore.idEntityAstroOrb = ConfigManagerCore.configuration.get("Entities", "idEntityAstroOrb", 164).getInt(164);
			ConfigManagerCore.idEntityParaChest = ConfigManagerCore.configuration.get("Entities", "idEntityParaChest", 165).getInt(165);
			ConfigManagerCore.idEntityAlienVillager = ConfigManagerCore.configuration.get("Entities", "idEntityAlienVillager", 166).getInt(166);
			ConfigManagerCore.idEntityOxygenBubble = ConfigManagerCore.configuration.get("Entities", "idEntityOxygenBubble", 167).getInt(167);
			ConfigManagerCore.idEntityLander = ConfigManagerCore.configuration.get("Entities", "idEntityLander", 168).getInt(168);
			ConfigManagerCore.idEntityLanderChest = ConfigManagerCore.configuration.get("Entities", "idEntityLanderChest", 169).getInt(169);
			ConfigManagerCore.idEntityEvolvedSkeletonBoss = ConfigManagerCore.configuration.get("Entities", "idEntityEvolvedSkeletonBoss", 170).getInt(170);
			ConfigManagerCore.idEntityMeteorChunk = ConfigManagerCore.configuration.get("Entities", "idEntityMeteorChunk", 179).getInt(179);

			ConfigManagerCore.moreStars = ConfigManagerCore.configuration.get(Configuration.CATEGORY_GENERAL, "More Stars", true, "Setting this to false will revert night skies back to default minecraft star count").getBoolean(true);
			ConfigManagerCore.wasdMapMovement = ConfigManagerCore.configuration.get(Configuration.CATEGORY_GENERAL, "WASD Map Movement", true, "If you prefer to move the Galaxy map with your mouse, set to false").getBoolean(true);
			ConfigManagerCore.oilGenFactor = ConfigManagerCore.configuration.get(Configuration.CATEGORY_GENERAL, "Oil Generation Factor", 1.8, "Increasing this will increase amount of oil that will generate in each chunk.").getDouble(1.8);
			ConfigManagerCore.disableSpaceshipParticles = ConfigManagerCore.configuration.get(Configuration.CATEGORY_GENERAL, "Disable Spaceship Particles", false, "If you have FPS problems, setting this to true will help if spaceship particles are in your sights").getBoolean(false);
			ConfigManagerCore.disableSpaceshipGrief = ConfigManagerCore.configuration.get(Configuration.CATEGORY_GENERAL, "Disable Spaceship Explosion", false, "Spaceships will not explode on contact if set to true").getBoolean(false);
			ConfigManagerCore.oxygenIndicatorLeft = ConfigManagerCore.configuration.get(Configuration.CATEGORY_GENERAL, "Minimap Left", false, "If true, this will move the Oxygen Indicator to the left side. You can combine this with \"Minimap Bottom\"").getBoolean(false);
			ConfigManagerCore.oxygenIndicatorBottom = ConfigManagerCore.configuration.get(Configuration.CATEGORY_GENERAL, "Minimap Bottom", false, "If true, this will move the Oxygen Indicator to the bottom. You can combine this with \"Minimap Left\"").getBoolean(false);
			ConfigManagerCore.disableLeafDecay = ConfigManagerCore.configuration.get(Configuration.CATEGORY_GENERAL, "Disable Oxygen Collector Leaf Decay", false, "If set to true, Oxygen Collectors will not consume leaf blocks.").getBoolean(false);
			ConfigManagerCore.spaceStationsRequirePermission = ConfigManagerCore.configuration.get(Configuration.CATEGORY_GENERAL, "Space Stations Require Permission", true, "While true, space stations require you to invite other players using /ssinvite <playername>").getBoolean(true);
			ConfigManagerCore.overrideCapes = ConfigManagerCore.configuration.get(Configuration.CATEGORY_GENERAL, "Override Capes", true, "By default, Galacticraft will override capes with the mod's donor cape. Set to false to disable.").getBoolean(true);
			ConfigManagerCore.spaceStationEnergyScalar = ConfigManagerCore.configuration.get(Configuration.CATEGORY_GENERAL, "Space Station Solar Energy Multiplier", 2.0, "If Mekanism is installed, solar panels will work (default 2x) more effective on space stations.").getDouble(2.0);
			ConfigManagerCore.sealableIDs = ConfigManagerCore.configuration.get(Configuration.CATEGORY_GENERAL, "External Sealable IDs", new String[] { String.valueOf(Block.getIdFromBlock(Blocks.glass_pane) + ":0") }, "List IDs of non-opaque blocks from other mods (for example, special types of glass) that the Oxygen Sealer should recognize as solid seals. Format is ID:METADATA").getStringList();
			ConfigManagerCore.detectableIDs = ConfigManagerCore.configuration.get(Configuration.CATEGORY_GENERAL, "External Detectable IDs", new String[] { String.valueOf(Block.getIdFromBlock(Blocks.coal_ore) + ":0"), String.valueOf(Block.getIdFromBlock(Blocks.diamond_ore) + ":0"), String.valueOf(Block.getIdFromBlock(Blocks.gold_ore) + ":0"), String.valueOf(Block.getIdFromBlock(Blocks.iron_ore) + ":0"), String.valueOf(Block.getIdFromBlock(Blocks.lapis_ore) + ":0"), String.valueOf(Block.getIdFromBlock(Blocks.redstone_ore) + ":0"), String.valueOf(Block.getIdFromBlock(Blocks.lit_redstone_ore) + ":0") }, "List IDs from other mods that the Sensor Glasses should recognize as solid blocks. Format is ID:METADATA").getStringList();
			ConfigManagerCore.dungeonBossHealthMod = ConfigManagerCore.configuration.get(Configuration.CATEGORY_GENERAL, "Dungeon Boss Health Modifier", 1.0D, "Change this is you wish to balance the mod (if you have more powerful weapon mods)").getDouble(1.0D);
			ConfigManagerCore.suffocationCooldown = ConfigManagerCore.configuration.get(Configuration.CATEGORY_GENERAL, "Suffocation Cooldown", 100, "Lower/Raise this value to change time between suffocation damage ticks").getInt(100);
			ConfigManagerCore.suffocationDamage = ConfigManagerCore.configuration.get(Configuration.CATEGORY_GENERAL, "Suffocation Damage", 2, "Change this value to modify the damage taken per suffocation tick").getInt(2);
			ConfigManagerCore.externalOilGen = ConfigManagerCore.configuration.get(Configuration.CATEGORY_GENERAL, "Oil gen in external dimensions", new int[] { 0 }, "List of non-galacticraft dimension IDs to generate oil in").getIntList();
			ConfigManagerCore.forceOverworldRespawn = ConfigManagerCore.configuration.get(Configuration.CATEGORY_GENERAL, "Force Overworld Spawn", false, "By default, you will respawn on galacticraft dimensions if you die. If you set this to true, you will respawn back on earth.").getBoolean(false);
			ConfigManagerCore.enableDebug = ConfigManagerCore.configuration.get(Configuration.CATEGORY_GENERAL, "Enable Debug Messages", false, "If this is enabled, debug messages will appear in the console. This is useful for finding bugs in the mod.").getBoolean(false);
			ConfigManagerCore.enableCopperOreGen = ConfigManagerCore.configuration.get(Configuration.CATEGORY_GENERAL, "Enable Copper Ore Gen", true, "If this is enabled, copper ore will generate on the overworld.").getBoolean(true);
			ConfigManagerCore.enableTinOreGen = ConfigManagerCore.configuration.get(Configuration.CATEGORY_GENERAL, "Enable Tin Ore Gen", true, "If this is enabled, tin ore will generate on the overworld.").getBoolean(true);
			ConfigManagerCore.enableAluminumOreGen = ConfigManagerCore.configuration.get(Configuration.CATEGORY_GENERAL, "Enable Aluminum Ore Gen", true, "If this is enabled, aluminum ore will generate on the overworld.").getBoolean(true);
			ConfigManagerCore.enableSiliconOreGen = ConfigManagerCore.configuration.get(Configuration.CATEGORY_GENERAL, "Enable Silicon Ore Gen", true, "If this is enabled, silicon ore will generate on the overworld.").getBoolean(true);
			ConfigManagerCore.disableCheeseMoon = ConfigManagerCore.configuration.get(Configuration.CATEGORY_GENERAL, "Disable Cheese Ore Gen on Moon", false).getBoolean(false);
			ConfigManagerCore.disableTinMoon = ConfigManagerCore.configuration.get(Configuration.CATEGORY_GENERAL, "Disable Tin Ore Gen on Moon", false).getBoolean(false);
			ConfigManagerCore.disableCopperMoon = ConfigManagerCore.configuration.get(Configuration.CATEGORY_GENERAL, "Disable Copper Ore Gen on Moon", false).getBoolean(false);
			ConfigManagerCore.disableMoonVillageGen = ConfigManagerCore.configuration.get(Configuration.CATEGORY_GENERAL, "Disable Moon Village Gen", false).getBoolean(false);
		}
		catch (final Exception e)
		{
			GCLog.severe("Problem loading core config (\"core.conf\")");
		}
		finally
		{
			if (ConfigManagerCore.configuration.hasChanged())
			{
				ConfigManagerCore.configuration.save();
			}

			ConfigManagerCore.loaded = true;
		}
	}

	public static boolean setLoaded(int newID)
	{
		boolean found = false;

		for (int staticLoadDimension : ConfigManagerCore.staticLoadDimensions)
		{
			if (staticLoadDimension == newID)
			{
				found = true;
				break;
			}
		}

		if (!found)
		{
			int[] oldIDs = ConfigManagerCore.staticLoadDimensions;
			ConfigManagerCore.staticLoadDimensions = new int[ConfigManagerCore.staticLoadDimensions.length + 1];

			for (int i = 0; i < oldIDs.length; i++)
			{
				ConfigManagerCore.staticLoadDimensions[i] = oldIDs[i];
			}

			ConfigManagerCore.staticLoadDimensions[ConfigManagerCore.staticLoadDimensions.length - 1] = newID;
			String[] values = new String[ConfigManagerCore.staticLoadDimensions.length];
			Arrays.sort(ConfigManagerCore.staticLoadDimensions);

			for (int i = 0; i < values.length; i++)
			{
				values[i] = String.valueOf(ConfigManagerCore.staticLoadDimensions[i]);
			}

			ConfigManagerCore.configuration.get("DIMENSIONS", "Static Loaded Dimensions", ConfigManagerCore.staticLoadDimensions, "IDs to load at startup, and keep loaded until server stops. Can be added via /gckeeploaded").set(values);
			ConfigManagerCore.configuration.save();
		}

		return !found;
	}
}
