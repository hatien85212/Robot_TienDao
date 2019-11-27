*** Settings ***
Library         TestSwingLibrary

*** Variables ***
${menuRoot}  Test Menu
${internalFrameMenu}  ${menuRoot}|Show Internal Frame
${internalFrameTitle}  Test Internal Frame

*** Test Cases ***
Close Internal Frame Closes Internal Frame
    [Setup]  openInternalFrame
    closeInternalFrame  ${internalFrameTitle}
    internalFrameShouldNotBeOpen  ${internalFrameTitle}

Internal Frame Should Not Exist Passes If Internal Frame Doesn't Exist
    internalFrameShouldNotExist  Non existent Internal Frame

Internal Frame Should Not Exist Fails If Internal Frame Exists
    runKeywordAndExpectError  Internal frame '${internalFrameTitle}' exists.  internalFrameShouldNotExist  ${internalFrameTitle}

Internal Frame Should Exist Passes If Internal Frame Exists
    internalFrameShouldExist  ${internalFrameTitle}

Internal Frame Should Exist Fails If Internal Frame Doesn't Exist
    runKeywordAndExpectError  Internal frame 'nonExistent' doesn't exist.  internalFrameShouldExist  nonExistent

Internal Frame Should Be Open Passes If Internal Frame Is Open
    [Setup]  openInternalFrame
    internalFrameShouldBeOpen  ${internalFrameTitle}
    [Teardown]  closeInternalFrame  ${internalFrameTitle}

Internal Frame Should Be Open Fails If Internal Frame Is Closed
    runKeywordAndExpectError  Internal frame '${internalFrameTitle}' is not open.  internalFrameShouldBeOpen  ${internalFrameTitle}

Internal Frame Should Not Be Open Passes If Internal Frame Is Closed
    internalFrameShouldNotBeOpen  ${internalFrameTitle}

Internal Frame Should Not Be Open Fails If Internal Frame Is Open
    [Setup]  openInternalFrame
    runKeywordAndExpectError  Internal frame '${internalFrameTitle}' is open.  internalFrameShouldNotBeOpen  ${internalFrameTitle}
    [Teardown]  closeInternalFrame  ${internalFrameTitle}

Internal Frame Keywords Should Fail Context Is Not Correct
    selectEmptyContext
    runKeywordAndExpectError  *To use this keyword you must first select a correct context*  closeInternalFrame  someFrame
    runKeywordAndExpectError  *To use this keyword you must first select a correct context*  internalFrameShouldExist  someFrame
    runKeywordAndExpectError  *To use this keyword you must first select a correct context*  internalFrameShouldNotExist  someFrame
    [Teardown]  selectMainWindow

Get Internal Frames In Context
    ${frames}=  getInternalFramesInContext
    lengthShouldBe  ${frames}  0
    selectFromMainMenuAndWait  ${internalFrameMenu}
    ${frames}=  getInternalFramesInContext
    Length Should Be  ${frames}  1
    shouldContain  ${frames}  Test Internal Frame
    [Teardown]  closeInternalFrame  ${internalFrameTitle}

*** Keywords ***
openInternalFrame
    selectFromMainMenuAndWait  ${internalFrameMenu}

