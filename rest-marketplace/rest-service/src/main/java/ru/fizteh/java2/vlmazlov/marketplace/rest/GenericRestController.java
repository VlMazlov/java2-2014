package ru.fizteh.java2.vlmazlov.marketplace.rest;

import com.google.common.base.Throwables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.fizteh.java2.vlmazlov.marketplace.api.GenericManager;
import ru.fizteh.java2.vlmazlov.marketplace.api.ManageableEntry;
import ru.fizteh.java2.vlmazlov.marketplace.execptions.BadRequestException;
import ru.fizteh.java2.vlmazlov.marketplace.execptions.ForbiddenException;
import ru.fizteh.java2.vlmazlov.marketplace.execptions.NotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Created by vlmazlov on 03.11.14.
 */

public class GenericRestController<T, V extends ManageableEntry>
{
    @Autowired
    private GenericManager<T, V> manager;

    @RequestMapping(value = "/", method = GET)
    protected List<String> list()
    {
        return manager.list();
    }

    @RequestMapping(value = "/{id}", method = GET)
    protected V get(@PathVariable(value = "id") String id)
            throws NotFoundException
    {
        if (id == null) {
            throw new IllegalStateException("Unable to search for a trader with no ID provided");
        }

        V entry = manager.get(id);
        if (entry == null) {
            throw new NotFoundException();
        }

        return entry;
    }

    @RequestMapping(value = "/{id}", method = PUT)
    protected void save(@PathVariable(value = "id") String id, @RequestBody V entry)
            throws BadRequestException
    {

        if (id == null) {
            throw new IllegalStateException("Unable to search: no ID provided");
        }

        if (entry.getIdentifier().equals(id)) {
            throw new BadRequestException();
        }

        manager.save(entry);
    }

    @RequestMapping(value = "/{id}", method = DELETE)
    protected void delete(@PathVariable(value = "id") String id)
            throws NotFoundException
    {
        if (id == null) {
            throw new IllegalStateException("Unable to delete: no ID provided");
        }

        if (!manager.delete(id)) {
            throw new NotFoundException();
        }
    }

    @RequestMapping(value = "/", method = POST)
    protected V create(@RequestBody T description)
    {
        if (description == null) {
            throw new IllegalStateException("Unable to create: no description provided");
        }

        return manager.create(description);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    String handleBadRequest(BadRequestException ex) {
        return "400 BAD REQUEST" + ex.getMessage();
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody String handleNotFound(NotFoundException ex) {
        return "404 NOT FOUND" + ex.getMessage();
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody String handleForbidden(ForbiddenException ex) {
        return "403 FORBIDDEN" + ex.getMessage();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody String handleException(Exception ex)
    {
        return "Exception " + ex + ":\n" + Throwables.getStackTraceAsString(ex);
    }
}
