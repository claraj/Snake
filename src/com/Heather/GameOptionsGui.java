package com.Heather;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

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

        //add listeners
        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String stSize=sizeComboBox.getSelectedItem().toString();
                String stSpeed=speedComboBox.getSelectedItem().toString();

                if(stSize.equals("Small")){
                    SnakeGame.setMaxDimension(251,251);
                }else if (stSize.equals("Medium")){
                    SnakeGame.setMaxDimension(501, 501);
                }else if (stSize.equals("Large")){
                    SnakeGame.setMaxDimension(751, 751);
                }
                if(stSpeed.equals("Slow")){
                    SnakeGame.setClockInterval(350);
                }else if (stSpeed.equals("Average")){
                    SnakeGame.setClockInterval(500);
                }else if (stSpeed.equals("Fast")){
                    SnakeGame.setClockInterval(650);
                }

            }
        });
        warpCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                warp=warpCheckBox.isSelected();
            }
        });
        mazeCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                maze=mazeCheckBox.isSelected();
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
            }
        });
    }
    public boolean getBolWarp(){
        return warp;
    }
    public boolean getBolMaze(){
        return maze;
    }

}
