package com.ricky30.movingobject.command;

import java.util.UUID;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import com.ricky30.movingobject.movingobject;

import ninja.leaping.configurate.ConfigurationNode;

public class commandDirection implements CommandExecutor
{
	private ConfigurationNode config = null;

	@Override
	public CommandResult execute(CommandSource src, CommandContext args)
			throws CommandException
	{
		final String Name = args.<String>getOne("name").get();
		final String Dir = args.<String>getOne("direction").get();
		final Player player = (Player) src;
		if (Dir.equals("north") || Dir.equals("south") || Dir.equals("east") || Dir.equals("west") || Dir.equals("up") || Dir.equals("down"))
		{
			this.config = movingobject.plugin.getConfig();
			if (this.config.getNode("objectName").getChildrenMap().get(Name) != null)
			{
				final UUID id = UUID.fromString(this.config.getNode("objectName", Name, "owner").getString());
				if (id.equals(player.getUniqueId()) || player.hasPermission("movingobject.bypass"))
				{
					this.config.getNode("objectName", Name, "direction").setValue(Dir);
					movingobject.plugin.save();
					src.sendMessage(Text.of("Object " , Name, " direction set to ", Dir));
					return CommandResult.success();
				}
				else
				{
					src.sendMessage(Text.of("You're not the owner of this object"));
				}
			}
		}
		else
		{
			src.sendMessage(Text.of("Direction must be: north south east west up down"));
			return CommandResult.empty();
		}

		src.sendMessage(Text.of("Object " , Name, " not found"));
		return CommandResult.empty();
	}

}
