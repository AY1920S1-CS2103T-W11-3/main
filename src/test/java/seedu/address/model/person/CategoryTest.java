package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class CategoryTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Category(null));
    }

    @Test
    public void constructor_invalidCategory_throwsIllegalArgumentException() {
        String invalidCategory = "";
        assertThrows(IllegalArgumentException.class, () -> new Category(invalidCategory));
    }

    @Test
    public void isValidCategory() {
        //null category
        assertThrows(NullPointerException.class, () -> Category.isValidCategory(null));

        //invalid category
        assertFalse(Category.isValidCategory(""));
        assertFalse(Category.isValidCategory(" "));
        assertFalse(Category.isValidCategory("0"));
        assertFalse(Category.isValidCategory("?"));
        assertFalse(Category.isValidCategory("Chinese?"));
        assertFalse(Category.isValidCategory("0 Chinese?"));

        //valid category
        assertTrue(Category.isValidCategory("Chinese"));
        assertTrue(Category.isValidCategory("chinese"));
        assertTrue(Category.isValidCategory("CHINESE"));
        assertTrue(Category.isValidCategory("Chinese Food"));
    }
}
