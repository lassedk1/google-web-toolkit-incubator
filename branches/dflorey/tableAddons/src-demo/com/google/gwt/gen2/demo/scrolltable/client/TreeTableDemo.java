package com.google.gwt.gen2.demo.scrolltable.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.gen2.datepicker.client.DatePicker;
import com.google.gwt.gen2.table.client.DateColumnDefinition;
import com.google.gwt.gen2.table.client.DefaultTableDefinition;
import com.google.gwt.gen2.table.client.NumberColumnDefinition;
import com.google.gwt.gen2.table.client.ScrollTable;
import com.google.gwt.gen2.table.client.TextColumnDefinition;
import com.google.gwt.gen2.table.client.TreeItem;
import com.google.gwt.gen2.table.client.TreeTable;
import com.google.gwt.gen2.table.client.TreeTableController;
import com.google.gwt.gen2.table.client.AbstractScrollTable.SortPolicy;
import com.google.gwt.gen2.table.shared.AbstractTreeTableItem;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.RootPanel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TreeTableDemo implements EntryPoint {
  static class Ancestor extends AbstractTreeTableItem {
    private String firstName, lastName;
    private double iq;
    private Date dateOfBirth;

    public Ancestor(String firstName, String lastName, int iq, Date dateOfBirth) {
      super();
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

  private static String[] firstNames = {"Miguel", "Fred", "Bob", "Emily", "John", "Ray", "Bruce", "Ray", "Larry", "Sergey", "Bill", "Steve", "Daniel" };
  private static String[] lastNames = {"Mendez", "Sauer", "Vatwer", "Crutcher", "LaBanca", "Ryan", "Johnson", "Cromwell", "Page", "Bin", "Gates", "Jobs", "Florey" };
  
  public void onModuleLoad() {
    DatePicker.injectDefaultCss();

    // Create some dummy data
    List<TreeItem<Ancestor>> files = new ArrayList<TreeItem<Ancestor>>();
    for (int i = 0; i < 12; i++) {
      TreeItem<Ancestor> ancestor = createAncestor();
      addChildren(ancestor, Random.nextInt(3) + 1, 0);
      files.add(ancestor);
    }
    // Create table definition
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

    TreeTable<Ancestor> treeTable = new TreeTable<Ancestor>(tableDefinition,
        files, true);
    treeTable.setPageSize(12);
    treeTable.setSortPolicy(SortPolicy.SINGLE_CELL);
    treeTable.setWidth("100%");
    treeTable.setHeight("100%");
    treeTable.setResizePolicy(ScrollTable.ResizePolicy.FILL_WIDTH);
    TreeTableController treeTableController = new TreeTableController(treeTable);
    treeTableController.setWidth("100%");
    treeTable.setSize("100%", "100%");
    DockPanel dockPanel = new DockPanel();
    dockPanel.setWidth("100%");
    dockPanel.setHeight("300px");
    dockPanel.add(treeTable, DockPanel.CENTER);
    dockPanel.setCellHeight(treeTable, "100%");
    dockPanel.setCellWidth(treeTable, "100%");
    dockPanel.add(treeTableController, DockPanel.SOUTH);
    RootPanel.get().add(dockPanel);
  }

  private void addChildren(TreeItem<Ancestor> parent, int numberOfChildren,
      int level) {
    for (int i = 0; i < numberOfChildren; i++) {
      TreeItem<Ancestor> child = createAncestor();
      parent.addChild(child);
      if (level < 4) {
        addChildren(child, Random.nextInt(4) + 1, level + 1);
      }
    }
  }

  private TreeItem<Ancestor> createAncestor() {
    return new TreeItem(new Ancestor(firstNames[Random.nextInt(firstNames.length-1)], lastNames[Random.nextInt(lastNames.length-1)], Random.nextInt(50)+80, new Date((long)Random.nextInt(1000000)*500000L)));
  }
}