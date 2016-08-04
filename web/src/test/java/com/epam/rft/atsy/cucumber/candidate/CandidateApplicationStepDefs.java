package com.epam.rft.atsy.cucumber.candidate;

import com.epam.rft.atsy.cucumber.util.DriverProvider;
import com.epam.rft.atsy.service.domain.CandidateApplicationDTO;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfAllElementsLocatedBy;

public class CandidateApplicationStepDefs {
  private static WebDriver webDriver = DriverProvider.getDriver();
  private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy. MM. dd. HH:mm:ss");

  private static final String APPLICATIONS_TABLE_ID = "applications_table";
  private static final String POSITION_NAME = "Fejlesztő";
  private static final String STATE_TYPE = "Beugró";

  private static final Integer TIME_OUT_FIVE_SECONDS = 5;
  private static final Integer COLUMN_ZEROTH = 0;
  private static final Integer COLUMN_FIRST = 1;
  private static final Integer COLUMN_SECOND = 2;
  private static final Integer COLUMN_THIRD = 3;

  private static List<CandidateApplicationDTO> candidateApplicationDTOList = null;


  @Given("^the user on an existing candidates page$")
  public void the_user_on_an_existing_candidates_page() throws Throwable {
    webDriver.get("http://localhost:8080/atsy/secure/candidate/3");
  }

  @And("^there are more than one applications exist for the candidate$")
  public void there_are_more_than_one_applications_exist_for_the_candidate() throws Throwable {
    Integer rowNumber = getNumberOfRowsInTableByTableId(APPLICATIONS_TABLE_ID);
    assertThat(rowNumber, greaterThan(1));
  }

  @Then("^the Application list displays all the applications ordered by last modification date and time descending$")
  public void the_application_list_displays_all_the_applications_ordered_by_last_modification_date_and_time_descending()
      throws Throwable {

    isSortedCandidateApplicationDTOListByModificationDate(getCandidateApplicationDTOListFromTableByTableId(APPLICATIONS_TABLE_ID));
  }

  @And("^each application has the values of creation date and time, last modification date and time, position, state$")
  public void each_application_has_the_values_of_creation_date_and_time_last_modification_date_and_time_position_state()
      throws Throwable {

    assertThat(getCandidateApplicationDTOListFromTableByTableId(APPLICATIONS_TABLE_ID),
        equalTo(getExpectedCandidateApplicationDTOList()));

  }

  private Integer getNumberOfRowsInTableByTableId(String tableId) {
    return getTableByTableId(tableId).size();
  }

  private List<CandidateApplicationDTO> getCandidateApplicationDTOListFromTableByTableId(String tableId)
      throws ParseException {
    List<CandidateApplicationDTO> candidateApplicationDTOList = new ArrayList<>();

    for (WebElement webElement : getTableByTableId(tableId)) {
      List<WebElement> currentRow = webElement.findElements(By.tagName("td"));
      String positionName = currentRow.get(COLUMN_ZEROTH).getText();
      Date creationDate = SIMPLE_DATE_FORMAT.parse(currentRow.get(COLUMN_FIRST).getText());
      Date lastModifiedDate = SIMPLE_DATE_FORMAT.parse(currentRow.get(COLUMN_SECOND).getText());
      String stateName = currentRow.get(COLUMN_THIRD).getText();

      CandidateApplicationDTO candidateApplicationDTO = CandidateApplicationDTO.builder().positionName(positionName)
          .creationDate(creationDate).modificationDate(lastModifiedDate).stateType(stateName).build();

      candidateApplicationDTOList.add(candidateApplicationDTO);
    }

    return candidateApplicationDTOList;
  }

  private List<WebElement> getTableByTableId(String tableId) {
    WebDriverWait webDriverWait = new WebDriverWait(webDriver, TIME_OUT_FIVE_SECONDS);
    return webDriverWait
        .until(visibilityOfAllElementsLocatedBy(By.cssSelector("#" + tableId + " >" + "tbody > tr[data-index]")));
  }

  private List<CandidateApplicationDTO> getExpectedCandidateApplicationDTOList() {
    if (candidateApplicationDTOList == null) {
      initCandidateApplicationDTOList();
    }
    return candidateApplicationDTOList;
  }

  private void initCandidateApplicationDTOList() {
    try {
      Date firstCreationDate = SIMPLE_DATE_FORMAT.parse("2016. 07. 26. 11:48:56");
      Date secondCreationDate = SIMPLE_DATE_FORMAT.parse("2016. 07. 26. 11:48:57");
      Date thirdCreationDate = SIMPLE_DATE_FORMAT.parse("2016. 07. 26. 11:48:58");

      Date firstModificationDate = SIMPLE_DATE_FORMAT.parse("2016. 07. 26. 11:48:58");
      Date secondModificationDate = SIMPLE_DATE_FORMAT.parse("2016. 07. 26. 11:48:57");
      Date thirdModificationDate = SIMPLE_DATE_FORMAT.parse("2016. 07. 26. 11:48:56");

      CandidateApplicationDTO firstCandidateApplicationDTO = CandidateApplicationDTO.builder().positionName(POSITION_NAME)
          .creationDate(firstCreationDate).modificationDate(firstModificationDate).stateType(STATE_TYPE).build();
      CandidateApplicationDTO secondCandidateApplicationDTO = CandidateApplicationDTO.builder().positionName(POSITION_NAME)
          .creationDate(secondCreationDate).modificationDate(secondModificationDate).stateType(STATE_TYPE).build();
      CandidateApplicationDTO thirdCandidateApplicationDTO = CandidateApplicationDTO.builder().positionName(POSITION_NAME)
          .creationDate(thirdCreationDate).modificationDate(thirdModificationDate).stateType(STATE_TYPE).build();

      candidateApplicationDTOList = Arrays.asList(firstCandidateApplicationDTO, secondCandidateApplicationDTO, thirdCandidateApplicationDTO);
    } catch (ParseException e) { e.printStackTrace(); }
  }

  private boolean isSortedCandidateApplicationDTOListByModificationDate(List<CandidateApplicationDTO> candidateApplicationDTOList) {
    for (int i = 1; i < candidateApplicationDTOList.size(); i++) {
      if (candidateApplicationDTOList.get(i - 1).getModificationDate()
          .compareTo(candidateApplicationDTOList.get(i).getModificationDate()) > 0) {
        return false;
      }
    }
    return true;
  }


}
