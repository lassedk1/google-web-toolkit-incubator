| Live demo | http://google-web-toolkit-incubator.googlecode.com/svn/branches/dflorey/tableAddons/demo/TreeTableDemo/TreeTableDemo.html |
|:----------|:--------------------------------------------------------------------------------------------------------------------------|
| Java docs | coming soon...                                                                                                            |
| Sample code | [Demo code](http://google-web-toolkit-incubator.googlecode.com/svn/branches/dflorey/tableAddons/src-demo/com/google/gwt/gen2/demo/scrolltable/client/TreeTableDemo.java) |
| Source starting point| [TreeTable.java](http://google-web-toolkit-incubator.googlecode.com/svn/branches/dflorey/tableAddons/src/com/google/gwt/gen2/table/client/TreeTable.java) |

# Introduction #
A TreeTable is able to show a tree where each tree item can have some additional fields that shall be displayed.
The TreeTable is extending the gwt incubator table framework. The code currently exists in my branch. To check out the latest sources, type

```
svn checkout http://google-web-toolkit-incubator.googlecode.com/svn/branches/dflorey/tableAddons gwt-incubator-tableaddons
```

# Details #

The TreeTable is able to display extremely large trees as you can use paging. On source code level the TreeTable extends PagingScrollTable and inherits all the nice features like column specific sorting and filtering.

If you display a large amount of data, you will end up with navigating to a page, where the root parent of your items may not be visible.
The TreeTable makes navigation handy by giving you shortcuts to all the parent items by simply clicking on the dotted lines referring to the parent node.
You can navigate between pages without loosing any information about opened or closed tree nodes.

# Tutorials #

The following short tutorials will guide you to create a working TreeTable.
The TreeTable can be used to display data constructed on client side (in the browser) or to display large trees coming from the server.

## Client side table ##
This example will show you how to create a fully functional TreeTable on the client side without client / server communication.

### Create the domain class ###

First of all we have to create a class reflecting the objects in our tree.
Let's create a fictional family tree, so we need a class representing our ancestors:

```
class Ancestor extends AbstractTreeTableItem {
    private String firstName, lastName;
    private double iq;
    private Date dateOfBirth;

    public Ancestor(String firstName, String lastName, int iq, Date dateOfBirth) {
      this.firstName = firstName;
      this.lastName = lastName;
      this.iq = iq;
      this.dateOfBirth = dateOfBirth;
    }

    public String getFirstName() {
      return firstName;
    }

    public String getLastName() {
      return lastName;
    }

    public double getIq() {
      return iq;
    }

    public Date getDateOfBirth() {
      return dateOfBirth;
    }

    @Override
    public String getDisplayName() {
      return firstName+" "+lastName;
    }

    @Override
    public String getId() {
      return firstName+lastName+dateOfBirth;
    }
}

```

Note that this class extends the AbstractTreeTableItem and we have to implement two abstract methods providing some info that the TreeTable requires to work properly.
We have to implement the getDisplayName() method that should return a user friendly textual representation and the getId() method that has to return a unique id.

If you have an existing class that you want to display in a TreeTable you can also implement the TreeTableItem interface directly of you can simply wrap your class into the TreeTableItemWrapper.

### Create some sample data ###

```
private static String[] firstNames = {"Miguel", "Fred", "Bob" };
private static String[] lastNames = {"Mendez", "Sauer", "Vatwer" };

List<TreeItem<Ancestor>> rootItems = new ArrayList<TreeItem<Ancestor>>();
for (int i = 0; i < 12; i++) {
  TreeItem<Ancestor> ancestor = createAncestor();
  addChildren(ancestor, Random.nextInt(3) + 1, 0);
  rootItems.add(ancestor);
}

private void addChildren(TreeItem<Ancestor> parent, int numberOfChildren, int level) {
  for (int i = 0; i < numberOfChildren; i++) {
    TreeItem<Ancestor> child = createAncestor();
    parent.addChild(child);
    if (level < 4) {
      addChildren(child, Random.nextInt(4) + 1, level + 1);
    }
  }
}

private TreeItem<Ancestor> createAncestor() {
  return new TreeItem(new Ancestor(firstNames[Random.nextInt(firstNames.length-1)],
  lastNames[Random.nextInt(lastNames.length-1)], 
  Random.nextInt(50)+80, 
  new Date((long)Random.nextInt(1000000)*500000L)));
}
```

There is no magic in this part of the code. Create a types TreeItem containing your domain object for each tree item and add children to each tree node as desired.

### Define the table ###

You have to create a TableDefinition in order to tell the TreeTable which columns you want to display and what type of values they will contain.

## Defining the table programmatically ##

You have two options how to do that. Let's first have a look at the classic approach using the the DefaultTableDefinition class:

```
DefaultTableDefinition<Ancestor> tableDefinition = new DefaultTableDefinition<Ancestor>();
tableDefinition.addColumnDefinition(new TextColumnDefinition<Ancestor>(
    "First name", true, false) {
  public String getCellValue(Ancestor ancestor) {
    return ancestor.getFirstName();
  }
});
tableDefinition.addColumnDefinition(new TextColumnDefinition<Ancestor>(
    "Last name", true, false) {
  public String getCellValue(Ancestor ancestor) {
    return ancestor.getLastName();
  }
});
tableDefinition.addColumnDefinition(new NumberColumnDefinition<Ancestor>(
    "IQ", NumberFormat.getDecimalFormat(), true, false) {
  public Double getCellValue(Ancestor ancestor) {
    return ancestor.getIq();
  }
});
tableDefinition.addColumnDefinition(new DateColumnDefinition<Ancestor>(
    "Date of birth", DateTimeFormat.getMediumDateFormat(), true, true,
    false) {
  public Date getCellValue(Ancestor ancestor) {
    return ancestor.getDateOfBirth();
  }
});
```

You have to provide a column definition for each column. The column definition will tell the table if this column shall be sortable, filterable, editable and which header to display.
There are already some column definition classes available for the most frequently used types.
If you define a column you will be asked to implement the proper getter method to enable the table to access the right field belonging to the column.
If you define your column as editable you also have to implement the proper setter method.

## Defining the table using annotations ##

You can also use an annotation based approach to define you table. To do so you have to let your domain class implement the RowValue interface and add annotations to the getter methods that you want to expose to the table:

```
class Ancestor extends AbstractTreeTableItem implements RowValue {
    private String firstName, lastName;
    private double iq;
    private Date dateOfBirth;

    public Ancestor(String firstName, String lastName, int iq, Date dateOfBirth) {
      this.firstName = firstName;
      this.lastName = lastName;
      this.iq = iq;
      this.dateOfBirth = dateOfBirth;
    }

    @ColumnDefinition(column = 0, header = "First name", sortable = true, filterable = false)
    public String getFirstName() {
      return firstName;
    }

    @ColumnDefinition(column = 1, header = "Last name")
    public String getLastName() {
      return lastName;
    }

    @ColumnDefinition(column = 2, header = "IQ", numberFormat = NumberColumnFormat.DECIMAL_FORMAT)
    public double getIq() {
      return iq;
    }

    @ColumnDefinition(column = 3, header = "Date of birth", dateTimeFormat = DateColumnFormat.SHORT_DATE_FORMAT)
    public Date getDateOfBirth() {
      return dateOfBirth;
    }


    @Override
    public String getDisplayName() {
      return firstName+" "+lastName;
    }

    @Override
    public String getId() {
      return firstName+lastName+dateOfBirth;
    }
}

```

Now you can create the table definition by calling:

```
TableDefinition<Ancestor> tableDefinition = GWT.create(Ancestor.class);
```


### Create the TreeTable ###

Now we are ready to create the TreeTable. We have to pass our table definition and the tree items:

```
TreeTable<Ancestor> treeTable = new TreeTable<Ancestor>(tableDefinition,
    rootItems, true);
treeTable.setPageSize(12);
treeTable.setResizePolicy(ScrollTable.ResizePolicy.FILL_WIDTH);
```

In our example we specify that the tree should be expanded by default by passing the optional boolean parameter set to true.

### Create the TreeTableController ###

As we set our page size we get a paging tree table. To provide the tree table controls for paging and other features to the user we have to create a TreeTableController.

```
TreeTableController treeTableController = new TreeTableController(treeTable);
```

### Putting it all together ###

Finally we will wrap both elements into a DockPanel - this is nothing TreeTable specific, just daily business:

```
treeTableController.setWidth("100%");
treeTable.setSize("100%", "100%");
DockPanel dockPanel = new DockPanel();
dockPanel.setWidth("100%");
dockPanel.setHeight("300px");
dockPanel.add(treeTable, DockPanel.CENTER);
dockPanel.setCellHeight(treeTable, "100%");
dockPanel.setCellWidth(treeTable, "100%");
dockPanel.add(treeTableController, DockPanel.SOUTH);
```

That's it. You'll find a link to the full demo source code and the working output at the top of this page.

## Server side TreeTable ##

If you want to display large trees in a TreeTable you typically have the data on the server side and you just want to pass the visible tree nodes to the client.

The TreeTable takes care of passing only the required data when using server side tree data. It will keep track of all opened / closed nodes by remembering the nodes the user clicked. So opening all tree nodes is an extremely cheap operation.

The client side code is a little bit different from the previous example, as we don't have to create sample data etc.

Let's assume we want to use the TreeTable to browse a filesystem.

### Create the domain class ###

Again we start with creating a class representing the objects in our tree:

```
public class FileTreeTableItem extends AbstractTreeTableItem {
  private String path, name;
  private Double size;
  private Date lastModified;
  
  public FileTreeTableItem() {}
  
  public FileTreeTableItem(String path, String name, Double size, Date lastModified) {
    this.path = path;
    this.name = name;
    this.size = size;
    this.lastModified = lastModified;
  }

  public String getPath() {
    return path;
  }

  public String getName() {
    return name;
  }

  public Double getSize() {
    return size;
  }

  public Date getLastModified() {
    return lastModified;
  }

  public String getDisplayName() {
    return name;
  }

  public String getId() {
    return path;
  }
}
```

We can use the file path as the unique id for our tree items.

### Define the table ###

You have to create a TableDefinition in order to tell the TreeTable which columns you want to display and what type of values they will contain.
The easiest way is to use the DefaultTableDefinition class:

```
DefaultTableDefinition<FileTreeTableItem> tableDefinition = new DefaultTableDefinition<FileTreeTableItem>();
tableDefinition.addColumnDefinition(new TextColumnDefinition<FileTreeTableItem>(
    "Filename", true, false) {
  public String getCellValue(FileTreeTableItem file) {
    return file.getName();
  }
});
tableDefinition.addColumnDefinition(new NumberColumnDefinition<FileTreeTableItem>(
    "Size", NumberFormat.getDecimalFormat(), true, false) {
  public Double getCellValue(FileTreeTableItem file) {
    return file.getSize();
  }
});
tableDefinition.addColumnDefinition(new DateColumnDefinition<FileTreeTableItem>(
    "Last modified", DateTimeFormat.getMediumDateFormat(), true, true,
    false) {
  public Date getCellValue(FileTreeTableItem file) {
    return file.getLastModified();
  }
});
```

We use static header names in our example, in real life it would be better to pass a localized message (see gwt i18n concepts).

## Creating a RemoteTreeTable ##

Up to this part, everything looked exactly as in the previous example.
Now we want to create the tree data on the server side instead of creating the full tree on the client.

We can use the RemoteTreeTable to setup such a table easily:

```
TreeTable<FileTreeTableItem> remoteTreeTable = new RemoteTreeTable<FileTreeTableItem>(
    tableDefinition, true) {
  RemoteTableServiceAsync remoteTableService = GWT.create(RemoteTableService.class);
  @Override
  protected void requestTreeItems(TreeRequest request,
      AsyncCallback<SerializableResponse<FileTreeTableItem>> callback) {
    remoteTableService.requestTreeItems(request, callback);
  }
};
```

When creating a RemoteTreeTable you have to implement the requestTreeItems method in order to tell the table which service to call to retrieve the tree items.
In our example we construct the RemoteTableService and call the requestTreeItems method on it to get the data.

## Implementing the server side logic ##

Up to this point everything looked very simple, even simpler than in the previous example. This is because the complicated logic to construct the tree will be done on the server side.

As the source of the data may differ, I will not pay too much attention on the server side code. We'll just have a quick look at the code that is grabbing the data from the filesystem and passes it back to the client:

```
public class RemoteTableServiceImpl extends RemoteServiceServlet implements
    RemoteTableService {
  /**
   * Comparator is used for proper tree sorting
   */
  class FileComparator implements Comparator<File> {
    private final ColumnSortInfo sortInfo;

    public FileComparator(ColumnSortInfo sortInfo) {
      this.sortInfo = sortInfo;
    }

    public int compare(File file1, File file2) {
      int result = 0;
      if (sortInfo.getColumn() == 0) {
        String name1 = file1.getName();
        if (name1.length() == 0) {
          name1 = file1.getAbsolutePath();
        }
        String name2 = file1.getName();
        if (name2.length() == 0) {
          name2 = file2.getAbsolutePath();
        }
        result = name1.compareTo(name2);
      } else if (sortInfo.getColumn() == 1) {
        result = Long.valueOf(file1.length()).compareTo(
            Long.valueOf(file2.length()));
      } else if (sortInfo.getColumn() == 2) {
        result = new Date(file1.lastModified()).compareTo(new Date(
            file2.lastModified()));
      }
      return result * (sortInfo.isAscending() ? 1 : -1);
    }
  }

  /* 
   * This method will be invoked when the RemoteTreeTable request a page of data
   */
  public SerializableResponse<FileTreeTableItem> requestTreeItems(TreeRequest request) {
    try {
      int startRow = request.getStartRow();
      int numRows = request.getNumRows();
      Set<String> invertedNodes = request.getInvertedNodes();
      boolean open = request.isOpen();

      ColumnSortList columnSortList = request.getColumnSortList();
      ColumnSortInfo columnSortInfo = columnSortList.getPrimaryColumnSortInfo();
      FileComparator comparator = null;
      File[] roots = File.listRoots();
      if (columnSortInfo != null) {
        comparator = new FileComparator(columnSortInfo);
        Arrays.sort(roots, comparator);
      }
      List<FileTreeTableItem> fileTreeItems = new ArrayList<FileTreeTableItem>();
      int row = 0;
      for (File root : roots) {
        FileTreeTableItem fileTreeItem = createFileTreeItem(root);
        fileTreeItem.setChildren(false);
        fileTreeItem.setParent(null);
        fileTreeItem.setRow(row);
        if (row >= startRow && row < startRow + numRows) {
          fileTreeItems.add(fileTreeItem);
        }
        row++;
        if (row == startRow + numRows) {
          break;
        }
        row = addChildren(fileTreeItems, fileTreeItem, root, row, startRow,
            numRows, invertedNodes, open, comparator);
      }
      return new SerializableResponse<FileTreeTableItem>(fileTreeItems, row);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  private boolean isNodeOpen(String id, Set<String> invertedNodes, boolean open) {
    if (invertedNodes.contains(id)) {
      return !open;
    } else {
      return open;
    }
  }

  private int addChildren(List<FileTreeTableItem> fileTreeItems,
      FileTreeTableItem parentTreeItem, File parentFile, int row, int startRow,
      int numRows, Set<String> invertedNodes, boolean open,
      FileComparator comparator) {
    if (parentFile.isDirectory()) {
      File[] children = parentFile.listFiles();
      if (children != null) {
        if (comparator != null) {
          Arrays.sort(children, comparator);
        }
        if (children.length > 0) {
          parentTreeItem.setChildren(true);
          if (isNodeOpen(parentTreeItem.getId(), invertedNodes, open)) {
            for (File childFile : children) {
              FileTreeTableItem childTreeItem = createFileTreeItem(childFile);
              childTreeItem.setParent(parentTreeItem);
              childTreeItem.setRow(row);
              if (row >= startRow && row <= startRow + numRows) {
                fileTreeItems.add(childTreeItem);
              }
              row++;
              if (row >= startRow + numRows) {
                return row;
              }
              row = addChildren(fileTreeItems, childTreeItem, childFile, row,
                  startRow, numRows, invertedNodes, open, comparator);
            }
          }
        }
      } else {
        parentTreeItem.setChildren(false);
      }
    }
    return row;
  }

  private FileTreeTableItem createFileTreeItem(File root) {
    String name = root.getName();
    double size = root.length();
    if (name.length() == 0) {
      name = root.getAbsolutePath();
    }
    return new FileTreeTableItem(root.getAbsolutePath(), name, size, new Date(
        root.lastModified()));
  }
}
```

You can find the working example in the src-demo folder.