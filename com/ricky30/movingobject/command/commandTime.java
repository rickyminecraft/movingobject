package com.ricky30.movingobject.command;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;

import com.ricky30.movingobject.movingobject;

import ninja.leaping.configurate.ConfigurationNode;

public class commandTime implements CommandExecutor
{
	private ConfigurationNode config = null;

	public CommandResult execute(CommandSource src, CommandContext args)
			throws CommandException
	{
		String Name = args.<String>getOne("name").get();
		int Time = args.<Integer>getOne("duration").get();
		this.config = movingobject.plugin.getConfig();
		if (this.config.getNode("objectName").getChildrenMap().get(Name) != null)
		{
			if (Time <121)
			{
				this.config.getNode("objectName", Name, "direction").setValue(Time);
				movingobject.plugin.save();
				src.sendMessage(Text.of("Object " , Name, " speed set to ", Time));
				return CommandResult.success();
			}
			else
			{
				src.sendMessage(Text.of("More than 2 minutes is not allowed"));
			}
		}
		return CommandResult.empty();
	}

}
