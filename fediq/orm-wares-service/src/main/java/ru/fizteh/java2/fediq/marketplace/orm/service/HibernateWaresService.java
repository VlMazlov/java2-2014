package ru.fizteh.java2.fediq.marketplace.orm.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fizteh.java2.fediq.marketplace.api.WaresService;
import ru.fizteh.java2.fediq.marketplace.model.Ware;
import ru.fizteh.java2.fediq.marketplace.model.WareDescription;
import ru.fizteh.java2.fediq.marketplace.orm.model.WareEntity;

import java.util.List;

/**
 * @author Fedor S. Lavrentyev <a href="mailto:fediq@oorraa.net"/>
 * @since 10.11.14
 */
@Service
@Transactional
public class HibernateWaresService implements WaresService {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private MeasuresService measuresService;

    @Override
    @Transactional(readOnly = true)
    public Ware getWare(String id) {
        WareEntity wareEntity = (WareEntity) sessionFactory.getCurrentSession().get(WareEntity.class, id);
        if (wareEntity == null) {
            return null;
        }
        return wareEntity.toWare();
    }

    @Override
    @Transactional
    public void saveWare(Ware ware) {
        WareEntity wareEntity = buildEntity(ware);
        sessionFactory.getCurrentSession().save(wareEntity);
    }

    @Override
    @Transactional
    public Ware createWare(WareDescription description) {
        Ware ware = new Ware();
        ware.setIdentifier(RandomStringUtils.randomAlphanumeric(6));
        ware.setMeasuring(description.getMeasuring());
        ware.setName(description.getName());
        saveWare(ware);
        return ware;
    }

    @Override
    @Transactional
    public boolean deleteWare(String id) {
        WareEntity wareEntity = (WareEntity) sessionFactory.getCurrentSession().get(WareEntity.class, id);
        if (wareEntity == null) {
            return false;
        }
        sessionFactory.getCurrentSession().delete(wareEntity);
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional(readOnly = true)
    public List<String> listWareIds() {
        return sessionFactory.getCurrentSession().createQuery("select id from WareEntity").list();
    }

    private WareEntity buildEntity(Ware ware) {
        WareEntity wareEntity = new WareEntity();
        wareEntity.setIdentifier(ware.getIdentifier());
        wareEntity.setName(ware.getName());
        wareEntity.setMeasureId(measuresService.findByName(ware.getMeasuring()).getId());
        return wareEntity;
    }
}
