package org.robotframework.swing.keyword.label;

import jdave.Block;
import jdave.junit4.JDaveRunner;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.robotframework.jdave.contract.FieldIsNotNullContract;
import org.robotframework.jdave.contract.RobotKeywordContract;
import org.robotframework.jdave.contract.RobotKeywordsContract;
import org.robotframework.swing.factory.IdentifierParsingOperatorFactory;
import org.robotframework.swing.factory.OperatorFactory;
import org.robotframework.jdave.mock.MockSupportSpecification;
import org.robotframework.swing.label.LabelOperator;
import org.robotframework.swing.util.IComponentConditionResolver;


@RunWith(JDaveRunner.class)
public class LabelKeywordsSpec extends MockSupportSpecification<LabelKeywords> {
    private String labelIdentifier = "someLabel";

    public class Any {
        public LabelKeywords create() {
            return new LabelKeywords();
        }

        public void isRobotKeywordsAnnotated() {
            specify(context, satisfies(new RobotKeywordsContract()));
        }

        public void hasLabelExistenceResolver() {
            specify(context, satisfies(new FieldIsNotNullContract("labelExistenceResolver")));
        }

        public void hasOperatorFactory() {
            specify(context, satisfies(new FieldIsNotNullContract("operatorFactory")));
        }

        public void hasGetLabelContentKeyword() {
            specify(context, satisfies(new RobotKeywordContract("getLabelContent")));
        }


        public void labelShouldBeEqualKeyword() {
            specify(context, satisfies(new RobotKeywordContract("labelTextShouldBe")));
        }

        public void hasLabelShouldExistKeyword() {
            specify(context, satisfies(new RobotKeywordContract("labelShouldExist")));
        }

        public void hasLabelShouldNotExistKeyword() {
            specify(context, satisfies(new RobotKeywordContract("labelShouldNotExist")));
        }
    }

    public class Operating {
        private LabelOperator labelOperator = mock(LabelOperator.class);

        public LabelKeywords create() {
            LabelKeywords labelKeywords = new LabelKeywords();
            final OperatorFactory operatorFactory = injectMockTo(labelKeywords, "operatorFactory", IdentifierParsingOperatorFactory.class);

            checking(new Expectations() {{
                one(operatorFactory).createOperator(with(equal(labelIdentifier)));
                will(returnValue(labelOperator));
            }});

            return labelKeywords;
        }

        public void getsLabelContent() {
            final String labelText = "textFromLabel";
            checking(new Expectations() {{
                one(labelOperator).getText(); will(returnValue(labelText));
            }});

            specify(context.getLabelContent(labelIdentifier), must.equal(labelText));
        }
    }

    public class CheckingLabel {
        private LabelOperator labelOperator = mock(LabelOperator.class);

        public LabelKeywords create() {
            LabelKeywords labelKeywords = new LabelKeywords();
            final OperatorFactory operatorFactory = injectMockTo(labelKeywords, "operatorFactory", IdentifierParsingOperatorFactory.class);

            checking(new Expectations() {{
                one(operatorFactory).createOperator(labelIdentifier);
                will(returnValue(labelOperator));
            }});

            return labelKeywords;
        }

    	public void labelShouldBePassesWhenLabelIsEqual() throws Throwable {
            checking(new Expectations() {{
                one(labelOperator).getText();
                will(returnValue("My label!"));
            }});

    		specify(new Block() {
				public void run() throws Throwable {
					context.labelTextShouldBe(labelIdentifier, "My label!");
				}
    		}, must.not().raiseAnyException());

    	}

    	public void labelShouldBeFailsWhenLabelIsUnEqual() {
            checking(new Expectations() {{
                one(labelOperator).getText();
                will(returnValue("My label!"));
            }});

    		specify(new Block() {
				public void run() throws Throwable {
					context.labelTextShouldBe(labelIdentifier, "Something else");
				}
    		}, must.raise(AssertionError.class,
    				"Expected label 'someLabel' value to be 'Something else', but was 'My label!'"));
    	}
    }



    public class CheckingConditions {
        private IComponentConditionResolver existenceResolver;

        public LabelKeywords create() {
            LabelKeywords labelKeywords = new LabelKeywords();
            existenceResolver = injectMockTo(labelKeywords, "labelExistenceResolver", IComponentConditionResolver.class);
            return labelKeywords;
        }

        public void labelShouldExistPassesIfLabelExists() throws Throwable {
            setLabelExists(true);

            specify(new Block() {
                public void run() throws Throwable {
                    context.labelShouldExist(labelIdentifier);
                }
            }, must.not().raise(AssertionError.class));
        }

        public void labelShouldExistFailsIfLabelDoesntExist() throws Throwable {
            setLabelExists(false);

            specify(new Block() {
                public void run() throws Throwable {
                    context.labelShouldExist(labelIdentifier);
                }
            }, must.raiseExactly(AssertionError.class, "Label '" + labelIdentifier + "' doesn't exist"));
        }

        public void labelShouldNotExistPassesIfLabelDoesntExist() throws Throwable {
            setLabelExists(false);

            specify(new Block() {
                public void run() throws Throwable {
                    context.labelShouldNotExist(labelIdentifier);
                }
            }, must.not().raise(AssertionError.class));
        }

        public void labelShouldNotExistFailsIfLabelExists() throws Throwable {
            setLabelExists(true);

            specify(new Block() {
                public void run() throws Throwable {
                    context.labelShouldNotExist(labelIdentifier);
                }
            }, must.raiseExactly(AssertionError.class, "Label '" + labelIdentifier + "' exists"));
        }

        private void setLabelExists(final boolean labelExists) {
            checking(new Expectations() {{
                one(existenceResolver).satisfiesCondition(with(equal(labelIdentifier)));
                will(returnValue(labelExists));
            }});
        }
    }
}
