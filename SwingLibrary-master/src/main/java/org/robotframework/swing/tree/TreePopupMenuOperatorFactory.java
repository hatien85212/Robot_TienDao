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

import javax.swing.JPopupMenu;
import javax.swing.tree.TreePath;

import org.netbeans.jemmy.operators.JPopupMenuOperator;
import org.robotframework.swing.factory.IdentifierParsingOperatorFactory;
import org.robotframework.swing.popup.PopupMenuOperatorFactory;

public class TreePopupMenuOperatorFactory extends IdentifierParsingOperatorFactory<JPopupMenuOperator> {
    protected final TreeOperator treeOperator;
    protected final PopupMenuOperatorFactory popupOperatorFactory = new PopupMenuOperatorFactory();
    
    public TreePopupMenuOperatorFactory(TreeOperator treeOperator) {
        this.treeOperator = treeOperator;
    }

    @Override
    public JPopupMenuOperator createOperatorByIndex(int index) {
        JPopupMenu popupMenu = treeOperator.callPopupOnRow(index);
        return createPopupOperator(popupMenu);
    }

    @Override
    public JPopupMenuOperator createOperatorByName(String path) {
        TreePath treePath = treeOperator.findPath(path);
        JPopupMenu popupMenu = treeOperator.callPopupOnPath(treePath);
        return createPopupOperator(popupMenu);
    }
    
    public JPopupMenuOperator createOperatorBySelection() {
        JPopupMenu popupMenu = treeOperator.callPopupOnPaths(treeOperator.getSelectionPaths());
        return createPopupOperator(popupMenu);
    }
    
    protected JPopupMenuOperator createPopupOperator(JPopupMenu popupMenu) {
        return popupOperatorFactory.createPopupOperator(popupMenu);
    }
}
