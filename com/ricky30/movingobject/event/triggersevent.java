package com.ricky30.movingobject.event;

import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.data.Transaction;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.filter.cause.First;
import com.flowpowered.math.vector.Vector3i;
import com.ricky30.movingobject.movingobject;
import com.ricky30.movingobject.task.timer;

import ninja.leaping.configurate.ConfigurationNode;

public class triggersevent
{
	private ConfigurationNode config = null;

	@Listener
	public void ontriggersevent(InteractBlockEvent.Secondary event, @First Player player)
	{
		final Vector3i BlockPos = event.getTargetBlock().getPosition();
		final String Eventworld = player.getWorld().getName();
		this.config = movingobject.plugin.getConfig();
		for (final Object text: this.config.getNode("objectName").getChildrenMap().keySet())
		{
			//get the location of the trigger
			int TX, TY, TZ;
			TX = this.config.getNode("objectName", text.toString(), "trigger_X").getInt();
			TY = this.config.getNode("objectName", text.toString(), "trigger_Y").getInt();
			TZ = this.config.getNode("objectName", text.toString(), "trigger_Z").getInt();

			//converted to vector
			final Vector3i trigger = new Vector3i(TX, TY, TZ);

			//get the world of this object
			final String World = this.config.getNode("objectName", text.toString(), "world").getString();

			if (Eventworld.equals(World))
			{
				if (BlockPos.compareTo(trigger) == 0)
				{
					Start(text.toString(), World);
				}
			}
		}
	}

	@Listener
	public void ontriggersevent(ChangeBlockEvent.Modify event, @First Player player)
	{
		final String Eventworld = player.getWorld().getName();
		this.config = movingobject.plugin.getConfig();
		boolean Activated = false;
		for (final Transaction<BlockSnapshot> Block :event.getTransactions())
		{
			final Vector3i BlockPos = Block.getOriginal().getPosition();

			for (final Object text: this.config.getNode("objectName").getChildrenMap().keySet())
			{
				//get the location of the trigger
				int TX, TY, TZ;
				TX = this.config.getNode("objectName", text.toString(), "trigger_X").getInt();
				TY = this.config.getNode("objectName", text.toString(), "trigger_Y").getInt();
				TZ = this.config.getNode("objectName", text.toString(), "trigger_Z").getInt();

				//converted to vector
				final Vector3i trigger = new Vector3i(TX, TY, TZ);

				//get the world of this object
				final String World = this.config.getNode("objectName", text.toString(), "world").getString();
				if (Eventworld.equals(World))
				{
					if (BlockPos.compareTo(trigger) == 0)
					{
						//need to do this because of stupid redstone
						if (!Activated)
						{
							Start(text.toString(), World);
							Activated = true;
						}
					}
				}
			}
		}
	}
	
	private void Start(String Name, String World)
	{
		//if we are here then a trigger was activated
		int X1, X2, Y1, Y2, Z1, Z2, duration, length, currentposition;
		String direction, currentstat, Sound;
		boolean hide;
		X1 = this.config.getNode("objectName", Name, "depart_X").getInt();
		Y1 = this.config.getNode("objectName", Name, "depart_Y").getInt();
		Z1 = this.config.getNode("objectName", Name, "depart_Z").getInt();
		X2 = this.config.getNode("objectName", Name, "fin_X").getInt();
		Y2 = this.config.getNode("objectName", Name, "fin_Y").getInt();
		Z2 = this.config.getNode("objectName", Name, "fin_Z").getInt();
		final Vector3i first = new Vector3i(X1, Y1, Z1);
		final Vector3i second = new Vector3i(X2, Y2, Z2);
		duration = this.config.getNode("objectName", Name, "movingtime").getInt();
		direction = this.config.getNode("objectName", Name, "direction").getString();
		length = this.config.getNode("objectName", Name, "length").getInt();
		currentposition = this.config.getNode("objectName", Name, "currentposition").getInt();
		currentstat = this.config.getNode("objectName", Name, "currentstat").getString();
		hide = this.config.getNode("objectName", Name, "hide").getBoolean();
		Sound = this.config.getNode("objectName", Name, "sound").getString();
		timer.start(Name, duration, length, direction, currentposition, currentstat, World, hide, first, second, Sound);
	}
}
