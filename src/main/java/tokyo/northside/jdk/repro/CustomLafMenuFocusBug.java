package tokyo.northside.jdk.repro;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class CustomLafMenuFocusBug {

    private volatile static JFrame f;
    private volatile static boolean uiCreated;

    public static void main(String[] args) throws Exception {
        String myLafClass = MyLookAndFeel.class.getName();
        UIManager.installLookAndFeel("My Custom Look and Feel", myLafClass);
        ClassLoader cl = ClassLoader.getSystemClassLoader();
        Class<?> clazz = cl.loadClass(myLafClass);
        UIManager.setLookAndFeel((LookAndFeel) clazz.getDeclaredConstructor().newInstance());
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
