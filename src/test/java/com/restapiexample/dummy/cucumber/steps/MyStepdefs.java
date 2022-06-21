package com.restapiexample.dummy.cucumber.steps;

import com.restapiexample.dummy.dummyrestapiInfo.DummyRestAPISteps;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.ValidatableResponse;
import net.thucydides.core.annotations.Steps;
import org.junit.Assert;

import java.util.HashMap;
import java.util.List;

import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.hasKey;

public class MyStepdefs {

    static String status = "success";
    static int employeeID;
    static String message;
    static String employeeName = "Kim";
    static ValidatableResponse response;
    @Steps
    DummyRestAPISteps dummyRestAPISteps;

    @Given("^I am on home page of given application$")
    public void iAmOnHomePageOfGivenApplication() {

    }

    @When("^I send POST request to the application using a valid payload$")
    public void iSendPOSTRequestToTheApplicationUsingAValidPayload() {
        HashMap<String, Object> createRecord = new HashMap<>();
        createRecord.put("name", employeeName);
        createRecord.put("salary", "12500");
        createRecord.put("age", "65");
        createRecord.put("id", 104);
        response = dummyRestAPISteps.createUser(status, createRecord);
        response.log().all().statusCode(200);

    }

    @Then("^I get status code (\\d+)$")
    public void iGetStatusCode(int code) {
        response.assertThat().statusCode(code);

    }

    @And("^I verify if a new employee created$")
    public void iVerifyIfANewEmployeeCreated() {
        response = dummyRestAPISteps.readingEmployee();
        response.log().all().statusCode(200);
        employeeID = response.log().all().extract().path("data.id");
        HashMap<?, ?> getEmployee = response.log().all().extract().path("");
        Assert.assertThat(getEmployee, hasKey("status"));
        Assert.assertThat(getEmployee, hasKey("data"));
        message = response.log().all().extract().path("message");
        Assert.assertThat(message, anything("Successfully!"));
    }

    @When("^I send PUT request to the application using a valid payload$")
    public void iSendPUTRequestToTheApplicationUsingAValidPayload() {
        HashMap<String, Object> createRecord = new HashMap<>();
        employeeName = employeeName + "_updated";
        createRecord.put("name", employeeName);
        response = dummyRestAPISteps.updatingEmployee(createRecord);
    }

    @And("^I verify if a new employee updated$")
    public void iVerifyIfANewEmployeeUpdated() {
        message = response.log().all().extract().path("message");
        System.out.println(message);
        Assert.assertThat(message, anything("Successfully!"));
    }

    @When("^I send DELETE request to the application$")
    public void iSendDELETERequestToTheApplication() {
        response = dummyRestAPISteps.deletingEmployee();
        response.log().all().statusCode(200);

    }

    @And("^I verify if a new employee deleted$")
    public void iVerifyIfANewEmployeeDeleted() {
        message = response.log().all().extract().path("message");
        System.out.println(message);
        Assert.assertThat(message, anything("Successfully!"));
    }

    @When("^I send GET request to the application to fetch all employees record$")
    public void iSendGETRequestToTheApplicationToFetchAllEmployeesRecord() {
        response = dummyRestAPISteps.readingAllEmployee();
    }

    @And("^I verify total records are (\\d+)$")
    public void iVerifyTotalRecordsAre(int expected) {
        List<String> totalRecord = response.log().all().extract().path("data");
        Assert.assertEquals(expected, totalRecord.size());
    }

    @And("^I verify twenty-forth employee id is (\\d+)$")
    public void iVerifyTwentyForthEmployeeIdIs(int expected) {
        int iD24 = response.log().all().extract().path("data[23].id");
        Assert.assertEquals(expected, iD24);
    }

    @And("^I verify twenty-forth employee name is \"([^\"]*)\"$")
    public void iVerifyTwentyForthEmployeeNameIs(String expected)  {
        String employeeName = response.log().all().extract().path("data[23].employee_name");
        Assert.assertEquals(expected,employeeName);
    }

    @And("^I verify message is \"([^\"]*)\"$")
    public void iVerifyMessageIs(String expected) {
        String message = response.log().all().extract().path("message");
        Assert.assertEquals(expected,message);
    }

    @And("^I verify status is \"([^\"]*)\"$")
    public void iVerifyStatusIs(String expected)  {
        String status = response.log().all().extract().path("status");
        Assert.assertEquals(expected,status);
    }

    @And("^I verify employee id (\\d+) has employee salary is (\\d+)$")
    public void iVerifyEmployeeIdHasEmployeeSalaryIs(int a,int expected) {
        List<?> employeeSalary = response.log().all().extract().path("data.findAll{it.id==3}.employee_salary");
        Assert.assertEquals(expected, employeeSalary.get(0));
    }

    @And("^I verify employee id (\\d+) has employee age is (\\d+)$")
    public void iVerifyEmployeeIdHasEmployeeAgeIs(int a,int expected) {
        List<?> employeeAge = response.log().all().extract().path("data.findAll{it.id==6}.employee_age");
        Assert.assertEquals(expected,employeeAge.get(0));
    }

    @And("^I verify employee id (\\d+) has employee salary is \"([^\"]*)\"$")
    public void iVerifyEmployeeIdHasEmployeeSalaryIs(int a,String expected) {
        List<?> employeeName1 = response.log().all().extract().path("data.findAll{it.id==11}.employee_name");
        Assert.assertEquals(expected,employeeName1.get(0));
    }
}

