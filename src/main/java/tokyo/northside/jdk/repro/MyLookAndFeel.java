package tokyo.northside.jdk.repro;

import javax.swing.*;
import java.awt.*;

public class MyLookAndFeel extends LookAndFeel {

    private final LookAndFeel systemLookAndFeel;
    private static final String NAME = "MyLookAndFeel";

    public MyLookAndFeel() throws UnsupportedLookAndFeelException {
        String systemLafClass = UIManager.getSystemLookAndFeelClassName();
        String systemLafName = null;
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if (info.getClassName().equals(systemLafClass)) {
                systemLafName = info.getName();
            }
        }
        if (systemLafName == null) {
            // Should never happen: system LAF is guaranteed to be installed
            throw new RuntimeException("Could not identify system LAF name");
        }
        systemLookAndFeel = UIManager.createLookAndFeel(systemLafName);
    }

    public UIDefaults setDefaults(UIDefaults defaults) {
        defaults.put("TextPane.background", defaults.getColor("List.background"));
        return defaults;
    }

    @Override
    public UIDefaults getDefaults() {
        return setDefaults(systemLookAndFeel.getDefaults());
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getID() {
        return NAME;
    }

    @Override
    public String getDescription() {
        return NAME;
    }

    @Override
    public boolean isNativeLookAndFeel() {
        return systemLookAndFeel.isNativeLookAndFeel();
    }

    @Override
    public boolean isSupportedLookAndFeel() {
        return systemLookAndFeel.isSupportedLookAndFeel();
    }

    @Override
    public boolean getSupportsWindowDecorations() {
        return systemLookAndFeel.getSupportsWindowDecorations();
    }

    @Override
    public void provideErrorFeedback(Component component) {
        systemLookAndFeel.provideErrorFeedback(component);
    }

    @Override
    public void initialize() {
        systemLookAndFeel.initialize();
    }

    @Override
    public void uninitialize() {
        systemLookAndFeel.uninitialize();
    }


}
