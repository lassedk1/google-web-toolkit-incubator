# Introduction #
This document is intended to cover some proposed enhancements to the current GWT accessibility support.
# Complete Keyboard accessibility #
| Priority | High |
|:---------|:-----|
| Level of effort required | High |


## Justification ##
While the visual impaired are certainly a large percentage of people that require accessibility, another important group is those that are not able to use a mouse due to physical handicaps. For them, having an application be fully keyboard navigable is absolutely critical.
## Problem statement ##
Here we list some of the problems that must be solved in order to implement complete keyboard accessibility.

#### When is a navigation key actually available for navigation? ####
  * A text box consumes its Left-arrow, but not if it is at the beginning of its input. Similarly, it consumed its Right-arrow, but not if it is at the end of its input.
  * A `DateBox` needs to defer to its underlying text box to determine which navigation keys are consumed, except if its `DatePicker` is currently showing. In that case, the `DateBox` should allow the `DatePicker` to determine which navigation keys are consumed.
  * A Cell-based selectable grid consumes all the arrow key. A row selectable grid only consumed the Up-arrow and Down-arrow keys.

#### If a navigation key is available for navigation, what should be done with it? ####
  * Tab order is the "typical" way to navigate an html form.  Should that be messed with?
  * In a grid control that has a selected cell, the four arrow keys should move the selected cell, and only move focus if the element in the cell is focusable.
  * In a grid control that can select rows, but not cells, the up and down arrows should move the selection, what should the left and right arrows do?
  * If the container is not navigation aware, what should it do with the navigation keys?
  * How should containers that switch their children be navigated? (`StackPanel`, `TabPanel`)
  * How should containers like `DisclosurePanel` be navigated?
#### How should we deal with elements not in the normal form-flow? ####
  * How should menus always be accessed?
  * How should popups be closed?

#### Proposed division of  widgets ####
A navigable widget is a widget which has some sort of internal navigation. `TextBox`, `DateBox`, `DatePicker`, `Tree` are examples of such. Here we further subdivide the navigable widgets in order to be able to meaningfully define behaviors for them.

| **Category** | **Description** | **Examples** |
|:-------------|:----------------|:-------------|
| Interactive Widget | Any widget that can be manipulated by user input |  `TextBox`, `HyperLink`, `Tree` |
| Navigatable Widget | A widget which has some sort of internal navigation | `ListBox`, `Tree`,`DatePicker` |
| Viewport Widget | A navigable widget which typically shows a discrete portion of its content | `TabPanel`, `PagingScrollTable` and  `StackPanel` |
| Disclosure Widget | Widgets which can show/hide a single extra data section | `DisclosurePanel` and `SuggestBoxBox` |
| Cell Grid    | A grid which has a cell highlighted, `DatePicker` is an example |

