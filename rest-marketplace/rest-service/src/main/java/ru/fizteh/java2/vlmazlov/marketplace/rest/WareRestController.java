package ru.fizteh.java2.vlmazlov.marketplace.rest;

import org.springframework.web.bind.annotation.*;
import ru.fizteh.java2.vlmazlov.marketplace.model.Ware;
import ru.fizteh.java2.vlmazlov.marketplace.model.WareDescription;

/**
 * Created by vlmazlov on 09.11.14.
 */
@RestController
@RequestMapping(value = "/wares")
public class WareRestController extends GenericRestController<WareDescription, Ware>
{
}
