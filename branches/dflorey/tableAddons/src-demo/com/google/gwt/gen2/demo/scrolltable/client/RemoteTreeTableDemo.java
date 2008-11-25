package com.google.gwt.gen2.demo.scrolltable.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.gen2.datepicker.client.DatePicker;
import com.google.gwt.gen2.demo.scrolltable.shared.FileTreeTableItem;
import com.google.gwt.gen2.table.client.DateColumnDefinition;
import com.google.gwt.gen2.table.client.DefaultTableDefinition;
import com.google.gwt.gen2.table.client.NumberColumnDefinition;
import com.google.gwt.gen2.table.client.RemoteTreeTable;
import com.google.gwt.gen2.table.client.ScrollTable;
import com.google.gwt.gen2.table.client.TextColumnDefinition;
import com.google.gwt.gen2.table.client.TreeTable;
import com.google.gwt.gen2.table.client.TreeTableController;
import com.google.gwt.gen2.table.client.AbstractScrollTable.SortPolicy;
import com.google.gwt.gen2.table.shared.SerializableResponse;
import com.google.gwt.gen2.table.shared.TreeRequest;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.RootPanel;

import java.util.Date;

public class RemoteTreeTableDemo implements EntryPoint {
  public void onModuleLoad() {
    DatePicker.injectDefaultCss();

    // Create table definition
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

    // Create remote tree table
    TreeTable<FileTreeTableItem> remoteTreeTable = new RemoteTreeTable<FileTreeTableItem>(
        tableDefinition, true) {
      RemoteTableServiceAsync remoteTableService = GWT.create(RemoteTableService.class);

      @Override
      protected void requestTreeItems(TreeRequest request,
          AsyncCallback<SerializableResponse<FileTreeTableItem>> callback) {
        remoteTableService.requestTreeItems(request, callback);
      }
    };
    remoteTreeTable.setPageSize(15);
    remoteTreeTable.setSortPolicy(SortPolicy.SINGLE_CELL);
    remoteTreeTable.setResizePolicy(ScrollTable.ResizePolicy.FILL_WIDTH);
    TreeTableController treeTableController = new TreeTableController(
        remoteTreeTable);
    treeTableController.setWidth("100%");
    remoteTreeTable.setSize("100%", "300px");
    DockPanel dockPanel = new DockPanel();
    dockPanel.setWidth("100%");
    dockPanel.setHeight("300px");
    dockPanel.add(remoteTreeTable, DockPanel.CENTER);
    dockPanel.setCellWidth(remoteTreeTable, "100%");
    dockPanel.setCellHeight(remoteTreeTable, "300px");
    dockPanel.add(treeTableController, DockPanel.SOUTH);
    RootPanel.get().add(dockPanel);
  }
}