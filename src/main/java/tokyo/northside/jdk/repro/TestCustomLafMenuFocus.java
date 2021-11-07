/*
  Reproducer of custom laf and menu focus

    Copyright (C) 2021 Hiroshi Miura

    This program is free software; you can redistribute it and/or modify it
    under the terms of the GNU General Public License as published by the Free
    Software Foundation; either version 2 of the License, or (at your option)
    any later version.

    This program is distributed in the hope that it will be useful, but WITHOUT
    ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
    FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for
    more details.

    You should have received a copy of the GNU General Public License along
    with this program; if not, write to the Free Software Foundation, Inc., 59
    Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package tokyo.northside.jdk.repro;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * When installs Custom LaF which wraps system LaF, the test failed as wrong focus owner.
 * <p>
 *     This is triggered by installing custom LaF that wrap system laf.
 *     CustomThemeOfWrappingSystemLaf class is the example.
 *     When failed, the focus is on TextField, not on RootPane.
 *     The issue is observed on Linux, and Windows.
 * </p>
 */
public class TestCustomLafMenuFocus {

    private volatile static JFrame f;
    private volatile static boolean uiCreated;

    public static void main(String[] args) throws Exception {
        CustomThemeOfWrappingSystemLaf.installAndSet();
        try {
            SwingUtilities.invokeAndWait(() -> {
                try {
                    uiCreated = createGUI();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            if (uiCreated) {
                test();
            }
        } finally {
            SwingUtilities.invokeAndWait(() -> f.dispose());
        }
    }

    private static void test() {
        final Robot robot;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
        robot.setAutoDelay(150);
        robot.waitForIdle();

        robot.keyPress(KeyEvent.VK_ALT);
        robot.keyPress(KeyEvent.VK_M);
        robot.keyRelease(KeyEvent.VK_M);
        robot.keyRelease(KeyEvent.VK_ALT);
        robot.waitForIdle();

        Component focusOwner = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();

        if (focusOwner != f.getRootPane()) {
            throw new RuntimeException("Wrong focus owner, actual is" + focusOwner.toString());
        }
    }

    private static boolean createGUI() {
        f = new JFrame();

        JPanel panel = new JPanel();
        JTextField tf = new JTextField();
        tf.setColumns(10);
        panel.add(tf);

        f.setJMenuBar(getMenuBar());
        f.add(panel);
        f.pack();
        f.setVisible(true);
        f.setLocationRelativeTo(null);
        tf.requestFocus();
        return true;
    }

    static JMenuBar getMenuBar() {
        JMenuBar menuBar;
        JMenu menu;
        menuBar = new JMenuBar();

        menu = new JMenu("Menu");
        menu.setMnemonic(KeyEvent.VK_M);
        menuBar.add(menu);

        JMenuItem mi = new JMenuItem("test");
        mi.setMnemonic(KeyEvent.VK_T);
        menu.add(mi);

        return menuBar;
    }
}
