# Reproducer of an issue related with custom LaF and menu focus

The reproducer simple Java/Swing application, that give focus on a text field then call menu by
Alt/Key combination, failed assertion as a "wrong focus owner", that the focus does not move to
`JRootPane` from `JTextField`, when activate a custom LaF that utilizes `UIManager`'s `getDefaults`
mechanism and just wraps system's default LaF.

`CustomThemeOfWrappingSystemLaf` class is the example of the custom LaF that wrap system laf.

## Conditions

OS: Linux and Windows
OpenJDK: 8, 11, 17 

## Project affected

- OmegaT [BUGS#1073](https://sourceforge.net/p/omegat/bugs/1073/)

## License

The codes and contents are licensed under GNU General Public License version 2,
or (at your option) any later versions.
