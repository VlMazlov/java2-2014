package ru.fizteh.java2.vlmazlov.storage.presentation.commands;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.fizteh.java2.vlmazlov.storage.shell.commands.api.CommandFailException;
import ru.fizteh.java2.vlmazlov.storage.presentation.DataBaseState;

import java.io.OutputStream;

@Lazy
@Component
public class DropCommand extends AbstractDataBaseCommand {
    public DropCommand() {
        super("drop", 1);
    }

    public void execute(String[] args, DataBaseState state, OutputStream out) throws CommandFailException {
        String tablename = args[0];

        if (state.getProvider().getTable(tablename) == null) {
            displayMessage(tablename + " not exists" + SEPARATOR, out);
            return;
        }

        if (state.getProvider().getTable(tablename) == state.getActiveTable()) {
            state.setActiveTable(null);
        }

        state.getProvider().removeTable(tablename);

        displayMessage("dropped" + SEPARATOR, out);
    }
}
