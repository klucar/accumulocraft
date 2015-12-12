package io.github.klucar.accumulocraft.command;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Texts;


public class TipCommand implements CommandExecutor {

  @Override
  public CommandResult execute(CommandSource src, CommandContext context) throws CommandException {
    src.sendMessage(Texts.of(getRandomTip()));
    return CommandResult.success();
  }

  private String getRandomTip() {
    int idx = new Double(Math.floor(Math.random() * nTips)).intValue();
    if (idx >= 0 && idx < nTips) {
      return TIPS[idx];
    }
    return "Apparently Math.random() changed behavior. You should probably look outside.";
  }

  private static final String[] TIPS = {"Care About Your Craft", "Provide Options, Don’t Make Lame Excuses ", "Be a Catalyst for Change", "Make Quality a Requirements Issue", "Critically Analyze What You Read and Hear", "DRY—Don’t Repeat " +
    "Yourself", "Eliminate Effects Between Unrelated Things", "Use Tracer Bullets to Find the Target", "Program Close to the Problem Domain", "Iterate the Schedule with the Code", "Use the Power of Command Shells", "Always Use Source Code " +
    "Control", "Don’t Panic When Debugging", "Don’t Assume It—Prove It", "Write Code That Writes Code", "Design with Contracts", "Use Assertions to Prevent the Impossible", "Finish What You Start", "Configure, Don’t Integrate", "Analyze Workflow " +
    "to Improve Concurrency", "Always Design for Concurrency", "Use Blackboards to Coordinate Workflow", "Estimate the Order of Your Algorithms", "Refactor Early, Refactor Often", "Test Your Software, or Your Users Will", "Don’t Gather " +
    "Requirements—Dig for Them", "Abstractions Live Longer than Details", "Don’t Think Outside the Box—Find the Box", "Some Things Are Better Done than Described", "Costly Tools Don’t Produce Better Designs", "Don’t Use Manual Procedures", "Coding" +
    " Ain’t Done ‘Til All the Tests Run", "Test State Coverage, Not Code Coverage", "English is Just a Programming Language", "Gently Exceed Your Users’ Expectations", "Think! About Your Work", "Don’t Live with Broken Windows", "Remember the Big " +
    "Picture", "Invest Regularly in Your Knowledge Portfolio", "It’s Both What You Say and the Way You Say It", "Make It Easy to Reuse", "There Are No Final Decisions", "Prototype to Learn", "Estimate to Avoid Surprises", "Keep Knowledge in Plain " +
    "Text", "Use a Single Editor Well", "Fix the Problem, Not the Blame", "\\“select\\” Isn’t Broken", "Learn a Text Manipulation Language", "You Can’t Write Perfect Software", "Crash Early", "Use Exceptions for Exceptional Problems", "Minimize " +
    "Coupling Between Modules", "Put Abstractions in Code, Details in Metadata", "Design Using Services", "Separate Views from Models", "Don’t Program by Coincidence", "Test Your Estimates", "Design to Test", "Don’t Use Wizard Code You Don’t " +
    "Understand", "Work with a User to Think Like a User", "Use a Project Glossary", "Start When You’re Ready", "Don’t Be a Slave to Formal Methods", "Organize Teams Around Functionality", "Test Early. Test Often. Test Automatically.", "Use " +
    "Saboteurs to Test Your Testing", "Find Bugs Once", "Build Documentation In, Don’t Bolt It On", "Sign Your Work"};

  private static final int nTips = TIPS.length;

}
