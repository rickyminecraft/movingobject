package com.ricky30.movingobject.command;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import com.ricky30.movingobject.movingobject;

import ninja.leaping.configurate.ConfigurationNode;

public class commandChangetool implements CommandExecutor
{
	private ConfigurationNode config = null;

	@Override
	public CommandResult execute(CommandSource src, CommandContext args)
			throws CommandException
	{
		final Player player = (Player) src;
		final String tool = player.getItemInHand().get().getItem().getId();
		if (tool != null)
		{
			this.config = movingobject.plugin.getConfig();
			this.config.getNode("tool").setValue(tool);
			movingobject.plugin.save();
			src.sendMessage(Text.of("Tool updated"));
			return CommandResult.success();
		}
		src.sendMessage(Text.of("You need to have an item in hand"));
		return CommandResult.empty();
	}

}
