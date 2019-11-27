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

package org.robotframework.swing.table;

import org.netbeans.jemmy.operators.JTableOperator;
import org.netbeans.jemmy.operators.JTableOperator.TableCellChooser;

public abstract class AbstractTableCellChooser implements TableCellChooser {
    private final int expectedRow;

    public AbstractTableCellChooser(int row) {
        this.expectedRow = row;
    }

    public boolean checkCell(JTableOperator tableOperator, int actualRow, int column) {
        return expectedRow == actualRow && checkColumn(tableOperator, column);
    }

    public String getDescription() {
        return "Chooses table cell according to row index and column identifier";
    }

    protected abstract boolean checkColumn(JTableOperator tableOperator, int column);
}
