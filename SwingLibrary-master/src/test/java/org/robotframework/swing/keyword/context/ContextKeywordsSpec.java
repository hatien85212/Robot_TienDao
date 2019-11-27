package org.robotframework.swing.keyword.context;

import jdave.Specification;
import jdave.junit4.JDaveRunner;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.laughingpanda.beaninject.Inject;
import org.robotframework.jdave.contract.FieldIsNotNullContract;
import org.robotframework.jdave.contract.RobotKeywordContract;
import org.robotframework.jdave.contract.RobotKeywordsContract;
import org.robotframework.swing.context.ContainerOperator;
import org.robotframework.swing.context.Context;
import org.robotframework.swing.factory.OperatorFactory;

import javax.swing.*;


@RunWith(JDaveRunner.class)
public class ContextKeywordsSpec extends Specification<ContextKeywords> {
    public class Any {
        public ContextKeywords create() {
            return new ContextKeywords();
        }

        public void isRobotKeywordsAnnotated() {
            specify(context, satisfies(new RobotKeywordsContract()));
        }

        public void hasOperatorFactory() throws Throwable {
            specify(context, satisfies(new FieldIsNotNullContract("operatorFactory")));
        }

        public void hasSelectContextKeyword() {
            specify(context, satisfies(new RobotKeywordContract("selectContext")));
        }

    }

    public class Operating {
        private String contextIdentifier = "someContainer";

        public ContextKeywords create() {
            return new ContextKeywords();
        }

        public void selectsContext() {
            final OperatorFactory operatorFactory = mock(OperatorFactory.class);
            final ContainerOperator containerOperator = mock(ContainerOperator.class);

            Inject.field("operatorFactory").of(context).with(operatorFactory);
            checking(new Expectations() {{
                one(operatorFactory).createOperator(contextIdentifier);
                will(returnValue(containerOperator));

                one(containerOperator).getSource();
                will(returnValue(new JPanel()));
            }});

            context.selectContext(contextIdentifier);
            specify(Context.getContext(), must.equal(containerOperator));
        }
    }
}
