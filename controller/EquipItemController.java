package controller;

import model.Character;
import model.GameModel;
import model.MagicItem;
import view.EquipItemView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class EquipItemController {
    private GameModel model;
    private Character character;
    private EquipItemView view;

    public EquipItemController(GameModel model, Character character) {
        this.model = model;
        this.character = character;

        List<MagicItem> inventory = character.getInventory();
        if (inventory.isEmpty()) {
            JOptionPane.showMessageDialog(null, "You have to win to get magic items!");
            return;
        }

        this.view = new EquipItemView(inventory);

        view.getConfirmButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JCheckBox[] checkBoxes = view.getItemCheckBoxes();
                MagicItem selectedItem = null;

                // Only one item can be equipped
                for (int i = 0; i < checkBoxes.length; i++) {
                    if (checkBoxes[i].isSelected()) {
                        if (selectedItem != null) {
                            JOptionPane.showMessageDialog(view, "You can only equip one item.");
                            return;
                        }
                        selectedItem = inventory.get(i);
                    }
                }

                if (selectedItem == null) {
                    JOptionPane.showMessageDialog(view, "Please select an item to equip.");
                } else {
                    character.setEquippedItem(selectedItem);
                    JOptionPane.showMessageDialog(view, "Equipped " + selectedItem.getName() + "!");
                    view.dispose();
                }
            }
        });
    }
}
