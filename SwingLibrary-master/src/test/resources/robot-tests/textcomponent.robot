*** Settings ***
Suite Setup     storeInitialTextFieldContents
Test Setup      resetTextField
Library         TestSwingLibrary

*** Variables ***
${textAreaName}  testTextArea
${textFieldName}  testTextField
${textFieldIndex}  0
${testText}  Some text

*** Test Cases ***
Insert Into Text Field By Name
    insertIntoTextField  ${textFieldName}  ${testText}
    ${textFieldContents}=  getTextFieldValue  ${textFieldName}
    shouldBeEqual  ${testText}  ${textFieldContents}

Insert Into Uneditable/Disabled Field
    run keyword and expect error  Text field 'uneditableTextField' is not editable.  insertIntoTextField  uneditableTextField  Some Value
    run keyword and expect error  Text field 'disabledTextField' is not editable.  insertIntoTextField  disabledTextField  Some Value

Insert Into Text Field By Index
    insertIntoTextField  ${textFieldIndex}  ${testText}
    ${textFieldContents}=  getTextFieldValue  ${textFieldName}
    shouldBeEqual  ${testText}  ${textFieldContents}

Clear Text Field By Index
    insertIntoTextField  ${textFieldIndex}  ${testText}
    clearTextField  ${textFieldIndex}
    ${textFieldContents}=  getTextFieldValue  ${textFieldIndex}
    shouldBeEmpty  ${textFieldContents}

Clear Text Field By Name
    insertIntoTextField  ${textFieldName}  ${testText}
    clearTextField  ${textFieldName}
    ${textFieldContents}=  getTextFieldValue  ${textFieldName}
    shouldBeEmpty  ${textFieldContents}

Type Into Text Field By Index
    clearTextField  ${textFieldIndex}
    typeIntoTextField  ${textFieldIndex}  ${testText}
    ${textFieldContents}=  getTextFieldValue  ${textFieldIndex}
    shouldBeEqual  ${testText}  ${textFieldContents}

Type Into Text Field By Name
    clearTextField  ${textFieldName}
    typeIntoTextField  ${textFieldName}  ${testText}
    ${textFieldContents}=  getTextFieldValue  ${textFieldName}
    shouldBeEqual  ${testText}  ${textFieldContents}

Get Text Field Value By Name
    ${textFieldContents}=  getTextFieldValue  ${textFieldName}
    shouldBeEqual  ${initialText}  ${textFieldContents}

Get Text Field Value By Index
    ${textFieldContents}=  getTextFieldValue  ${textFieldIndex}
    shouldBeEqual  ${initialText}  ${textFieldContents}

Insert Into Text Field Works With TextAreas
    insertIntoTextField  ${textAreaName}  ${testText}
    ${textAreaContents}=  getTextFieldValue  ${textAreaName}
    shouldBeEqual  ${testText}  ${textAreaContents}

Clear Text Field Works With TextAreas
    insertIntoTextField  ${textAreaName}  ${testText}
    ${textAreaContents}=  getTextFieldValue  ${textAreaName}
    shouldBeEqual  ${testText}  ${textAreaContents}
    clearTextField  ${textAreaName}
    ${textAreaContents}=  getTextFieldValue  ${textAreaName}
    shouldBeEmpty  ${textAreaContents}

Type Into Text Field Works With TextAreas
    typeIntoTextField  ${textAreaName}  ${testText}
    ${textAreaContents}=  getTextFieldValue  ${textAreaName}
    shouldBeEqual  ${testText}  ${textAreaContents}

Get Text Field Value Works With TextAreas
    ${textAreaContents}=  getTextFieldValue  ${textAreaName}
    shouldBeEmpty  ${textAreaContents}
    insertIntoTextField  ${textAreaName}  ${testText}
    ${textAreaContents}=  getTextFieldValue  ${textAreaName}
    shouldBeEqual  ${testText}  ${textAreaContents}

Text Field Should Be Enabled
    textFieldShouldBeEnabled  ${textFieldName}
    disableTextField  ${textFieldName}
    runKeywordAndExpectError  Textfield '${textFieldName}' is disabled.  textFieldShouldBeEnabled  ${textFieldName}
    [Teardown]  enableTextField  ${textFieldName}

Text Field Should Be Disabled
    runKeywordAndExpectError  Textfield '${textFieldName}' is enabled.  textFieldShouldBeDisabled  ${textFieldName}
    disableTextField  ${textFieldName}
    textFieldShouldBeDisabled  ${textFieldName}
    [Teardown]  enableTextField  ${textFieldName}

Textfield Keywords Should Fail Context Is Not Correct
    [Setup]  selectEmptyContext
    runKeywordAndExpectError  *To use this keyword you must first select a correct context*  insertIntoTextField  ${textFieldName}  Something
    runKeywordAndExpectError  *To use this keyword you must first select a correct context*  getTextFieldValue  ${textFieldName}
    runKeywordAndExpectError  *To use this keyword you must first select a correct context*  clearTextField  ${textFieldName}
    runKeywordAndExpectError  *To use this keyword you must first select a correct context*  typeIntoTextField  ${textFieldName}  Something
    runKeywordAndExpectError  *To use this keyword you must first select a correct context*  textFieldShouldBeEnabled  ${textFieldName}
    runKeywordAndExpectError  *To use this keyword you must first select a correct context*  textFieldShouldBeDisabled  ${textFieldName}
    [Teardown]  selectMainWindow

*** Keywords ***
storeInitialTextFieldContents
    ${initialText}=  getTextFieldValue  ${textFieldName}
    setSuiteVariable  \${initialText}  ${initialText}

resetTextField
    insertIntoTextField  ${textFieldName}  ${initialText}
    clearTextField  ${textAreaName}

