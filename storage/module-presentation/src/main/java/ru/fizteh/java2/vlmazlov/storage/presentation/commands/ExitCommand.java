package ru.fizteh.java2.vlmazlov.storage.presentation.commands;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.fizteh.java2.vlmazlov.storage.presentation.DataBaseState;
import ru.fizteh.java2.vlmazlov.storage.shell.commands.api.UserInterruptionException;
import java.io.OutputStream;

@Lazy
@Component
public class ExitCommand extends AbstractDataBaseCommand {
    public ExitCommand() {
        super("exit", 0);
    }

    public void execute(String[] args, DataBaseState state, OutputStream out) throws UserInterruptionException {
        throw new UserInterruptionException();
    }
}
