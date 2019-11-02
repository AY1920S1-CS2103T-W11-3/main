package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.commands.AddTagCommand.EditEateryDescriptor;
import seedu.address.model.eatery.Tag;

import java.util.Set;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

public class AddTagCommandParser implements Parser<AddTagCommand>{

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

        editEateryDescriptor.addTags(parseTagsForAdding(argumentMultimap.getValue(PREFIX_TAG).get()));

        return new AddTagCommand(index, editEateryDescriptor);

    }

    private Set<Tag> parseTagsForAdding(String tags) {
        assert tags != null;

        return ParserUtil.parseTagsToAdd(tags);
    }

}
