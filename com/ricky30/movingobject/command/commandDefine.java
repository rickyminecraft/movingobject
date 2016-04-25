package com.ricky30.movingobject.command;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;

import com.ricky30.movingobject.event.selectionevent;

public class commandDefine implements CommandExecutor
{

	@Override
	public CommandResult execute(CommandSource src, CommandContext args)
			throws CommandException
	{
		selectionevent.Reset();
		src.sendMessage(Text.of("Ready to define a movingobject"));
		return CommandResult.success();
	}

}
