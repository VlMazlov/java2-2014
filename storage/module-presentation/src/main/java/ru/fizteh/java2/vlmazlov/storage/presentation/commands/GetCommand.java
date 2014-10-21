package ru.fizteh.java2.vlmazlov.storage.presentation.commands;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.fizteh.java2.vlmazlov.storage.shell.commands.api.CommandFailException;
import ru.fizteh.java2.vlmazlov.storage.presentation.DataBaseState;

import java.io.OutputStream;

@Lazy
@Component
public class GetCommand extends AbstractDataBaseCommand {
    public GetCommand() {
        super("get", 1);
    }

    public void execute(String[] args, DataBaseState state, OutputStream out) throws CommandFailException {
        if (state.getActiveTable() == null) {
            displayMessage("no table" + SEPARATOR, out);
            return;
        }

        String key = args[0];
        Object value;
        try {
            value = state.getActiveTable().get(key);
        } catch (IllegalArgumentException ex) {
            displayMessage("operation failed: " + ex.getMessage() + SEPARATOR, out);
            return;
        }

        if (value == null) {
            displayMessage("not found" + SEPARATOR, out);
        } else {
            displayMessage("found" + SEPARATOR
                + state.getProvider().serialize(state.getActiveTable(), value) + SEPARATOR, out);
        }
    }
}
