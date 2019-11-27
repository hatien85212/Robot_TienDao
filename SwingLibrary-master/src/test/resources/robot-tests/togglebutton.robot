*** Settings ***
Library         TestSwingLibrary

*** Variables ***
${buttonName}  testToggleButton
${buttonText}  Test Toggle Button

*** Test Cases ***
Toggle Button Should Be Selected Passes If Button Is Selected
    selectToggleButton  ${buttonName}
    toggleButtonShouldBeSelected  ${buttonName}
    toggleButtonShouldBeSelected  ${buttonText}
    unSelectToggleButton  ${buttonName}
    runKeywordAndExpectError  Toggle Button '${buttonName}' is not selected.  toggleButtonShouldBeSelected  ${buttonName}
    runKeywordAndExpectError  Toggle Button '${buttonText}' is not selected.  toggleButtonShouldBeSelected  ${buttonText}

Toggle Button Should Not Be Selected Passes If Button Is Not Selected
    unSelectToggleButton  ${buttonName}
    toggleButtonShouldNotBeSelected  ${buttonName}
    toggleButtonShouldNotBeSelected  ${buttonText}
    selectToggleButton  ${buttonName}
    runKeywordAndExpectError  Toggle Button '${buttonName}' is selected.  toggleButtonShouldNotBeSelected  ${buttonName}
    runKeywordAndExpectError  Toggle Button '${buttonText}' is selected.  toggleButtonShouldNotBeSelected  ${buttonText}

Toggle Button Keywords Should Fail If Context Is Not A Window
    selectEmptyContext
    runKeywordAndExpectError  *To use this keyword you must first select a correct context*  toggleButtonShouldBeSelected  someButton
    runKeywordAndExpectError  *To use this keyword you must first select a correct context*  toggleButtonShouldNotBeSelected  someButton
    [Teardown]  selectMainWindow

Push Toggle Button Toggles Selection State
    selectToggleButton  ${buttonName}
    pushToggleButton  ${buttonName}
    toggleButtonShouldNotBeSelected  ${buttonName}
    pushToggleButton  ${buttonName}
    toggleButtonShouldBeSelected  ${buttonName}

