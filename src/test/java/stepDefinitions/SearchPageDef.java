package stepDefinitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import main.CucumberRunner;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import pageobjects.SearchPage;

public class SearchPageDef extends CucumberRunner {

    SearchPage searchPage = PageFactory.initElements(driver, SearchPage.class);


    @Given("^I am on \"(.*?)\" search page$")
    public void verifyPageTitle(String text) throws Throwable {
        String title = driver.getTitle();
        if(title == "google") {
            Assert.assertEquals(title, "Google");
        } else if (text == "cucumber") {
            Assert.assertEquals(title, "cucumber - Google SearchPageDef");
        } else if (text == "junit") {
            Assert.assertEquals(title, "junit - Google SearchPageDef");
        }
    }

    @When("^I type \"(.*?)\"$")
    public void enterSearchValue(String text) {
        searchPage.enterSearchValue(text);
    }

    @Then("^I click search button$")
    public void clickGoogleSearch(){
        searchPage.clickGoogleSearch();
    }

}
