# Reproducer of a custom LaF and menu focus issue

This is a reproducer which give focus on a text field then call menu by
Alt/Key combination, failed assertion as a "wrong focus owner", that the focus does not move to
`JRootPane` from `JTextField`, when activate a custom LaF that utilizes `UIManager`'s `getDefaults`
mechanism and just wraps system's default LaF.

`CustomThemeOfWrappingSystemLaf` class is the example of the minimum custom LaF that wrap system laf.

When comment-out `CustomThemeOfWrappingSystemLaf.installAndSet()` from a main method,
all the tests are passed as expected.

## Conditions

- OS: Linux, macOS and Windows
- OpenJDK: 8, 11, 17

## Work around

When custom LaF is inherited from abstract `BasicLookAndFeel` instead of `LookAndFeel` class,
the problem is gone. Please see PR #1 .

## Project affected

- OmegaT [BUGS#1073](https://sourceforge.net/p/omegat/bugs/1073/)

## License

The codes and contents are licensed under GNU General Public License version 2,
or (at your option) any later versions.
