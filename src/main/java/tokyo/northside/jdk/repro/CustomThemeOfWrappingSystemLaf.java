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

/**
 * Custom Look And Feel that wraps system LookAndFeel.
 * Just return values of system laf's getDefaults for getDefaults method.
 */
public class CustomThemeOfWrappingSystemLaf extends LookAndFeel {

    private static final String NAME = CustomThemeOfWrappingSystemLaf.class.getSimpleName();
    private static final String CLASSNAME = CustomThemeOfWrappingSystemLaf.class.getName();

    private final LookAndFeel systemLookAndFeel;

    /**
     * Utility to install and set LookAndFeel.
     * @throws Exception when got error.
     */
    public static void installAndSet() throws Exception {
        UIManager.installLookAndFeel(NAME, CLASSNAME);
        Class<?> clazz = ClassLoader.getSystemClassLoader().loadClass(CLASSNAME);
        UIManager.setLookAndFeel((LookAndFeel) clazz.getDeclaredConstructor().newInstance());
    }

    /**
     * Constructor of custom laf.
     * @throws UnsupportedLookAndFeelException when failed to create laf.
     */
    public CustomThemeOfWrappingSystemLaf() throws UnsupportedLookAndFeelException {
        String systemLafClass = UIManager.getSystemLookAndFeelClassName();
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if (info.getClassName().equals(systemLafClass)) {
                systemLookAndFeel = UIManager.createLookAndFeel(info.getName());
                return;
            }
        }
        // Should never happen: system LAF is guaranteed to be installed
        throw new RuntimeException("Could not identify system LAF name");
    }

    @Override
    public UIDefaults getDefaults() {
        return systemLookAndFeel.getDefaults();
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
