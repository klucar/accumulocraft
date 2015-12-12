package io.github.klucar.accumulocraft;

import com.google.inject.Inject;
import io.github.klucar.accumulocraft.command.TipCommand;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.command.CommandManager;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStartingServerEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Texts;

/**
 * A simple sponge plugin
 */
@Plugin(id = PomData.ARTIFACT_ID, name = PomData.NAME, version = PomData.VERSION)
public class AccumulocraftPlugin {


  @Inject
  private Logger logger;

  // Give us a configuration to work from
  @Inject
  @DefaultConfig(sharedRoot = true)
  private ConfigurationLoader<CommentedConfigurationNode> configLoader;

  @Inject
  private Game game;


  @Listener
  public void onPreInit(GamePreInitializationEvent event) {
    configureCommands();
  }

  @Listener
  public void onInit(GameInitializationEvent event) {

  }

  @Listener
  public void onServerStarting(GameStartingServerEvent event) {
    // Perform initialization tasks here

  }

  @Listener
  public void onServerStart(GameStartedServerEvent event) {
    // Hey! The server has started!
    // Try instantiating your logger in here.
    // (There's a guide for that)
    logger.info("Server is starting.");
  }

  @Listener
  public void disable(GameStoppingServerEvent event) {
    // Perform shutdown tasks here
    logger.info("Server shutting down");
  }

  private void configureCommands(){
    logger.info("Configuring Accumulocraft Commands");

    CommandManager mgr = game.getCommandManager();
    // Perform initialization tasks here
    CommandSpec tipCommandSpec = CommandSpec.builder()
      .description(Texts.of("Get a random tip."))
      .permission("accumulocraft.command.tip")
      .executor(new TipCommand())
      .build();

    mgr.register(this, tipCommandSpec, "tip", "tip", "tip");

  }

}
