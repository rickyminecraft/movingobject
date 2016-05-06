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

public class commandSound implements CommandExecutor
{
	private ConfigurationNode config = null;

	@Override
	public CommandResult execute(CommandSource src, CommandContext args)
			throws CommandException
	{
		final String Name = args.<String>getOne("name").get();
		final String Sound = args.<String>getOne("sound").get();
		final Player player = (Player) src;
		if (Sound.equals("grass") || Sound.equals("gravel") || Sound.equals("ladder") || Sound.equals("sand") || Sound.equals("snow") || Sound.equals("stone") || Sound.equals("wood") || Sound.equals("wool") || Sound.equals("none"))
		{
			this.config = movingobject.plugin.getConfig();
			if (this.config.getNode("objectName").getChildrenMap().get(Name) != null)
			{
				final UUID id = UUID.fromString(this.config.getNode("objectName", Name, "owner").getString());
				if (id.equals(player.getUniqueId()) || player.hasPermission("movingobject.bypass"))
				{
					this.config.getNode("objectName", Name, "sound").setValue(Sound);
					movingobject.plugin.save();
					src.sendMessage(Text.of("Object " , Name, " sound set to ", Sound));
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
			src.sendMessage(Text.of("Sound must be: grass gravel ladder sand snow stone wood wool none"));
			return CommandResult.empty();
		}

		src.sendMessage(Text.of("Object " , Name, " not found"));
		return CommandResult.empty();
	}
}
