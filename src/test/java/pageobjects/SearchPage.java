package pageobjects;

import main.CucumberRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class SearchPage extends CucumberRunner {

    @FindBy(how = How.CSS, using = "input[name='q']")
    WebElement searchBox;

    @FindBy(name="btnK")
    WebElement btnGoogleSearch;


    public void enterSearchValue(String text) {
        searchBox.clear();
        searchBox.sendKeys(text);
    }

    public void clickGoogleSearch(){
        searchBox.sendKeys(Keys.ENTER);
    }
}
