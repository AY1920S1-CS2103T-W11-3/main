package seedu.eatme.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.eatme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.eatme.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import seedu.eatme.commons.core.index.Index;
import seedu.eatme.logic.commands.AddTagCommand;
import seedu.eatme.logic.commands.AddTagCommand.EditEateryDescriptor;
import seedu.eatme.logic.parser.exceptions.ParseException;
import seedu.eatme.model.eatery.Tag;

/**
 * Parses user input and returns a new AddTag command object.
 */
public class AddTagCommandParser implements Parser<AddTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddTagCommand
     * and returns an AddTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddTagCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argumentMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTagCommand.MESSAGE_USAGE), pe);
        }

        EditEateryDescriptor editEateryDescriptor = new EditEateryDescriptor();

        editEateryDescriptor.addTags(parseTagsForAdding(argumentMultimap.getAllValues(PREFIX_TAG)));

        return new AddTagCommand(index, editEateryDescriptor);

    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     */
    private Set<Tag> parseTagsForAdding(Collection<String> tags) throws ParseException {
        assert tags != null;

        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return ParserUtil.parseTags(tagSet);
    }
}
