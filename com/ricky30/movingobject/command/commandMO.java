package com.ricky30.movingobject.command;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;

public class commandMO implements CommandExecutor
{

	public CommandResult execute(CommandSource src, CommandContext args)
			throws CommandException
	{
		src.sendMessage(Text.of("Movingobject plugin"));
		src.sendMessage(Text.of("Usage:"));
		src.sendMessage(Text.of("/mo define"));
		src.sendMessage(Text.of("/mo reload"));
		src.sendMessage(Text.of("/mo list"));
		src.sendMessage(Text.of("/mo changetool"));
		src.sendMessage(Text.of("/mo save NAME"));
		src.sendMessage(Text.of("/mo delete NAME"));
		src.sendMessage(Text.of("/mo direction NAME direction"));
		src.sendMessage(Text.of("/mo time NAME timing"));
		src.sendMessage(Text.of("/mo length NAME length"));
		src.sendMessage(Text.of("/mo hide NAME true/false"));
		return CommandResult.success();
	}

}
