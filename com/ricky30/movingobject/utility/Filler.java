package com.ricky30.movingobject.utility;

import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.world.extent.worker.procedure.BlockVolumeFiller;

public class Filler implements BlockVolumeFiller
{

	@Override
	public BlockState produce(int x, int y, int z)
	{
		BlockState state = BlockTypes.AIR.getDefaultState();
		return state;
	}

}
