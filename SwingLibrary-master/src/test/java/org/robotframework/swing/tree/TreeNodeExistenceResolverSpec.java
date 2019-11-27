/*
 * Copyright 2008-2011 Nokia Siemens Networks Oyj
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package org.robotframework.swing.tree;

import jdave.junit4.JDaveRunner;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.netbeans.jemmy.TimeoutExpiredException;
import org.robotframework.jdave.mock.MockSupportSpecification;

@RunWith(JDaveRunner.class)
public class TreeNodeExistenceResolverSpec extends MockSupportSpecification<TreeNodeExistenceResolver> {
    public class ResolvingNodeExistence {
        private TreePathFactory treePathFactory;
        private String nodePath = "some|path";

        public TreeNodeExistenceResolver create() {
            TreeNodeExistenceResolver treeNodeExistenceResolver = new TreeNodeExistenceResolver(dummy(TreeOperator.class));
            treePathFactory = injectMockTo(treeNodeExistenceResolver, TreePathFactory.class);
            return treeNodeExistenceResolver;
        }
        
        public void passesWhenTreeNodeExists() {
            checking(new Expectations() {{
                one(treePathFactory).parseArgument(nodePath);
            }});
            
            specify(context.treeNodeExists(nodePath));
        }
        
        public void failsWhenTreeNodeDoesntExist() {
            checking(new Expectations() {{
                one(treePathFactory).parseArgument(nodePath);
                will(throwException(new TimeoutExpiredException(nodePath)));
            }});
            
            specify(!context.treeNodeExists(nodePath));
        }
    }
}
