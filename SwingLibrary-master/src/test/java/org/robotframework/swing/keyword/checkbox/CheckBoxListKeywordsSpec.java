package org.robotframework.swing.keyword.checkbox;

import java.awt.Container;
import java.util.ArrayList;

import jdave.Block;
import jdave.junit4.JDaveRunner;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.netbeans.jemmy.operators.JCheckBoxOperator;
import org.robotframework.jdave.contract.FieldIsNotNullContract;
import org.robotframework.jdave.contract.RobotKeywordContract;
import org.robotframework.jdave.contract.RobotKeywordsContract;
import org.robotframework.jdave.mock.MockSupportSpecification;
import org.robotframework.swing.checkbox.CheckBoxOperator;
import org.robotframework.swing.context.Context;
import org.robotframework.swing.context.ContextVerifier;
import org.robotframework.swing.factory.OperatorsFactory;
import org.robotframework.swing.operator.ComponentWrapper;


@RunWith(JDaveRunner.class)
public class CheckBoxListKeywordsSpec extends MockSupportSpecification<CheckBoxListKeywords> {
    private CheckBoxListKeywords checkboxListKeywords = new CheckBoxListKeywords();

    public class Any {
        public void isRobotKeywordsAnnotated() {
            specify(checkboxListKeywords, satisfies(new RobotKeywordsContract()));
        }

        public void hasCheckAllCheckboxesKeyword() {
            specify(checkboxListKeywords, satisfies(new RobotKeywordContract("checkAllCheckboxes")));
        }

        public void hasUncheckAllCheckboxesKeyword() {
            specify(checkboxListKeywords, satisfies(new RobotKeywordContract("uncheckAllCheckboxes")));
        }

        public void hasAllCheckboxesShouldBeCheckedKeyword() {
            specify(checkboxListKeywords, satisfies(new RobotKeywordContract("allCheckboxesShouldBeChecked")));
        }

        public void hasAllCheckboxesShouldBeUncheckedKeyword() {
            specify(checkboxListKeywords, satisfies(new RobotKeywordContract("allCheckboxesShouldBeUnchecked")));
        }

        public void hasOperatorListFactory() {
            specify(checkboxListKeywords, satisfies(new FieldIsNotNullContract("operatorsFactory")));
        }

        public void hasContextVerifier() {
            specify(checkboxListKeywords, satisfies(new FieldIsNotNullContract("contextVerifier")));
        }
    }

    public class Operating {
        private CheckBoxOperator operator;
        private Container containerContext;
        private String checkboxText = "Some checkbox";

        public CheckBoxListKeywords create() {
            injectMockInternals();
            return checkboxListKeywords;
        }

        public void checksAllCheckBoxes() {
            checking(new Expectations() {{
                exactly(2).of(operator).changeSelection(true);
            }});

            context.checkAllCheckboxes();
        }

        public void unchecksAllCheckBoxes() {
            checking(new Expectations() {{
                exactly(2).of(operator).changeSelection(false);
            }});

            context.uncheckAllCheckboxes();
        }

        public void allCheckboxesShouldBeCheckedPassesIfAllCheckboxesAreChecked() throws Throwable {
            setCheckboxesAreSelected(true);

            specify(new Block() {
                public void run() throws Throwable {
                    context.allCheckboxesShouldBeChecked();
                }
            }, must.not().raise(Exception.class));
        }

        public void allCheckboxesShouldBeCheckedFailsIfAnyOfCheckboxesIsUnchecked() throws Throwable {
            checking(new Expectations() {{
                one(operator).isSelected(); will(returnValue(true));
                one(operator).isSelected(); will(returnValue(false));
                allowing(operator).getText(); will(returnValue(checkboxText));
            }});

            specify(new Block() {
                public void run() throws Throwable {
                    context.allCheckboxesShouldBeChecked();
                }
            }, must.raiseExactly(AssertionError.class, "Checkbox '" + checkboxText + "' is not checked."));
        }

        public void allCheckboxesShouldBeUncheckedPassesIfAllCheckboxesAreUnchecked() throws Throwable {
            setCheckboxesAreSelected(false);

            specify(new Block() {
                public void run() throws Throwable {
                    context.allCheckboxesShouldBeUnchecked();
                }
            }, must.not().raise(Exception.class));
        }

        public void allCheckboxesShouldBeUncheckedFailsIfAnyOfCheckboxesIsChecked() throws Throwable {
            checking(new Expectations() {{
                one(operator).isSelected(); will(returnValue(false));
                one(operator).isSelected(); will(returnValue(true));
                allowing(operator).getText(); will(returnValue(checkboxText));
            }});

            specify(new Block() {
                public void run() throws Throwable {
                    context.allCheckboxesShouldBeUnchecked();
                }
            }, must.raiseExactly(AssertionError.class, "Checkbox '" + checkboxText + "' is checked."));
        }

        private void setCheckboxesAreSelected(final boolean b) {
            checking(new Expectations() {{
                exactly(2).of(operator).isSelected(); will(returnValue(b));
                allowing(operator).getText(); will(returnValue(checkboxText));
            }});
        }

        private void injectMockInternals() {
            operator = mock(CheckBoxOperator.class);
            createMockContext();
            injectMockContextVerifier();
            injectMockOperatorFactory();
        }

        private void injectMockOperatorFactory() {
            final OperatorsFactory<?> operatorListFactory = injectMockTo(checkboxListKeywords, OperatorsFactory.class);
            checking(new Expectations() {{
                one(operatorListFactory).createOperators(containerContext);
                will(returnValue(new ArrayList<JCheckBoxOperator>() {{ add(operator); add(operator); }}));
            }});
        }

        private void createMockContext() {
            final ComponentWrapper contextOperator = mock(ComponentWrapper.class);
            containerContext = mock(Container.class);
            checking(new Expectations() {{
                one(contextOperator).getSource(); will(returnValue(containerContext));
            }});

            Context.setContext(contextOperator);
        }

        private void injectMockContextVerifier() {
            final ContextVerifier contextVerifier = injectMockTo(checkboxListKeywords, ContextVerifier.class);
            checking(new Expectations() {{
                one(contextVerifier).verifyContext();
            }});
        }
    }
}

