package com.spotify.view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.spotify.api.SpotifyClient;
import com.spotify.interface_adapter.save_playlist.SavePlaylistController;
import com.spotify.interface_adapter.save_playlist.SavePlaylistState;
import com.spotify.interface_adapter.save_playlist.SavePlaylistViewModel;

/**
 * The View for the Save Use Case.
 */
public class SaveView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "Enter Playlist Info";

    private final SavePlaylistViewModel saveViewModel;
    private final JTextField nameInputField = new JTextField(30);
    private final JTextField descriptionInputField = new JTextField(30);
    private final JCheckBox isPublicInputField = new JCheckBox("Public");
    private SavePlaylistController savePlaylistController;

    private final JButton save;
    private final JButton cancel;

    public SaveView(SavePlaylistViewModel savePlaylistViewModel) {
        this.saveViewModel = savePlaylistViewModel;
        saveViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel(SavePlaylistViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final LabelTextPanel nameInfo = new LabelTextPanel(
                new JLabel(SavePlaylistViewModel.NAME_LABEL), nameInputField);
        final LabelTextPanel descriptionInfo = new LabelTextPanel(
                new JLabel(SavePlaylistViewModel.DESCRIPTION_LABEL), descriptionInputField);
        final LabelTextPanel publicInfo = new LabelTextPanel(
                new JLabel(SavePlaylistViewModel.PUBLIC_LABEL), isPublicInputField);


        final JPanel buttons = new JPanel();
        this.save = new JButton(SavePlaylistViewModel.SAVE_BUTTON_LABEL);
        buttons.add(save);

        this.cancel = new JButton(SavePlaylistViewModel.CANCEL_BUTTON_LABEL);
        buttons.add(cancel);

        save.addActionListener(
                // This creates an anonymous subclass of ActionListener and instantiates it.
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(save)) {
                            final SavePlaylistState currentState = saveViewModel.getState();

                            try {
                                savePlaylistController.execute(
                                        currentState.getName(),
                                        currentState.getDescription(),
                                        currentState.getIsPublic()
                                );
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }
        );

        cancel.addActionListener(this);

        addNameListener();
        addDescriptionListener();
        addPublicListener();

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(title);
        this.add(nameInfo);
        this.add(descriptionInfo);
        this.add(publicInfo);
        this.add(buttons);

    }

    private void addNameListener() {
        nameInputField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final SavePlaylistState currentState = saveViewModel.getState();
                currentState.setName(nameInputField.getText());
                saveViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
        });
    }

    private void addDescriptionListener() {
        descriptionInputField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final SavePlaylistState currentState = saveViewModel.getState();
                currentState.setDescription(descriptionInputField.getText());
                saveViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
        });
    }

    private void addPublicListener() {
        isPublicInputField.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                final SavePlaylistState currentState = saveViewModel.getState();
                currentState.setIsPublic(isPublicInputField.isSelected());
                saveViewModel.setState(currentState);
            }
        });
    }


    @Override
    public void actionPerformed(ActionEvent evt) {
        JOptionPane.showMessageDialog(this, "Cancel not implemented yet.");
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final SavePlaylistState state = (SavePlaylistState) evt.getNewValue();
        if (state.getNameError() != null) {
            JOptionPane.showMessageDialog(this, state.getNameError());
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setSignupController(SavePlaylistController controller) {
        this.savePlaylistController = controller;
    }
}

