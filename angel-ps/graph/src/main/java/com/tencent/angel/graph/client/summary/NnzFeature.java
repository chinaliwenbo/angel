package com.tencent.angel.graph.client.summary;

import com.tencent.angel.graph.data.Node;
import com.tencent.angel.ml.matrix.psf.aggr.enhance.UnaryAggrFunc;
import com.tencent.angel.ps.storage.vector.ServerLongAnyRow;
import com.tencent.angel.ps.storage.vector.ServerRow;
import com.tencent.angel.ps.storage.vector.element.IElement;
import com.tencent.angel.ps.storage.vector.storage.LongElementStorage;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;

public class NnzFeature extends UnaryAggrFunc {

  public NnzFeature(int matrixId, int rowId) {
    super(matrixId, rowId);
  }

  public NnzFeature() {
    this(-1, -1);
  }

  @Override
  public double mergeInit() {
    return 0;
  }

  @Override
  public double mergeOp(double a, double b) {
    return a + b;
  }

  @Override
  public double processRow(ServerRow row) {
    LongElementStorage storage = ((ServerLongAnyRow) row).getStorage();
    ObjectIterator<Long2ObjectMap.Entry<IElement>> it = storage.iterator();
    long size = 0;
    while (it.hasNext()) {
      Node node = (Node) (it.next().getValue());
      if (node.getFeats() != null)
        size ++;
    }
    return size;
  }
}