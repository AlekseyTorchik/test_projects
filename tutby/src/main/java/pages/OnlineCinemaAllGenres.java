package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class OnlineCinemaAllGenres {
    private static final int LINK_PRESENSE_TIMEOUT = 15;
    private WebDriver driver;
    private WebDriverWait wait;



    @FindBy(xpath = "(//a[contains(@href , 'resource')])[1]")
    private WebElement resources;

    @FindBy(xpath = "//li[contains(@class, 'topbar__li mores')]/a")
    private WebElement resourcesAll;

    @FindBy(xpath = "//li[contains(@class, 'lists__li')]/a[contains(@data-url-1, '1802')]")
    private WebElement onlineCinema;

    @FindBy(xpath = "(//div[contains(@class,'active')]//button[contains(@class,'dropdown')])[1]")
    private WebElement dropDownMenuGenres;

    @FindBy(xpath = "//a[@data-show]")
    private List <WebElement> kindOfCinemaTabs;

    @FindBy(xpath = "//div[contains(@class, 'custom_select open')]//span[@class='text']")
    private List <WebElement> selectGenre;

    @FindBy(xpath = "//div[contains(@class,'txt')]/p")
    private List <WebElement> chosenGenresOfCinema;

    public OnlineCinemaAllGenres(WebDriver webdriver) {
        this.driver = webdriver;
        PageFactory.initElements(webdriver, this);
    }

    public void clickResources() {
        toBeClickableWaiter(resources);
        resources.click();
    }

    public void clickResourcesAll() {
        toBeClickableWaiter(resourcesAll);
        resourcesAll.click();
    }

    public void clickOnlineCinema() {
        toBeClickableWaiter(onlineCinema);
        onlineCinema.click();
    }

    public void switchToActiveTab() {
        for (String handle: driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }
    }
    public void clickDropDownMenuGenres() {
        toBeClickableWaiter(dropDownMenuGenres);
        dropDownMenuGenres.click();
    }

    public void clickToGenre(String genre) {
        for (WebElement element: selectGenre) {
            if (element.getText().contains(genre)) {
                element.click();
                break;
            }
        }
    }

    public void clickToKindOfCinemaTabs(String kindOfCinema) {
        for (WebElement element: kindOfCinemaTabs) {
            if (element.getText().contains(kindOfCinema)) {
                element.click();
                break;
            }
        }
    }

      public boolean matchingTheSelectedGenre (String genre) {
        boolean verify = false;
        for (WebElement element : chosenGenresOfCinema) {
            visibilityWaiter(element);
            if (element.getText().contains(genre)) {
                verify = true;
            } else {
                verify = false;
                break;
            }
        }
        return verify;
    }

    public void visibilityWaiter(WebElement elementVisibility) {
        wait = new WebDriverWait(driver, LINK_PRESENSE_TIMEOUT);
        wait.until(ExpectedConditions.visibilityOf(elementVisibility));
    }

    public void toBeClickableWaiter(WebElement elementClickable) {
        wait = new WebDriverWait(driver, LINK_PRESENSE_TIMEOUT);
        wait.until(ExpectedConditions.elementToBeClickable(elementClickable));
    }
}

