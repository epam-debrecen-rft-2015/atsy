package com.epam.rft.atsy.cucumber.welcome;

import com.epam.rft.atsy.cucumber.util.DriverProvider;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

import com.epam.rft.atsy.service.domain.CandidateDTO;
import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Map;

/**
 * Created by mates on 2015. 11. 11..
 */
public class WelcomeStepDefs {

    private DriverProvider driverProvider;
    private List<CandidateDTO> candidateDTOs;

    public WelcomeStepDefs(DriverProvider driverProvider) {
        this.driverProvider = driverProvider;
    }

    @Given("the user is logged in")
    public void loggedIn(){
        driverProvider.getDriver().get("http://localhost:8080/atsy/secure/welcome");
        driverProvider.getDriver().findElement(By.id("name")).sendKeys("test");
        driverProvider.getDriver().findElement(By.id("password")).sendKeys("pass3");
        driverProvider.getDriver().findElement(By.id("loginButton")).click();
    }

    @When("the user clicks on the Főoldal button")
    public void homeClicked(){
        driverProvider.getDriver().findElement(By.cssSelector(".navbar .navbar-nav :first-child > a")).click();


    }

    @Then("the Candidates page appears")
    public void candidatesPageAppears(){
    }

    @And("the list of candidates appears with the columns: Név, E-Mail, Telefonszám, Pályázott pozíciók")
    public void tableAppears(){
        WebDriverWait wait = new WebDriverWait(driverProvider.getDriver(), 5);
        wait.until(presenceOfElementLocated(By.id("candidates_table")));

        List<WebElement> webElements=driverProvider.getDriver().findElements(By.cssSelector("table > thead > tr > th"));

        assertThat(webElements.get(0).getText(),is("Név"));
        assertThat(webElements.get(1).getText(),is("E-Mail cím"));
        assertThat(webElements.get(2).getText(),is("Telefonszám"));
        assertThat(webElements.get(3).getText(),is("Pozíciók"));
        assertThat(webElements.get(4).getText(),is("Műveletek"));
    }

    @Given("there is an existing candidate with (.*), (.*), (.*) and (.*)")
    public void candidateExists(String name, String email, String phone, String positions){
        /*WebDriverWait wait = new WebDriverWait(driverProvider.getDriver(), 5);
        wait.until(presenceOfElementLocated(By.id("candidates_table")));

        List<WebElement> webElements=driverProvider.getDriver().findElement(By.id("candidates")).findElements(By.cssSelector("tbody > tr"));
        for(WebElement webElement: webElements){
            if(webElement.findElements(By.cssSelector("td")).get(0).getText().equals(name)){

                candidateDTOs.add(new CandidateDTO(webElement.findElements(By.cssSelector("td")).get(0).getText(),
                        webElement.findElements(By.cssSelector("td")).get(1).getText(),
                        webElement.findElements(By.cssSelector("td")).get(2).getText(),
                        "-","-", (byte) 0
                ));

            }
        }*/


    }

    @When("the user visits the Candidates page")
    public  void visitCandidatesPage(){
    }

    @Then("the list of candidates contains name (.*)")
    public void containsName(String name){
        WebDriverWait wait = new WebDriverWait(driverProvider.getDriver(), 5);
        wait.until(presenceOfElementLocated(By.id("candidates_table")));

        List<WebElement> webElements=driverProvider.getDriver().findElement(By.id("candidates")).findElements(By.cssSelector("tbody > tr"));

        for(WebElement webElement: webElements){
            if(webElement.findElements(By.cssSelector("td")).get(0).getText().equals(name)){
                assertThat(webElement.findElements(By.cssSelector("td")).get(0).getText(),is(name));

            }
        }
    }

    @And("email (.*)")
    public void containsEmail(String email){
        WebDriverWait wait = new WebDriverWait(driverProvider.getDriver(), 5);
        wait.until(presenceOfElementLocated(By.id("candidates_table")));

        List<WebElement> webElements=driverProvider.getDriver().findElement(By.id("candidates")).findElements(By.cssSelector("tbody > tr"));

        for(WebElement webElement: webElements){
            if(webElement.findElements(By.cssSelector("td")).get(1).getText().equals(email)){
                assertThat(webElement.findElements(By.cssSelector("td")).get(1).getText(),is(email));

            }
        }
    }

    @And("phone (.*)")
    public void containsPhone(String phone){
        WebDriverWait wait = new WebDriverWait(driverProvider.getDriver(), 5);
        wait.until(presenceOfElementLocated(By.id("candidates_table")));

        List<WebElement> webElements=driverProvider.getDriver().findElement(By.id("candidates")).findElements(By.cssSelector("tbody > tr"));

        for(WebElement webElement: webElements){
            if(webElement.findElements(By.cssSelector("td")).get(2).getText().equals(phone)){
                assertThat(webElement.findElements(By.cssSelector("td")).get(2).getText(),is(phone));

            }
        }
    }

    @And("previous positions (.*)")
    public void containsPositions(String positions){
        WebDriverWait wait = new WebDriverWait(driverProvider.getDriver(), 5);
        wait.until(presenceOfElementLocated(By.id("candidates_table")));

        List<WebElement> webElements=driverProvider.getDriver().findElement(By.id("candidates")).findElements(By.cssSelector("tbody > tr"));

        for(WebElement webElement: webElements){
            if(webElement.findElements(By.cssSelector("td")).get(3).getText().equals(positions)){
                assertThat(webElement.findElements(By.cssSelector("td")).get(3).getText(),is(positions));

            }
        }
    }

    @Given("the following candidates exists only:")
    public void existOnly(DataTable table){

    }

    @Then("the candidate details are displayed as:")
    public void displayedAs(DataTable table){

        WebDriverWait wait = new WebDriverWait(driverProvider.getDriver(), 5);
        wait.until(presenceOfElementLocated(By.id("candidates_table")));

        List<WebElement> webElements=driverProvider.getDriver().findElement(By.id("candidates")).findElements(By.cssSelector("tbody > tr"));

        assertThat(webElements.get(0).findElements(By.cssSelector("td")).get(0).getText(), is("Candidate A"));
        assertThat(webElements.get(1).findElements(By.cssSelector("td")).get(0).getText(),is("Candidate B"));
        assertThat(webElements.get(2).findElements(By.cssSelector("td")).get(0).getText(),is("Candidate C"));
    }

    @Given("the user is on the Candidates page")
    public void onCandidatesPage(){

    }

    @Given("the list is not sorted by (.*)")
    public void notSortedBy(String field){

    }

    @When("the user clicks on the (.*) header")
    public void clickFieldHeader(String field){
        List<WebElement> webElements=driverProvider.getDriver().findElements(By.cssSelector("thead > tr > th"));
        for (WebElement webElement: webElements){
            if(webElement.getText().equals(field)){
                webElement.click();
            }
        }
    }

    @Then("the sorting changes to (.*) (.*) in the header")
    public void sortingChanges(String field, String order){
        int fieldNumber=0;
        List<WebElement> webElements=driverProvider.getDriver().findElements(By.cssSelector("thead > tr > th"));
        for(int i=0;i<webElements.size();i++){
        //for (WebElement webElement: webElements){
            if(webElements.get(i).getText().equals(field)){
                fieldNumber=i;
            }
        }
        webElements=driverProvider.getDriver().findElements(By.cssSelector("tbody > tr"));
        //webElements.get(0).findElements(By.cssSelector("td")).get(fieldNumber).
        //assertThat(webElements.get(0).findElements(By.cssSelector("td")).get(fieldNumber).getText(), );

    }
    @And("the details are displayed ordered by (.*) (.*)")
    public void displayOrdered(String field, String order){
        int fieldNumber=0;
        List<WebElement> webElements=driverProvider.getDriver().findElements(By.cssSelector("thead > tr > th"));
        for(int i=0;i<webElements.size();i++){
            //for (WebElement webElement: webElements){
            if(webElements.get(i).getText().equals(field)){
                fieldNumber=i;
            }
        }

        webElements=driverProvider.getDriver().findElement(By.id("candidates")).findElements(By.cssSelector("tbody > tr"));
        assertThat(webElements.get(0).findElements(By.cssSelector("td")).get(fieldNumber).getText().equals(
                webElements.get(1).findElements(By.cssSelector("td")).get(fieldNumber).getText()
        ), is(Boolean.FALSE));
        assertThat(webElements.get(1).findElements(By.cssSelector("td")).get(fieldNumber).getText().equals(
                webElements.get(2).findElements(By.cssSelector("td")).get(fieldNumber).getText()
        ), is(Boolean.FALSE));
        assertThat(webElements.get(0).findElements(By.cssSelector("td")).get(fieldNumber).getText().equals(
                webElements.get(2).findElements(By.cssSelector("td")).get(fieldNumber).getText()
        ), is(Boolean.FALSE));
    }

}
