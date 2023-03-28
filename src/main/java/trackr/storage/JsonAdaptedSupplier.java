package trackr.storage;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import trackr.commons.exceptions.IllegalValueException;
import trackr.model.commons.Tag;
import trackr.model.person.PersonAddress;
import trackr.model.person.PersonEmail;
import trackr.model.person.PersonName;
import trackr.model.person.PersonPhone;
import trackr.model.person.Supplier;

/**
 * Jackson-friendly version of {@link Supplier}.
 */
class JsonAdaptedSupplier extends JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Supplier's %s field is missing!";

    /**
     * Constructs a {@code JsonAdaptedSupplier} with the given supplier details.
     */
    @JsonCreator
    public JsonAdaptedSupplier(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                               @JsonProperty("email") String email, @JsonProperty("address") String address,
                               @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        super(name, phone, email, address, tagged);
    }

    /**
     * Converts a given {@code Supplier} into this class for Jackson use.
     */
    public JsonAdaptedSupplier(Supplier source) {
        super(source);
    }

    /**
     * Converts this Jackson-friendly adapted supplier object into the model's {@code Supplier} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted supplier.
     */
    public Supplier toModelType() throws IllegalValueException {
        final PersonName modelPersonName = getPersonName(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                PersonName.class.getSimpleName()));

        final PersonPhone modelPersonPhone = getPersonPhone(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                PersonPhone.class.getSimpleName()));

        final PersonEmail modelPersonEmail = getPersonEmail(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                PersonEmail.class.getSimpleName()));

        final PersonAddress modelPersonAddress = getPersonAddress(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                PersonAddress.class.getSimpleName()));

        final Set<Tag> modelTags = getTags();

        return new Supplier(modelPersonName, modelPersonPhone, modelPersonEmail, modelPersonAddress, modelTags);
    }

}
