package ru.fizteh.java2.vlmazlov.marketplace.web.ui.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.fizteh.java2.vlmazlov.marketplace.model.Trader;
import ru.fizteh.java2.vlmazlov.marketplace.model.TraderDescription;

/**
 * Created by vlmazlov on 09.11.14.
 */
@Controller
@RequestMapping(value = "/traders")
public class TraderMarketPageView extends GenericMarketPageView<TraderDescription, Trader>
{
    private static final String PAGE_VIEW_NAME = "traders";
    private static final String LIST_ATTR = "tradersList";

    public TraderMarketPageView() {
        super(PAGE_VIEW_NAME, LIST_ATTR);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addWare(@RequestParam("name") String name, @RequestParam("country") String country) {
        return super.add(new TraderDescription(name, country));
    }
}
