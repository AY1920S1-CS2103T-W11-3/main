package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.eatery.Review;

class JsonAdaptedReview {
    private final String review;


    @JsonCreator
    public JsonAdaptedReview(String reviewDescription, double reviewCost, int reviewRating) {
        this.review = reviewDescription + String.valueOf(reviewCost) + String.valueOf(reviewRating);
    }

    public JsonAdaptedReview(Review source) {
         this.review = source.getDescription() + " " + source.getCost() + " " + source.getRating();
    }

    @JsonValue
    public String getReview() {
        return review;
    }

    public Review toModelType() throws IllegalValueException {
        String toTest = this.review.trim();
        int reviewRating = Integer.valueOf(toTest.charAt(toTest.length() - 1));
        String withoutRating = toTest.substring(0,toTest.length() -3);
        double reviewCost = Double.valueOf(withoutRating.substring(withoutRating.lastIndexOf(" ")));
        String reviewDescription = withoutRating.substring(0, withoutRating.lastIndexOf(" ") - 1);

        if(!Review.isValidReview(reviewDescription, reviewCost, reviewRating)) {
            throw new IllegalValueException(Review.REVIEW_CONSTRAINTS);
        }
        return new Review(reviewDescription, reviewCost, reviewRating);
    }
}
