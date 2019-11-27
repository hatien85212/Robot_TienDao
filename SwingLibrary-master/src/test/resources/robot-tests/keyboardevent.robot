*** Settings ***
Test Setup      selectMainWindow
Library         TestSwingLibrary

*** Variables ***
${textFieldName}  testTextField
${testText}  Rf
${tableName}  testTable

*** Test Cases ***
Send Key Event Keyword Sends Key Events
    [Tags]  display-required
    shouldInsertKeysToTextField
    shouldSwitchCellsWithTabs

*** Keywords ***
shouldInsertKeysToTextField
    clearAndSelectTextField
    sendKeyboardEvent  VK_R  SHIFT_MASK
    sendKeyboardEvent  VK_F
    textFieldContentsShouldBe  ${testText}

clearAndSelectTextField
    clearTextField  ${textFieldName}
    FocusToComponent  ${textFieldName}

textFieldContentsShouldBe
    [Arguments]  ${expectedText}
    selectMainWindow
    ${textFieldContents}=  getTextFieldValue  ${textFieldName}
    shouldBeEqual  ${expectedText}  ${textFieldContents}

shouldSwitchCellsWithTabs
    selectTableCell  ${tableName}  0  0
    tableCellShouldBeSelected  ${tableName}  0  0
    sendKeyboardEvent  VK_TAB
    tableCellShouldBeSelected  ${tableName}  0  1
    sendKeyboardEvent  VK_TAB  SHIFT_MASK
    tableCellShouldBeSelected  ${tableName}  0  0

