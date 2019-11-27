*** Settings ***
Library         org.robotframework.swing.testapp.extension.ExtendedSwingLibrary

*** Test Cases ***
Runs Keyword In Separate Thread
    [Tags]  flickering
    runKeywordAndExpectError  *  shouldBeRunInSeparateThread
    runInSeparateThread  shouldBeRunInSeparateThread
    runInSeparateThread  shouldberuninseparatethread
    runInSeparateThread  Should Be Run In Separate Thread

Runs Keywords From Other Packages
    runKeywordInSeparateThread  keywordInOtherPackage  arg

Checks Arguments If ArgumentNames Are Available
    runKeywordAndExpectError  Expected 1 but got 2 arguments.*  runKeywordInSeparateThread  keywordInOtherPackage  foo  bar

Doesn't Fail When Argument Names Are Not Available
    runKeywordInSeparateThread  keywordWithoutArgumentNames  arg
    runKeywordInSeparateThread  keywordWithoutArgumentNames  foo  bar  # even when it should

Works With Real Keywords
    runKeywordInSeparateThread  selectFromMainMenuAndWait  Test Menu|Show Test Dialog
    Set Jemmy Timeout  DialogWaiter.WaitDialogTimeout  2
    Wait Until Keyword Succeeds  10 seconds  1 second  dialogShouldBeOpen  Message
    [Teardown]  closeDialog  Message

Run keywords with varargs
    runKeywordInSeparateThread   callComponentMethod  testButton  getToolTipText
    runKeywordInSeparateThread   callComponentMethod  testButton  getBaseline   10   10
    runKeywordAndExpectError  Expected 2 or more but got 1 arguments.*  runKeywordInSeparateThread  callComponentMethod   foo

*** Keywords ***
Run In Separate Thread
    [Arguments]  ${keyword name}
    runKeywordInSeparateThread  ${keyword name}
    Wait Until Keyword Succeeds  10 seconds  1 second  keywordWasRun

