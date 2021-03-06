package com.ricky30.movingobject.command;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import com.flowpowered.math.vector.Vector3i;
import com.ricky30.movingobject.movingobject;
import com.ricky30.movingobject.event.selectionevent;
import com.ricky30.movingobject.utility.size;

import ninja.leaping.configurate.ConfigurationNode;

public class commandSave implements CommandExecutor
{
	private ConfigurationNode config = null;

	@Override
	public CommandResult execute(CommandSource src, CommandContext args)
			throws CommandException
	{
		final String Name = args.<String>getOne("name").get();
		final Player player = (Player) src;
		final String world = player.getWorld().getName();
		this.config = movingobject.plugin.getConfig();
		if (this.config.getNode("objectName").getChildrenMap().get(Name) != null)
		{
			src.sendMessage(Text.of("Object ", Name, " already saved. change name."));
			return CommandResult.empty();
		}
		if (selectionevent.IsreadytoSave(player))
		{
			final Vector3i positiondepart = selectionevent.getFirst(player);
			final Vector3i positionfin = selectionevent.getSecond(player);
			final Vector3i trigger = selectionevent.getTrigger(player);
			final Vector3i Objectsize = size.length(positiondepart, positionfin);
			this.config.getNode("objectName", Name).setValue("");
			this.config.getNode("objectName", Name, "world").setValue(world);
			this.config.getNode("objectName", Name, "depart_X").setValue(positiondepart.getX());
			this.config.getNode("objectName", Name, "depart_Y").setValue(positiondepart.getY());
			this.config.getNode("objectName", Name, "depart_Z").setValue(positiondepart.getZ());
			this.config.getNode("objectName", Name, "fin_X").setValue(positionfin.getX());
			this.config.getNode("objectName", Name, "fin_Y").setValue(positionfin.getY());
			this.config.getNode("objectName", Name, "fin_Z").setValue(positionfin.getZ());
			this.config.getNode("objectName", Name, "trigger_X").setValue(trigger.getX());
			this.config.getNode("objectName", Name, "trigger_Y").setValue(trigger.getY());
			this.config.getNode("objectName", Name, "trigger_Z").setValue(trigger.getZ());
			this.config.getNode("objectName", Name, "movingtime").setValue(1);
			this.config.getNode("objectName", Name, "direction").setValue("up");
			this.config.getNode("objectName", Name, "sound").setValue("none");
			this.config.getNode("objectName", Name, "length").setValue(Objectsize.getY());
			this.config.getNode("objectName", Name, "currentposition").setValue(0);
			this.config.getNode("objectName", Name, "currentstat").setValue("inactive");
			this.config.getNode("objectName", Name, "hide").setValue("false");
			this.config.getNode("objectName", Name, "owner").setValue(player.getUniqueId().toString());
			movingobject.plugin.save();
			selectionevent.Clear(player);
			src.sendMessage(Text.of("Object " , Name, " saved"));
			return CommandResult.success();
		}
		else
		{
			src.sendMessage(Text.of("Not ready to save yet"));
		}
		return CommandResult.empty();
	}

}
