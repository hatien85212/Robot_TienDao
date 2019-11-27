package org.robotframework.swing.spinner;

import java.awt.Component;

import org.netbeans.jemmy.operators.JSpinnerOperator;

public class DefaultSpinnerOperator implements SpinnerOperator {
    private final JSpinnerOperator spinnerOperator;

    public DefaultSpinnerOperator(JSpinnerOperator spinnerOperator) {
        this.spinnerOperator = spinnerOperator;
    }

    public Component getSource() {
        return spinnerOperator.getSource();
    }

    public Object getValue() {
        return spinnerOperator.getValue();
    }

    public void setValue(Object value) {
    	spinnerOperator.setValue(value);
    }
    
    public void decrease() {
        spinnerOperator.getDecreaseOperator().push();
    }

    public void decreaseToMinimum() {
        spinnerOperator.scrollToMinimum();
    }

    public void increase() {
        spinnerOperator.getIncreaseOperator().push();
    }

    public void increaseToMaximum() {
        spinnerOperator.scrollToMaximum();
    }
}
