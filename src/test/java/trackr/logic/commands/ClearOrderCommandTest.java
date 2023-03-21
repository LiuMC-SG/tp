package trackr.logic.commands;

import static trackr.logic.commands.CommandTestUtil.assertCommandSuccess;
import static trackr.testutil.TypicalOrders.getTypicalOrderList;
import static trackr.testutil.TypicalSuppliers.getTypicalSupplierList;
import static trackr.testutil.TypicalTasks.getTypicalTaskList;

import org.junit.jupiter.api.Test;

import trackr.logic.commands.order.ClearOrderCommand;
import trackr.model.Model;
import trackr.model.ModelEnum;
import trackr.model.ModelManager;
import trackr.model.UserPrefs;

public class ClearOrderCommandTest {

    @Test
    public void execute_emptyOrderList_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearOrderCommand(),
                model,
                String.format(ClearOrderCommand.MESSAGE_SUCCESS, ModelEnum.ORDER),
                expectedModel);
    }

    @Test
    public void execute_nonEmptyOrderList_success() {
        Model model = new ModelManager(getTypicalSupplierList(), getTypicalTaskList(),
                getTypicalOrderList(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalSupplierList(), getTypicalTaskList(),
                getTypicalOrderList(), new UserPrefs());
        expectedModel.setItemList(ModelEnum.ORDER);

        assertCommandSuccess(new ClearOrderCommand(),
                model,
                String.format(ClearOrderCommand.MESSAGE_SUCCESS, ModelEnum.ORDER),
                expectedModel);
    }

}
