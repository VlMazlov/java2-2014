package ru.fizteh.java2.vlmazlov.marketplace.remote;

import org.springframework.stereotype.Service;
import ru.fizteh.java2.vlmazlov.marketplace.model.Ware;
import ru.fizteh.java2.vlmazlov.marketplace.model.WareDescription;
import ru.fizteh.java2.vlmazlov.marketplace.web.urlmanager.URLManager;

import javax.annotation.Resource;

/**
 * Created by vlmazlov on 03.11.14.
 */
@Service
public class RestClientWareManager extends GenericRestClientManager<WareDescription, Ware>
{
    @Override
    protected String constructURL(String id)
    {
        return urlManager.getUrl() + "/wares/" + id;
    }

    @Override
    protected Class<Ware> getTargetEntryClass()
    {
        return Ware.class;
    }
}
