import com.epam.DriverManager;
import com.epam.GmailHomePage;
import com.epam.GmailLoginPage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.epam.Constants.*;

public class AndroidGmailTest {

    @BeforeMethod
    public void beforeMethod() {
        DriverManager.getThread();
    }

    @Test
    public void testGmail () {
        GmailLoginPage gmailLoginPage = new GmailLoginPage();
        gmailLoginPage.openInitialPage();
        gmailLoginPage.logIn(ADDRESS, PASSWORD);
        GmailHomePage gmailHomePage = new GmailHomePage();
        gmailHomePage.composeMail(ADDRESS, SUBJECT, BODY);
        gmailHomePage.deleteMail();
        Assert.assertTrue(gmailHomePage.verifyMailIsDeleted());
    }

    @AfterMethod
    public void afterMethod() {
        DriverManager.releaseThread();
    }
}
