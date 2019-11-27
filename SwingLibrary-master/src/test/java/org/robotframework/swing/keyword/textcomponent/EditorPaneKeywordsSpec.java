package org.robotframework.swing.keyword.textcomponent;

import jdave.junit4.JDaveRunner;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.robotframework.jdave.contract.RobotKeywordContract;
import org.robotframework.jdave.contract.RobotKeywordsContract;
import org.robotframework.swing.factory.OperatorFactory;
import org.robotframework.jdave.mock.MockSupportSpecification;
import org.robotframework.swing.textcomponent.EditorPaneOperator;

@RunWith(JDaveRunner.class)
public class EditorPaneKeywordsSpec extends MockSupportSpecification<EditorPaneKeywords> {
    public class Any {
        public EditorPaneKeywords create() {
            return new EditorPaneKeywords();
        }
        
        public void isRobotKeywordsAnnotated() {
            specify(context, satisfies(new RobotKeywordsContract()));
        }
        
        public void hasClickOnHyperLinkKeyword() {
            specify(context, satisfies(new RobotKeywordContract("clickHyperLink")));
        }
    }
    
    public class Operating {
        public EditorPaneKeywords create() {
            return new EditorPaneKeywords();
        }
        
        public void clicksHyperLink() {
            final String identifier = "someEditor";
            final String linkText = "someLink";
            
            final OperatorFactory operatorFactory = injectMockToContext(OperatorFactory.class);
            final EditorPaneOperator operator = mock(EditorPaneOperator.class);
            
            checking(new Expectations() {{
                one(operatorFactory).createOperator(identifier); will(returnValue(operator));
                one(operator).activateHyperLink(linkText);
            }});
            
            context.clickHyperLink(identifier, linkText);
        }
    }
}
