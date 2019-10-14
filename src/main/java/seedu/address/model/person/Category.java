package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Eatery's category in the EatMe application.
 */
public class Category {

    public static final String MESSAGE_CONSTRAINTS =
            "Categories should only contain alphabets and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "([A-Za-z]\\w+[ A-Za-z]*)";

    private static int categoryId = -1;
    private int id;
    private String name;

    /**
     * Constructs a {@code Category}.
     *
     * @param name A valid category name.
     */
    public Category(String name) {
        requireNonNull(name);
        checkArgument(isValidCategory(name), MESSAGE_CONSTRAINTS);
        this.id = categoryId + 1;
        this.name = name;
    }

    /**
     * Returns true if a given string is a valid category.
     */
    public static boolean isValidCategory(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Category)
                && name.equals(((Category) other).name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
