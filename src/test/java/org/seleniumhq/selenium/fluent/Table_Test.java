package org.seleniumhq.selenium.fluent;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class Table_Test {

    private StringBuilder sb;
    private FluentWebDriverImpl fwd;

    @Before
    public void setup() {
        sb = new StringBuilder();
        fwd = new FluentWebDriverImpl(new WebDriverJournal(sb));
        FluentWebDriverImplTest.FAIL_ON_NEXT.set(null);
    }

    @Test
    public void table_functionality() {
        BaseFluentWebDriver fc = fwd.table()
                .table(By.xpath("@foo = 'bar'"))
                .table(By.cssSelector("baz"))
                .tables();

        assertThat(fc, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: table) -> we1\n" +
                        "we1.getTagName() -> 'table'\n" +
                        "we1.findElement(By.xpath: .//table[@foo = 'bar']) -> we2\n" +
                        "we2.getTagName() -> 'table'\n" +
                        "we2.findElement(By.selector: baz) -> we3\n" +
                        "we3.getTagName() -> 'table'\n" +
                        "we3.findElements(By.tagName: table) -> [we4, we5]\n" +
                        "we4.getTagName() -> 'table'\n" +
                        "we5.getTagName() -> 'table'\n"
        ));
    }


    @Test
    public void tables_functionality() {
        BaseFluentWebDriver fc = fwd.table()
                .tables(By.name("qux"));

        assertThat(fc, notNullValue());
        assertThat(sb.toString(), equalTo(
                "wd0.findElement(By.tagName: table) -> we1\n" +
                        "we1.getTagName() -> 'table'\n" +
                        "we1.findElements(By.name: qux) -> [we2, we3]\n" +
                        "we2.getTagName() -> 'table'\n" +
                        "we3.getTagName() -> 'table'\n"
        ));
    }

    @Test
    public void table_mismatched() {
        try {
            fwd.table(By.linkText("mismatching_tag_name"))
                    .clearField();
            fail("should have barfed");
        } catch (FluentExecutionStopped e) {
            assertThat(e.getMessage(), equalTo("AssertionError during invocation of: ?.table(By.linkText: mismatching_tag_name)"));
            assertTrue(e.getCause().getMessage().contains("tag was incorrect"));
        }
    }

}
