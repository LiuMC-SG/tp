package trackr.logic.parser;

import static trackr.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static trackr.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import trackr.logic.commands.Command;
import trackr.logic.commands.ExitCommand;
import trackr.logic.commands.HelpCommand;
import trackr.logic.commands.order.AddOrderCommand;
import trackr.logic.commands.order.ClearOrderCommand;
import trackr.logic.commands.order.DeleteOrderCommand;
import trackr.logic.commands.order.EditOrderCommand;
import trackr.logic.commands.order.FindOrderCommand;
import trackr.logic.commands.order.ListOrderCommand;
import trackr.logic.commands.supplier.AddSupplierCommand;
import trackr.logic.commands.supplier.ClearSupplierCommand;
import trackr.logic.commands.supplier.DeleteSupplierCommand;
import trackr.logic.commands.supplier.EditSupplierCommand;
import trackr.logic.commands.supplier.FindSupplierCommand;
import trackr.logic.commands.supplier.ListSupplierCommand;
import trackr.logic.commands.task.AddTaskCommand;
import trackr.logic.commands.task.ClearTaskCommand;
import trackr.logic.commands.task.DeleteTaskCommand;
import trackr.logic.commands.task.EditTaskCommand;
import trackr.logic.commands.task.FindTaskCommand;
import trackr.logic.commands.task.ListTaskCommand;
import trackr.logic.parser.exceptions.ParseException;
import trackr.logic.parser.order.AddOrderCommandParser;
import trackr.logic.parser.order.DeleteOrderCommandParser;
import trackr.logic.parser.order.EditOrderCommandParser;
import trackr.logic.parser.order.FindOrderCommandParser;
import trackr.logic.parser.supplier.AddSupplierCommandParser;
import trackr.logic.parser.supplier.DeleteSupplierCommandParser;
import trackr.logic.parser.supplier.EditSupplierCommandParser;
import trackr.logic.parser.supplier.FindSupplierCommandParser;
import trackr.logic.parser.task.AddTaskCommandParser;
import trackr.logic.parser.task.DeleteTaskCommandParser;
import trackr.logic.parser.task.EditTaskCommandParser;
import trackr.logic.parser.task.FindTaskCommandParser;

/**
 * Parses user input.
 */
public class TrackrParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddOrderCommand.COMMAND_WORD:
        case AddOrderCommand.COMMAND_WORD_SHORTCUT:
            return new AddOrderCommandParser().parse(arguments);

        case AddSupplierCommand.COMMAND_WORD:
        case AddSupplierCommand.COMMAND_WORD_SHORTCUT:
            return new AddSupplierCommandParser().parse(arguments);

        case AddTaskCommand.COMMAND_WORD:
        case AddTaskCommand.COMMAND_WORD_SHORTCUT:
            return new AddTaskCommandParser().parse(arguments);

        case EditSupplierCommand.COMMAND_WORD:
        case EditSupplierCommand.COMMAND_WORD_SHORTCUT:
            return new EditSupplierCommandParser().parse(arguments);

        case EditTaskCommand.COMMAND_WORD:
        case EditTaskCommand.COMMAND_WORD_SHORTCUT:
            return new EditTaskCommandParser().parse(arguments);

        case DeleteSupplierCommand.COMMAND_WORD:
        case DeleteSupplierCommand.COMMAND_WORD_SHORTCUT:
            return new DeleteSupplierCommandParser().parse(arguments);
        case EditOrderCommand.COMMAND_WORD:
        case EditOrderCommand.COMMAND_WORD_SHORTCUT:
            return new EditOrderCommandParser().parse(arguments);


        case DeleteTaskCommand.COMMAND_WORD:
        case DeleteTaskCommand.COMMAND_WORD_SHORTCUT:
            return new DeleteTaskCommandParser().parse(arguments);

        case ClearSupplierCommand.COMMAND_WORD:
        case ClearSupplierCommand.COMMAND_WORD_SHORTCUT:
            return new ClearSupplierCommand();

        case DeleteOrderCommand.COMMAND_WORD:
        case DeleteOrderCommand.COMMAND_WORD_SHORTCUT:
            return new DeleteOrderCommandParser().parse(arguments);

        case ClearTaskCommand.COMMAND_WORD:
        case ClearTaskCommand.COMMAND_WORD_SHORTCUT:
            return new ClearTaskCommand();

        case ClearOrderCommand.COMMAND_WORD:
        case ClearOrderCommand.COMMAND_WORD_SHORTCUT:
            return new ClearOrderCommand();

        case FindSupplierCommand.COMMAND_WORD:
        case FindSupplierCommand.COMMAND_WORD_SHORTCUT:
            return new FindSupplierCommandParser().parse(arguments);

        case FindTaskCommand.COMMAND_WORD:
        case FindTaskCommand.COMMAND_WORD_SHORTCUT:
            return new FindTaskCommandParser().parse(arguments);

        case FindOrderCommand.COMMAND_WORD:
        case FindOrderCommand.COMMAND_WORD_SHORTCUT:
            return new FindOrderCommandParser().parse(arguments);

        case ListSupplierCommand.COMMAND_WORD:
        case ListSupplierCommand.COMMAND_WORD_SHORTCUT:
            return new ListSupplierCommand();

        case ListOrderCommand.COMMAND_WORD:
        case ListOrderCommand.COMMAND_WORD_SHORTCUT:
            return new ListOrderCommand();

        case ListTaskCommand.COMMAND_WORD:
        case ListTaskCommand.COMMAND_WORD_SHORTCUT:
            return new ListTaskCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
