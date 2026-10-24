Feature: Login
  As a registered user
  I want to log in to the app
  So that I can access the product catalog

  Background:
    Given the app is launched

  Scenario: Open the login screen
    When the user opens the navigation menu and selects "Login"
    Then the login screen is displayed
    And the username and password fields are visible
    And the Login button is visible

  Scenario: Login succeeds with valid credentials
    Given the user opens the navigation menu and selects "Login"
    When the user logs in with username "bod@example.com" and password "Password123"
    Then the product catalog is displayed

  Scenario Outline: Login fails with invalid credentials
    Given the user opens the navigation menu and selects "Login"
    When the user logs in with username "<username>" and password "<password>"
    Then the login attempt is rejected and the login form remains displayed

    Examples:
      | username          | password      |
      | bod@example.com   | wrong123      |
      | bod@example.com   | 00000000      |
      | invalid@example.com | Password123 |