package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.eatery.Eatery;
import seedu.address.model.eatery.Review;

import java.util.List;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static  seedu.address.logic.parser.CliSyntax.PREFIX_RATING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;

/**
 * Adds review to an existing eatery in the address book
 */
public class ReviewCommand extends Command{

    public static final String COMMAND_WORD = "review";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a review to the eatery identified "
            + "by the index number used in the last eatery listing. "
            + "New review will be added to the eatery.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_DESCRIPTION + "[DESCRIPTION]"
            + PREFIX_COST + "[PRICE PER PAX]"
            + PREFIX_RATING + "[RATING]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DESCRIPTION + "Nice condensed milk pork ribs "
            + PREFIX_COST + "6.20 "
            + PREFIX_RATING + "4";

    public static final String MESSAGE_ADD_REVIEW_SUCCESS = "Added review to eatery: %1$s";

    private final Index index;
    private Review review;

    /**
     * @param index of the eatery to which review is added.
     * @param review to be added to the eatery.
     */
    public ReviewCommand (Index index, Review review) {
        requireAllNonNull(index, review);

        this.index = index;
        this.review = review;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Eatery> lastShownList = model.getFilteredEateryList();

        if(index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EATERY_DISPLAYED_INDEX);
        }

        Eatery eateryToAddReview = lastShownList.get(index.getZeroBased());
        Eatery editedEatery = new Eatery(eateryToAddReview.getName(), eateryToAddReview.getAddress(),
                eateryToAddReview.getCategory(), eateryToAddReview.getTags());

        editedEatery.addReview(review);

        model.setEatery(eateryToAddReview, editedEatery);
        model.updateFilteredEateryList(Model.PREDICATE_SHOW_ALL_EATERIES);

        return new CommandResult(String.format(MESSAGE_ADD_REVIEW_SUCCESS, review));
    }

}
