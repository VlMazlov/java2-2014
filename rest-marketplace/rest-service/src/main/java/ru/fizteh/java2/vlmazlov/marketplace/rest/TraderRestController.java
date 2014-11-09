package ru.fizteh.java2.vlmazlov.marketplace.rest;

import org.springframework.web.bind.annotation.*;
import ru.fizteh.java2.vlmazlov.marketplace.model.Trader;
import ru.fizteh.java2.vlmazlov.marketplace.model.TraderDescription;

/**
 * Created by vlmazlov on 09.11.14.
 */
@RestController
@RequestMapping(value = "/traders")
public class TraderRestController extends GenericRestController<TraderDescription, Trader>
{
}
