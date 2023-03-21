package trackr.logic.commands;

import static trackr.logic.commands.CommandTestUtil.assertCommandSuccess;
import static trackr.logic.commands.CommandTestUtil.showSupplierAtIndex;
import static trackr.testutil.TypicalIndexes.INDEX_FIRST_OBJECT;
import static trackr.testutil.TypicalOrders.getTypicalOrderList;
import static trackr.testutil.TypicalSuppliers.getTypicalSupplierList;
import static trackr.testutil.TypicalTasks.getTypicalTaskList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import trackr.logic.commands.supplier.ListSupplierCommand;
import trackr.model.Model;
import trackr.model.ModelEnum;
import trackr.model.ModelManager;
import trackr.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListSupplierCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalSupplierList(), getTypicalTaskList(),
                getTypicalOrderList(), new UserPrefs());
        expectedModel = new ModelManager(model.getSupplierList(), model.getTaskList(),
                model.getOrderList(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListSupplierCommand(),
                model,
                String.format(ListItemCommand.MESSAGE_SUCCESS, ModelEnum.SUPPLIER.toString().toLowerCase()),
                expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showSupplierAtIndex(model, INDEX_FIRST_OBJECT);
        assertCommandSuccess(new ListSupplierCommand(),
                model,
                String.format(ListItemCommand.MESSAGE_SUCCESS, ModelEnum.SUPPLIER.toString().toLowerCase()),
                expectedModel);
    }
}
