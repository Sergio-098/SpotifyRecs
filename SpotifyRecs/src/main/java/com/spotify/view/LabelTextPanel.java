package com.spotify.view;

import javax.swing.*;

/**
 * A panel containing a label and a text field.
 */
class LabelTextPanel extends JPanel {
    LabelTextPanel(JLabel label, JComponent field) {
        this.add(label);
        this.add(field);
    }
}
