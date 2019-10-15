package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.eatery.Eatery;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;

    private FilteredList<Eatery> filteredTodo;
    private FilteredList<Eatery> filteredEateries;


    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);

        filteredEateries = new FilteredList<>(this.addressBook.getEateryList());
        filteredTodo = new FilteredList<>(this.addressBook.getTodoList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasEatery(Eatery eatery) {
        requireNonNull(eatery);
        return addressBook.hasEatery(eatery);
    }

    @Override
    public void deleteEatery(Eatery target) {
        addressBook.removeEatery(target);
    }

    @Override
    public void addEatery(Eatery eatery) {
        addressBook.addEatery(eatery);
        updateFilteredEateryList(PREDICATE_SHOW_ALL_EATERIES);
    }

    @Override
    public void setEatery(Eatery target, Eatery editedEatery) {
        requireAllNonNull(target, editedEatery);

        addressBook.setEatery(target, editedEatery);
    }

    //=========== Filtered Eatery List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Eatery} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Eatery> getFilteredEateryList() {
        return filteredEateries;
    }

    @Override
    public ObservableList<Eatery> getFilteredTodoList() {
        return filteredTodo;
    }

    @Override
    public void updateFilteredEateryList(Predicate<Eatery> predicate) {
        requireNonNull(predicate);
        if (addressBook.modeStatus()) {
            filteredEateries.setPredicate(predicate);
        } else {
            filteredTodo.setPredicate(predicate);
        }
    }

    //=========== General =============================================================

    @Override
    public void toggle() {
        addressBook.toggle();
    }

    @Override
    public boolean modeStatus() {
        return addressBook.modeStatus();
    }


    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return addressBook.equals(other.addressBook)
                && userPrefs.equals(other.userPrefs)
                && filteredEateries.equals(other.filteredEateries);
    }

}
