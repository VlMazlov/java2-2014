package ru.fizteh.java2.vlmazlov.marketplace.web.ui.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.fizteh.java2.vlmazlov.marketplace.api.GenericManager;
import ru.fizteh.java2.vlmazlov.marketplace.model.Trader;
import ru.fizteh.java2.vlmazlov.marketplace.model.TraderDescription;
import ru.fizteh.java2.vlmazlov.marketplace.model.WareDescription;

import javax.annotation.PostConstruct;

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
