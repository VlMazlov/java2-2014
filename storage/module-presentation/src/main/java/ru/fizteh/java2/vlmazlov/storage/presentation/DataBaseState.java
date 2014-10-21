package ru.fizteh.java2.vlmazlov.storage.presentation;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.fizteh.java2.vlmazlov.storage.core.generics.GenericTable;
import ru.fizteh.java2.vlmazlov.storage.core.generics.GenericTableProvider;
import ru.fizteh.java2.vlmazlov.storage.shell.ShellState;

import javax.annotation.Resource;

@Component
@Lazy
public class DataBaseState<V, T extends GenericTable<V>> implements ShellState {
    private GenericTable<V> activeTable;

    @Resource(name = "tableProvider")
    private final GenericTableProvider<V, T> provider;

    public DataBaseState() {
        provider = null;
    }

    public GenericTable<V> getActiveTable() {
        return activeTable;
    }

    public void setActiveTable(GenericTable<V> newActiveTable) {
        activeTable = newActiveTable;
    }

    public GenericTableProvider<V, T> getProvider() {
        return provider;
    }

    public String getStateDescription() {
        return ("provider: " + provider + ", active table: " + activeTable);
    }
}
