package ru.fizteh.java2.vlmazlov.storage.shell.commands.api;

import java.io.OutputStream;

public interface Command<T> {
    String getName();

    int getArgNum();

    void execute(String[] args, T state, OutputStream out) 
    throws CommandFailException, UserInterruptionException;

}
