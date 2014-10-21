package ru.fizteh.java2.vlmazlov.storage.main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.fizteh.java2.vlmazlov.storage.api.Storeable;
import ru.fizteh.java2.vlmazlov.storage.presentation.DataBasePresenter;
import ru.fizteh.java2.vlmazlov.storage.core.StoreableTable;

public abstract class Main {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        context.registerShutdownHook();

        DataBasePresenter<Storeable, StoreableTable> presenter = context.getBean(DataBasePresenter.class);
        presenter.present(args);
    }
}
