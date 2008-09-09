/*
 * Copyright 2008 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.google.gwt.gen2.selection.client;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.KeyboardListener;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.widgetideas.table.client.overrides.ElementMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Highlighting, selectable cell grid. Base class for {@link CustomListBox} and
 * (eventually) Calendar view.
 */
abstract class CellGridImpl<ValueType, CellType extends CellGridImpl.Cell>
    extends com.google.gwt.widgetideas.table.client.overrides.Grid {

  /**
   * Cell type.
   * 
   */
  protected abstract class Cell extends UIObject {
    public boolean enabled = true;
    public boolean highlighted = false;
    public boolean selected = false;
    protected ValueType value;
    private int index;

    public Cell(Element elem, ValueType value) {
      this.value = value;
      CellType current = (CellType) this;
      index = cellList.size();
      cellList.add(current);

      valueToCell.put(value, current);
      setElement(elem);
      elementToCell.put(current);
    }

    public abstract void onEnabled(boolean enabled);

    public abstract void onHighlight();

    public abstract void onHighlightLost();

    public abstract void onKeyDown(int keyCode);

    public abstract void onSelected();

    public void setEnabled(boolean enabled) {
      this.enabled = enabled;
      onEnabled(enabled);
    }

    protected CellType nextItem() {
      if (index != cellList.size() - 1) {
        return cellList.get(index + 1);
      } else {
        return null;
      }
    }

    protected CellType previousItem() {
      if (index != 0) {
        return cellList.get(index - 1);
      } else {
        return null;
      }
    }

    protected void verticalNavigation(int keyCode) {
      switch (keyCode) {
        case KeyboardListener.KEY_UP:
          setHighlighted(previousItem());
          break;
        case KeyboardListener.KEY_DOWN:
          setHighlighted(nextItem());
          break;
        case KeyboardListener.KEY_ESCAPE:
          // Figure out new event for this.
          break;
        case KeyboardListener.KEY_ENTER:
          processSelected();
          break;
      }
    }

    private void processHighlight() {
      if (!highlighted) {
        highlighted = true;
        onHighlight();
        lastHighlighted = (CellType) this;
      }
    }

    private void processHighlightLost() {
      if (highlighted) {
        highlighted = false;
        onHighlightLost();
        lastHighlighted = null;
      }
    }

    private void processSelected() {
      selected = true;
      onSelected();
    }
  }

  protected CellType lastHighlighted;

  private HashMap<ValueType, CellType> valueToCell = new HashMap<ValueType, CellType>();

  private ElementMapper<CellType> elementToCell = new ElementMapper<CellType>();

  private ArrayList<CellType> cellList = new ArrayList<CellType>();

  protected CellGridImpl() {
    setCellPadding(0);
    setCellSpacing(0);
    setBorderWidth(0);
    sinkEvents(Event.ONCLICK | Event.ONMOUSEOVER | Event.ONMOUSEOUT);
  }

  public Iterator getCells() {
    return cellList.iterator();
  }

  public int getNumCells() {
    return valueToCell.size();
  }

  @Override
  public void onBrowserEvent(Event event) {

    switch (DOM.eventGetType(event)) {
      case Event.ONCLICK: {
        Cell cell = getCell(event);
        if (isActive(cell)) {
          cell.processSelected();
        }
        break;
      }
      case Event.ONMOUSEOUT: {
        Element e = DOM.eventGetFromElement(event);
        if (e != null) {
          Cell cell = elementToCell.get((com.google.gwt.user.client.Element) e);
          if (cell != null) {
            cell.processHighlightLost();
          }
        }
        break;
      }
      case Event.ONMOUSEOVER: {
        Element e = DOM.eventGetToElement(event);
        if (e != null) {
          CellType cell = elementToCell.get((com.google.gwt.user.client.Element) e);
          if (isActive(cell)) {
            cell.processHighlight();
          }
        }
        break;
      }
      case Event.ONKEYDOWN: {
        if (isActive(lastHighlighted)) {
          lastHighlighted.onKeyDown(event.getKeyCode());
        }
      }
        break;
    }
  }

  @Override
  public void onUnload() {
    if (lastHighlighted != null) {
      lastHighlighted.processHighlightLost();
    }
  }

  protected CellType getCell(Event e) {
    // Find out which cell was actually clicked.
    Element td = getEventTargetCell(e);
    return td != null
        ? elementToCell.get((com.google.gwt.user.client.Element) td) : null;
  }

  protected CellType getCell(int i) {
    return cellList.get(i);
  }

  protected CellType getCellFromElement(Element element) {
    return elementToCell.get((com.google.gwt.user.client.Element) element);
  }

  protected CellType getCellFromValue(ValueType value) {
    return valueToCell.get(value);
  }

  private boolean isActive(Cell cell) {
    return cell != null && cell.enabled;
  }

  private void setHighlighted(CellType highlighted) {
    if (lastHighlighted != null) {
      lastHighlighted.processHighlightLost();
    }
    if (highlighted != null) {
      highlighted.processHighlight();
    }
  }
}
