package ru.fizteh.java2.fediq.marketplace.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.fizteh.java2.fediq.marketplace.api.WaresService;
import ru.fizteh.java2.fediq.marketplace.exceptions.BadRequestException;
import ru.fizteh.java2.fediq.marketplace.model.Ware;
import ru.fizteh.java2.fediq.marketplace.model.WareDescription;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Fedor S. Lavrentyev <a href="mailto:me@fediq.ru"/>
 * @since 13/10/14
 */
@Controller
public class WebMarketplaceController {

    public static final String INDEX_VIEW_NAME = "index";
    public static final String WARES_VIEW_NAME = "wares";

    public static final String WARES_LIST_ATTR = "waresList";
    public static final String TOTAL_COUNT_ATTR = "totalCount";
    public static final String PAGE_ATTR = "page";

    public static final int PAGE_SIZE = 10;

    @Autowired
    private WaresService waresService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String handleIndex() {
        return INDEX_VIEW_NAME;
    }

    @RequestMapping(value = "/wares", method = RequestMethod.GET)
    public ModelAndView listWares(@RequestParam(value = PAGE_ATTR, defaultValue = "0") int page) {
        Page<Ware> waresPage = loadWaresPage(page);

        ModelAndView result = new ModelAndView(WARES_VIEW_NAME);

        writePageIntoModel(waresPage, result);

        return result;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
    public String addWare(@RequestParam("name") String name, @RequestParam("measuring") String measuring) {
        WareDescription description = new WareDescription();
        description.setName(name);
        description.setMeasuring(measuring);
        waresService.createWare(description);

        return "redirect:/wares";
    }

    @RequestMapping(value = "/del", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
    public String deleteWare(@RequestParam("id") String id) {
        waresService.deleteWare(id);
        return "redirect:/wares";
    }

    private Page<Ware> loadWaresPage(int pageNumber) {
        if (pageNumber < 0) {
            throw new BadRequestException("Incorrect page number: " + pageNumber);
        }

        List<String> wareIds = waresService.listWareIds();
        int totalCount = wareIds.size();

        int offset = pageNumber * PAGE_SIZE;

        offset = offset > totalCount ? totalCount : offset;
        int limit = PAGE_SIZE < totalCount - offset ? PAGE_SIZE : totalCount - offset;

        List<String> resultWareIds = wareIds.subList(offset, offset + limit);
        List<Ware> waresPageData = resultWareIds
                .stream()
                .map(waresService::getWare)
                .collect(Collectors.toList());

        return new Page<>(waresPageData, totalCount, pageNumber);
    }

    private void writePageIntoModel(Page<Ware> waresPage, ModelAndView result) {
        result
                .addObject(WARES_LIST_ATTR, waresPage.getData())
                .addObject(TOTAL_COUNT_ATTR, waresPage.getTotalCount())
                .addObject(PAGE_ATTR, waresPage.getPageNumber());
    }
}
