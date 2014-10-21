package ru.fizteh.java2.vlmazlov.storage.presentation.commands;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.fizteh.java2.vlmazlov.storage.shell.commands.api.CommandFailException;
import ru.fizteh.java2.vlmazlov.storage.presentation.DataBaseState;

import java.io.OutputStream;
import java.text.ParseException;

@Lazy
@Component
public class PutCommand extends AbstractDataBaseCommand {
    public PutCommand() {
        super("put", 2);
    }

    public void execute(String[] args, DataBaseState state, OutputStream out) throws CommandFailException {
        if (state.getActiveTable() == null) {
            displayMessage("no table" + SEPARATOR, out);
            return;
        }

        String key = args[0];
        String value = args[1];
        Object oldValue;

        try {
            oldValue = state.getActiveTable().put(key, state.getProvider().deserialize(state.getActiveTable(), value));
        } catch (ParseException ex) {
            displayMessage("wrong type("
                    + value + " cannot be deserialized as a row for table " + state.getActiveTable().getName()
                    + ": " + ex.getMessage() + ")" + SEPARATOR, out);
            return;
        } catch (IllegalArgumentException ex) {
            displayMessage("operation failed: " + ex.getMessage() + SEPARATOR, out);
            return;
        }

        if (oldValue == null) {
            displayMessage("new" + SEPARATOR, out);
        } else {
            displayMessage("overwrite" + SEPARATOR
                + state.getProvider().serialize(state.getActiveTable(), oldValue) + SEPARATOR, out);
        }
    }
}
