package ru.fizteh.java2.vlmazlov.storage.presentation.commands;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.fizteh.java2.vlmazlov.storage.shell.commands.api.CommandFailException;
import ru.fizteh.java2.vlmazlov.storage.presentation.DataBaseState;

import java.io.OutputStream;

@Lazy
@Component
public class UseCommand extends AbstractDataBaseCommand {
    public UseCommand() {
        super("use", 1);
    }

    public void execute(String[] args, DataBaseState state, OutputStream out) throws CommandFailException {
        String tablename = args[0];

        if ((state.getActiveTable() != null) && (state.getActiveTable().getDiffCount() != 0)) {
            displayMessage(state.getActiveTable().getDiffCount() + " unsaved changes" + SEPARATOR, out);
            return;
        }

        try {
            if (state.getProvider().getTable(tablename) == null) {
                displayMessage(tablename + " not exists" + SEPARATOR, out);
            } else {
                state.setActiveTable(state.getProvider().getTable(tablename));

                displayMessage("using " + tablename + SEPARATOR, out);
            }
        } catch (IllegalArgumentException ex) {
            displayMessage("operation failed: " + ex.getMessage() + SEPARATOR, out);
        }
    }
}
