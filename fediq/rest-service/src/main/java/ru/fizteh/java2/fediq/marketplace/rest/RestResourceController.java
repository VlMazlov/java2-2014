package ru.fizteh.java2.fediq.marketplace.rest;

import com.google.common.base.Throwables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.fizteh.java2.fediq.marketplace.api.WaresService;
import ru.fizteh.java2.fediq.marketplace.exceptions.BadRequestException;
import ru.fizteh.java2.fediq.marketplace.exceptions.NotFoundException;
import ru.fizteh.java2.fediq.marketplace.model.Ware;
import ru.fizteh.java2.fediq.marketplace.model.WareDescription;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * @author Fedor S. Lavrentyev <a href="mailto:me@fediq.ru"/>
 * @since 06/10/14
 */
@RestController
@RequestMapping("/wares")
public class RestResourceController {
    private static final Logger log = LoggerFactory.getLogger(RestResourceController.class);

    @Autowired
    private WaresService waresService;

    @RequestMapping(value = "/{id}", method = GET)
    public Ware getWare(@PathVariable String id) {
        Ware ware = waresService.getWare(id);
        if (ware == null) {
            throw new NotFoundException();
        }
        return ware;
    }

    @RequestMapping(value = "/", method = POST)
    public Ware createWare(@RequestBody WareDescription description) {
        return waresService.createWare(description);
    }

    @RequestMapping(value = "/{id}", method = PUT)
    public void saveWare(@PathVariable String id, @RequestBody Ware ware) {
        if (id == null) {
            throw new IllegalStateException("Impossible state - no id");
        }
        if (!id.equals(ware.getIdentifier())) {
            throw new BadRequestException();
        }
        waresService.saveWare(ware);
    }

    @RequestMapping(value = "/{id}", method = DELETE)
    public void deleteWare(@PathVariable String id) {
        if (waresService.deleteWare(id)) {
            return;
        } else {
            throw new NotFoundException();
        }
    }

    @RequestMapping(value = "/", method = GET)
    public List<String> listWares() {
        return waresService.listWareIds();
    }





    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody String handleBadRequest(HttpServletRequest req, BadRequestException ex) {
        return "Bad request!";
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody String handleNotFound(HttpServletRequest req, NotFoundException ex) {
        return "404 NOT FOUND";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody String handleException(HttpServletRequest req, Exception ex) {
        log.error("Exception caught", ex);
        return "Exception " + ex + ":\n" + Throwables.getStackTraceAsString(ex);
    }

}
