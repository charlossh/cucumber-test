Feature: Test Search Hotel
	Perform a search of hotel & booking dates and get available rooms
Scenario: TestSearchHotel

  Given Open chrome
  And Navigate to melia
  And Close melia cookies
  When Enter not recognized hotel name
  Then Unrecognized hotel message is displayed
  When Enter valid name of a hotel
  And Enter Checkin and Checkout dates
  And Select rooms and guests
  And Press Search button
  Then Available rooms are displayed
  Then Application should be closed