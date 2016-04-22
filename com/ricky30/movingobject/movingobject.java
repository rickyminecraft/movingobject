package com.ricky30.movingobject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.scheduler.Scheduler;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.extent.ExtentBufferFactory;

import com.google.inject.Inject;
import com.ricky30.movingobject.command.commandDefine;
import com.ricky30.movingobject.command.commandDelete;
import com.ricky30.movingobject.command.commandDirection;
import com.ricky30.movingobject.command.commandHide;
import com.ricky30.movingobject.command.commandLength;
import com.ricky30.movingobject.command.commandList;
import com.ricky30.movingobject.command.commandMO;
import com.ricky30.movingobject.command.commandReload;
import com.ricky30.movingobject.command.commandSave;
import com.ricky30.movingobject.command.commandTime;
import com.ricky30.movingobject.event.selectionevent;
import com.ricky30.movingobject.event.triggersevent;
import com.ricky30.movingobject.task.timer;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;

@Plugin(id = "com.ricky30.movingobject", name = "movingobject", version = "1.0")
public class movingobject
{
	public static ExtentBufferFactory EXTENT_BUFFER_FACTORY;
	@Inject
	private Logger logger;
	private ConfigurationNode config = null;
	public static movingobject plugin;
	
	@Inject
	@DefaultConfig(sharedRoot = true)
	private Path defaultConfig;
	
	@Inject
	@DefaultConfig(sharedRoot = true)
	private ConfigurationLoader<CommentedConfigurationNode> configManager;
	
	private Scheduler scheduler = Sponge.getScheduler();
	private Task.Builder taskBuilder = scheduler.createTaskBuilder();
	private Task task;
	
	public Task gettasks()
	{
		return this.task;
	}
	
	public Task.Builder getTaskbuilder()
	{
		return this.taskBuilder;
	}
	
	public ConfigurationNode getConfig()
	{
		return this.config;
	}
	
	public Path getDefaultConfig() 
	{
        return this.defaultConfig;
    }
	
	public ConfigurationLoader<CommentedConfigurationNode> getConfigManager() 
	{
        return this.configManager;
    }
	
	public Logger getLogger()
	{
		return this.logger;
	}

	@Listener
	public void onServerStart(GameInitializationEvent event)
	{
		getLogger().info("movingobject start.");
		EXTENT_BUFFER_FACTORY = Sponge.getRegistry().getExtentBufferFactory();
		plugin = this;
		try
		{
			reload();
			if (!Files.exists(getDefaultConfig())) 
			{

				Files.createFile(getDefaultConfig());
				setupconfig();

				getConfigManager().save(this.config);
			}
		}
		catch (IOException e)
		{
			getLogger().error("Couldn't create default configuration file!");
		}
		
		task = movingobject.plugin.getTaskbuilder().execute(new Runnable()
		{
			public void run()
			{
				timer.run();
			}
		}).interval(1, TimeUnit.SECONDS).name("movingobject").submit(this);
		
		HashMap<List<String>, CommandSpec> subcommands = new HashMap<List<String>, CommandSpec>();
		
		subcommands.put(Arrays.asList("define"), CommandSpec.builder()
				.description(Text.of("allow use of stick to define movingobject"))
				.permission("movingobject.define")
				.executor(new commandDefine())
				.build());
		subcommands.put(Arrays.asList("list"), CommandSpec.builder()
				.description(Text.of("list all movingobject"))
				.permission("movingobject.list")
				.executor(new commandList())
				.build());
		subcommands.put(Arrays.asList("reload"), CommandSpec.builder()
				.description(Text.of("reload config file"))
				.permission("movingobject.list")
				.executor(new commandReload())
				.build());
		subcommands.put(Arrays.asList("save"), CommandSpec.builder()
				.description(Text.of("save a named object"))
				.permission("movingobject.save")
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("name"))))
				.executor(new commandSave())
				.build());
		subcommands.put(Arrays.asList("delete"), CommandSpec.builder()
				.description(Text.of("delete a named object"))
				.permission("movingobject.delete")
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("name"))))
				.executor(new commandDelete())
				.build());
		subcommands.put(Arrays.asList("direction"), CommandSpec.builder()
				.description(Text.of("set moving direction for named object"))
				.permission("movingobject.direction")
				.arguments(GenericArguments.seq(GenericArguments.onlyOne(GenericArguments.string(Text.of("name"))),
				GenericArguments.onlyOne(GenericArguments.string(Text.of("direction")))))
				.executor(new commandDirection())
				.build());
		subcommands.put(Arrays.asList("time"), CommandSpec.builder()
				.description(Text.of("Set speed in seconds for named object"))
				.permission("movingobject.time")
				.arguments(GenericArguments.seq(GenericArguments.onlyOne(GenericArguments.string(Text.of("name"))),
				GenericArguments.onlyOne(GenericArguments.integer(Text.of("duration")))))
				.executor(new commandTime())
				.build());
		subcommands.put(Arrays.asList("length"), CommandSpec.builder()
				.description(Text.of("Set displacement length for named object"))
				.permission("movingobject.length")
				.arguments(GenericArguments.seq(GenericArguments.onlyOne(GenericArguments.string(Text.of("name"))),
				GenericArguments.onlyOne(GenericArguments.integer(Text.of("length")))))
				.executor(new commandLength())
				.build());
		subcommands.put(Arrays.asList("hide"), CommandSpec.builder()
				.description(Text.of("Set hidding for named object"))
				.permission("movingobject.hide")
				.arguments(GenericArguments.seq(GenericArguments.onlyOne(GenericArguments.string(Text.of("name"))),
				GenericArguments.onlyOne(GenericArguments.string(Text.of("hide")))))
				.executor(new commandHide())
				.build());
		
		CommandSpec movingobjectcommand = CommandSpec.builder()
			    .description(Text.of("list all movingobject Command"))
			    .executor(new commandMO())
			    .children(subcommands)
			    .build();
		
		Sponge.getCommandManager().register(this, movingobjectcommand, "mo");
		Sponge.getEventManager().registerListeners(this, new triggersevent());
		Sponge.getEventManager().registerListeners(this, new selectionevent());
		
		getLogger().info("movingobject started.");
	}
	
	@Listener
	public void onServerStopping(GameStoppingServerEvent event)
	{
		getLogger().info("movingobject stop.");
		save();
		getLogger().info("movingobject stopped.");
	}
	
	private void setupconfig()
	{
        this.config.getNode("ConfigVersion").setValue(1);
        save();
	}
	
	public void save()
	{
		try
		{
			getConfigManager().save(this.config);
		} catch (IOException e) 
        {
            getLogger().error("Failed to save config file!", e);
        }
	}
	
	public void reload()
	{
		try
		{
			this.config = getConfigManager().load();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
