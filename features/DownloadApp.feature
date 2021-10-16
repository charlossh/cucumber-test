Feature: Test check ios app link
	Check ios app link and click them
Scenario: TestScenario

  Given Open chrome
  And Navigate to melia
  And Close melia cookies
  When Check link ios app
  And Click link ios app
  Then Application should be closed