package seedu.eatme.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.eatme.logic.parser.CliSyntax.PREFIX_USER;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import seedu.eatme.logic.commands.exceptions.CommandException;
import seedu.eatme.model.Model;
import seedu.eatme.model.UserPrefs;

/**
 * Loads all eateries from the specified JSON file.
 */
public class LoadCommand extends Command {

    public static final String COMMAND_WORD = "load";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Loads the specified user profile.\n"
            + "Parameters: " + PREFIX_USER + " username\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_USER + " john";

    public static final String MESSAGE_SUCCESS_LOADED = "User profile loaded: %1$s. Please relaunch EatMe!";
    public static final String MESSAGE_ALREADY_LOADED = "%1$s is already loaded.";
    public static final String MESSAGE_PROFILE_NOT_FOUND = "Please check that user profile for %1$s exists.";

    public final Path toLoad;

    /**
     * Creates a LoadCommand to add the specified {@code Path}.
     */
    public LoadCommand(Path eateryListFilePath) {
        requireNonNull(eateryListFilePath);
        toLoad = eateryListFilePath;
    }

    public LoadCommand() {
        toLoad = Paths.get("data", System.getProperty("user.name") + ".json");
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.getEateryListFilePath().equals(toLoad)) {
            throw new CommandException(String.format(MESSAGE_ALREADY_LOADED, toLoad));
        }

        if (!new File(toLoad.toString()).exists()) {
            throw new CommandException(String.format(MESSAGE_PROFILE_NOT_FOUND, toLoad));
        }

        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setEateryListFilePath(toLoad);
        model.setUserPrefs(userPrefs);

        return new CommandResult(String.format(MESSAGE_SUCCESS_LOADED, toLoad));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LoadCommand // instanceof handles nulls
                && toLoad.equals(((LoadCommand) other).toLoad));
    }
}