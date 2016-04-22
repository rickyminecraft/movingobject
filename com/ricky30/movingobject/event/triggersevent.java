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
		Vector3i BlockPos = event.getTargetBlock().getPosition();
		String Eventworld = player.getWorld().getName();
		this.config = movingobject.plugin.getConfig();
		for (Object text: this.config.getNode("objectName").getChildrenMap().keySet())
		{
			//get the location of the trigger
			int TX, TY, TZ;
			TX = this.config.getNode("objectName", text.toString(), "trigger_X").getInt();
			TY = this.config.getNode("objectName", text.toString(), "trigger_Y").getInt();
			TZ = this.config.getNode("objectName", text.toString(), "trigger_Z").getInt();

			//converted to vector
			Vector3i trigger = new Vector3i(TX, TY, TZ);

			//get the world of this mine
			String World = this.config.getNode("objectName", text.toString(), "world").getString();

			if (Eventworld.equals(World))
			{
				if (BlockPos.compareTo(trigger) == 0)
				{
					//if we are here then a trigger was activated
					//
					int X1, X2, Y1, Y2, Z1, Z2, duration, length;
					String direction;
					boolean hide;
					X1 = this.config.getNode("objectName", text.toString(), "depart_X").getInt();
					Y1 = this.config.getNode("objectName", text.toString(), "depart_Y").getInt();
					Z1 = this.config.getNode("objectName", text.toString(), "depart_Z").getInt();
					X2 = this.config.getNode("objectName", text.toString(), "fin_X").getInt();
					Y2 = this.config.getNode("objectName", text.toString(), "fin_Y").getInt();
					Z2 = this.config.getNode("objectName", text.toString(), "fin_Z").getInt();
					Vector3i first = new Vector3i(X1, Y1, Z1);
					Vector3i second = new Vector3i(X2, Y2, Z2);
					duration = this.config.getNode("objectName", text.toString(), "movingtime").getInt();
					direction = this.config.getNode("objectName", text.toString(), "direction").getString();
					length = this.config.getNode("objectName", text.toString(), "length").getInt();
					hide = this.config.getNode("objectName", text.toString(), "hide").getBoolean();
					timer.start(text.toString(), duration, length, direction, World, hide, first, second);
				}
			}
		}
	}

	@Listener
	public void ontriggersevent(ChangeBlockEvent.Modify event, @First Player player)
	{
		String Eventworld = player.getWorld().getName();
		this.config = movingobject.plugin.getConfig();
		for ( Transaction<BlockSnapshot> Block :event.getTransactions())
		{
			Vector3i BlockPos = Block.getDefault().getPosition();

			for (Object text: this.config.getNode("objectName").getChildrenMap().keySet())
			{

				//get the location of the trigger
				int TX, TY, TZ;
				TX = this.config.getNode("objectName", text.toString(), "trigger_X").getInt();
				TY = this.config.getNode("objectName", text.toString(), "trigger_Y").getInt();
				TZ = this.config.getNode("objectName", text.toString(), "trigger_Z").getInt();

				//converted to vector
				Vector3i trigger = new Vector3i(TX, TY, TZ);

				//get the world of this mine
				String World = this.config.getNode("objectName", text.toString(), "world").getString();

				if (Eventworld.equals(World))
				{
					if (BlockPos.compareTo(trigger) == 0)
					{
						//if we are here then a trigger was activated
						int X1, X2, Y1, Y2, Z1, Z2, duration, length;
						String direction;
						boolean hide;
						X1 = this.config.getNode("objectName", text.toString(), "depart_X").getInt();
						Y1 = this.config.getNode("objectName", text.toString(), "depart_Y").getInt();
						Z1 = this.config.getNode("objectName", text.toString(), "depart_Z").getInt();
						X2 = this.config.getNode("objectName", text.toString(), "fin_X").getInt();
						Y2 = this.config.getNode("objectName", text.toString(), "fin_Y").getInt();
						Z2 = this.config.getNode("objectName", text.toString(), "fin_Z").getInt();
						Vector3i first = new Vector3i(X1, Y1, Z1);
						Vector3i second = new Vector3i(X2, Y2, Z2);
						duration = this.config.getNode("objectName", text.toString(), "movingtime").getInt();
						direction = this.config.getNode("objectName", text.toString(), "direction").getString();
						length = this.config.getNode("objectName", text.toString(), "length").getInt();
						hide = this.config.getNode("objectName", text.toString(), "hide").getBoolean();
						timer.start(text.toString(), duration, length, direction, World, hide, first, second);
					}
				}
			}
		}
	}
}
