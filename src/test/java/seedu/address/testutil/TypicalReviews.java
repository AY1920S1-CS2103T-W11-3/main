package seedu.address.testutil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import seedu.address.model.eatery.Review;

/**
 * Contains a list of typical reviews used for testing.
 */
public class TypicalReviews {

    private static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private static Date date;
    static {
        try {
            date = dateFormat.parse("22/11/2019");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    public static final Review REVIEW_1 = new Review("good", 3, 4, date);
    public static final Review REVIEW_2 = new Review("bad", 4, 3, date);

    public TypicalReviews() {
    }
}
