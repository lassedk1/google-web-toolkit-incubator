package com.google.gwt.gen2.table.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.libideas.resources.client.ImageResource;
import com.google.gwt.libideas.resources.client.ImageResource.ImageOptions;
import com.google.gwt.libideas.resources.client.ImageResource.RepeatStyle;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;

public class TreeTableController extends PagingOptions {
  public static interface Css extends PagingOptions.Css {
    /**
     * Widget style name.
     * 
     * @return the widget's style name
     */
    @ClassName("gwt-TreeTableController")
    String defaultStyleName();
  }

  public interface TreeTableControllerResources extends PagingOptionsResources {
    TreeTableControllerStyle getStyle();

    TreeTableControllerMessages getMessages();
  }

  public interface TreeTableControllerMessages extends PagingOptionsMessages {
    @DefaultMessage("Open all tree nodes")
    String openTree();

    @DefaultMessage("Close all tree nodes")
    String closeTree();
  }

  /**
   * Resources used.
   */
  public interface TreeTableControllerStyle extends PagingOptionsStyle {
    @Resource("com/google/gwt/gen2/widgetbase/public/TreeTableController.css")
    Css css();

    @Resource("closeTree.png")
    ImageResource closeTree();

    @Resource("openTree.png")
    ImageResource openTree();

    @Resource("headerBackground.png")
    @ImageOptions(repeatStyle = RepeatStyle.Horizontal)
    ImageResource pagingBackground();
  }

  protected static class DefaultTreeTableControllerResources implements
      TreeTableControllerResources {
    private TreeTableControllerStyle style;
    private TreeTableControllerMessages messages;

    public TreeTableControllerStyle getStyle() {
      if (style == null) {
        style = ((TreeTableControllerStyle) GWT.create(TreeTableControllerStyle.class));
      }
      return style;
    }

    public TreeTableControllerMessages getMessages() {
      if (messages == null) {
        messages = ((TreeTableControllerMessages) GWT.create(TreeTableControllerMessages.class));
      }
      return messages;
    }
  }

  private TreeTable<?> treeTable;
  private Image toggleTreeImage;
  private PushButton toggleTreeButton;
  private TreeTableControllerResources resources;


  public TreeTableController(TreeTable<?> treeTable) {
    this(treeTable, new DefaultTreeTableControllerResources());
  }

  public TreeTableController(TreeTable<?> treeTable,
      TreeTableControllerResources resources) {
    super(treeTable, resources);
    this.treeTable = treeTable;
    this.resources = resources;
    toggleTreeImage = createImage(resources.getStyle().closeTree());
    toggleTreeButton = new PushButton(toggleTreeImage);
    getButtonPanel().insert(toggleTreeButton, 0);
    getButtonPanel().getWidget(3).setVisible(false);
    getButtonPanel().getWidget(3).setVisible(false);
    ClickListener listener = new ClickListener() {
      public void onClick(Widget sender) {
        TreeTableController.this.treeTable.setTreeOpen(!TreeTableController.this.treeTable.isTreeOpen());
      }
    };
    toggleTreeButton.addClickListener(listener);

    updateButtonState();
  }

  @Override
  protected void updateButtonState() {
    super.updateButtonState();
    if (treeTable.isTreeOpen()) {
      applyImage(toggleTreeImage, resources.getStyle().closeTree());
      toggleTreeButton.setTitle(resources.getMessages().closeTree());
    } else {
      applyImage(toggleTreeImage, resources.getStyle().openTree());
      toggleTreeButton.setTitle(resources.getMessages().openTree());
    }

  }
}