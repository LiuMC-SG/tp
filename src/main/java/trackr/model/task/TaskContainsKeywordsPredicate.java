package trackr.model.task;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import trackr.commons.util.StringUtil;
import trackr.model.item.Item;

/**
 * Tests that a {@code Task}'s {@code TaskName}, {@code TaskDeadline}, {@code TaskStatus} matches the keywords given.
 */
public class TaskContainsKeywordsPredicate extends TaskDescriptor implements Predicate<Item> {
    private List<String> taskNameKeywords;

    public TaskContainsKeywordsPredicate() {
        super();
    }

    /**
     * Copy constructor.
     */
    public TaskContainsKeywordsPredicate(TaskContainsKeywordsPredicate toCopy) {
        setTaskNameKeywords(toCopy.taskNameKeywords);
        setTaskDeadline(toCopy.getTaskDeadline().isPresent() ? toCopy.getTaskDeadline().get() : null);
        setTaskStatus(toCopy.getTaskStatus().isPresent() ? toCopy.getTaskStatus().get() : null);
    }

    public void setTaskNameKeywords(List<String> taskNameKeywords) {
        this.taskNameKeywords = taskNameKeywords;
    }

    public Optional<List<String>> getTaskNameKeywords() {
        return Optional.ofNullable(taskNameKeywords);
    }

    public boolean isAnyFieldPresent() {
        return isAnyFieldNonNull() || taskNameKeywords != null;
    }

    @Override
    public boolean test(Item item) {
        if (!(item instanceof Task)) {
            return false;
        }

        Task task = (Task) item;

        boolean isTaskNameMatch;
        boolean isTaskDeadlineMatch;
        boolean isTaskStatusMatch;

        if (taskNameKeywords != null) {
            isTaskNameMatch = taskNameKeywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(task.getTaskName().getName(), keyword));
        } else {
            // Default value since no name keyword is set
            isTaskNameMatch = true;
        }

        if (getTaskDeadline().isPresent()) {
            isTaskDeadlineMatch = getTaskDeadline().get().equals(task.getTaskDeadline());
        } else {
            // Default value since no deadline keyword is set
            isTaskDeadlineMatch = true;
        }

        if (getTaskStatus().isPresent()) {
            isTaskStatusMatch = getTaskStatus().get().equals(task.getTaskStatus());
        } else {
            // Default value since no status keyword is set
            isTaskStatusMatch = true;
        }

        return isTaskNameMatch && isTaskDeadlineMatch && isTaskStatusMatch;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TaskContainsKeywordsPredicate)) {
            return false;
        }

        // state check
        TaskContainsKeywordsPredicate predicate = (TaskContainsKeywordsPredicate) other;

        return getTaskNameKeywords().equals(predicate.getTaskNameKeywords())
                && getTaskDeadline().equals(predicate.getTaskDeadline())
                && getTaskStatus().equals(predicate.getTaskStatus());
    }

}
