package ru.fizteh.java2.fediq.marketplace.orm.service;

import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fizteh.java2.fediq.marketplace.orm.model.MeasuringEntity;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Fedor S. Lavrentyev <a href="mailto:fediq@oorraa.net"/>
 * @since 11.11.14
 */
@Service
@Transactional
public class MeasuresService {
    private static final Logger log = LoggerFactory.getLogger(MeasuresService.class);

    @Autowired
    private SessionFactory sessionFactory;


    private final AtomicBoolean firstRun = new AtomicBoolean(false);

    @Transactional
    public void initMeasures() {
        if (!firstRun.compareAndSet(false, true)) {
            return;
        }

        for (String s : new String[]{"kg", "g", "mg", "u", "m"}) {
            createIfNotExists(s);
        }
    }

    @Transactional
    public MeasuringEntity createIfNotExists(String name) {
        MeasuringEntity result = findByName(name);
        if (result == null) {
            result = new MeasuringEntity(name);
            sessionFactory.getCurrentSession().save(result);
            log.info("{} measure created", name);
        }
        return result;
    }

    @Transactional(readOnly = true)
    public MeasuringEntity findByName(String name) {
        // Это костыль исключительно для того, чтобы продемонстрировать наполнение базы через код
        initMeasures();

        Session session = sessionFactory.getCurrentSession();
        return (MeasuringEntity) session
                .createCriteria(MeasuringEntity.class)
                .add(Restrictions.eq("name", name))
                .uniqueResult();
    }

}
