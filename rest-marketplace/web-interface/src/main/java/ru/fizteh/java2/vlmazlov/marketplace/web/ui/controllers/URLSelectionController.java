package ru.fizteh.java2.vlmazlov.marketplace.web.ui.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.fizteh.java2.vlmazlov.marketplace.web.urlmanager.URLManager;

/**
 * Created by vlmazlov on 07.11.14.
 */
@Controller
public class URLSelectionController
{
    @Autowired
    private
    URLManager urlManager;

    @RequestMapping(value = "/setHost", method = RequestMethod.POST)
    public String setHost(@RequestParam("host") String host)
    {
        urlManager.setHost(host);
        return "redirect:/";
    }

    @RequestMapping(value = "/setPort", method = RequestMethod.POST)
    public String setPort(@RequestParam("port") int port)
    {
        urlManager.setPort(port);
        return "redirect:/";
    }
}
