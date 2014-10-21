package ru.fizteh.java2.vlmazlov.storage.presentation.commands;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.fizteh.java2.vlmazlov.storage.shell.commands.api.CommandFailException;
import ru.fizteh.java2.vlmazlov.storage.presentation.DataBaseState;

import java.io.IOException;
import java.io.OutputStream;

@Lazy
@Component
public class CommitCommand extends AbstractDataBaseCommand {
    public CommitCommand() {
        super("commit", 0);
    }

    public void execute(String[] args, DataBaseState state, OutputStream out) throws CommandFailException {
        if (state.getActiveTable() == null) {
            displayMessage("no table" + SEPARATOR, out);
            return;
        }
        try {
            displayMessage(state.getActiveTable().commit() + SEPARATOR, out);
        } catch (IOException ex) {
            throw new CommandFailException("Commit failed: " + ex.getMessage());
        }
    }
}
