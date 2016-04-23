package com.ricky30.movingobject.event;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.text.Text;
import com.flowpowered.math.vector.Vector3i;
import com.ricky30.movingobject.movingobject;

public class selectionevent
{
	public static boolean isActive = false;
	private static boolean primaryUsed = false;
	private static boolean secondaryUsed = false;
	private static boolean triggerselect = false;
	private static boolean isready = false;
	private static Vector3i first;
	private static Vector3i second;
	private static Vector3i trigger;
	
	@Listener
	public void oninteractblockPrimary(ChangeBlockEvent.Break Event, @First Player player)
	{
		if (isActive)
		{
			if (triggerselect)
			{
				isActive = false;
			}
			if (player.getItemInHand().isPresent())
			{
				if (player.getItemInHand().get().getItem().getId().equals(movingobject.plugin.GetTool()))
				{
					Event.setCancelled(true);
				}
			}
		}
	}
	
	@Listener
	public void oninteractblockPrimary(InteractBlockEvent.Primary Event, @First Player player)
	{
		if (isActive)
		{
			if (player.getItemInHand().isPresent())
			{
				if (player.getItemInHand().get().getItem().getId().equals(movingobject.plugin.GetTool()))
				{
					if (!primaryUsed)
					{
						first = Event.getTargetBlock().getPosition();
						primaryUsed = true;
						player.getCommandSource().get().sendMessage(Text.of("First point defined"));
						
					}
					else if (!secondaryUsed)
					{
						second = Event.getTargetBlock().getPosition();
						secondaryUsed = true;
						player.getCommandSource().get().sendMessage(Text.of("Second point defined"));
						player.getCommandSource().get().sendMessage(Text.of("Now select the trigger block/item"));
					}
					else if (!triggerselect)
					{
						trigger = Event.getTargetBlock().getPosition();
						triggerselect = true;
						isready = true;
						player.getCommandSource().get().sendMessage(Text.of("ok now ready to save"));
					}
				}
			}
		}
	}
	
	public static Vector3i getFirst()
	{
		return first;
	}
	
	public static Vector3i getSecond()
	{
		return second;
	}
	
	public static Vector3i getTrigger()
	{
		return trigger;
	}
	
	public static boolean IsreadytoSave()
	{
		return isready;
	}
	
	public static void Reset()
	{
		primaryUsed = false;
		secondaryUsed = false;
		triggerselect = false;
		isready = false;
		isActive = true;
	}
}
