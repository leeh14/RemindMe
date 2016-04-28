package teamspoiler.renameme;

import org.joda.time.LocalDateTime;
import org.junit.Test;

import teamspoiler.renameme.DataElements.*;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class DataElementsUnitTest {
    IterableMap<Category> categories;


    @Test
    public void categories_init() throws Exception {
       categories = new IterableMap<Category>();
        String[] categoryNames  = {"groceries", "immunizations", "tests"};
        for (int i = 0; i < categoryNames.length; i++) {
            categories.add(new Category(i, categoryNames[i]));
            assertEquals(categories.get(i).getID(), i);
            assertEquals(categories.get(i).getName(), categoryNames[i]);
        }

        String[] itemNames  = {"item0", "item1", "item2"};
        String[] itemNotes = {"note0", "note1", "note2"};
        for(Category category: categories) {
            for (int i = 0; i < itemNames.length; i++) {
                assert(category.getID() >= 0 && category.getID() < categoryNames.length);
                Item item = new Item(i, itemNames[i], category.getID());
                item.setNote(itemNotes[i]);
                item.setDate(LocalDateTime.now());
                category.addItem(item);
                item = category.getItem(i);
                assertEquals(item.getID(), i);
                assertEquals(item.getName(), itemNames[i]);
                assertEquals(item.getNote(), itemNotes[i]);
                System.out.println(item.getDate());
            }
        }
        assertEquals(categories.toList().size(), categoryNames.length);

        int categoryDeleteIndex = 1;
        categories.delete(categoryDeleteIndex);
        assertEquals(categories.get(categoryDeleteIndex), null);
    }
}