package micdoodle8.mods.galacticraft.core.tile;

import java.util.LinkedList;

import micdoodle8.mods.galacticraft.api.power.ILaserNode;
import micdoodle8.mods.galacticraft.api.vector.BlockVec3;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public abstract class TileEntityBeamOutput extends TileEntityAdvanced implements ILaserNode
{
	public LinkedList<ILaserNode> nodeList = new LinkedList<ILaserNode>();
	public ILaserNode target;
	public float pitch;
	public float yaw;

	@Override
	public void updateEntity()
	{
		super.updateEntity();
		
		if (this.target == null)
		{
			this.initiateReflector();
		}
		
		if (this.worldObj.isRemote)
		{
			this.updateOrientation();
		}
	}
	
	@Override
	public void invalidate()
	{
		super.invalidate();
		this.invalidateReflector();
	}
	
	@Override
	public void validate()
	{
		super.validate();
	}

	@Override
    public void onChunkUnload()
    {
		this.invalidateReflector();
    }
	
	public void invalidateReflector()
	{
		for (ILaserNode node : nodeList)
		{
			node.removeNode(this);
		}
		
		nodeList.clear();
	}
	
	public void initiateReflector()
	{
		this.nodeList.clear();
		
		int chunkXMin = (this.xCoord - 15) >> 4;
		int chunkZMin = (this.zCoord - 15) >> 4;
		int chunkXMax = (this.xCoord + 15) >> 4;
		int chunkZMax = (this.zCoord + 15) >> 4;
		
		for (int cX = chunkXMin; cX <= chunkXMax; cX++)
		{
			for (int cZ = chunkZMin; cZ <= chunkZMax; cZ++)
			{
				if (worldObj.getChunkProvider().chunkExists(cX, cZ))
				{
					Chunk chunk = worldObj.getChunkFromChunkCoords(cX, cZ);
					
					for (Object obj : chunk.chunkTileEntityMap.values())
					{
						if (obj != this && obj instanceof ILaserNode)
						{
							BlockVec3 deltaPos = new BlockVec3(this).subtract(new BlockVec3(((ILaserNode) obj).getTile()));
							
							if (deltaPos.x < 16 && deltaPos.y < 16 && deltaPos.z < 16)
							{
								ILaserNode laserNode = (ILaserNode) obj;
								
								if (this.canConnectTo(laserNode) && laserNode.canConnectTo(this))
								{
									this.addNode(laserNode);
									laserNode.addNode(this);
								}
							}
						}
					}
				}
			}
		}
		
		this.target = this.nodeList.peekFirst();
	}

	@Override
	public void addNode(ILaserNode node) 
	{
		int index = -1;
		
		for (int i = 0; i < nodeList.size(); i++)
		{
			if (new BlockVec3(this.nodeList.get(i).getTile()).equals(new BlockVec3(node.getTile())))
			{
				index = i;
				break;
			}
		}
		
		if (index != -1)
		{
			this.nodeList.set(index, node);
			return;
		}
		
		if (nodeList.isEmpty())
		{
			nodeList.add(node);
		} 
		else 
		{
			int nodeCompare = nodeList.get(0).compareTo(node, new BlockVec3(this));
			
			if (nodeCompare <= 0)
			{
				nodeList.addFirst(node);
				return;
			}
			
			nodeCompare = nodeList.get(nodeList.size() - 1).compareTo(node, new BlockVec3(this));
			
			if (nodeCompare >= 0)
			{
				nodeList.addLast(node);
				return;
			}
			
			index = 1;
			nodeCompare = 0;
			
			while (index < this.nodeList.size() && (nodeCompare = nodeList.get(index).compareTo(node, new BlockVec3(this))) > 0)
			{
				index++;
			}
			
			nodeList.add(index, node);
        }
	}

	@Override
	public void removeNode(ILaserNode node) 
	{
		int index = -1;
		
		for (int i = 0; i < nodeList.size(); i++)
		{
			if (new BlockVec3(this.nodeList.get(i).getTile()).equals(new BlockVec3(node.getTile())))
			{
				index = i;
				break;
			}
		}
		
		if (node == this.target)
		{
			if (index == 0)
			{
				if (this.nodeList.size() > 1)
				{
					this.target = this.nodeList.get(index + 1);
				}
				else
				{
					this.target = null;
				}
			}
			else
			{
				this.target = this.nodeList.get(index - 1);
			}
		}
		
		if (index != -1)
		{
			this.nodeList.remove(index);
		}
	}

	public void updateOrientation()
	{
		if (this.target != null)
		{
			Vector3 direction = Vector3.subtract(this.getOutputPoint(false), this.target.getInputPoint()).normalize();
			this.pitch = (float) -Vector3.getAngle(new Vector3(-direction.x, -direction.y, -direction.z), new Vector3(0, 1, 0)) * (float)(180.0F / Math.PI) + 90;
			this.yaw = (float) -(Math.atan2(direction.z, direction.x) * (float)(180.0F / Math.PI)) + 90;
		}
	}

	@Override
	public TileEntity getTile()
	{
		return this;
	}

	@Override
	public int compareTo(ILaserNode otherNode, BlockVec3 origin) 
	{
		int thisDistance = new BlockVec3(this).subtract(origin).getMagnitudeSquared();
		int otherDistance = new BlockVec3(otherNode.getTile()).subtract(origin).getMagnitudeSquared();
		
		if (thisDistance < otherDistance)
		{
			return 1;
		}
		else if (thisDistance > otherDistance)
		{
			return -1;
		}		
		
		return 0;
	}

	public boolean onMachineActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int side, float hitX, float hitY, float hitZ)
	{
		if (this.nodeList.size() > 1)
		{
			int index = -1;
			
			for (int i = 0; i < nodeList.size(); i++)
			{
				if (new BlockVec3(this.nodeList.get(i).getTile()).equals(new BlockVec3(this.target.getTile())))
				{
					index = i;
					break;
				}
			}
			
			if (index == -1)
			{
				// This shouldn't happen, but just in case...
				this.initiateReflector();
			}
			else
			{
				index++;
				index %= this.nodeList.size();
				this.target = this.nodeList.get(index);
				return true;
			}
		}
		
		return false;
	}

	@Override
	public ILaserNode getTarget() 
	{
		return this.target;
	}
}
