*** Settings ***
Test Setup      uncheckAllCheckboxes
Library         TestSwingLibrary

*** Variables ***
${checkBoxIndex}  0
${checkBoxText}  Test Checkbox
${checkBoxName}  TestCheckbox2

*** Test Cases ***
Checkbox Should Be Enabled
    checkBoxShouldBeEnabled  ${checkBoxIndex}
    checkBoxShouldBeEnabled  ${checkBoxText}
    disableCheckBox  ${checkBoxText}
    runKeywordAndExpectError  Checkbox '${checkBoxText}' is disabled.  checkBoxShouldBeEnabled  ${checkBoxText}

Checkbox Should Be Disabled
    disableCheckBox  ${checkBoxText}
    checkBoxShouldBeDisabled  ${checkBoxIndex}
    checkBoxShouldBeDisabled  ${checkBoxText}
    enableCheckBox  ${checkBoxText}
    runKeywordAndExpectError  Checkbox '${checkBoxText}' is enabled.  checkBoxShouldBeDisabled  ${checkBoxText}

Check Checkbox By Index
    checkCheckBox  ${checkBoxIndex}
    checkBoxShouldBeChecked  ${checkBoxIndex}

Check Checkbox By Text
    checkCheckBox  ${checkBoxText}
    checkBoxShouldBeChecked  ${checkBoxText}

Uncheck Checkbox By Index
    checkBoxShouldBeUnChecked  ${checkBoxIndex}

Uncheck Checkbox By Text
    checkBoxShouldBeUnChecked  ${checkBoxText}

Checkbox Should Be Checked By Index
    checkCheckBox  ${checkBoxIndex}
    checkBoxShouldBeChecked  ${checkBoxIndex}

Checkbox Should Be Checked By Text
    checkCheckBox  ${checkBoxText}
    checkBoxShouldBeChecked  ${checkBoxText}

Checkbox Should Be Unchecked By Index
    checkBoxShouldBeUnChecked  ${checkBoxIndex}

Checkbox Should Be Unchecked By Text
    checkBoxShouldBeUnChecked  ${checkBoxText}

Checkbox Should Not Be Checked By Index
    checkBoxShouldNotBeChecked  ${checkBoxIndex}

Checkbox Should Not Be Checked By Text
    checkBoxShouldNotBeChecked  ${checkBoxText}

Checkbox Keywords Should Work With Internal Names
    checkCheckBox  ${checkBoxName}
    checkBoxShouldBeChecked  ${checkBoxName}
    uncheckCheckBox  ${checkBoxName}
    checkBoxShouldBeUnchecked  ${checkBoxName}
    disableCheckBox  ${checkBoxName}
    checkBoxShouldBeDisabled  ${checkBoxName}
    enableCheckBox  ${checkBoxName}
    checkBoxShouldBeEnabled  ${checkBoxName}

All Checkboxes Should Be Checked
    runKeywordAndExpectError  Checkbox 'Test Checkbox' is not checked.  allCheckboxesShouldBeChecked
    checkAllCheckboxes
    allCheckboxesShouldBeChecked

All Checkboxes Should Be Unchecked
    allCheckboxesShouldBeUnchecked
    checkAllCheckboxes
    runKeywordAndExpectError  Checkbox 'Test Checkbox' is checked.  allCheckboxesShouldBeUnchecked

Check All Checkboxes
    checkAllCheckboxes
    allCheckboxesShouldBeChecked

Uncheck All Checkboxes
    checkAllCheckboxes
    allCheckboxesShouldBeChecked
    uncheckAllCheckboxes
    allCheckboxesShouldBeUnchecked

Checkbox Keywords Should Fail If Context Is Not A Window
    selectEmptyContext
    runKeywordAndExpectError  *To use this keyword you must first select a correct context*  checkCheckBox  ${checkBoxText}
    runKeywordAndExpectError  *To use this keyword you must first select a correct context*  checkBoxShouldBeChecked  ${checkBoxText}
    runKeywordAndExpectError  *To use this keyword you must first select a correct context*  uncheckCheckBox  ${checkBoxText}
    runKeywordAndExpectError  *To use this keyword you must first select a correct context*  checkBoxShouldBeUnChecked  ${checkBoxText}
    runKeywordAndExpectError  *To use this keyword you must first select a correct context*  checkBoxShouldNotBeChecked  ${checkBoxText}
    runKeywordAndExpectError  *To use this keyword you must first select a correct context*  allCheckboxesShouldBeChecked
    runKeywordAndExpectError  *To use this keyword you must first select a correct context*  allCheckboxesShouldBeUnchecked
    runKeywordAndExpectError  *To use this keyword you must first select a correct context*  checkAllCheckboxes
    runKeywordAndExpectError  *To use this keyword you must first select a correct context*  uncheckAllCheckboxes
    [Teardown]  selectMainWindow

