package io.github.klucar.accumulocraft.command;

import com.google.inject.Inject;
import org.apache.accumulo.core.client.Connector;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Texts;


public class TablesCommand implements CommandExecutor {

  @Inject
  Connector connector;

  public TablesCommand(){
  }

  @Override
  public CommandResult execute(CommandSource src, CommandContext context) throws CommandException {

    for (String tablename : connector.tableOperations().list()){
      src.sendMessage(Texts.of(tablename));
    }

    return CommandResult.success();
  }




}
