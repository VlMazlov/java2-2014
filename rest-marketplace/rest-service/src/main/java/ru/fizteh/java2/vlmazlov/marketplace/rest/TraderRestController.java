package ru.fizteh.java2.vlmazlov.marketplace.rest;

import com.google.common.base.Throwables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.fizteh.java2.vlmazlov.marketplace.api.GenericManager;
import ru.fizteh.java2.vlmazlov.marketplace.execptions.BadRequestException;
import ru.fizteh.java2.vlmazlov.marketplace.execptions.ForbiddenException;
import ru.fizteh.java2.vlmazlov.marketplace.execptions.NotFoundException;
import ru.fizteh.java2.vlmazlov.marketplace.model.Trader;
import ru.fizteh.java2.vlmazlov.marketplace.model.TraderDescription;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Created by vlmazlov on 09.11.14.
 */
@RestController
@RequestMapping(value = "/traders")
public class TraderRestController extends GenericRestController<TraderDescription, Trader>
{
}
