package com.ricky30.movingobject.task;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.extent.MutableBlockVolume;

import com.flowpowered.math.vector.Vector3i;
import com.ricky30.movingobject.movingobject;
import com.ricky30.movingobject.utility.size;

public class timer
{
	//the direction of the displacement
	static Map<String, String> Thedirection = new HashMap<String, String>();
	//the stat of an object - false mean not meet the end of displacement
	static Map<String, Boolean> Thestat = new HashMap<String, Boolean>();
	//current world of the object
	static Map<String, String> Theworld = new HashMap<String, String>();
	//the length to move
	static Map<String, Integer> Thelength = new HashMap<String, Integer>();
	//the step already done
	static Map<String, Integer> TheCurrentlength = new HashMap<String, Integer>();
	//the duration of a move
	static Map<String, Integer> Theduration = new HashMap<String, Integer>();
	//the time already passed
	static Map<String, Integer> TheCurrentduration = new HashMap<String, Integer>();
	//hide or not the blocks
	static Map<String, Boolean> Thehide = new HashMap<String, Boolean>();
	//first vector
	static Map<String, Vector3i> Thevector1 = new HashMap<String, Vector3i>();
	//second vector
	static Map<String, Vector3i> Thevector2 = new HashMap<String, Vector3i>();
	//is activated or not (like with a lever)
	static Map<String, Boolean> Theactivestat = new HashMap<String, Boolean>();
	//stored volume in case of hiding
	static Map<String, MutableBlockVolume> Thevolume = new HashMap<String, MutableBlockVolume>();
	
	
	public static void run() 
	{
		//if this is empty then there is nothing to do
		if (!Thedirection.isEmpty())
		{
			for (Entry<String, String> ObjectName: Thedirection.entrySet()) 
			{
				if (Theactivestat.get(ObjectName.getKey()).booleanValue())
				{
					//if false then we can move
					if (!Thestat.get(ObjectName.getKey()).booleanValue())
					{
						//timing is used here
						if (TheCurrentduration.get(ObjectName.getKey()).intValue() < Theduration.get(ObjectName.getKey()).intValue())
						{
							int currentDuration = TheCurrentduration.get(ObjectName.getKey()).intValue();
							TheCurrentduration.replace(ObjectName.getKey(), ++currentDuration);
						}
						else
						{
							//we reset it to 1;
							TheCurrentduration.replace(ObjectName.getKey(), 1);
							//we get the two vector of our object
							Vector3i A = Thevector1.get(ObjectName.getKey());
							Vector3i B = Thevector2.get(ObjectName.getKey());
							//we get the min and max out of them
							Vector3i Min = size.Min(A, B);
							Vector3i Max = size.Max(A, B);
							Vector3i HideMin = Min;
							Vector3i HideMax = Max;
							//min2 is the same as min but without 1 added
							Vector3i Min2 = Min;
							//we get the world where the object is
							World world = Sponge.getServer().getWorld(Theworld.get(ObjectName.getKey()).toString()).get();
							//we get the current displacement
							int Currentmove = TheCurrentlength.get(ObjectName.getKey());
							//not used yet
							Vector3i hiding1 = new Vector3i(0, 0, 0);
							Vector3i hiding2 = new Vector3i(0, 0, 0);
							//switch depending on direction
							//we add or substract one on x, y or z
							//then we add or substract the currentmove (0, 1, 2, ...)
							//the same goes for min2 except we only adding currentmove
							switch (Thedirection.get(ObjectName.getKey()).toString())
							{
								case "up":
									Min = new Vector3i(Min.getX(), Min.getY()+1, Min.getZ());
									Max = new Vector3i(Max.getX(), Max.getY()+1, Max.getZ());
									hiding2 = new Vector3i(0, Currentmove+1, 0);
									Min = new Vector3i(Min.getX(), Min.getY()+Currentmove, Min.getZ());
									Max = new Vector3i(Max.getX(), Max.getY()+Currentmove, Max.getZ());
									Min2 = new Vector3i(Min2.getX(), Min2.getY()+Currentmove, Min2.getZ());
									break;
								case "down":
									Min = new Vector3i(Min.getX(), Min.getY()-1, Min.getZ());
									Max = new Vector3i(Max.getX(), Max.getY()-1, Max.getZ());
									hiding1 = new Vector3i(0, Currentmove+1, 0);
									Min = new Vector3i(Min.getX(), Min.getY()-Currentmove, Min.getZ());
									Max = new Vector3i(Max.getX(), Max.getY()-Currentmove, Max.getZ());
									Min2 = new Vector3i(Min2.getX(), Min2.getY()-Currentmove, Min2.getZ());
									break;
								case "north":
									Min = new Vector3i(Min.getX(), Min.getY(), Min.getZ()-1);
									Max = new Vector3i(Max.getX(), Max.getY(), Max.getZ()-1);
									hiding1 = new Vector3i(0, 0, Currentmove+1);
									Min = new Vector3i(Min.getX(), Min.getY(), Min.getZ()-Currentmove);
									Max = new Vector3i(Max.getX(), Max.getY(), Max.getZ()-Currentmove);
									Min2 = new Vector3i(Min2.getX(), Min2.getY(), Min2.getZ()-Currentmove);
									break;
								case "south":
									Min = new Vector3i(Min.getX(), Min.getY(), Min.getZ()+1);
									Max = new Vector3i(Max.getX(), Max.getY(), Max.getZ()+1);
									hiding2 = new Vector3i(0, 0, Currentmove+1);
									Min = new Vector3i(Min.getX(), Min.getY(), Min.getZ()+Currentmove);
									Max = new Vector3i(Max.getX(), Max.getY(), Max.getZ()+Currentmove);
									Min2 = new Vector3i(Min2.getX(), Min2.getY(), Min2.getZ()+Currentmove);
									break;
								case "east":
									Min = new Vector3i(Min.getX()+1, Min.getY(), Min.getZ());
									Max = new Vector3i(Max.getX()+1, Max.getY(), Max.getZ());
									hiding2 = new Vector3i(Currentmove+1, 0, 0);
									Min = new Vector3i(Min.getX()+Currentmove, Min.getY(), Min.getZ());
									Max = new Vector3i(Max.getX()+Currentmove, Max.getY(), Max.getZ());
									Min2 = new Vector3i(Min2.getX()+Currentmove, Min2.getY(), Min2.getZ());
									break;
								case "west":
									Min = new Vector3i(Min.getX()-1, Min.getY(), Min.getZ());
									Max = new Vector3i(Max.getX()-1, Max.getY(), Max.getZ());
									hiding1 = new Vector3i(Currentmove+1, 0, 0);
									Min = new Vector3i(Min.getX()-Currentmove, Min.getY(), Min.getZ());
									Max = new Vector3i(Max.getX()-Currentmove, Max.getY(), Max.getZ());
									Min2 = new Vector3i(Min2.getX()-Currentmove, Min2.getY(), Min2.getZ());
									break;
							}
							//then we add 1 to currentmove
							Currentmove++;
							//and we update the displacement stat
							TheCurrentlength.replace(ObjectName.getKey(), Currentmove);
							//if we get at the end then next time we don't move
							if (Currentmove == Thelength.get(ObjectName.getKey()).intValue())
							{
								Thestat.replace(ObjectName.getKey(), false, true);
							}
							//this is to prevent misuse of redstone circuits
							if (Currentmove > Thelength.get(ObjectName.getKey()).intValue())
							{
								Currentmove = Thelength.get(ObjectName.getKey()).intValue();
								TheCurrentlength.replace(ObjectName.getKey(), Currentmove);
								break;
							}
							final Vector3i min = Thevolume.get(ObjectName.getKey()).getBlockMin();
							final Vector3i max = Thevolume.get(ObjectName.getKey()).getBlockMax();
							
							//here goes the hiding
							int hiding = 0; //not hiding
							if (Thehide.get(ObjectName.getKey()).booleanValue())
							{
								hiding++;
							}
							
							switch (hiding)
							{
								//not hiding
								case 0:
									//and we fill the current block space with air
									for (int x = min.getX(); x <= max.getX(); x++) 
									{
										for (int y = min.getY(); y <= max.getY(); y++) 
										{
											for (int z = min.getZ(); z <= max.getZ(); z++) 
											{
												world.setBlock(Min2.getX() + x, Min2.getY() + y, Min2.getZ() + z, BlockTypes.AIR.getDefaultState());
											}
										}
									}
									break;
								//hiding
								case 1:
									//and we fill the current block space with air
									for (int x = HideMin.getX(); x <= HideMax.getX(); x++) 
									{
										for (int y = HideMin.getY(); y <= HideMax.getY(); y++) 
										{
											for (int z = HideMin.getZ(); z <= HideMax.getZ(); z++) 
											{
												world.setBlock(x, y, z, BlockTypes.AIR.getDefaultState());
											}
										}
									}
									break;
							}
							
							//finally, we put the blocks inside the buffer on the new location
							switch (hiding)
							{
								//not hiding
								case 0:
									for (int x = min.getX(); x <= max.getX(); x++) 
									{
										for (int y = min.getY(); y <= max.getY(); y++) 
										{
											for (int z = min.getZ(); z <= max.getZ(); z++) 
											{
												BlockState block = Thevolume.get(ObjectName.getKey()).getBlock(x, y, z);
												world.setBlock(Min.getX() + x, Min.getY() + y, Min.getZ() + z, block);
											}
										}
									}
									break;
								//hiding
								case 1:
									for (int x = min.getX() + hiding1.getX(); x <= max.getX() - hiding2.getX(); x++) 
									{
										for (int y = min.getY() + hiding1.getY(); y <= max.getY() - hiding2.getY(); y++) 
										{
											for (int z = min.getZ() + hiding1.getZ(); z <= max.getZ() - hiding2.getZ(); z++) 
											{
												BlockState block = Thevolume.get(ObjectName.getKey()).getBlock(x, y, z);
												world.setBlock(Min.getX() + x, Min.getY() + y, Min.getZ() + z, block);
											}
										}
									}
									break;
							}
						}
					}
				}
				else
				{
					//if true then we can move
					if (Thestat.get(ObjectName.getKey()).booleanValue())
					{
						if (TheCurrentduration.get(ObjectName.getKey()).intValue() < Theduration.get(ObjectName.getKey()).intValue())
						{
							int currentDuration = TheCurrentduration.get(ObjectName.getKey()).intValue();
							TheCurrentduration.replace(ObjectName.getKey(), ++currentDuration);
						}
						else
						{
							TheCurrentduration.replace(ObjectName.getKey(), 1);
							Vector3i A = Thevector1.get(ObjectName.getKey());
							Vector3i B = Thevector2.get(ObjectName.getKey());
							Vector3i Min = size.Min(A, B);
							Vector3i Max = size.Max(A, B);
							Vector3i HideMin = Min;
							Vector3i HideMax = Max;
							Vector3i Min2 = Min;
							World world = Sponge.getServer().getWorld(Theworld.get(ObjectName.getKey()).toString()).get();
							int Currentmove = TheCurrentlength.get(ObjectName.getKey());
							Vector3i hiding1 = new Vector3i(0, 0, 0);
							Vector3i hiding2 = new Vector3i(0, 0, 0);
							switch (Thedirection.get(ObjectName.getKey()).toString())
							{
								case "up":
									Min = new Vector3i(Min.getX(), Min.getY()-1, Min.getZ());
									Max = new Vector3i(Max.getX(), Max.getY()-1, Max.getZ());
									hiding2 = new Vector3i(0, Currentmove-1, 0);
									Min = new Vector3i(Min.getX(), Min.getY()+Currentmove, Min.getZ());
									Max = new Vector3i(Max.getX(), Max.getY()+Currentmove, Max.getZ());
									Min2 = new Vector3i(Min2.getX(), Min2.getY()+Currentmove, Min2.getZ());
									break;
								case "down":
									Min = new Vector3i(Min.getX(), Min.getY()+1, Min.getZ());
									Max = new Vector3i(Max.getX(), Max.getY()+1, Max.getZ());
									hiding1 = new Vector3i(0, Currentmove-1, 0);
									Min = new Vector3i(Min.getX(), Min.getY()-Currentmove, Min.getZ());
									Max = new Vector3i(Max.getX(), Max.getY()-Currentmove, Max.getZ());
									Min2 = new Vector3i(Min2.getX(), Min2.getY()-Currentmove, Min2.getZ());
									break;
								case "north":
									Min = new Vector3i(Min.getX(), Min.getY(), Min.getZ()+1);
									Max = new Vector3i(Max.getX(), Max.getY(), Max.getZ()+1);
									hiding1 = new Vector3i(0, 0, Currentmove-1);
									Min = new Vector3i(Min.getX(), Min.getY(), Min.getZ()-Currentmove);
									Max = new Vector3i(Max.getX(), Max.getY(), Max.getZ()-Currentmove);
									Min2 = new Vector3i(Min2.getX(), Min2.getY(), Min2.getZ()-Currentmove);
									break;
								case "south":
									Min = new Vector3i(Min.getX(), Min.getY(), Min.getZ()-1);
									Max = new Vector3i(Max.getX(), Max.getY(), Max.getZ()-1);
									hiding2 = new Vector3i(0, 0, Currentmove-1);
									Min = new Vector3i(Min.getX(), Min.getY(), Min.getZ()+Currentmove);
									Max = new Vector3i(Max.getX(), Max.getY(), Max.getZ()+Currentmove);
									Min2 = new Vector3i(Min2.getX(), Min2.getY(), Min2.getZ()+Currentmove);
									break;
								case "east":
									Min = new Vector3i(Min.getX()-1, Min.getY(), Min.getZ());
									Max = new Vector3i(Max.getX()-1, Max.getY(), Max.getZ());
									hiding2 = new Vector3i(Currentmove-1, 0, 0);
									Min = new Vector3i(Min.getX()+Currentmove, Min.getY(), Min.getZ());
									Max = new Vector3i(Max.getX()+Currentmove, Max.getY(), Max.getZ());
									Min2 = new Vector3i(Min2.getX()+Currentmove, Min2.getY(), Min2.getZ());
									break;
								case "west":
									Min = new Vector3i(Min.getX()+1, Min.getY(), Min.getZ());
									Max = new Vector3i(Max.getX()+1, Max.getY(), Max.getZ());
									hiding1 = new Vector3i(Currentmove-1, 0, 0);
									Min = new Vector3i(Min.getX()-Currentmove, Min.getY(), Min.getZ());
									Max = new Vector3i(Max.getX()-Currentmove, Max.getY(), Max.getZ());
									Min2 = new Vector3i(Min2.getX()-Currentmove, Min2.getY(), Min2.getZ());
									break;
							}
							Currentmove--;
							TheCurrentlength.replace(ObjectName.getKey(), Currentmove);
							if (Currentmove == 0)
							{
								Thestat.replace(ObjectName.getKey(), true, false);
							}
							//this is to prevent misuse of redstone circuits
							if (Currentmove < 0)
							{
								Currentmove = 0;
								TheCurrentlength.replace(ObjectName.getKey(), Currentmove);
								remove(ObjectName.getKey());
								break;
							}
							final Vector3i min = Thevolume.get(ObjectName.getKey()).getBlockMin();
							final Vector3i max = Thevolume.get(ObjectName.getKey()).getBlockMax();
							int hiding = 0;
							if (Thehide.get(ObjectName.getKey()).booleanValue())
							{
								hiding++;
							}
							
							switch (hiding)
							{
								//not hiding
								case 0:
									//and we fill the current block space with air
									for (int x = min.getX(); x <= max.getX(); x++) 
									{
										for (int y = min.getY(); y <= max.getY(); y++) 
										{
											for (int z = min.getZ(); z <= max.getZ(); z++) 
											{
												world.setBlock(Min2.getX() + x, Min2.getY() + y, Min2.getZ() + z, BlockTypes.AIR.getDefaultState());
											}
										}
									}
									break;
								//hiding
								case 1:
									//and we fill the current block space with air
									for (int x = HideMin.getX(); x <= HideMax.getX(); x++) 
									{
										for (int y = HideMin.getY(); y <= HideMax.getY(); y++) 
										{
											for (int z = HideMin.getZ(); z <= HideMax.getZ(); z++) 
											{
												world.setBlock(x, y, z, BlockTypes.AIR.getDefaultState());
											}
										}
									}
									break;
							}

							switch (hiding)
							{
								//not hiding
								case 0:
									for (int x = min.getX(); x <= max.getX(); x++) 
									{
										for (int y = min.getY(); y <= max.getY(); y++) 
										{
											for (int z = min.getZ(); z <= max.getZ(); z++) 
											{
												BlockState block = Thevolume.get(ObjectName.getKey()).getBlock(x, y, z);
												world.setBlock(Min.getX() + x, Min.getY() + y, Min.getZ() + z, block);
											}
										}
									}
									break;
								//hiding
								case 1:
									for (int x = min.getX() + hiding1.getX(); x <= max.getX() - hiding2.getX(); x++) 
									{
										for (int y = min.getY() + hiding1.getY(); y <= max.getY() - hiding2.getY(); y++) 
										{
											for (int z = min.getZ() + hiding1.getZ(); z <= max.getZ() - hiding2.getZ(); z++) 
											{
												BlockState block = Thevolume.get(ObjectName.getKey()).getBlock(x, y, z);
												world.setBlock(Min.getX() + x, Min.getY() + y, Min.getZ() + z, block);
											}
										}
									}
									break;
							}
						}
					}
					else
					{
						//remove at end
						remove(ObjectName.getKey());
					}
				}
			}
		}
	}
	
	//start animation (lever push) used also to init it
	public static void start(String Name, int Duration, int Length, String Direction, String World, boolean Hide, Vector3i First, Vector3i Second)
	{
		//first we store all values provided
		if (Thedirection.get(Name) == null)
		{
			Theduration.put(Name, Duration);
			TheCurrentduration.put(Name, 1);
			Thelength.put(Name, Length);
			TheCurrentlength.put(Name, 0);
			Thedirection.put(Name, Direction);
			Theworld.put(Name, World);
			Thehide.put(Name, Hide);
			Thevector1.put(Name, First);
			Thevector2.put(Name, Second);
			Thestat.put(Name, false);
			Theactivestat.put(Name, true);
			//here we copy the blocks from the world to the backup volume
			MutableBlockVolume volume = movingobject.EXTENT_BUFFER_FACTORY.createBlockBuffer(size.length(First, Second));
			World world = Sponge.getServer().getWorld(World).get();
			final Vector3i min = volume.getBlockMin();
			final Vector3i max = volume.getBlockMax();
			final Vector3i Min2 = size.Min(First, Second);
			//we copy blocks inside the volume
			for (int x = min.getX(); x <= max.getX(); x++) 
			{
				for (int y = min.getY(); y <= max.getY(); y++) 
				{
					for (int z = min.getZ(); z <= max.getZ(); z++) 
					{
						volume.setBlock(x, y, z, world.getBlock(x+Min2.getX(), y+Min2.getY(), z+Min2.getZ()));
					}
				}
			}
			Thevolume.put(Name, volume);
		}
		else //do a switch
		{
			//but if this is the second time then we change moving direction
			if (Theactivestat.get(Name).booleanValue())
			{
				stop(Name);
			}
			else
			{
				Theactivestat.replace(Name, true);
				//if we don't do this we got an unexpected behavior
				Thestat.replace(Name, false);
			}
		}
	}
	
	//stop animation (lever pull)
	public static void stop(String Name)
	{
		Theactivestat.replace(Name, false);
		//if we don't do this we got an unexpected behavior
		Thestat.replace(Name, true);
	}
	
	//when the object return to the first position, we delete it
	private static void remove(String Name)
	{
		if (Thedirection.get(Name) != null)
		{
			Theduration.remove(Name);
			Thedirection.remove(Name);
			TheCurrentduration.remove(Name);
			TheCurrentlength.remove(Name);
			Thestat.remove(Name);
			Theworld.remove(Name);
			Thelength.remove(Name);
			Thevector1.remove(Name);
			Thevector2.remove(Name);
			Thehide.remove(Name);
			Theactivestat.remove(Name);
			Thevolume.remove(Name);
		}
	}
}
