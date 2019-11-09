package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATING;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.eatery.Eatery;
import seedu.address.model.eatery.Review;

/**
 * Edits the details of an eatery's existing review.
 */
public class EditReviewCommand extends Command {

    public static final String COMMAND_WORD = "editreview";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the review identified "
            + "by the index number used in the displayed review list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_DESCRIPTION + " DESCRIPTION "
            + "[" + PREFIX_COST + " COST "
            + "[" + PREFIX_RATING + " RATING "
            + "[" + PREFIX_DATE + " DATE [dd/MM/yyyy format only]"
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_EDITED_REVIEW_SUCCESS = "Edited Review";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    private final Index index;
    private final EditReviewDescriptor editReviewDescriptor;

    /**
     * @param index of the review in the review list to edit
     * @param editReviewDescriptor details to edit the review with
     */
    public EditReviewCommand(Index index, EditReviewDescriptor editReviewDescriptor) {
        requireNonNull(index);
        requireNonNull(editReviewDescriptor);

        this.index = index;
        this.editReviewDescriptor = new EditReviewDescriptor(editReviewDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        requireNonNull(model);
        List<Review> lastShownList = model.getActiveReviews();
        Eatery activeEatery = model.getActiveEatery();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EATERY_DISPLAYED_INDEX);
        }

        Review reviewToEdit = lastShownList.get(index.getZeroBased());
        Review editedReview = createEditedReview(reviewToEdit, editReviewDescriptor);

        lastShownList.set(index.getZeroBased(), editedReview);
        activeEatery.setReviews(lastShownList);
        return new CommandResult(String.format(MESSAGE_EDITED_REVIEW_SUCCESS), editedReview);
    }

    /**
     * Creates and returns a {@code Review} with the details of {@code reviewToEdit}
     * edited with {@code editReviewDescriptor}.
     */
    private static Review createEditedReview(Review reviewToEdit, EditReviewDescriptor editReviewDescriptor) {
        assert reviewToEdit != null;

        String updatedDescription = editReviewDescriptor.getDescription().orElse(reviewToEdit.getDescription());
        double updatedCost = editReviewDescriptor.getCost().orElse(reviewToEdit.getCost());
        int updatedRating = editReviewDescriptor.getRating().orElse(reviewToEdit.getRating());
        Date updatedDate = editReviewDescriptor.getDate().orElse(reviewToEdit.getDate());

        return new Review(updatedDescription, updatedCost, updatedRating, updatedDate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof EditReviewCommand)) {
            return false;
        }

        EditReviewCommand e = (EditReviewCommand) other;
        return index.equals(e.index)
                && editReviewDescriptor.equals(e.editReviewDescriptor);
    }

    /**
     * Stores the details to edit the review with. Each non-empty field value will replace the
     * corresponding field value of the review.
     */
    public static class EditReviewDescriptor {
        private String description;
        private double cost;
        private int rating;
        private Date date;

        public EditReviewDescriptor() {}

        /**
         * Copy constructor
         */
        public EditReviewDescriptor(EditReviewDescriptor toCopy) {
            setDescription(toCopy.description);
            setCost(toCopy.cost);
            setRating(toCopy.rating);
            setDate(toCopy.date);
        }

        /**
         * Returns true if at least one field is edited.
         * @return
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(description, cost, rating, date);
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Optional<String> getDescription() {
            return Optional.ofNullable(description);
        }

        public void setCost(double cost) {
            this.cost = cost;
        }

        public OptionalDouble getCost() {
            return OptionalDouble.of(cost);
        }

        public void setRating(int rating) {
            this.rating = rating;
        }

        public OptionalInt getRating() {
            return OptionalInt.of(rating);
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public Optional<Date> getDate() {
            return Optional.ofNullable(date);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            if (!(other instanceof EditReviewDescriptor)) {
                return false;
            }

            EditReviewDescriptor e = (EditReviewDescriptor) other;

            return getCost().equals(e.getCost())
                    && getRating().equals(e.getRating())
                    && getDate().equals(e.getDate());
        }
    }
}
