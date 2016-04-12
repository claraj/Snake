package com.Heather;

import javax.swing.*;

/**
 * Created by cryst on 4/12/2016.
 */
public class GameOptionsGui extends JFrame {
    private JComboBox speedComboBox;
    private JComboBox sizeComboBox;
    private JCheckBox warpCheckBox;
    private JCheckBox mazeCheckBox;
    private JButton OKButton;
    private JButton cancelButton;
    private JPanel rootPanel;

    private boolean warp;
    private boolean maze;
    //Combobox models
    DefaultComboBoxModel<String>speedComboModel;
    DefaultComboBoxModel<String>sizeComboModel;

    public GameOptionsGui(){
        super("Game Options");

        setContentPane(rootPanel);
        pack();
        setVisible(true);

        //create model
        speedComboModel=new DefaultComboBoxModel<>();
        sizeComboModel=new DefaultComboBoxModel<>();
        //configure to use model
        speedComboBox.setModel(speedComboModel);
        sizeComboBox.setModel(sizeComboModel);
        //Put stuff in comboboxes
        speedComboModel.addElement("Slow");
        speedComboModel.addElement("Average");
        speedComboModel.addElement("Fast");
        sizeComboModel.addElement("Small");
        sizeComboModel.addElement("Medium");
        sizeComboModel.addElement("Fast");
    }


}
