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

package org.robotframework.swing.chooser;

import java.awt.Component;

import org.jretrofit.Retrofit;
import org.netbeans.jemmy.ComponentChooser;
import org.robotframework.swing.util.ObjectUtils;

public class ByTitleComponentChooser implements ComponentChooser {
    private final String expectedTitle;

    public ByTitleComponentChooser(String expectedTitle) {
        this.expectedTitle = expectedTitle;
    }

    @Override
    public boolean checkComponent(Component component) {
        WithTitle withTitle = (WithTitle) Retrofit.partial(component,
                WithTitle.class);
        return ObjectUtils.nullSafeEquals(expectedTitle, withTitle.getTitle());
    }

    @Override
    public String getDescription() {
        return expectedTitle;
    }
}
