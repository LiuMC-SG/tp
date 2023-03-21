package trackr.model.order;

import static java.util.Objects.requireNonNull;
import static trackr.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import trackr.model.item.Item;
import trackr.model.order.exceptions.DuplicateOrderException;
import trackr.model.order.exceptions.OrderNotFoundException;

/**
 * A list of orders that enforces uniqueness between its elements and does not allow nulls.
 * an order is considered unique by comparing using {@code Order#isSameItem(Item)}. As such, adding and updating of
 * orders uses Order#isSameItem(Item) for equality to ensure that the Order being added or updated is unique
 * in terms of identity in the UniqueOrderList. However, the removal of an Order uses Order#equals(Object) to
 * ensure that the Order with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Order#isSameItem(Item)
 */
public class UniqueOrderList implements Iterable<Order> {
    private final ObservableList<Order> internalList = FXCollections.observableArrayList();
    private final ObservableList<Order> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent order as the given argument.
     */
    public boolean contains(Order toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameItem);
    }

    /**
     * Adds a order to the list.
     * The order must not already exist in the list.
     */
    public void add(Order toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateOrderException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the order {@code target} in the list with {@code editedOrder}.
     * {@code target} must exist in the list.
     * The order identity of {@code editedOrder} must not be the same as another existing order in the list.
     */
    public void setOrder(Order target, Order editedOrder) {
        requireAllNonNull(target, editedOrder);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new OrderNotFoundException();
        }

        if (!target.isSameItem(editedOrder) && contains(editedOrder)) {
            throw new DuplicateOrderException();
        }

        internalList.set(index, editedOrder);
    }

    /**
     * Removes the equivalent orders from the list.
     * The orders must exist in the list.
     */
    public void remove(Order toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new OrderNotFoundException();
        }
    }

    public void setOrders(UniqueOrderList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code orders}.
     * {@code orders} must not contain duplicate orders.
     */
    public void setOrders(List<Order> orders) {
        requireAllNonNull(orders);
        if (!ordersAreUnique(orders)) {
            throw new DuplicateOrderException();
        }

        internalList.setAll(orders);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Order> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Order> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueOrderList // instanceof handles nulls
                && internalList.equals(((UniqueOrderList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code orders} contains only unique orders.
     */
    private boolean ordersAreUnique(List<Order> orders) {
        for (int i = 0; i < orders.size() - 1; i++) {
            for (int j = i + 1; j < orders.size(); j++) {
                if (orders.get(i).isSameItem(orders.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

}
