package ru.fizteh.java2.fediq.marketplace.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.fizteh.java2.fediq.marketplace.api.WaresService;
import ru.fizteh.java2.fediq.marketplace.model.Ware;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @author Fedor S. Lavrentyev <a href="mailto:fediq@oorraa.net"/>
 * @since 06/10/14
 */
@RestController
@RequestMapping("/wares")
public class RestResourceController {

    @Autowired
    private WaresService waresService;

    @RequestMapping(value = "/{id}", method = GET)
    public Ware getWare(@PathVariable String id) {
        return waresService.getWare(id);
    }
}
