package com.ricky30.movingobject.utility;

import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.world.extent.UnmodifiableBlockVolume;
import org.spongepowered.api.world.extent.worker.procedure.BlockVolumeMerger;

public class Merger implements BlockVolumeMerger
{

	@Override
	public BlockState merge(UnmodifiableBlockVolume firstVolume, int xFirst,
			int yFirst, int zFirst, UnmodifiableBlockVolume secondVolume,
			int xSecond, int ySecond, int zSecond)
	{
		final BlockState state = secondVolume.getBlock(xSecond, ySecond, zSecond);
		return state;
	}

}
