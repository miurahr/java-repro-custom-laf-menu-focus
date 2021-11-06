# Reproducer of an issue related with custom LaF and menu focus

When running Swing application with custom LaF that utilize UIManager.getDefaults(),
and wraps system LaF, test failed as wrong focus owner.

This is triggered by installing custom LaF that wrap system laf.
CustomThemeOfWrappingSystemLaf class is the example. 
After calling JMenu by Alt/Key combinations, the focus is still on TextField,
not on JRootPane. The issue is observed on Linux, and Windows.

- related: OmegaT [BUGS#1073](https://sourceforge.net/p/omegat/bugs/1073/)



