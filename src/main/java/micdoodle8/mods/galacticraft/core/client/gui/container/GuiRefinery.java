package micdoodle8.mods.galacticraft.core.client.gui.container;

import java.util.ArrayList;
import java.util.List;

import micdoodle8.mods.galacticraft.api.transmission.ElectricityDisplay;
import micdoodle8.mods.galacticraft.api.transmission.ElectricityDisplay.ElectricUnit;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.client.gui.element.GuiElementInfoRegion;
import micdoodle8.mods.galacticraft.core.inventory.ContainerRefinery;
import micdoodle8.mods.galacticraft.core.network.PacketSimple;
import micdoodle8.mods.galacticraft.core.network.PacketSimple.EnumSimplePacket;
import micdoodle8.mods.galacticraft.core.tile.TileEntityRefinery;
import micdoodle8.mods.galacticraft.core.util.CompatibilityManager;
import micdoodle8.mods.galacticraft.core.util.EnumColor;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * GCCoreGuiRefinery.java
 * 
 * This file is part of the Galacticraft project
 * 
 * @author micdoodle8
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
@SideOnly(Side.CLIENT)
public class GuiRefinery extends GuiContainerGC
{
	private static final ResourceLocation refineryTexture = new ResourceLocation(GalacticraftCore.ASSET_DOMAIN, "textures/gui/refinery.png");

	private final TileEntityRefinery tileEntity;

	private GuiButton buttonDisable;

	private int containerWidth;
	private int containerHeight;

	private GuiElementInfoRegion fuelTankRegion = new GuiElementInfoRegion((this.width - this.xSize) / 2 + 153, (this.height - this.ySize) / 2 + 28, 16, 38, new ArrayList<String>(), this.width, this.height);
	private GuiElementInfoRegion oilTankRegion = new GuiElementInfoRegion((this.width - this.xSize) / 2 + 7, (this.height - this.ySize) / 2 + 28, 16, 38, new ArrayList<String>(), this.width, this.height);
	private GuiElementInfoRegion electricInfoRegion = new GuiElementInfoRegion((this.width - this.xSize) / 2 + 62, (this.height - this.ySize) / 2 + 16, 56, 9, new ArrayList<String>(), this.width, this.height);

	public GuiRefinery(InventoryPlayer par1InventoryPlayer, TileEntityRefinery tileEntity)
	{
		super(new ContainerRefinery(par1InventoryPlayer, tileEntity));
		this.tileEntity = tileEntity;
		this.ySize = 168;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initGui()
	{
		super.initGui();
		List<String> oilTankDesc = new ArrayList<String>();
		oilTankDesc.add("The refinery oil tank");
		int oilLevel = this.tileEntity.oilTank != null && this.tileEntity.oilTank.getFluid() != null ? this.tileEntity.oilTank.getFluid().amount : 0;
		int oilCapacity = this.tileEntity.oilTank != null ? this.tileEntity.oilTank.getCapacity() : 0;
		oilTankDesc.add(EnumColor.YELLOW + "Oil: " + oilLevel + " / " + oilCapacity);
		this.oilTankRegion.tooltipStrings = oilTankDesc;
		this.oilTankRegion.xPosition = (this.width - this.xSize) / 2 + 7;
		this.oilTankRegion.yPosition = (this.height - this.ySize) / 2 + 28;
		this.oilTankRegion.parentWidth = this.width;
		this.oilTankRegion.parentHeight = this.height;
		this.infoRegions.add(this.oilTankRegion);
		List<String> batterySlotDesc = new ArrayList<String>();
		batterySlotDesc.add("Refinery battery slot, place battery here");
		batterySlotDesc.add("if not using a connected power source");
		this.infoRegions.add(new GuiElementInfoRegion((this.width - this.xSize) / 2 + 49, (this.height - this.ySize) / 2 + 50, 18, 18, batterySlotDesc, this.width, this.height));
		List<String> fuelTankDesc = new ArrayList<String>();
		fuelTankDesc.add("The refinery fuel tank");
		int fuelLevel = this.tileEntity.fuelTank != null && this.tileEntity.fuelTank.getFluid() != null ? this.tileEntity.fuelTank.getFluid().amount : 0;
		int fuelCapacity = this.tileEntity.fuelTank != null ? this.tileEntity.fuelTank.getCapacity() : 0;
		fuelTankDesc.add(EnumColor.YELLOW + "Fuel: " + fuelLevel + " / " + fuelCapacity);
		this.fuelTankRegion.tooltipStrings = fuelTankDesc;
		this.fuelTankRegion.xPosition = (this.width - this.xSize) / 2 + 153;
		this.fuelTankRegion.yPosition = (this.height - this.ySize) / 2 + 28;
		this.fuelTankRegion.parentWidth = this.width;
		this.fuelTankRegion.parentHeight = this.height;
		this.infoRegions.add(this.fuelTankRegion);
		List<String> oilSlotDesc = new ArrayList<String>();
		oilSlotDesc.add("Refinery oil input. Place oil canisters" + (CompatibilityManager.isBCraftLoaded() ? " or oil buckets" : ""));
		oilSlotDesc.add("into this slot to load it into the oil tank.");
		this.infoRegions.add(new GuiElementInfoRegion((this.width - this.xSize) / 2 + 6, (this.height - this.ySize) / 2 + 6, 18, 18, oilSlotDesc, this.width, this.height));
		List<String> fuelSlotDesc = new ArrayList<String>();
		fuelSlotDesc.add("Refinery fuel output. Place empty liquid");
		fuelSlotDesc.add("canisters into this slot to fill them");
		fuelSlotDesc.add("from the fuel tank.");
		this.infoRegions.add(new GuiElementInfoRegion((this.width - this.xSize) / 2 + 152, (this.height - this.ySize) / 2 + 6, 18, 18, fuelSlotDesc, this.width, this.height));
		List<String> electricityDesc = new ArrayList<String>();
		electricityDesc.add("Electrical Storage");
		electricityDesc.add(EnumColor.YELLOW + "Energy: " + ((int) Math.floor(this.tileEntity.getEnergyStoredGC()) + " / " + (int) Math.floor(this.tileEntity.getMaxEnergyStoredGC())));
		this.electricInfoRegion.tooltipStrings = electricityDesc;
		this.electricInfoRegion.xPosition = (this.width - this.xSize) / 2 + 62;
		this.electricInfoRegion.yPosition = (this.height - this.ySize) / 2 + 16;
		this.electricInfoRegion.parentWidth = this.width;
		this.electricInfoRegion.parentHeight = this.height;
		this.infoRegions.add(this.electricInfoRegion);
		this.buttonList.add(this.buttonDisable = new GuiButton(0, this.width / 2 - 39, this.height / 2 - 56, 76, 20, StatCollector.translateToLocal("gui.button.refine.name")));
	}

	@Override
	protected void actionPerformed(GuiButton par1GuiButton)
	{
		switch (par1GuiButton.id)
		{
		case 0:
			GalacticraftCore.packetPipeline.sendToServer(new PacketSimple(EnumSimplePacket.S_UPDATE_DISABLEABLE_BUTTON, new Object[] { this.tileEntity.xCoord, this.tileEntity.yCoord, this.tileEntity.zCoord, 0 }));
			break;
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		this.fontRendererObj.drawString(this.tileEntity.getInventoryName(), 68, 5, 4210752);
		String displayText = "";
		int yOffset = -18;

		if (this.tileEntity.oilTank.getFluid() == null || this.tileEntity.oilTank.getFluidAmount() == 0)
		{
			displayText = EnumColor.RED + StatCollector.translateToLocal("gui.status.nooil.name");
		}
		else if (this.tileEntity.oilTank.getFluidAmount() > 0 && this.tileEntity.disabled)
		{
			displayText = EnumColor.ORANGE + StatCollector.translateToLocal("gui.status.ready.name");
		}
		else if (this.tileEntity.canProcess())
		{
			displayText = EnumColor.BRIGHT_GREEN + StatCollector.translateToLocal("gui.status.refining.name");
		}
		else
		{
			displayText = EnumColor.RED + StatCollector.translateToLocal("gui.status.unknown.name");
		}

		this.buttonDisable.enabled = this.tileEntity.disableCooldown == 0;
		this.buttonDisable.displayString = this.tileEntity.processTicks == 0 ? StatCollector.translateToLocal("gui.button.refine.name") : StatCollector.translateToLocal("gui.button.stoprefine.name");
		this.fontRendererObj.drawString(StatCollector.translateToLocal("gui.message.status.name") + ": " + displayText, 72, 45 + 23 + yOffset, 4210752);
//		this.fontRendererObj.drawString(ElectricityDisplay.getDisplay(this.tileEntity.ueWattsPerTick * 20, ElectricUnit.WATT), 72, 56 + 23 + yOffset, 4210752);
//		this.fontRendererObj.drawString(ElectricityDisplay.getDisplay(this.tileEntity.getVoltage(), ElectricUnit.VOLTAGE), 72, 68 + 23 + yOffset, 4210752);
		this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 118 + 2 + 23, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		this.mc.renderEngine.bindTexture(GuiRefinery.refineryTexture);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		this.containerWidth = (this.width - this.xSize) / 2;
		this.containerHeight = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(this.containerWidth, this.containerHeight, 0, 0, this.xSize, this.ySize);

		int displayInt = this.tileEntity.getScaledOilLevel(38);
		this.drawTexturedModalRect((this.width - this.xSize) / 2 + 7, (this.height - this.ySize) / 2 + 17 + 49 - displayInt, 176, 38 - displayInt, 16, displayInt);

		displayInt = this.tileEntity.getScaledFuelLevel(38);
		this.drawTexturedModalRect((this.width - this.xSize) / 2 + 153, (this.height - this.ySize) / 2 + 17 + 49 - displayInt, 176 + 16, 38 - displayInt, 16, displayInt);

		List<String> oilTankDesc = new ArrayList<String>();
		oilTankDesc.add("The refinery oil tank");
		int oilLevel = this.tileEntity.oilTank != null && this.tileEntity.oilTank.getFluid() != null ? this.tileEntity.oilTank.getFluid().amount : 0;
		int oilCapacity = this.tileEntity.oilTank != null ? this.tileEntity.oilTank.getCapacity() : 0;
		oilTankDesc.add(EnumColor.YELLOW + "Oil: " + oilLevel + " / " + oilCapacity);
		this.oilTankRegion.tooltipStrings = oilTankDesc;

		List<String> fuelTankDesc = new ArrayList<String>();
		fuelTankDesc.add("The refinery fuel tank");
		int fuelLevel = this.tileEntity.fuelTank != null && this.tileEntity.fuelTank.getFluid() != null ? this.tileEntity.fuelTank.getFluid().amount : 0;
		int fuelCapacity = this.tileEntity.fuelTank != null ? this.tileEntity.fuelTank.getCapacity() : 0;
		fuelTankDesc.add(EnumColor.YELLOW + "Fuel: " + fuelLevel + " / " + fuelCapacity);
		this.fuelTankRegion.tooltipStrings = fuelTankDesc;

		List<String> electricityDesc = new ArrayList<String>();
		electricityDesc.add("Electrical Storage");
		electricityDesc.add(EnumColor.YELLOW + "Energy: " + ((int) Math.floor(this.tileEntity.getEnergyStoredGC()) + " / " + (int) Math.floor(this.tileEntity.getMaxEnergyStoredGC())));
		this.electricInfoRegion.tooltipStrings = electricityDesc;

		if (this.tileEntity.getEnergyStoredGC() > 0)
		{
			this.drawTexturedModalRect(this.containerWidth + 49, this.containerHeight + 16, 208, 0, 11, 10);
		}

		this.drawTexturedModalRect(this.containerWidth + 63, this.containerHeight + 17, 176, 38, Math.min(this.tileEntity.getScaledElecticalLevel(54), 54), 7);
	}
}
