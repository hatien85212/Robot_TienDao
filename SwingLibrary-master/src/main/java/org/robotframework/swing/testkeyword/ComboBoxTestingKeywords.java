package org.robotframework.swing.testkeyword;

import org.netbeans.jemmy.operators.ContainerOperator;
import org.netbeans.jemmy.operators.JComboBoxOperator;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.swing.chooser.ByNameOrTextComponentChooser;
import org.robotframework.swing.context.Context;

@RobotKeywords
public class ComboBoxTestingKeywords {
    @RobotKeyword
    public void disableCombobox(String identifier) {
        createOperator(identifier).setEnabled(false);
    }
    
    @RobotKeyword
    public void enableCombobox(String identifier) {
        createOperator(identifier).setEnabled(true);
    }
    
    @RobotKeyword
    public String getComboboxText(String identifier) {
        return createOperator(identifier).getTextField().getText();
    }

    private JComboBoxOperator createOperator(String identifier) {
        return new JComboBoxOperator((ContainerOperator) Context.getContext(), new ByNameOrTextComponentChooser(identifier));
    }
}
