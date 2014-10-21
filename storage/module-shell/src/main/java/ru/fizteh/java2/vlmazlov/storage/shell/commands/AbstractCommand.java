package ru.fizteh.java2.vlmazlov.storage.shell.commands;

import ru.fizteh.java2.vlmazlov.storage.shell.commands.api.Command;
import ru.fizteh.java2.vlmazlov.storage.shell.commands.api.CommandFailException;
import ru.fizteh.java2.vlmazlov.storage.shell.commands.api.UserInterruptionException;

import java.io.IOException;
import java.io.OutputStream;

public abstract class AbstractCommand<T> implements Command<T> {
    protected static final String SEPARATOR = System.getProperty("line.separator");
    private final String name;
    private final int argNum;

    public String getName() {
        return name;
    }

    public int getArgNum() {
        return argNum;
    }

    protected void displayMessage(String message, OutputStream out) throws CommandFailException {
        try {
            out.write(message.getBytes());
        } catch (IOException ex) {
            throw new CommandFailException(getName() + ": Unable to display result message");
        }
    }

    protected AbstractCommand(String name, int argNum) {
        this.name = name;
        this.argNum = argNum;
    }

    public abstract void execute(String[] args, T state, OutputStream out)
    throws CommandFailException, UserInterruptionException;
}
