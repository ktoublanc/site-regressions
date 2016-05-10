Feature: GitHub Web Page

  Scenario: Compare snapshots
    Given I want to take snapshot of https://github.com
    When I take snapshot with each available browsers
    Then I assert that there are no differences between snapshots

