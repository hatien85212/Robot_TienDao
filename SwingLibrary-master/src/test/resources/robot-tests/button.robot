*** Settings ***
Suite Setup     captureOriginalButtonText
Test Setup      resetButton
Library         TestSwingLibrary

*** Variables ***
${buttonName}  testButton
${buttonText}  Test Button
${toolTipText}  testToolTip
${buttonTextAfterPush}  Button Was Pushed1
${buttonIndex}  0

*** Test Cases ***
Get Button Text By Internal Name
    ${text}=  getButtonText  ${buttonName}
    shouldBeEqual  Test Button  ${text}

Get Button Text By Index
    ${text}=  getButtonText  ${buttonIndex}
    shouldBeEqual  Test Button  ${text}

Button Should Exist By Internal Name
    buttonShouldExist  ${buttonName}

Button Should Exist By Text
    buttonShouldExist  ${buttonText}

Button Should Exist By ToolTip
    buttonShouldExist  ${toolTipText}

Button Should Exist Index
    buttonShouldExist  ${buttonIndex}

Button Should Exist Fails If The Button Doesn't Exist
    runKeywordAndExpectError  *Button 'Unexisting Button' doesn't exist*  buttonShouldExist  Unexisting Button
    runKeywordAndExpectError  *Button '123' doesn't exist*  buttonShouldExist  123

Button Should Not Exist
    buttonShouldNotExist  Unexisting Button
    buttonShouldNotExist  123

Button Should Not Exist Fails If The Button Exists
    runKeywordAndExpectError  *Button '${buttonName}' exists*  buttonShouldNotExist  ${buttonName}
    runKeywordAndExpectError  *Button '${buttonIndex}' exists*  buttonShouldNotExist  ${buttonIndex}

Push Button By Internal Name
    pushButton  ${buttonName}
    buttonWasPushed  ${buttonName}

Push Button By Text
    pushButton  ${buttonText}
    buttonWasPushed  ${buttonName}

Push Button By Index
    pushButton  ${buttonIndex}
    buttonWasPushed  ${buttonIndex}

Button Should Be Enabled By Index
    buttonShouldBeEnabled  ${buttonIndex}
    disableButton  ${buttonIndex}
    runKeywordAndExpectError  Button was disabled.  buttonShouldBeEnabled  ${buttonIndex}
    [Teardown]  enableButton  ${buttonIndex}

Button Should Be Enabled By Name
    buttonShouldBeEnabled  ${buttonName}
    disableButton  ${buttonName}
    runKeywordAndExpectError  Button was disabled.  buttonShouldBeEnabled  ${buttonName}

Button Should Be Disabled By Index
    disableButton  ${buttonIndex}
    buttonShouldBeDisabled  ${buttonIndex}
    enableButton  ${buttonIndex}
    runKeywordAndExpectError  Button was enabled.  buttonShouldBeDisabled  ${buttonIndex}

Button Should Be Disabled By Name
    disableButton  ${buttonName}
    buttonShouldBeDisabled  ${buttonName}
    enableButton  ${buttonName}
    runKeywordAndExpectError  Button was enabled.  buttonShouldBeDisabled  ${buttonName}

Button Keywords Should Fail Context Is Not Correct
    selectEmptyContext
    runKeywordAndExpectError  *To use this keyword you must first select a correct context*  pushButton  ${buttonName}
    runKeywordAndExpectError  *To use this keyword you must first select a correct context*  getButtonText  ${buttonName}
    runKeywordAndExpectError  *To use this keyword you must first select a correct context*  buttonShouldExist  ${buttonName}
    runKeywordAndExpectError  *To use this keyword you must first select a correct context*  buttonShouldNotExist  Unexisting button
    runKeywordAndExpectError  *To use this keyword you must first select a correct context*  buttonShouldBeEnabled  ${buttonName}
    runKeywordAndExpectError  *To use this keyword you must first select a correct context*  buttonShouldBeDisabled  ${buttonName}
    [Teardown]  selectMainWindow

*** Keywords ***
buttonWasPushed
    [Arguments]  ${buttonIdentifier}
    ${buttonText}=  getButtonText  ${buttonIdentifier}
    shouldBeEqual  ${buttonText}  ${buttonTextAfterPush}

captureOriginalButtonText
    ${text}=  getButtonText  ${buttonName}
    setSuiteVariable  \${originalButtonText}  ${text}

resetButton
    setButtonText  ${buttonName}  ${originalButtonText}
    enableButton  ${buttonName}

