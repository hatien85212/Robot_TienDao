*** Settings ***
Library         org.robotframework.swing.testapp.extension.ExtendedSwingLibrary  WITH NAME  ext1
Library         org.robotframework.swing.testapp.extension.ExtendedSwingLibrary2  WITH NAME  ext2
suite teardown    Restore timeouts


*** Test Cases ***
Test Extended Keyword Is Available
    ${value}=  ext1.keywordInOtherPackage  HelloWorld!
    shouldBeEqual  ${value}  HelloWorld!
    ${value}=  ext2.keywordInOtherPackage  HelloWorld!
    shouldBeEqual  ${value}  HelloWorld!

*** Keywords ***
Restore timeouts
    ext2.Set jemmy timeouts   1


