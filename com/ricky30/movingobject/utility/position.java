package com.ricky30.movingobject.utility;

import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3i;
import com.ricky30.movingobject.movingobject;

import ninja.leaping.configurate.ConfigurationNode;

public class position
{
	private static ConfigurationNode config = null;

	public static boolean isInsideMO(Vector3i Pos, World world2)
	{
		config = movingobject.plugin.getConfig();
		boolean isInside = false;
		final int PosX = Pos.getX();
		final int PosY = Pos.getY();
		final int PosZ = Pos.getZ();
		for (final Object text: config.getNode("objectName").getChildrenMap().keySet())
		{
			//get the size of the object
			int X1, X2, Y1, Y2, Z1, Z2;
			X1 = config.getNode("objectName", text.toString(), "depart_X").getInt();
			Y1 = config.getNode("objectName", text.toString(), "depart_Y").getInt();
			Z1 = config.getNode("objectName", text.toString(), "depart_Z").getInt();
			X2 = config.getNode("objectName", text.toString(), "fin_X").getInt();
			Y2 = config.getNode("objectName", text.toString(), "fin_Y").getInt();
			Z2 = config.getNode("objectName", text.toString(), "fin_Z").getInt();

			//converted to vector
			final Vector3i first = new Vector3i(X1, Y1, Z1);
			final Vector3i second = new Vector3i(X2, Y2, Z2);

			//get the world of this object
			final String World = config.getNode("objectName", text.toString(), "world").getString();

			boolean InsideX = false;
			boolean InsideY = false;
			boolean InsideZ = false;

			//before anything if we are not inside the good world, skip
			if (World.equals(world2.getName()))
			{
				//we check if the three coordinates are inside this object
				//if this is true then we are inside else not
				if (first.getX() < second.getX())
				{
					if ((PosX >= first.getX()) && (PosX <= second.getX()))
					{
						InsideX = true;
					}
				}
				else
				{
					if ((PosX <= first.getX()) && (PosX >= second.getX()))
					{
						InsideX = true;
					}
				}
				if (first.getY() < second.getY())
				{
					if ((PosY >= first.getY()) && (PosY <= second.getY()))
					{
						InsideY = true;
					}
				}
				else
				{
					if ((PosY <= first.getY()) && (PosY >= second.getY()))
					{
						InsideY = true;
					}
				}
				if (first.getZ() < second.getZ())
				{
					if ((PosZ >= first.getZ()) && (PosZ <= second.getZ()))
					{
						InsideZ = true;
					}
				}
				else
				{
					if ((PosZ <= first.getZ()) && (PosZ >= second.getZ()))
					{
						InsideZ = true;
					}
				}

				if (InsideX && InsideY && InsideZ)
				{
					isInside = true;
				}
			}
		}
		return isInside;
	}
}
