Feature: Create a mail storage quota

  Scenario Outline: Create a mail storage quota with <quota> days
    When I create a mail storage quota with <quota> days
    Then I expect that the mail storage quota is set to <quota> days and quota type is <type>

    Examples:
    | quota | type |
    |  3   | custom |
    |  14   | custom |
    |  30   | custom |
    |  90   | custom |
    |  -1   | custom |