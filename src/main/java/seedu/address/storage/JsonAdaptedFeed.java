package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.feed.Feed;

/**
 * Jackson-friendly version of {@link Feed}.
 */
class JsonAdaptedFeed {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Feed's %s field is missing!";

    private final String name;
    private final String address;
    private final String data;

    /**
     * Constructs a {@code JsonAdaptedFeed} with the given feed details.
     */
    @JsonCreator
    public JsonAdaptedFeed(@JsonProperty("name") String name,
                           @JsonProperty("address") String address,
                           @JsonProperty("data") String data) {
        this.name = name;
        this.address = address;
        this.data = data;
    }

    /**
     * Converts a given {@code Feed} into this class for Jackson use.
     */
    public JsonAdaptedFeed(Feed source) {
        name = source.getName();
        address = source.getAddress();
        data = source.getData();
    }

    /**
     * Converts this Jackson-friendly adapted feed object into the model's {@code Feed} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted feed.
     */
    public Feed toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "name"));
        }
        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "address"));
        }
        if (data == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "data"));
        }

        return new Feed(name, address, data);
    }

}
