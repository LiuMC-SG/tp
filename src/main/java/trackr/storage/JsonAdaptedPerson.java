package trackr.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import trackr.commons.exceptions.IllegalValueException;
import trackr.model.commons.Tag;
import trackr.model.person.Person;
import trackr.model.person.PersonAddress;
import trackr.model.person.PersonEmail;
import trackr.model.person.PersonName;
import trackr.model.person.PersonPhone;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                             @JsonProperty("email") String email, @JsonProperty("address") String address,
                             @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getPersonName().getName();
        phone = source.getPersonPhone().personPhone;
        email = source.getPersonEmail().personEmail;
        address = source.getPersonAddress().personAddress;
        tagged.addAll(source.getPersonTags().stream()
                              .map(JsonAdaptedTag::new)
                              .collect(Collectors.toList()));
    }

    public PersonName getPersonName(String errorMsg) throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(errorMsg);
        }
        if (!PersonName.isValidName(name)) {
            throw new IllegalValueException(PersonName.MESSAGE_CONSTRAINTS);
        }
        return new PersonName(name);
    }

    public PersonPhone getPersonPhone(String errorMsg) throws IllegalValueException {
        if (phone == null) {
            throw new IllegalValueException(errorMsg);
        }
        if (!PersonPhone.isValidPersonPhone(phone)) {
            throw new IllegalValueException(PersonPhone.MESSAGE_CONSTRAINTS);
        }
        return new PersonPhone(phone);
    }

    public PersonEmail getPersonEmail(String errorMsg) throws IllegalValueException {
        if (email == null) {
            throw new IllegalValueException(errorMsg);
        }
        if (!PersonEmail.isValidPersonEmail(email)) {
            throw new IllegalValueException(PersonEmail.MESSAGE_CONSTRAINTS);
        }
        return new PersonEmail(email);
    }

    public PersonAddress getPersonAddress(String errorMsg) throws IllegalValueException {
        if (address == null) {
            throw new IllegalValueException(errorMsg);
        }
        if (!PersonAddress.isValidPersonAddress(address)) {
            throw new IllegalValueException(PersonAddress.MESSAGE_CONSTRAINTS);
        }
        return new PersonAddress(address);
    }

    public Set<Tag> getTags() throws IllegalValueException {
        final List<Tag> supplierTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            supplierTags.add(tag.toModelType());
        }
        return new HashSet<>(supplierTags);
    }

}
