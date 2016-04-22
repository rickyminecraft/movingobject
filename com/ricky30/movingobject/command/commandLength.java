package com.ricky30.movingobject.command;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;

import com.ricky30.movingobject.movingobject;

import ninja.leaping.configurate.ConfigurationNode;

public class commandLength implements CommandExecutor
{
	private ConfigurationNode config = null;

	@Override
	public CommandResult execute(CommandSource src, CommandContext args)
			throws CommandException
	{
		String Name = args.<String>getOne("name").get();
		int Length = args.<Integer>getOne("length").get();
		this.config = movingobject.plugin.getConfig();
		if (this.config.getNode("objectName").getChildrenMap().get(Name) != null)
		{
				this.config.getNode("objectName", Name, "length").setValue(Length);
				movingobject.plugin.save();
				src.sendMessage(Text.of("Object " , Name, " displacement set to ", Length));
				return CommandResult.success();
		}
		return CommandResult.empty();
	}

}
