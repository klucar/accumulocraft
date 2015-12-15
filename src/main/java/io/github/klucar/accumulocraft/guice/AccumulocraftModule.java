package io.github.klucar.accumulocraft.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import io.github.klucar.accumulocraft.Configuration;
import org.apache.accumulo.core.client.AccumuloException;
import org.apache.accumulo.core.client.AccumuloSecurityException;
import org.apache.accumulo.core.client.Connector;
import org.apache.accumulo.core.client.Instance;
import org.apache.accumulo.core.client.ZooKeeperInstance;
import org.apache.accumulo.core.client.security.tokens.PasswordToken;


public class AccumulocraftModule extends AbstractModule {

  private Configuration config = null;
  private Instance instance;

  public AccumulocraftModule(Configuration config){
    this.config = config;
  }

  @Override
  protected void configure() {
  }

  @Provides
  Configuration provideConfiguration(){
    if ( null == this.config ){
      this.config = new Configuration();
      this.config.initialize();
    }
    return this.config;
  }

  @Provides
  Instance provideInstance(){
    if( this.instance == null ) {
      this.instance = new ZooKeeperInstance(config.getInstance(), config.getZkServers());
    }
    return instance;
  }

  @Provides
  Connector provideConnector() {
    Connector connector = null;
    try {
      connector = provideInstance().getConnector(config.getUsername(), new PasswordToken(config.getPassword()));
    } catch (AccumuloSecurityException e) {
      throw new RuntimeException("Accumulo Security Exception Occurred", e);
    } catch (AccumuloException e) {
      throw new RuntimeException("Accumulo Exception", e);
    }
    return connector;
  }
}
