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

public class commandLength implements CommandExecutor
{
	private ConfigurationNode config = null;

	@Override
	public CommandResult execute(CommandSource src, CommandContext args)
			throws CommandException
	{
		String Name = args.<String>getOne("name").get();
		int Length = args.<Integer>getOne("length").get();
		if (Length <1)
		{
			Length = 1;
		}
		Player player = (Player) src;
		this.config = movingobject.plugin.getConfig();
		if (this.config.getNode("objectName").getChildrenMap().get(Name) != null)
		{
			UUID id = UUID.fromString(this.config.getNode("objectName", Name, "owner").getString());
			if (id.equals(player.getUniqueId()) || player.hasPermission("movingobject.bypass"))
			{
				this.config.getNode("objectName", Name, "length").setValue(Length);
				movingobject.plugin.save();
				src.sendMessage(Text.of("Object " , Name, " displacement set to ", Length));
				return CommandResult.success();
			}
			else
			{
				src.sendMessage(Text.of("you're not the owner of this object"));
			}
		}
		return CommandResult.empty();
	}

}
