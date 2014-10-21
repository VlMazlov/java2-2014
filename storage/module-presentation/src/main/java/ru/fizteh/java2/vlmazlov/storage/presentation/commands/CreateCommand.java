package ru.fizteh.java2.vlmazlov.storage.presentation.commands;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.fizteh.java2.vlmazlov.storage.shell.commands.api.CommandFailException;
import ru.fizteh.java2.vlmazlov.storage.presentation.DataBaseState;

import java.io.OutputStream;

@Lazy
@Component
public class CreateCommand extends AbstractDataBaseCommand {
    public CreateCommand() {
        super("create", 1);
    }

    public void execute(String[] args, DataBaseState state, OutputStream out) throws CommandFailException {
        String tablename = args[0];

        if (state.getProvider().getTable(tablename) != null) {
            displayMessage(tablename + " exists" + SEPARATOR, out);
            return;
        }

        try {
            state.getProvider().createTable(tablename, null);
        } catch (IllegalArgumentException ex) {
            displayMessage("operation failed: " + ex.getMessage() + SEPARATOR, out);
            return;
        }

        displayMessage("created" + SEPARATOR, out);
    }
}
