package com.ricky30.movingobject.utility;

import com.flowpowered.math.vector.Vector3i;

public class size
{
	public static Vector3i length(Vector3i first, Vector3i second)
	{
		Vector3i currentsize = new Vector3i(0,0,0);

		//here we look for which is greater than
		int x1, y1, z1;
		if (first.getX() < second.getX())
		{
			x1 = second.getX() - first.getX();
		}
		else
		{
			x1 = first.getX() - second.getX();

		}
		if (first.getY() < second.getY())
		{
			y1 = second.getY() - first.getY();
		}
		else
		{
			y1 = first.getY() - second.getY();
		}
		if (first.getZ() < second.getZ())
		{
			z1 = second.getZ() - first.getZ();
		}
		else
		{
			z1 = first.getZ() - second.getZ();
		}
		//set the size of the mine
		Vector3i size = new Vector3i(x1, y1, z1);
		size = size.add(1, 1, 1);
		currentsize = size;
		return currentsize;
	}
	public static Vector3i Min(Vector3i first, Vector3i second)
	{
		int x1, y1, z1;
		if (first.getX() < second.getX())
		{
			x1 = first.getX();
		}
		else
		{
			x1 = second.getX();

		}
		if (first.getY() < second.getY())
		{
			y1 = first.getY();
		}
		else
		{
			y1 = second.getY();
		}
		if (first.getZ() < second.getZ())
		{
			z1 = first.getZ();
		}
		else
		{
			z1 = second.getZ();
		}
		Vector3i vector3i = new Vector3i(x1, y1, z1);
		return vector3i;
	}
	
	public static Vector3i Max(Vector3i first, Vector3i second)
	{
		int x1, y1, z1;
		if (first.getX() < second.getX())
		{
			x1 = second.getX();
		}
		else
		{
			x1 = first.getX();
		}
		if (first.getY() < second.getY())
		{
			y1 = second.getY();
		}
		else
		{
			y1 = first.getY();
		}
		if (first.getZ() < second.getZ())
		{
			z1 = second.getZ();
		}
		else
		{
			z1 = first.getZ();
		}
		Vector3i vector3i = new Vector3i(x1, y1, z1);
		return vector3i;
	}
}
