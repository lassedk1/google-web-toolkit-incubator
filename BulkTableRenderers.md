# Introduction #
For those that need every last millisecond of speed for table manipulation, we have introduced table bulk renderers and PreloadedTable.

A `TableBulkRenderer` will render all rows in a table at once.
After being loaded, widgets, cell spans, row spans etc. may be added to the table, but there will be no speed advantage for them.

A `PreloadedTable` is a simple FlexTable that allows programmers to use the familiar declarative API and still get some of the speed advantages from bulk rendering the contents of the table only after its been attached to the DOM.

Here are some comparison numbers for a 120 X 15 row table.

| IE-Table Widget | 1st run| 2nd run | 3rd run| average milli|
|:----------------|:-------|:--------|:-------|:-------------|
| `FlexTable`     | 2125   | 1953    | 1844   |1973          |
| `Grid`          | 734    | 734     |734     | 734          |
|  bulk rendered `FlexTable` | 156    | 156     |157     |156           |
| `PreloadedTable` |203     |203      | 187    |197           |


| FireFox-Table Widget | 1st run| 2nd run | 3rd run| average milli|
|:---------------------|:-------|:--------|:-------|:-------------|
| `FlexTable`          | 687    | 687     | 688    | 687          |
| `Grid`               | 436    | 438     | 500    | 458          |
|  Bulk rendered `FlexTable` | 78     | 172     | 78     | 111          |
| `PreloadedTable`     | 125    | 219     | 219    | 187          |