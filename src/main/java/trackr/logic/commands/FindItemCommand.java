package trackr.logic.commands;

import static java.util.Objects.requireNonNull;
import static trackr.commons.util.CollectionUtil.requireAllNonNull;

import java.util.function.Predicate;

import trackr.commons.core.Messages;
import trackr.model.Model;
import trackr.model.ModelEnum;
import trackr.model.item.Item;

/**
 * Finds and lists all items in item list.
 */
public abstract class FindItemCommand<T extends Item> extends Command {

    private final ModelEnum modelEnum;
    private final Predicate<Item> predicate;

    /**
     * Creates an FindItemCommand to find all {@code Item} that match the predicate.
     */
    public FindItemCommand(Predicate<Item> predicate, ModelEnum modelEnum) {
        requireAllNonNull(predicate, modelEnum);
        this.predicate = predicate;
        this.modelEnum = modelEnum;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredItemList(predicate, modelEnum);
        return new CommandResult(String.format(Messages.MESSAGE_ITEMS_LISTED_OVERVIEW,
                model.getFilteredItemList(modelEnum).size(),
                modelEnum.toString().toLowerCase()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindItemCommand // instanceof handles nulls
                && predicate.equals(((FindItemCommand) other).predicate)); // state check
    }
}