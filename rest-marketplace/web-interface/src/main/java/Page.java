import java.util.Collections;
import java.util.List;

/**
 * Created by vlmazlov on 04.11.14.
 */
public class Page<T>
{
    private final List<T> data;
    private final int pageNumber;

    public Page(List<T> data, int pageNumber) {
        this.data = Collections.unmodifiableList(data);
        this.pageNumber = pageNumber;
    }
}
