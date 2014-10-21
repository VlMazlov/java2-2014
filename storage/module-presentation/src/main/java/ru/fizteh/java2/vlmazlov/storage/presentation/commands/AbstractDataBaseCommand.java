package ru.fizteh.java2.vlmazlov.storage.presentation.commands;

import ru.fizteh.java2.vlmazlov.storage.shell.commands.AbstractCommand;
import ru.fizteh.java2.vlmazlov.storage.presentation.DataBaseState;

public abstract class AbstractDataBaseCommand extends AbstractCommand<DataBaseState> {
    public AbstractDataBaseCommand(String name, int argNum) {
        super(name, argNum);
    }
}
