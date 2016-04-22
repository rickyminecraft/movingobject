package com.ricky30.movingobject.command;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;

import com.ricky30.movingobject.movingobject;
import ninja.leaping.configurate.ConfigurationNode;

public class commandDelete implements CommandExecutor
{
	private ConfigurationNode config = null;

	@Override
	public CommandResult execute(CommandSource src, CommandContext args)
			throws CommandException
	{
		String Name = args.<String>getOne("name").get();
		this.config = movingobject.plugin.getConfig();
		if (this.config.getNode("objectName").getChildrenMap().get(Name) != null)
		{
			this.config.getNode("objectName").removeChild(Name);
			movingobject.plugin.save();
			src.sendMessage(Text.of("Object " , Name, " deleted"));
			return CommandResult.success();
		}
		src.sendMessage(Text.of("Object " , Name, " not found"));
		return CommandResult.empty();
	}

}
