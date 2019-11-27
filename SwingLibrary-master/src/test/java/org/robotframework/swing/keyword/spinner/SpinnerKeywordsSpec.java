package org.robotframework.swing.keyword.spinner;

import jdave.Block;
import jdave.junit4.JDaveRunner;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.robotframework.jdave.contract.FieldIsNotNullContract;
import org.robotframework.jdave.contract.RobotKeywordContract;
import org.robotframework.jdave.contract.RobotKeywordsContract;
import org.robotframework.swing.factory.IdentifierParsingOperatorFactory;
import org.robotframework.jdave.mock.MockSupportSpecification;
import org.robotframework.swing.spinner.SpinnerOperator;
import org.robotframework.swing.util.IComponentConditionResolver;

@RunWith(JDaveRunner.class)
public class SpinnerKeywordsSpec extends MockSupportSpecification<SpinnerKeywords> {
    private String spinnerIdentifier = "spinnerIdentifier";
    private SpinnerKeywords spinnerKeywords;

    public class Any {
        public SpinnerKeywords create() {
            return new SpinnerKeywords();
        }

        public void isRobotKeywordsAnnotated() {
            specify(context, satisfies(new RobotKeywordsContract()));
        }

        public void hasSpinnerShouldExistKeyword() {
            specify(context, satisfies(new RobotKeywordContract("spinnerShouldExist")));
        }

        public void hasSpinnerShouldNotExistKeyword() {
            specify(context, satisfies(new RobotKeywordContract("spinnerShouldNotExist")));
        }

        public void hasGetSpinnerValueKeyword() {
            specify(context, satisfies(new RobotKeywordContract("getSpinnerValue")));
        }

        public void hasIncreaseSpinnerValueKeyword() {
            specify(context, satisfies(new RobotKeywordContract("increaseSpinnerValue")));
        }

        public void hasDecreaseSpinnerValueKeyword() {
            specify(context, satisfies(new RobotKeywordContract("decreaseSpinnerValue")));
        }

        public void createsOperatorFactory() {
            specify(context, satisfies(new FieldIsNotNullContract("operatorFactory")));
        }

        public void createsExistenceResolver() {
            specify(context, satisfies(new FieldIsNotNullContract("existenceResolver")));
        }
    }

    public class OperatingOnSpinners {
        private SpinnerOperator spinnerOperator;
        private String someValue = "someValue";

        public SpinnerKeywords create() {
            spinnerKeywords = new SpinnerKeywords();
            injectMockOperatorFactory();
            return spinnerKeywords;
        }

        public void increasesSpinnerValue() {
            checking(new Expectations() {{
                one(spinnerOperator).increase();
            }});

            context.increaseSpinnerValue(spinnerIdentifier);
        }

        public void increaseSpinnerValueByDefinedAmount() {
            checking(new Expectations() {{
                exactly(8).of(spinnerOperator).increase();
            }});

            context.increaseSpinnerValue(spinnerIdentifier, 8);
        }

        public void decreasesSpinnerValue() {
            checking(new Expectations() {{
                one(spinnerOperator).decrease();
            }});

            context.decreaseSpinnerValue(spinnerIdentifier);
        }

        public void decreaseSpinnerValueByDefinedAmount() {
            checking(new Expectations() {{
                exactly(8).of(spinnerOperator).decrease();
            }});

            context.decreaseSpinnerValue(spinnerIdentifier, 8);
        }

        public void getsSpinnerValue() {
            checking(new Expectations() {{
                one(spinnerOperator).getValue(); will(returnValue(someValue));
            }});

            specify(context.getSpinnerValue(spinnerIdentifier), must.equal(someValue));
        }

        private void injectMockOperatorFactory() {
            final IdentifierParsingOperatorFactory<?> operatorFactory = injectMockTo(spinnerKeywords, "operatorFactory", IdentifierParsingOperatorFactory.class);
            spinnerOperator = mock(SpinnerOperator.class);

            checking(new Expectations() {{
                one(operatorFactory).createOperator(spinnerIdentifier);
                will(returnValue(spinnerOperator));
            }});
        }
    }

    public class ResolvingSpinnerExistence {
        private IComponentConditionResolver existenceResolver;

        public SpinnerKeywords create() {
            spinnerKeywords = new SpinnerKeywords();
            injectMockExistenceResolver();
            return spinnerKeywords;
        }

        public void spinnerShouldExistPassesIfSpinnerExists() throws Throwable {
            checking(new Expectations() {{
                one(existenceResolver).satisfiesCondition(spinnerIdentifier); will(returnValue(true));
            }});

            specify(new Block() {
                public void run() throws Throwable {
                    context.spinnerShouldExist(spinnerIdentifier);
                }
            }, should.not().raise(AssertionError.class));
        }

        public void spinnerShouldExistFailsIfSpinnerDoesntExists() throws Throwable {
            checking(new Expectations() {{
                one(existenceResolver).satisfiesCondition(spinnerIdentifier); will(returnValue(false));
            }});

            specify(new Block() {
                public void run() throws Throwable {
                    context.spinnerShouldExist(spinnerIdentifier);
                }
            }, should.raiseExactly(AssertionError.class, "Spinner '" + spinnerIdentifier + "' doesn't exist."));
        }

        public void spinnerShouldNotExistPassesIfSpinnerDoesntExist() throws Throwable {
            checking(new Expectations() {{
                one(existenceResolver).satisfiesCondition(spinnerIdentifier); will(returnValue(false));
            }});

            specify(new Block() {
                public void run() throws Throwable {
                    context.spinnerShouldNotExist(spinnerIdentifier);
                }
            }, should.not().raise(AssertionError.class));
        }

        public void spinnerShouldNotExistFailsIfSpinnerExists() throws Throwable {
            checking(new Expectations() {{
                one(existenceResolver).satisfiesCondition(spinnerIdentifier); will(returnValue(true));
            }});

            specify(new Block() {
                public void run() throws Throwable {
                    context.spinnerShouldNotExist(spinnerIdentifier);
                }
            }, should.raiseExactly(AssertionError.class, "Spinner '" + spinnerIdentifier + "' exists."));
        }

        private void injectMockExistenceResolver() {
            existenceResolver = injectMockTo(spinnerKeywords, "existenceResolver", IComponentConditionResolver.class);
        }
    }
}
