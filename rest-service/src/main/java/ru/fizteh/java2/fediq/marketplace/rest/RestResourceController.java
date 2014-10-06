package ru.fizteh.java2.fediq.marketplace.rest;

import com.google.common.base.Throwables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.fizteh.java2.fediq.marketplace.api.WaresService;
import ru.fizteh.java2.fediq.marketplace.model.Ware;
import ru.fizteh.java2.fediq.marketplace.model.WareDescription;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * @author Fedor S. Lavrentyev <a href="mailto:me@fediq.ru"/>
 * @since 06/10/14
 */
@RestController
@RequestMapping("/wares")
public class RestResourceController {

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
        Ware ware = new Ware();
        ware.setMeasuring(description.getMeasuring());
        ware.setWare(description.getWare());

        ware.setIdentifier(UUID.randomUUID().toString());

        waresService.saveWare(ware);
        return ware;
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
        return "Exception " + ex + ":\n" + Throwables.getStackTraceAsString(ex);
    }

    public static class BadRequestException extends RuntimeException { }

    public static class NotFoundException extends RuntimeException { }
}
