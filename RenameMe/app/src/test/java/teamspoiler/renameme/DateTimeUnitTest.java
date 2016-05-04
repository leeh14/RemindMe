package teamspoiler.renameme;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

/**
 * Created by hirats on 5/4/2016.
 */
public class DateTimeUnitTest {
    public static DateTimeFormatter FORMATTER = DateTimeFormat.forPattern("MM/dd 'at' hh:mm a");

    @Test
    public void categories_init() throws Exception {
        LocalDateTime time = new LocalDateTime(2016, 5, 4, 17, 25);
        System.out.println(FORMATTER.print(time));

    }
}
