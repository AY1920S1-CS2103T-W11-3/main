package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.eatery.Address;
import seedu.address.model.eatery.Category;
import seedu.address.model.eatery.Eatery;
import seedu.address.model.eatery.Name;
import seedu.address.model.eatery.Tag;

import java.util.List;
import java.util.Set;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EATERIES;

public class AddTagCommand extends Command {

    public static final String COMMAND_WORD = "addtag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the TAGS of the eatery identified "
            + "by the index number used in the displayed eatery list. "
            + "The new tags will be added to the existing tags.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_TAG + " TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_TAG + "good elder-friendly";

    public static final String ADD_TAG_SUCCESS = "Added tags to Eatery: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one tag to add must be new.";

    private final Index index;
    private final EditEateryDescriptor editEateryDescriptor;

    public AddTagCommand(Index index, EditEateryDescriptor editEateryDescriptor) {

        requireNonNull(index);
        requireNonNull(editEateryDescriptor);

        this.index = index;
        this.editEateryDescriptor = editEateryDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Eatery> lastShownList = model.getFilteredEateryList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EATERY_DISPLAYED_INDEX);
        }

        Eatery eateryToEdit = lastShownList.get(index.getZeroBased());
        Eatery editedEatery = createEditedEatery(eateryToEdit, editEateryDescriptor);

        model.setEatery(eateryToEdit, editedEatery);
        model.updateFilteredEateryList(PREDICATE_SHOW_ALL_EATERIES);
        return new CommandResult(String.format(ADD_TAG_SUCCESS, editedEatery));
    }

    private static Eatery createEditedEatery(Eatery eateryToEdit, EditEateryDescriptor editEateryDescriptor) {
        Name name = eateryToEdit.getName();
        Address address = eateryToEdit.getAddress();
        Category category = eateryToEdit.getCategory();

        editEateryDescriptor.addTags(eateryToEdit.getTags());
        Set<Tag> updatedTags = editEateryDescriptor.getTags();

        return new Eatery(name, address, category, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddTagCommand)) {
            return false;
        }

        // state check
        AddTagCommand e = (AddTagCommand) other;
        return index.equals(e.index)
                && editEateryDescriptor.equals(e.editEateryDescriptor);
    }

    public static class EditEateryDescriptor {

        private Set<Tag> tags;

        public EditEateryDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditEateryDescriptor(EditEateryDescriptor toCopy) {
            addTags(toCopy.tags);
        }

        public void addTags(Set<Tag> tag) {
            this.tags.addAll(tag);
        }

        public Set<Tag> getTags() {
            return tags;
        }
    }

}
