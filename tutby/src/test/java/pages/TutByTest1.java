package pages;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;

public class TutByTest1 {

    private WebDriver driver;
    private OnlineCinemaAllGenres onlineCinemaAllGenres;
    private static Logger logger = Logger.getLogger(TutByTest1.class);


    @BeforeClass
    public void beforeClass() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://www.tut.by");
        driver.manage().window().maximize();
        onlineCinemaAllGenres = new OnlineCinemaAllGenres(driver);
        logger.info("Start before tests, open browser, scenario 1");
    }

    @Parameters({"kindOfCinema", "genre"})
    @Test
    public void startTest(String kind, String value) {
        onlineCinemaAllGenres.clickResources();
        onlineCinemaAllGenres.clickResourcesAll();
        onlineCinemaAllGenres.clickOnlineCinema();
        onlineCinemaAllGenres.switchToActiveTab();
        logger.info("Select page online-cinema");
        onlineCinemaAllGenres.clickToKindOfCinemaTabs(kind);
        logger.info("Select kind of movie on the page online-cinema " + kind);
        onlineCinemaAllGenres.clickDropDownMenuGenres();
        onlineCinemaAllGenres.clickToGenre(value);
        logger.info("Select genre " + value);
        onlineCinemaAllGenres.clickDropDownMenuGenres();
        Assert.assertTrue(onlineCinemaAllGenres.matchingTheSelectedGenre(value));
        logger.info("Verify genres after search");

    }

    @AfterClass
    public void afterClass(){
        driver.quit();
    }
}
