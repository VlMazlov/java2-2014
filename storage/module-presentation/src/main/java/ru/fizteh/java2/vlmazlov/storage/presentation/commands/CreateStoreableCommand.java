package ru.fizteh.java2.vlmazlov.storage.presentation.commands;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.fizteh.java2.vlmazlov.storage.presentation.DataBaseState;
import ru.fizteh.java2.vlmazlov.storage.core.StoreableTableProvider;
import ru.fizteh.java2.vlmazlov.storage.utils.TypeName;
import ru.fizteh.java2.vlmazlov.storage.shell.commands.api.CommandFailException;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@Lazy
@Component
public class CreateStoreableCommand extends AbstractDataBaseCommand {
    public CreateStoreableCommand() {
        super("create", 2);
    }

    public void execute(String[] args, DataBaseState state, OutputStream out) throws CommandFailException {
        if (!(state.getProvider() instanceof StoreableTableProvider)) {
            throw new CommandFailException("create storeable: Incorrect provider type");
        }

        String tablename = args[0];
        String[] names = args[1].substring(1, args[1].length() - 1).split("\\s", 0);
        List<Class<?>> types = new ArrayList<Class<?>>(names.length);

        for (int i = 0; i < names.length; ++i) {
            String name = names[i];

            Class<?> clazz = TypeName.getClassByName(name);

            if (clazz == null) {
                displayMessage("wrong type (" + name + " is not a valid type)" + SEPARATOR, out);
                return;
            }

            types.add(i, clazz);
        }

        if (state.getProvider().getTable(tablename) != null) {
            displayMessage(tablename + " exists" + SEPARATOR, out);
            return;
        }

        try {
            ((StoreableTableProvider) state.getProvider()).createTable(tablename, types);
        } catch (IOException ex) {
            throw new CommandFailException("create storeable: unable to write signature");
        } catch (IllegalArgumentException ex) {
            displayMessage(ex.getMessage() + SEPARATOR, out);
            return;
        }
        displayMessage("created" + SEPARATOR, out);
    }
}
