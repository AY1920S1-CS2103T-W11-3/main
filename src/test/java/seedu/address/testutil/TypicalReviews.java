package seedu.address.testutil;

import seedu.address.model.eatery.Review;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TypicalReviews {

    static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public static Review REVIEW_1;

    static {
        try {
            REVIEW_1 = new Review("good", 3, 4, dateFormat.parse("22/11/2019"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static Review REVIEW_2;

    static {
        try {
            REVIEW_2 = new Review("bad", 4, 3, dateFormat.parse("23/11/2019"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
