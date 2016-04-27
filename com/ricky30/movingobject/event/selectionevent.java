package com.ricky30.movingobject.event;

import java.util.HashMap;
import java.util.Map;

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
	public static Map<String, Boolean> isActive = new HashMap<String, Boolean>();
	private static Map<String, Boolean> primaryUsed = new HashMap<String, Boolean>();
	private static Map<String, Boolean> secondaryUsed = new HashMap<String, Boolean>();
	private static Map<String, Boolean> triggerselect = new HashMap<String, Boolean>();
	private static Map<String, Boolean> isready = new HashMap<String, Boolean>();
	private static Map<String, Vector3i> first = new HashMap<String, Vector3i>();
	private static Map<String, Vector3i> second = new HashMap<String, Vector3i>();
	private static Map<String, Vector3i> trigger = new HashMap<String, Vector3i>();
	
	@Listener
	public void oninteractblockPrimary(ChangeBlockEvent.Break Event, @First Player player)
	{
		if (isActive.get(player.getUniqueId()).booleanValue())
		{
			if (triggerselect.get(player.getUniqueId()).booleanValue())
			{
				isActive.put(player.getUniqueId().toString(), false);
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
		if (isActive.get(player.getUniqueId()).booleanValue())
		{
			if (player.getItemInHand().isPresent())
			{
				if (player.getItemInHand().get().getItem().getId().equals(movingobject.plugin.GetTool()))
				{
					if (!primaryUsed.get(player.getUniqueId().toString()).booleanValue())
					{
						first.put(player.getUniqueId().toString(), Event.getTargetBlock().getPosition());
						primaryUsed.put(player.getUniqueId().toString(), true);
						player.getCommandSource().get().sendMessage(Text.of("First point defined"));
					}
					else if (!secondaryUsed.get(player.getUniqueId().toString()).booleanValue())
					{
						second.put(player.getUniqueId().toString(), Event.getTargetBlock().getPosition());
						secondaryUsed.put(player.getUniqueId().toString(), true);
						player.getCommandSource().get().sendMessage(Text.of("Second point defined"));
						player.getCommandSource().get().sendMessage(Text.of("Now select the trigger block/item"));
					}
					else if (!triggerselect.get(player.getUniqueId().toString()).booleanValue())
					{
						trigger.put(player.getUniqueId().toString(), Event.getTargetBlock().getPosition());
						triggerselect.put(player.getUniqueId().toString(), true);
						isready.put(player.getUniqueId().toString(), true);
						player.getCommandSource().get().sendMessage(Text.of("Ok now ready to save"));
					}
				}
			}
		}
	}
	
	public static Vector3i getFirst(Player player)
	{
		return first.get(player.getUniqueId().toString());
	}
	
	public static Vector3i getSecond(Player player)
	{
		return second.get(player.getUniqueId().toString());
	}
	
	public static Vector3i getTrigger(Player player)
	{
		return trigger.get(player.getUniqueId().toString());
	}
	
	public static boolean IsreadytoSave(Player player)
	{
		return isready.get(player.getUniqueId().toString());
	}
	
	public static void Reset(Player player)
	{
		//in case we defined wrongly, and choose to redo the define command , we try to remove every value (set or not)
		isready.remove(player.getUniqueId().toString());
		primaryUsed.remove(player.getUniqueId().toString());
		secondaryUsed.remove(player.getUniqueId().toString());
		triggerselect.remove(player.getUniqueId().toString());
		isActive.remove(player.getUniqueId().toString());
		first.remove(player.getUniqueId().toString());
		second.remove(player.getUniqueId().toString());
		trigger.remove(player.getUniqueId().toString());
		
		isready.put(player.getUniqueId().toString(), false);
		primaryUsed.put(player.getUniqueId().toString(), false);
		secondaryUsed.put(player.getUniqueId().toString(), false);
		triggerselect.put(player.getUniqueId().toString(), false);
		isActive.put(player.getUniqueId().toString(), true);
	}
	
	public static void Clear(Player player)
	{
		isready.remove(player.getUniqueId().toString());
		primaryUsed.remove(player.getUniqueId().toString());
		secondaryUsed.remove(player.getUniqueId().toString());
		triggerselect.remove(player.getUniqueId().toString());
		isActive.remove(player.getUniqueId().toString());
		first.remove(player.getUniqueId().toString());
		second.remove(player.getUniqueId().toString());
		trigger.remove(player.getUniqueId().toString());
	}
}
