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

package org.robotframework.swing.context;

import java.awt.Component;

import org.junit.Assert;

public abstract class AbstractContextVerifier implements ContextVerifier {
    private final String errorMessage;

    public AbstractContextVerifier(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void verifyContext() {
        Class<? extends Component>[] expectedClasses = getExpectedClasses();
        for (Class<? extends Component> expectedClass : expectedClasses) {
            if (expectedClass.isAssignableFrom(contextClass())) {
                return;
            }
        }
        Assert.fail(errorMessage + " Current context is " + contextClass().getName());
    }

    protected abstract Class<? extends Component>[] getExpectedClasses();
    
    private Class<? extends Component> contextClass() {
        return Context.getContext().getSource().getClass();
    }
}
