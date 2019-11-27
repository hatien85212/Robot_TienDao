*** Settings ***
Suite Setup     setTimeouts
Library         TestSwingLibrary

*** Variables ***
${spinnerName}  testSpinner

*** Test Cases ***
Spinner Should Exist By Name
    spinnerShouldExist  ${spinnerName}
    runKeywordAndExpectError  Spinner 'nonexisting' doesn't exist.  spinnerShouldExist  nonexisting

Spinner Should Not Exist By Name
    spinnerShouldNotExist  nonexisting
    runKeywordAndExpectError  Spinner '${spinnerName}' exists.  spinnerShouldNotExist  ${spinnerName}

Spinner Should Exist By Index
    spinnerShouldExist  0
    runKeywordAndExpectError  Spinner '199' doesn't exist.  spinnerShouldExist  199

Spinner Should Not Exist By Index
    spinnerShouldNotExist  14
    runKeywordAndExpectError  Spinner '0' exists.  spinnerShouldNotExist  0

Get Spinner Value By Name
    ${spinnerValue}=  getSpinnerValue  ${spinnerName}
    shouldBeEqualAsIntegers  0  ${spinnerValue}

Get Spinner Value By Index
    ${spinnerValue}=  getSpinnerValue  0
    shouldBeEqualAsIntegers  0  ${spinnerValue}

Set Integer Spinner Value
    ${oldValue}=  getSpinnerValue  ${spinnerName}
    setSpinnerNumberValue  ${spinnerName}  666
    ${spinnerValue}=  getSpinnerValue  ${spinnerName}
    shouldBeEqualAsIntegers  666  ${spinnerValue}
    [Teardown]  setSpinnerNumberValue  ${spinnerName}  ${oldValue}

Set Float Spinner Value
    ${oldValue}=  getSpinnerValue  floatSpinner
    setSpinnerNumberValue  floatSpinner  66.6
    ${spinnerValue}=  getSpinnerValue  floatSpinner
    shouldBeEqualAsNumbers  66.599998  ${spinnerValue}
    run keyword and expect error  Can't convert 'hevonen' to a number.  setSpinnerNumberValue  floatSpinner  hevonen
    [Teardown]  setSpinnerNumberValue  floatSpinner  ${oldValue}

Set String Spinner Value
    ${oldValue}=  getSpinnerValue  stringSpinner
    setSpinnerStringValue  stringSpinner  Monday
    ${spinnerValue}=  getSpinnerValue  stringSpinner
    shouldBeEqual  Monday  ${spinnerValue}
    [Teardown]  setSpinnerStringValue  stringSpinner  ${oldValue}

Increase Spinner Value By Name Without Parameters
    increaseSpinnerValue  ${spinnerName}
    ${spinnerValue}=  getSpinnerValue  ${spinnerName}
    shouldBeEqualAsIntegers  1  ${spinnerValue}
    [Teardown]  decreaseSpinnerValue  ${spinnerName}

Increase Spinner Value By Index Without Parameters
    increaseSpinnerValue  0
    ${spinnerValue}=  getSpinnerValue  0
    shouldBeEqualAsIntegers  1  ${spinnerValue}
    [Teardown]  decreaseSpinnerValue  0

Decrease Spinner Value By Name Without Parameters
    decreaseSpinnerValue  ${spinnerName}
    ${spinnerValue}=  getSpinnerValue  ${spinnerName}
    shouldBeEqualAsIntegers  -1  ${spinnerValue}
    [Teardown]  increaseSpinnerValue  ${spinnerName}

Decrease Spinner Value By Index Without Parameters
    decreaseSpinnerValue  0
    ${spinnerValue}=  getSpinnerValue  0
    shouldBeEqualAsIntegers  -1  ${spinnerValue}
    [Teardown]  increaseSpinnerValue  0

Increase Spinner Value By Name With Parameters
    increaseSpinnerValue  ${spinnerName}  6
    ${spinnerValue}=  getSpinnerValue  ${spinnerName}
    shouldBeEqualAsIntegers  6  ${spinnerValue}
    [Teardown]  decreaseSpinnerValue  ${spinnerName}  6

Increase Spinner Value By Index With Parameters
    increaseSpinnerValue  0  6
    ${spinnerValue}=  getSpinnerValue  0
    shouldBeEqualAsIntegers  6  ${spinnerValue}
    [Teardown]  decreaseSpinnerValue  0  6

Decrease Spinner Value By Name With Parameters
    decreaseSpinnerValue  ${spinnerName}  5
    ${spinnerValue}=  getSpinnerValue  ${spinnerName}
    shouldBeEqualAsIntegers  -5  ${spinnerValue}
    [Teardown]  increaseSpinnerValue  ${spinnerName}  5

Decrease Spinner Value By Index With Parameters
    decreaseSpinnerValue  0  5
    ${spinnerValue}=  getSpinnerValue  0
    shouldBeEqualAsIntegers  -5  ${spinnerValue}
    [Teardown]  increaseSpinnerValue  0  5

Spinner Keywords Should Fail Context Is Not Correct
    selectEmptyContext
    runKeywordAndExpectError  *To use this keyword you must first select a correct context*  spinnerShouldExist  someSpinner
    runKeywordAndExpectError  *To use this keyword you must first select a correct context*  spinnerShouldNotExist  someSpinner
    runKeywordAndExpectError  *To use this keyword you must first select a correct context*  getSpinnerValue  someSpinner
    runKeywordAndExpectError  *To use this keyword you must first select a correct context*  increaseSpinnerValue  someSpinner
    runKeywordAndExpectError  *To use this keyword you must first select a correct context*  decreaseSpinnerValue  someSpinner
    [Teardown]  selectMainWindow

*** Keywords ***
setTimeouts
    setJemmyTimeouts  1

