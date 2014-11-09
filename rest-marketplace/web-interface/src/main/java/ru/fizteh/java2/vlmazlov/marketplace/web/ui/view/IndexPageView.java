package ru.fizteh.java2.vlmazlov.marketplace.web.ui.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.fizteh.java2.vlmazlov.marketplace.web.urlmanager.URLManager;

/**
 * Created by vlmazlov on 07.11.14.
 */
@Controller
public class IndexPageView
{
    @Autowired
    private
    URLManager urlManager;

    private static final String INDEX_VIEW_NAME = "index";

    private static final String HOST_VIEW_ATTR="host";
    private static final String PORT_VIEW_ATTR="port";

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView handleIndexPage()
    {
        ModelAndView indexModelAndView = new ModelAndView(INDEX_VIEW_NAME);

        return writeHostAndPortIntoView(indexModelAndView);
    }

    ModelAndView writeHostAndPortIntoView(ModelAndView modelAndView) {
        return modelAndView
                .addObject(HOST_VIEW_ATTR, urlManager.getHost())
                .addObject(PORT_VIEW_ATTR, urlManager.getPort());
    }
}
