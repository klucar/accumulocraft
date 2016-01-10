package io.github.klucar.accumulocraft;

import com.google.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.apache.accumulo.minicluster.MiniAccumuloCluster;
import org.apache.accumulo.minicluster.impl.MiniAccumuloClusterImpl;
import org.apache.accumulo.minicluster.impl.MiniAccumuloConfigImpl;
import org.slf4j.Logger;
import org.spongepowered.api.config.DefaultConfig;

public class Configuration {


  public static final String[] MINICLUSTER_NODE = {"accumulocraft","minicluster"};
  public static final String[] ZK_NODE = {"accumulocraft","zk_hosts"};
  public static final String[] INSTANCE_NODE = {"accumulocraft","instance"};
  public static final String[] USERNAME_NODE = {"accumulocraft","username"};
  public static final String[] PASSWORD_NODE = {"accumulocraft","password"};

  public static final String ZK_DEFAULT = "localhost:2181";
  public static final String INSTANCE_DEFAULT = "accumulocraft";
  public static final String USERNAME_DEFAULT = "root";
  public static final String PASSWORD_DEFAULT = "secret";

  @Inject
  Logger logger;

  // Give us a configuration to work from
  @Inject
  @DefaultConfig(sharedRoot = false)
  private ConfigurationLoader<CommentedConfigurationNode> configManager;

  private String instance;
  private String zkServers;
  private String password;
  private String username;

  private MiniAccumuloCluster cluster = null;

  public Configuration(){}

  public void initialize(){
    try {
      logger.info("Initializing Configuration");
      // Load the config files
      CommentedConfigurationNode config = configManager.load();
      this.setZkServers(config.getNode(ZK_NODE).getString(ZK_DEFAULT));
      this.setInstance(config.getNode(INSTANCE_NODE).getString(INSTANCE_DEFAULT));
      this.setPassword(config.getNode(PASSWORD_NODE).getString(PASSWORD_DEFAULT));
      this.setUsername(config.getNode(USERNAME_NODE).getString(USERNAME_DEFAULT));

      // start miniaccumulo if needed
      logger.info("minicluster: {}", config.getNode(MINICLUSTER_NODE).getBoolean());
      if( config.getNode(MINICLUSTER_NODE).getBoolean() ){
        logger.info("Starting MiniAccumuloCluster");
        File tempFile = Files.createTempDirectory("miniaccumulocraft").toFile();
        logger.info("tempFile: {}", tempFile);
        logger.info("classpath: {}", System.getProperty("java.class.path"));

        ClassLoader cl = ClassLoader.getSystemClassLoader();
        URL[] urls = ((URLClassLoader)cl).getURLs();
        for(URL url: urls){
          logger.info(url.getFile());
        }

        MiniAccumuloConfigImpl confImpl = new MiniAccumuloConfigImpl(tempFile, this.getPassword());
        String cp = System.getenv("CLASSPATH");
        logger.info("MiniAccumulo classpath: {}", cp);
        String[] cpItems = cp.split(":");
        confImpl.setClasspathItems(cpItems);
        confImpl.setInstanceName(this.getInstance());
        MiniAccumuloClusterImpl cluster = new MiniAccumuloClusterImpl(confImpl);
        cluster.start();
        this.setZkServers(cluster.getZooKeepers());
        this.setInstance(cluster.getInstanceName());
        this.setUsername("root");
      }

    } catch (IOException exception) {
      logger.error("The default configuration could not be loaded or created!");
      throw new RuntimeException(exception);
    } catch (InterruptedException e) {
      logger.error("Failed to launch mini accumulo cluster");
      throw new RuntimeException(e);
    }

    //logger.info("Default Configuration File: {}", defaultConfig.toAbsolutePath());
  }

  public String getInstance(){
    return this.instance;
  }

  public void setInstance(String instance) {
    this.instance = instance;
  }

  public String getZkServers() {
    return zkServers;
  }

  public void setZkServers(String zkServers) {
    this.zkServers = zkServers;
  }

  public String getUsername(){
    return username;
  }

  public void setUsername(String username){
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public boolean isMiniCluster(){
    return cluster == null ? false : true;
  }

  public void shutdownMiniCluster() throws IOException, InterruptedException {
    this.cluster.stop();
  }
}
