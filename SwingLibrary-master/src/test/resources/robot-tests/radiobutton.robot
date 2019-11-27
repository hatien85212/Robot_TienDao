*** Settings ***
Suite setup     selectMainWindow
Library         TestSwingLibrary

*** Variables ***
${buttonName}  one

*** Test Cases ***
Select Radio Button Selects Radio Button
    radioButtonShouldBeSelectableWithIndex
    radioButtonShouldBeSelectableWithName
    radioButtonShouldBeSelectableWithText

Radio Button Should Be Selected Passes If Button Is Selected
    radioButtonShouldBeSelectedShouldWorkWithIndexes
    radioButtonShouldBeSelectedShouldWorkWithNames
    radioButtonShouldBeSelectedShouldWorkWithText

Radio Button Should Not Be Selected Passes If Button Is Not Selected
    radioButtonShouldNotBeSelectedShouldWorkWithIndexes
    radioButtonShouldNotBeSelectedShouldWorkWithNames
    radioButtonShouldNotBeSelectedShouldWorkWithText

Radio Button Should Be Enabled
    radioButtonShouldBeEnabled  ${buttonName}
    disableRadioButton  ${buttonName}
    runKeywordAndExpectError  Radio Button '${buttonName}' is disabled.  radioButtonShouldBeEnabled  ${buttonName}
    [Teardown]  enableRadioButton  ${buttonName}

Radio Button Should Be Disabled
    disableRadioButton  ${buttonName}
    radioButtonShouldBeDisabled  ${buttonName}
    enableRadioButton  ${buttonName}
    runKeywordAndExpectError  Radio Button '${buttonName}' is enabled.  radioButtonShouldBeDisabled  ${buttonName}

Radio Button Keywords Should Fail If Context Is Not A Window
    selectEmptyContext
    runKeywordAndExpectError  *To use this keyword you must first select a correct context*  pushRadioButton  someButton
    runKeywordAndExpectError  *To use this keyword you must first select a correct context*  radioButtonShouldBeSelected  someButton
    runKeywordAndExpectError  *To use this keyword you must first select a correct context*  radioButtonShouldNotBeSelected  someButton
    [Teardown]  selectMainWindow

*** Keywords ***
radioButtonShouldBeSelectableWithIndex
    pushRadioButton  0
    radioButtonShouldBeSelected  0
    pushRadioButton  1
    radioButtonShouldBeSelected  1

radioButtonShouldBeSelectableWithName
    pushRadioButton  one
    radioButtonShouldBeSelected  0
    pushRadioButton  three
    radioButtonShouldBeSelected  2

radioButtonShouldBeSelectableWithText
    pushRadioButton  Two
    radioButtonShouldBeSelected  1
    pushRadioButton  One
    radioButtonShouldBeSelected  0

radioButtonShouldBeSelectedShouldWorkWithIndexes
    pushRadioButton  0
    radioButtonShouldBeSelected  0
    pushRadioButton  1
    radioButtonShouldBeSelected  1
    pushRadioButton  0
    runKeywordAndExpectError  *  radioButtonShouldBeSelected  1

radioButtonShouldBeSelectedShouldWorkWithNames
    pushRadioButton  0
    radioButtonShouldBeSelected  one
    pushRadioButton  2
    radioButtonShouldBeSelected  three
    pushRadioButton  0
    runKeywordAndExpectError  *  radioButtonShouldBeSelected  two

radioButtonShouldBeSelectedShouldWorkWithText
    pushRadioButton  1
    radioButtonShouldBeSelected  Two
    pushRadioButton  2
    radioButtonShouldBeSelected  Three
    pushRadioButton  0
    runKeywordAndExpectError  *  radioButtonShouldBeSelected  Two

radioButtonShouldNotBeSelectedShouldWorkWithIndexes
    pushRadioButton  0
    radioButtonShouldNotBeSelected  1
    pushRadioButton  1
    radioButtonShouldNotBeSelected  0
    pushRadioButton  0
    runKeywordAndExpectError  *  radioButtonShouldNotBeSelected  0

radioButtonShouldNotBeSelectedShouldWorkWithNames
    pushRadioButton  0
    radioButtonShouldNotBeSelected  two
    pushRadioButton  1
    radioButtonShouldNotBeSelected  one
    pushRadioButton  0
    runKeywordAndExpectError  *  radioButtonShouldNotBeSelected  one

radioButtonShouldNotBeSelectedShouldWorkWithText
    pushRadioButton  0
    radioButtonShouldNotBeSelected  Two
    pushRadioButton  1
    radioButtonShouldNotBeSelected  One
    pushRadioButton  0
    runKeywordAndExpectError  *  radioButtonShouldNotBeSelected  One

