import org.junit.Test;
import pages.StaticPages;

public class StaticPagesTest extends BasicTest {
    @Test
    public void testStatic() {
        StaticPages pages = new StaticPages(driver);
        for (int i = 0; i < pages.size(); i++) {
            if (pages.hasCookiesBanner()) {
                pages.cookieBannerTest();
            }
            pages.testPageByIndex(i);
        }
    }
}
