package ru.fizteh.java2.vlmazlov.marketplace.remote;

import org.springframework.stereotype.Service;
import ru.fizteh.java2.vlmazlov.marketplace.model.Trader;
import ru.fizteh.java2.vlmazlov.marketplace.model.TraderDescription;
import ru.fizteh.java2.vlmazlov.marketplace.web.urlmanager.URLManager;

import javax.annotation.Resource;

/**
 * Created by vlmazlov on 03.11.14.
 */
@Service
public class RestClientTraderManager extends GenericRestClientManager<TraderDescription, Trader>
{

    @Override
    protected String constructURL(String id)
    {
        return urlManager.getUrl() + "/traders/" + id;
    }

    @Override
    protected Class<Trader> getTargetEntryClass()
    {
        return Trader.class;
    }
}
