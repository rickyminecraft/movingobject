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

public class commandDelete implements CommandExecutor
{
	private ConfigurationNode config = null;

	@Override
	public CommandResult execute(CommandSource src, CommandContext args)
			throws CommandException
	{
		final String Name = args.<String>getOne("name").get();
		final Player player = (Player) src;
		this.config = movingobject.plugin.getConfig();
		if (this.config.getNode("objectName").getChildrenMap().get(Name) != null)
		{
			final UUID id = UUID.fromString(this.config.getNode("objectName", Name, "owner").getString());
			if (id.equals(player.getUniqueId()) || player.hasPermission("movingobject.bypass"))
			{
				this.config.getNode("objectName").removeChild(Name);
				movingobject.plugin.save();
				src.sendMessage(Text.of("Object " , Name, " deleted"));
				return CommandResult.success();
			}
			else
			{
				src.sendMessage(Text.of("You're not the owner of this object"));
			}
		}
		src.sendMessage(Text.of("Object " , Name, " not found"));
		return CommandResult.empty();
	}

}
