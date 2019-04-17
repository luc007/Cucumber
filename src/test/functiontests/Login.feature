Feature: Login Feature
  This feature deals with the login functionality of the application

  Scenario: Login Google Home Page
    Given I navigate to the login page
    And I enter the username and password
      | username | password   |
      | User 1   | Password 1 |
      | User 2   | Password 2 |
      | User 3   | Password 3 |
    And I click login button
    Then I should see the userform page

#  Scenario Outline: Login with correct username and password
#    Given I navigate to the login page
#    And I just need to see how step looks for Cucumber-Java9
#    And I enter <username> and <password>
#    And I click login button
#    Then I should see the userform page
#
#    Examples:
#      | username | password   |
#      | User 3   | Password 1 |
#      | User 2   | Password 2 |
