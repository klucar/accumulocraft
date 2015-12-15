package io.github.klucar.accumulocraft;

import com.google.inject.Inject;
import com.google.inject.Injector;
import io.github.klucar.accumulocraft.command.TablesCommand;
import io.github.klucar.accumulocraft.command.TipCommand;
import io.github.klucar.accumulocraft.guice.AccumulocraftModule;
import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.command.CommandManager;
import org.spongepowered.api.command.spec.CommandSpec;
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

  @Inject
  private Game game;

  @Inject
  Injector injector;

  private Configuration config;

  @Listener
  public void onPreInit(GamePreInitializationEvent event) {
    logger.info("Pre-Init");
    config = new Configuration();
    injector.injectMembers(config);
    injector.createChildInjector(new AccumulocraftModule(config));
    config.initialize();
    configureCommands();
  }

  @Listener
  public void onInit(GameInitializationEvent event) {
    logger.info("Init");
  }

  @Listener
  public void onServerStarting(GameStartingServerEvent event) {
    // Perform initialization tasks here
    logger.info("Server Starting");
  }

  @Listener
  public void onServerStart(GameStartedServerEvent event) {
    // Hey! The server has started!
    // Try instantiating your logger in here.
    // (There's a guide for that)
    logger.info("Server Start");
  }

  @Listener
  public void disable(GameStoppingServerEvent event) {
    logger.info("Plugin disable");
    // Perform shutdown tasks here
    if( config.isMiniCluster() ){
      try {
        config.shutdownMiniCluster();
      } catch (Exception e) {
        logger.error("Problem shutting down mini accumulo cluster");
      }
    }
    logger.info("Server shutting down");
  }

  private void configureCommands(){
    logger.info("Configuring Accumulocraft Commands");

    CommandManager mgr = game.getCommandManager();
    // Perform initialization tasks here
    CommandSpec tipCommandSpec = CommandSpec.builder()
      .description(Texts.of("Get a random tip."))
      .executor(new TipCommand())
      .build();

    mgr.register(this, tipCommandSpec, "tip", "tip", "tip");

    CommandSpec tablesCommandSpec = CommandSpec.builder()
      .description(Texts.of("Display Accumulo Tables"))
      .executor(new TablesCommand())
      .build();

    mgr.register(this, tablesCommandSpec, "tables", "tables", "tables");

  }

}
