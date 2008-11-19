package com.google.gwt.gen2.table.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.util.Collection;
import java.util.Iterator;

/**
 * A response from the {@link TableModelHelper} that is serializable, and can
 * by used over RPC.
 * 
 * @param <RowType> the serializable data type of the row values
 */
public class SerializableResponse<RowType extends IsSerializable>
    extends Response<RowType> implements IsSerializable {
  /**
   * The {@link Collection} of row values.
   */
  private Collection<RowType> rowValues;

  /**
   * Default constructor used for RPC.
   */
  public SerializableResponse() {
    this(null);
  }

  /**
   * Create a new {@link SerializableResponse}.
   */
  public SerializableResponse(Collection<RowType> rowValues) {
    this.rowValues = rowValues;
  }

  @Override
  public Iterator<RowType> getRowValues() {
    return rowValues.iterator();
  }
  
  public int getRowCount() {
    return rowValues.size();
  }
}