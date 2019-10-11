package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.AddressBook;
import seedu.address.testutil.TypicalEateries;

public class JsonSerializableAddressBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_EATERIES_FILE = TEST_DATA_FOLDER.resolve("typicalEateriesAddressBook.json");
    private static final Path INVALID_EATERY_FILE = TEST_DATA_FOLDER.resolve("invalidEateryAddressBook.json");
    private static final Path DUPLICATE_EATERY_FILE = TEST_DATA_FOLDER.resolve("duplicateEateryAddressBook.json");

    @Test
    public void toModelType_typicalEateriesFile_success() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_EATERIES_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook typicalEaterysAddressBook = TypicalEateries.getTypicalAddressBook();
        assertEquals(addressBookFromFile, typicalEaterysAddressBook);
    }

    @Test
    public void toModelType_invalidEateryFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(INVALID_EATERY_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateEateries_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_EATERY_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableAddressBook.MESSAGE_DUPLICATE_EATERY,
                dataFromFile::toModelType);
    }

}
