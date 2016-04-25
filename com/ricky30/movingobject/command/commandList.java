package com.ricky30.movingobject.command;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;

import com.flowpowered.math.vector.Vector3i;
import com.ricky30.movingobject.movingobject;

import ninja.leaping.configurate.ConfigurationNode;

public class commandList implements CommandExecutor
{
	private ConfigurationNode config = null;

	@Override
	public CommandResult execute(CommandSource src, CommandContext args)
			throws CommandException
	{
		this.config = movingobject.plugin.getConfig();
		for (Object text: this.config.getNode("objectName").getChildrenMap().keySet())
		{
			//get the size of the mine
			int X1, X2, Y1, Y2, Z1, Z2, TX, TY, TZ;
			X1 = this.config.getNode("objectName", text.toString(), "depart_X").getInt();
			Y1 = this.config.getNode("objectName", text.toString(), "depart_Y").getInt();
			Z1 = this.config.getNode("objectName", text.toString(), "depart_Z").getInt();
			X2 = this.config.getNode("objectName", text.toString(), "fin_X").getInt();
			Y2 = this.config.getNode("objectName", text.toString(), "fin_Y").getInt();
			Z2 = this.config.getNode("objectName", text.toString(), "fin_Z").getInt();
			TX = this.config.getNode("objectName", text.toString(), "trigger_X").getInt();
			TY = this.config.getNode("objectName", text.toString(), "trigger_Y").getInt();
			TZ = this.config.getNode("objectName", text.toString(), "trigger_Z").getInt();

			//converted to vector
			Vector3i first = new Vector3i(X1, Y1, Z1);
			Vector3i second = new Vector3i(X2, Y2, Z2);
			Vector3i trigger = new Vector3i(TX, TY, TZ);
			
			//get the world of this mine
			String World = this.config.getNode("objectName", text.toString(), "world").getString();
			src.sendMessage(Text.of("Object: " , text.toString()));
			src.sendMessage(Text.of("Coordinates: X=" , first.getX()," Y=", first.getY()," Z=", first.getZ(), " to X=" ,second.getX()," Y=", second.getY()," Z=", second.getZ()));
			src.sendMessage(Text.of("Trigger: X=" , trigger.getX()," Y=", trigger.getY()," Z=", trigger.getZ()));
			src.sendMessage(Text.of("World: " , World));
		}
		return CommandResult.success();
	}

}
