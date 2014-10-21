package ru.fizteh.java2.vlmazlov.storage.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ru.fizteh.java2.vlmazlov.storage.api.Storeable;
import ru.fizteh.java2.vlmazlov.storage.core.StoreableTable;
import ru.fizteh.java2.vlmazlov.storage.core.generics.GenericTable;
import ru.fizteh.java2.vlmazlov.storage.shell.Shell;
import ru.fizteh.java2.vlmazlov.storage.shell.commands.api.CommandFailException;
import ru.fizteh.java2.vlmazlov.storage.shell.commands.api.UserInterruptionException;
import ru.fizteh.java2.vlmazlov.storage.shell.commands.api.WrongCommandException;
import ru.fizteh.java2.vlmazlov.storage.utils.ValidityCheckFailedException;

import java.io.IOException;

@Service
@Lazy
public class DataBasePresenter<V, T extends GenericTable<V>> {

    //@Resource(name = "dataBaseState")
    @Autowired
    private DataBaseState<Storeable, StoreableTable> state;

    @Autowired
    private Shell<DataBaseState> shell;

    public void present(String[] args) {

        try {
            state.getProvider().read();
        } catch (IOException ex) {
            System.err.println("Unable to retrieve database: " + ex.getMessage());
            System.exit(3);
        } catch (ValidityCheckFailedException ex) {
            System.err.println("Validity check failed: " + ex.getMessage());
            System.exit(4);
        }

        try {
            shell.process(args);
        } catch (WrongCommandException ex) {
            System.err.println(ex.getMessage());
            System.exit(5);
        } catch (CommandFailException ex) {
            System.err.println(ex.getMessage());
            System.exit(6);
        } catch (IllegalArgumentException ex) {
            System.err.println(ex.getMessage());
            System.exit(7);
        } catch (UserInterruptionException ex) {
            //Do nothing
        }

        try {
            state.getProvider().write();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            System.exit(8);
        } catch (ValidityCheckFailedException ex) {
            System.err.println("Validity check failed: " + ex.getMessage());
            System.exit(9);
        }
    }
}
