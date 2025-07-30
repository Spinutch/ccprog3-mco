package controller;

import model.*;
import view.*;

import javax.swing.*;
import java.awt.event.*;
import java.util.List;

public class MagicItemController {
    public MagicItemController(GameModel model, model.Character character, boolean isPlayer1) {
        MagicItemManagementView view = new MagicItemManagementView();

        view.setViewInventoryListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<MagicItem> inventory = character.getInventory();
                if (inventory.isEmpty()) {
                    JOptionPane.showMessageDialog(view, "You have to win to get magic items!", "Inventory", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(view, String.join(", ",
                        inventory.stream().map(MagicItem::getName).toArray(String[]::new)
                    ), "Inventory", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        view.setEquipItemListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose();
                new EquipItemView(character.getInventory());  // You need to implement this view
            }
        });

        view.setUnequipItemListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MagicItem item = character.getEquippedItem();
                if (item == null) {
                    JOptionPane.showMessageDialog(view, "No item equipped!", "Info", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                int res = JOptionPane.showConfirmDialog(view,
                    "Are you sure you want to unequip " + item.getName() + "?",
                    "Confirm Unequip", JOptionPane.YES_NO_OPTION);

                if (res == JOptionPane.YES_OPTION) {
                    character.unequipItem();
                }
            }
        });

        view.setBackListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose();
                new ActualEditCharacterView();
                new ActualEditCharacterController(model, character, isPlayer1);
            }
        });

        view.setVisible(true);
    }
}
