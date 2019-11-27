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

package org.robotframework.swing.internalframe;

import org.netbeans.jemmy.ComponentChooser;
import org.netbeans.jemmy.operators.ContainerOperator;
import org.netbeans.jemmy.operators.JInternalFrameOperator;
import org.robotframework.swing.operator.ComponentWrapper;

public class InternalFrameOperator extends JInternalFrameOperator implements ComponentWrapper {
    public InternalFrameOperator(ContainerOperator container, int index) {
        super(container, index);
    }

    public InternalFrameOperator(ContainerOperator container, ComponentChooser chooser) {
        super(container, chooser);
    }
}
