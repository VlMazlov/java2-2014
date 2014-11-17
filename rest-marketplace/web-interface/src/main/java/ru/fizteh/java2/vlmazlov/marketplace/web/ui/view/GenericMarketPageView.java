package ru.fizteh.java2.vlmazlov.marketplace.web.ui.view;

import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.fizteh.java2.vlmazlov.marketplace.api.GenericManager;
import ru.fizteh.java2.vlmazlov.marketplace.api.ManageableEntry;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Math.min;

/**
 * Created by vlmazlov on 08.11.14.
 */
public class GenericMarketPageView<T, V extends ManageableEntry>
{
    private final String PAGE_VIEW_NAME;
    private final String LIST_ATTR;

    private static final String FIRST_SHOWN_PAGE_ATTR = "FIRST_SHOWN_PAGE";
    private static final String VISIBLE_PAGES_LIST_ATTR = "visiblePagesList";
    private static final String ACTIVE_PAGE_ATTR = "activePage";


    private int FIRST_SHOWN_PAGE = 0;
    private static final int MAX_SHOWN_PAGES = 5;
    private static final int MAX_PAGE_SIZE = 5;

    @Autowired
    private GenericManager<T, V> manager;

    public GenericMarketPageView(String pageViewName, String listAttr) {
        PAGE_VIEW_NAME = pageViewName;
        LIST_ATTR = listAttr;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    protected ModelAndView handleMarketPage(@RequestParam(value="pageNum", defaultValue = "0") int pageNum)
    {
        ModelAndView modelAndView = new ModelAndView(PAGE_VIEW_NAME);

        generateView(modelAndView, pageNum);

        return modelAndView;
    }

    protected String add(T description)
    {
        manager.create(description);

        return "redirect:/" + PAGE_VIEW_NAME;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String delete(@RequestParam("id") String id)
    {
        manager.delete(id);

        return "redirect:/" + PAGE_VIEW_NAME;
    }

    @RequestMapping(value = "/left_shift_pages_list", method = RequestMethod.GET)
    public String leftShiftPagesList() {
        if (FIRST_SHOWN_PAGE > 0)
        {
            --FIRST_SHOWN_PAGE;
        }

        return "redirect:/" + PAGE_VIEW_NAME + "?pageNum=" + FIRST_SHOWN_PAGE;
    }

    @RequestMapping(value = "/right_shift_pages_list", method = RequestMethod.GET)
    public String rightShiftPagesList()
    {
        int pagesNum = getPagesNum();

        if (FIRST_SHOWN_PAGE + MAX_SHOWN_PAGES < pagesNum)
        {
            ++FIRST_SHOWN_PAGE;
        }

        //min added in order for it to function when right shift is not possible
        return "redirect:/" + PAGE_VIEW_NAME + "?pageNum=" + min(FIRST_SHOWN_PAGE + MAX_SHOWN_PAGES - 1, pagesNum - 1);
    }

    private List<V> getActivePage(int pageNum)
    {
        List<String> knownIds = manager.list();

        int beginning = min(pageNum * MAX_PAGE_SIZE, knownIds.size());
        int end = min(beginning + MAX_PAGE_SIZE, knownIds.size());

        List<String> visibleIds = knownIds.subList(beginning, end);

        return visibleIds
                .stream()
                .map(manager::get)
                .collect(Collectors.toList());


    }

    private List<Integer> getVisiblePagesList(int pagesNum) {
        return ContiguousSet
                .create(Range.closedOpen(FIRST_SHOWN_PAGE,
                        min(FIRST_SHOWN_PAGE + MAX_SHOWN_PAGES, pagesNum)),
                        DiscreteDomain.integers())
                .asList();
    }

    private int getPagesNum() {
        List<String> knownIds = manager.list();

        int pagesNum = knownIds.size() / MAX_PAGE_SIZE;
        if (knownIds.size() % MAX_PAGE_SIZE != 0) {
            ++pagesNum;
        }

        //there is always at least 1 page

        if (knownIds.size() == 0) {
            pagesNum = 1;
        }

        return pagesNum;
    }

    private void generateView(ModelAndView modelAndView, int pageNum)
    {
        modelAndView
                .addObject(FIRST_SHOWN_PAGE_ATTR, FIRST_SHOWN_PAGE)
                .addObject(VISIBLE_PAGES_LIST_ATTR, getVisiblePagesList(getPagesNum()))
                .addObject(FIRST_SHOWN_PAGE_ATTR, FIRST_SHOWN_PAGE)
                .addObject(LIST_ATTR, getActivePage(pageNum))
                .addObject(ACTIVE_PAGE_ATTR, pageNum);
    }
}
