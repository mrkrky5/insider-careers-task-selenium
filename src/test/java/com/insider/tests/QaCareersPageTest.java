package com.insider.tests;

import com.insider.pages.QaCareersPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.insider.constants.TestGroups.CONTENT;
/**
 * Verifies that the QA Careers page opens and the
 * "See all QA jobs" call-to-action is visible.
 */
public class QaCareersPageTest extends BaseTest {

    @Test(groups = {CONTENT})
    public void shouldOpenQaCareersPageAndShowSeeAllJobsButton() {
        QaCareersPage qaCareersPage = new QaCareersPage(driver).open();

        Assert.assertTrue(
                qaCareersPage.isQaCareersPageLoaded(),
                "QA Careers page is not loaded."
        );

        Assert.assertTrue(
                qaCareersPage.isSeeAllQaJobsButtonVisible(),
                "'See all QA jobs' button is not visible on QA Careers page."
        );
    }
}
