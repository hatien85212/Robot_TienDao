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
package org.robotframework.swing.testkeyword;

import org.junit.Assert;
import java.io.File;

import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.swing.testapp.TestFileChooser;

@RobotKeywords
public class FileChooserTestingKeywords {
    @RobotKeyword
    public void fileChooserShouldHaveBeenCancelled() {
        Assert.assertTrue(TestFileChooser.cancelled);
    }
    
    @RobotKeyword
    public void selectedFileShouldBe(String expectedFile) {
        File expected = new File(expectedFile);
        File selected = new File(TestFileChooser.selectedFilePath);
        if (!expected.equals(selected))
            throw new RuntimeException("Paths are not equal "+expected+" != "+selected);
    }
}
