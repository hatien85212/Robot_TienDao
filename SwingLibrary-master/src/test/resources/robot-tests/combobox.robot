*** Settings ***
Test Teardown   resetDropdownMenu
Library         TestSwingLibrary
Library         Collections

*** Variables ***
${comboboxName}  testComboBox
${comboboxIndex}  0
${comboboxItem1}  one
${comboboxItem2}  two

*** Test Cases ***
Get Selected Item From Combobox By Index
    ${selectedItem}=  getSelectedItemFromComboBox  ${comboboxIndex}
    shouldBeEqual  ${comboboxItem1}  ${selectedItem}

Get Selected Item From Combobox By Name
    ${selectedItem}=  getSelectedItemFromCombobox  ${comboboxName}
    shouldBeEqual  ${comboboxItem1}  ${selectedItem}

Get Selected Item From Dropdown Menu By Index
    ${selectedItem}=  getSelectedItemFromDropdownMenu  ${comboboxIndex}
    shouldBeEqual  ${comboboxItem1}  ${selectedItem}

Get Selected Item From Dropdown Menu By Name
    ${selectedItem}=  getSelectedItemFromDropdownMenu  ${comboboxName}
    shouldBeEqual  ${comboboxItem1}  ${selectedItem}

Get Selected Item From Disabled Combobox
    ${selectedItem}=  getSelectedItemFromComboBox  disabledComboBox
    shouldBeEqual  ${comboboxItem1}  ${selectedItem}

Select From Combobox By Index
    selectFromCombobox  ${comboboxIndex}  ${comboboxItem2}
    comboboxSelectionShouldBe  ${comboboxIndex}  ${comboboxItem2}

Select From Combobox By Name
    selectFromComboBox  ${comboboxName}  two
    comboboxSelectionShouldBe  ${comboboxName}  two

Select From Changing Combobox By Name No Verification
    selectFromComboBox  contentChangingCombobox  0  false
    comboboxSelectionShouldBe  contentChangingCombobox  Foo
    selectFromComboBox  contentChangingCombobox  Quux  false
    comboboxSelectionShouldBe  contentChangingCombobox  Quux
    selectFromComboBox  contentChangingCombobox  Removable  false
    pushButton  resetContentChangingComboBox

Select From Changing Combobox By Name
    selectFromComboBox  contentChangingCombobox  Bar
    comboboxSelectionShouldBe  contentChangingCombobox  Bar
    selectFromComboBox  contentChangingCombobox  0
    comboboxSelectionShouldBe  contentChangingCombobox  Bar
    pushButton  resetContentChangingComboBox
    selectFromComboBox  contentChangingCombobox  0
    comboboxSelectionShouldBe  contentChangingCombobox  Foo

Select From Combobox Works With Rendered Text
    selectFromComboBox  comboboxWithRenderer  TWO
    comboboxSelectionShouldBe  comboboxWithRenderer  TWO
    selectFromComboBox  comboboxWithRenderer  1
    comboboxSelectionShouldBe  comboboxWithRenderer  TWO

Select From Combobbox Keyword Should Work With Indexes
    selectFromCombobox  ${comboboxName}  0
    comboboxSelectionShouldBe  ${comboboxName}  ${comboboxItem1}
    selectFromCombobox  ${comboboxName}  1
    comboboxSelectionShouldBe  ${comboboxName}  ${comboboxItem2}
    selectFromCombobox  ${comboboxName}  2
    comboboxSelectionShouldBe  ${comboboxName}  three

Select From Dropdown Menu By Index
    selectFromDropdownMenu  ${comboboxIndex}  ${comboboxItem2}
    comboboxSelectionShouldBe  ${comboboxIndex}  ${comboboxItem2}

Select From Dropdown Menu By Name
    selectFromDropdownMenu  ${comboboxName}  ${comboboxItem2}
    comboboxSelectionShouldBe  ${comboboxName}  ${comboboxItem2}

Select From Combobox Works Only With Exact Match
    runKeywordAndExpectError  *  selectFromCombobox  ${comboboxName}  wo

Combobox Should Be Enabled
    comboboxShouldBeEnabled  ${comboboxName}
    disableCombobox  ${comboboxName}
    runKeywordAndExpectError  Combobox '${ComboboxName}' was disabled.  comboboxShouldBeEnabled  ${comboboxName}
    [Teardown]  enableCombobox  ${comboboxName}

Combobox Should Be Disabled
    disableCombobox  ${comboboxName}
    comboboxShouldBeDisabled  ${comboboxName}
    enableCombobox  ${comboboxName}
    runKeywordAndExpectError  Combobox '${comboboxName}' was enabled.  comboboxShouldBeDisabled  ${comboboxName}
    [Teardown]  enableCombobox  ${comboboxName}

Type Into Combobox
    typeIntoCombobox  ${comboboxName}  someText
    ${comboboxText}=  getComboboxText  ${comboboxName}
    shouldBeEqual  someText  ${comboboxText}

Dropdown Keywords Should Fail If Context Is Not A Window
    selectEmptyContext
    runKeywordAndExpectError  *To use this keyword you must first select a correct context*  selectFromCombobox  ${comboboxName}  ${comboboxItem2}
    runKeywordAndExpectError  *To use this keyword you must first select a correct context*  selectFromDropdownMenu  ${comboboxName}  ${comboboxItem2}
    runKeywordAndExpectError  *To use this keyword you must first select a correct context*  getSelectedItemFromCombobox  ${comboboxName}
    runKeywordAndExpectError  *To use this keyword you must first select a correct context*  getSelectedItemFromDropdownMenu  ${comboboxName}
    [Teardown]  selectMainWindow

Get Combobox Values
    ${expectedList}=  createList  one  two  three
    ${values}=  getComboboxValues  ${comboboxName}
    logMany  ${values}
    listsShouldBeEqual  ${expectedList}  ${values}

Get Combobox Values out of view
    ${expectedList}=  createList      combo_9  b  c
    Component should not be visible   combo_9
    ${values}=  getComboboxValues     combo_9
    logMany  ${values}
    listsShouldBeEqual  ${expectedList}  ${values}

*** Keywords ***
resetDropdownMenu
    selectFromDropdownMenu  ${comboboxIndex}  ${comboboxItem1}

comboboxSelectionShouldBe
    [Arguments]  ${identifier}  ${expectedSelection}
    ${actualSelection}=  getSelectedItemFromCombobox  ${identifier}
    shouldBeEqual  ${expectedSelection}  ${actualSelection}

