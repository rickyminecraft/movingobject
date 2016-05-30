package com.ricky30.movingobject.task;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.effect.sound.SoundTypes;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.extent.MutableBlockVolume;

import com.flowpowered.math.vector.Vector3d;
import com.flowpowered.math.vector.Vector3i;
import com.ricky30.movingobject.movingobject;
import com.ricky30.movingobject.utility.size;

public class timer
{
	//the direction of the displacement
	static Map<String, String> Thedirection = new ConcurrentHashMap<String, String>();
	//the stat of an object - false mean not meet the end of displacement
	static Map<String, Boolean> Thestat = new HashMap<String, Boolean>();
	//current world of the object
	static Map<String, String> Theworld = new HashMap<String, String>();
	//the sound played while moving
	static Map<String, String> Thesound = new HashMap<String, String>();
	//the length to move
	static Map<String, Integer> Thelength = new HashMap<String, Integer>();
	//the step already done
	static Map<String, Integer> Thecurrentposition = new HashMap<String, Integer>();
	//the duration of a move
	static Map<String, Integer> Theduration = new HashMap<String, Integer>();
	//the time already passed
	static Map<String, Integer> Thecurrentduration = new HashMap<String, Integer>();
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
			final Set<Entry<String, String>> DirectionCopy =  Thedirection.entrySet();
			for (final Entry<String, String> ObjectName: DirectionCopy) 
			{
				final String Name = ObjectName.getKey();
				if (Theactivestat.get(Name).booleanValue())
				{
					//if false then we can move
					if (!Thestat.get(Name).booleanValue())
					{
						//timing is used here
						if (Thecurrentduration.get(Name).intValue() < Theduration.get(Name).intValue())
						{
							int currentDuration = Thecurrentduration.get(Name).intValue();
							Thecurrentduration.replace(Name, ++currentDuration);
						}
						else
						{
							//we reset it to 1;
							Thecurrentduration.replace(Name, 1);
							//we get the two vector of our object
							final Vector3i A = Thevector1.get(Name);
							final Vector3i B = Thevector2.get(Name);
							//we get the min and max out of them
							Vector3i Min = size.Min(A, B);
							Vector3i Max = size.Max(A, B);
							final Vector3i HideMin = Min;
							final Vector3i HideMax = Max;
							//min2 is the same as min but without 1 added
							Vector3i Min2 = Min;
							Vector3i hiding1 = new Vector3i(0, 0, 0);
							Vector3i hiding2 = new Vector3i(0, 0, 0);
							//avoid potential error
							if (!Thevolume.containsKey(Name))
							{
								remove(Name);
								return;
							}
							final Vector3i min = Thevolume.get(Name).getBlockMin();
							final Vector3i max = Thevolume.get(Name).getBlockMax();
							//we get the world where the object is
							final World world = Sponge.getServer().getWorld(Theworld.get(Name).toString()).get();
							//we get the current displacement
							int Currentmove = Thecurrentposition.get(Name);
							//switch depending on direction
							//we add or substract one on x, y or z
							//then we add or substract the currentmove (0, 1, 2, ...)
							//the same goes for min2 except we only adding currentmove
							switch (Thedirection.get(Name).toString())
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
							movingobject.plugin.Updatestats(Currentmove, "on", Name);
							//and we update the displacement stat
							Thecurrentposition.replace(Name, Currentmove);
							//if we get at the end then next time we don't move
							if (Currentmove == Thelength.get(Name).intValue())
							{
								Thestat.replace(Name, false, true);
							}
							//this is to prevent misuse of redstone circuits
							if (Currentmove > Thelength.get(Name).intValue())
							{
								Thecurrentposition.replace(Name, Thelength.get(Name).intValue());
								break;
							}
							//here goes the hiding
							int hiding = 0; //not hiding
							if (Thehide.get(Name).booleanValue())
							{
								hiding++;
							}
							final BlockState state_AIR = BlockTypes.AIR.getDefaultState();
							switch (hiding)
							{
								//not hiding
								case 0:
									//we must fill only the needed blocks not more not less
									switch (Thedirection.get(Name).toString())
									{
										case "up"://+y
											for (int x = min.getX(); x <= max.getX(); x++) 
											{
												for (int y = min.getY(); y <= min.getY(); y++) 
												{
													for (int z = min.getZ(); z <= max.getZ(); z++) 
													{
														world.setBlock(Min2.getX() + x, Min2.getY() + y, Min2.getZ() + z, state_AIR);
													}
												}
											}
											break;
										case "down"://-y
											for (int x = min.getX(); x <= max.getX(); x++) 
											{
												for (int y = max.getY(); y <= max.getY(); y++) 
												{
													for (int z = min.getZ(); z <= max.getZ(); z++) 
													{
														world.setBlock(Min2.getX() + x, Min2.getY() + y, Min2.getZ() + z, state_AIR);
													}
												}
											}
											break;
										case "north"://-z
											for (int x = min.getX(); x <= max.getX(); x++) 
											{
												for (int y = min.getY(); y <= max.getY(); y++) 
												{
													for (int z = max.getZ(); z <= max.getZ(); z++) 
													{
														world.setBlock(Min2.getX() + x, Min2.getY() + y, Min2.getZ() + z, state_AIR);
													}
												}
											}
											break;
										case "south"://+z
											for (int x = min.getX(); x <= max.getX(); x++) 
											{
												for (int y = min.getY(); y <= max.getY(); y++) 
												{
													for (int z = min.getZ(); z <= min.getZ(); z++) 
													{
														world.setBlock(Min2.getX() + x, Min2.getY() + y, Min2.getZ() + z, state_AIR);
													}
												}
											}
											break;
										case "east"://+x
											for (int x = min.getX(); x <= min.getX(); x++) 
											{
												for (int y = min.getY(); y <= max.getY(); y++) 
												{
													for (int z = min.getZ(); z <= max.getZ(); z++) 
													{
														world.setBlock(Min2.getX() + x, Min2.getY() + y, Min2.getZ() + z, state_AIR);
													}
												}
											}
											break;
										case "west"://-x
											for (int x = max.getX(); x <= max.getX(); x++) 
											{
												for (int y = min.getY(); y <= max.getY(); y++) 
												{
													for (int z = min.getZ(); z <= max.getZ(); z++) 
													{
														world.setBlock(Min2.getX() + x, Min2.getY() + y, Min2.getZ() + z, state_AIR);
													}
												}
											}
											break;
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
												world.setBlock(x, y, z, state_AIR);
											}
										}
									}
									break;
							}

							//finally, we put the blocks inside the buffer on the new location
							moveBlocks (hiding, min, max, Min, hiding1, hiding2, world, Name);
						}
					}
				}
				else
				{
					//if true then we can move
					if (Thestat.get(Name).booleanValue())
					{
						if (Thecurrentduration.get(Name).intValue() < Theduration.get(Name).intValue())
						{
							int currentDuration = Thecurrentduration.get(Name).intValue();
							Thecurrentduration.replace(Name, ++currentDuration);
						}
						else
						{
							Thecurrentduration.replace(Name, 1);
							final Vector3i A = Thevector1.get(Name);
							final Vector3i B = Thevector2.get(Name);
							Vector3i Min = size.Min(A, B);
							Vector3i Max = size.Max(A, B);
							final Vector3i HideMin = Min;
							final Vector3i HideMax = Max;
							Vector3i Min2 = Min;
							Vector3i hiding1 = new Vector3i(0, 0, 0);
							Vector3i hiding2 = new Vector3i(0, 0, 0);
							//avoid potential error
							if (!Thevolume.containsKey(Name))
							{
								remove(Name);
								return;
							}
							final Vector3i min = Thevolume.get(Name).getBlockMin();
							final Vector3i max = Thevolume.get(Name).getBlockMax();
							final World world = Sponge.getServer().getWorld(Theworld.get(Name).toString()).get();
							int Currentmove = Thecurrentposition.get(Name);
							switch (Thedirection.get(Name).toString())
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
							movingobject.plugin.Updatestats(Currentmove, "off", Name);
							Thecurrentposition.replace(Name, Currentmove);
							if (Currentmove == 0)
							{
								Thestat.replace(Name, true, false);
							}
							//this is to prevent misuse of redstone circuits
							if (Currentmove < 0)
							{
								Thecurrentposition.replace(Name, 0);
								remove(Name);
								break;
							}
							int hiding = 0;
							if (Thehide.get(Name).booleanValue())
							{
								hiding++;
							}
							final BlockState state_AIR = BlockTypes.AIR.getDefaultState();
							switch (hiding)
							{
								//not hiding
								case 0:
									//we must fill only the needed blocks not more not less
									switch (Thedirection.get(Name).toString())
									{
										case "up"://-y
											for (int x = min.getX(); x <= max.getX(); x++) 
											{
												for (int y = max.getY(); y <= max.getY(); y++) 
												{
													for (int z = min.getZ(); z <= max.getZ(); z++) 
													{
														world.setBlock(Min2.getX() + x, Min2.getY() + y, Min2.getZ() + z, state_AIR);
													}
												}
											}
											break;
										case "down"://+y
											for (int x = min.getX(); x <= max.getX(); x++) 
											{
												for (int y = min.getY(); y <= min.getY(); y++) 
												{
													for (int z = min.getZ(); z <= max.getZ(); z++) 
													{
														world.setBlock(Min2.getX() + x, Min2.getY() + y, Min2.getZ() + z, state_AIR);
													}
												}
											}
											break;
										case "north"://+z
											for (int x = min.getX(); x <= max.getX(); x++) 
											{
												for (int y = min.getY(); y <= max.getY(); y++) 
												{
													for (int z = min.getZ(); z <= max.getZ(); z++) 
													{
														world.setBlock(Min2.getX() + x, Min2.getY() + y, Min2.getZ() + z, state_AIR);
													}
												}
											}
											break;
										case "south"://-z
											for (int x = min.getX(); x <= max.getX(); x++) 
											{
												for (int y = min.getY(); y <= max.getY(); y++) 
												{
													for (int z = min.getZ(); z <= max.getZ(); z++) 
													{
														world.setBlock(Min2.getX() + x, Min2.getY() + y, Min2.getZ() + z, state_AIR);
													}
												}
											}
											break;
										case "east"://-x
											for (int x = min.getX(); x <= max.getX(); x++) 
											{
												for (int y = min.getY(); y <= max.getY(); y++) 
												{
													for (int z = min.getZ(); z <= max.getZ(); z++) 
													{
														world.setBlock(Min2.getX() + x, Min2.getY() + y, Min2.getZ() + z, state_AIR);
													}
												}
											}
											break;
										case "west"://+x
											for (int x = min.getX(); x <= max.getX(); x++) 
											{
												for (int y = min.getY(); y <= max.getY(); y++) 
												{
													for (int z = min.getZ(); z <= max.getZ(); z++) 
													{
														world.setBlock(Min2.getX() + x, Min2.getY() + y, Min2.getZ() + z, state_AIR);
													}
												}
											}
											break;
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
												world.setBlock(x, y, z, state_AIR);
											}
										}
									}
									break;
							}
							moveBlocks (hiding, min, max, Min, hiding1, hiding2, world, Name);
						}
					}
					else
					{
						//remove at end
						remove(Name);
					}
				}
			}
		}
	}

	//////
	//
	// copy volume content to new position
	// args:
	// 1: object move and disappear or object move
	// 2: vector minimum in the volume
	// 3: vector maximum in the volume
	// 4: vector minimum in the world
	// 5: hiding pos 1
	// 6: hiding pos 2
	// 7: the world where the blocks go
	// 8: the name of the Mo
	//
	//////
	private static void moveBlocks(int hiding, Vector3i min, Vector3i max, Vector3i Min, Vector3i hiding1, Vector3i hiding2, World world, String Name)
	{
		switch (hiding)
		{
			//not hiding
			case 0:
				//next location in the world of our blocks inside a volume
				for (int x = min.getX(); x <= max.getX(); x++) 
				{
					for (int y = min.getY(); y <= max.getY(); y++) 
					{
						for (int z = min.getZ(); z <= max.getZ(); z++) 
						{
							final BlockState block = Thevolume.get(Name).getBlock(x, y, z);
							world.setBlock(Min.getX() + x, Min.getY() + y, Min.getZ() + z, block, false);
							playSound(Name, world, Min, x, y, z);
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
							final BlockState block = Thevolume.get(Name).getBlock(x, y, z);
							world.setBlock(Min.getX() + x, Min.getY() + y, Min.getZ() + z, block, false);
							playSound(Name, world, Min, x, y, z);
						}
					}
				}
				break;
		}
	}

	//start animation, used also to init it
	public static void start(String Name, int Duration, int Length, String Direction, int Currentposition, String Currentstat, String World, boolean Hide, Vector3i First, Vector3i Second, String Sound)
	{
		//first we store all values provided
		if (Thedirection.get(Name) == null)
		{
			Theduration.put(Name, Duration);
			Thecurrentduration.put(Name, 1);
			Thelength.put(Name, Length);
			Thedirection.put(Name, Direction);
			Theworld.put(Name, World);
			Thehide.put(Name, Hide);
			Thevector1.put(Name, First);
			Thevector2.put(Name, Second);
			Thesound.put(Name, Sound);
			if (Currentstat.equals("inactive"))
			{
				Thecurrentposition.put(Name, Currentposition);
				Theactivestat.put(Name, true);
				Thestat.put(Name, false);
			}
			else if (Currentstat.equals("on"))
			{
				Thecurrentposition.put(Name, Currentposition);
				Theactivestat.put(Name, false);
				Thestat.put(Name, true);

			}
			else if (Currentstat.equals("off"))
			{
				Thecurrentposition.put(Name, Currentposition);
				Theactivestat.put(Name, true);
				Thestat.put(Name, false);
			}
			Vector3i pos = new Vector3i(0, 0, 0);
			switch (Thedirection.get(Name))
			{
				case "up":
					pos = new Vector3i(0, pos.getY()+Currentposition, 0);
					break;
				case "down":
					pos = new Vector3i(0, pos.getY()-Currentposition, 0);
					break;
				case "north":
					pos = new Vector3i(0, 0, pos.getZ()-Currentposition);
					break;
				case "south":
					pos = new Vector3i(0, 0, pos.getZ()+Currentposition);
					break;
				case "east":
					pos = new Vector3i(pos.getX()+Currentposition, 0, 0);
					break;
				case "west":
					pos = new Vector3i(pos.getX()-Currentposition, 0, 0);
					break;
			}
			//here we copy the blocks from the world to the backup volume
			MutableBlockVolume volume = movingobject.EXTENT_BUFFER_FACTORY.createBlockBuffer(size.length(First, Second));
			final World world = Sponge.getServer().getWorld(World).get();
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
						volume.setBlock(x, y, z, world.getBlock(x+Min2.getX()+pos.getX(), y+Min2.getY()+pos.getY(), z+Min2.getZ()+pos.getZ()));
					}
				}
			}
			if (Currentstat.equals("inactive") & Hide)
			{
				movingobject.plugin.Storevolume(volume, Name);
			}
			if (Currentstat.equals("on") & Hide)
			{
				volume = movingobject.plugin.Getvolume(Name);
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
		movingobject.plugin.Updatestats(0, "inactive", Name);
		if (Thedirection.get(Name) != null)
		{
			Theduration.remove(Name);
			Thedirection.remove(Name);
			Thecurrentduration.remove(Name);
			Thecurrentposition.remove(Name);
			Thestat.remove(Name);
			Theworld.remove(Name);
			Thelength.remove(Name);
			Thevector1.remove(Name);
			Thevector2.remove(Name);
			Thehide.remove(Name);
			Theactivestat.remove(Name);
			Thevolume.remove(Name);
			Thesound.remove(Name);
		}
	}

	private static void playSound(String Name, World world, Vector3i min, int x, int y, int z)
	{
		switch (Thesound.get(Name))
		{
			case "grass":
				world.playSound(SoundTypes.STEP_GRASS, new Vector3d(min.getX() + x, min.getY() + y, min.getZ() + z), 1);
				break;
			case "gravel":
				world.playSound(SoundTypes.STEP_GRAVEL, new Vector3d(min.getX() + x, min.getY() + y, min.getZ() + z), 1);
				break;
			case "ladder":
				world.playSound(SoundTypes.STEP_LADDER, new Vector3d(min.getX() + x, min.getY() + y, min.getZ() + z), 1);
				break;
			case "sand":
				world.playSound(SoundTypes.STEP_SAND, new Vector3d(min.getX() + x, min.getY() + y, min.getZ() + z), 1);
				break;
			case "snow":
				world.playSound(SoundTypes.STEP_SNOW, new Vector3d(min.getX() + x, min.getY() + y, min.getZ() + z), 1);
				break;
			case "stone":
				world.playSound(SoundTypes.STEP_STONE, new Vector3d(min.getX() + x, min.getY() + y, min.getZ() + z), 1);
				break;
			case "wood":
				world.playSound(SoundTypes.STEP_WOOD, new Vector3d(min.getX() + x, min.getY() + y, min.getZ() + z), 1);
				break;
			case "wool":
				world.playSound(SoundTypes.STEP_WOOL, new Vector3d(min.getX() + x, min.getY() + y, min.getZ() + z), 1);
				break;
			default:
		}
	}

}
