package org.cqframework.cql.elm.execution;

import org.cqframework.cql.execution.Context;
import org.cqframework.cql.runtime.Interval;
import org.cqframework.cql.runtime.Value;

/**
 * Created by Bryn on 5/25/2016.
 * Edited by Chris Schuler on 6/8/2016 - added Interval Logic
 */
public class IncludedInEvaluator extends IncludedIn {

  @Override
  public Object evaluate(Context context) {
    Object left = getOperand().get(0).evaluate(context);
    Object right = getOperand().get(1).evaluate(context);

    if (left != null || right != null) {
      if (left instanceof Interval) {
        Object leftStart = ((Interval)left).getStart();
        Object leftEnd = ((Interval)left).getEnd();
        Object rightStart = ((Interval)right).getStart();
        Object rightEnd = ((Interval)right).getEnd();

        if (leftStart == null || leftEnd == null || rightStart == null || rightEnd == null) { return null; }

        return (Value.compareTo(rightStart, leftStart, "<=") && Value.compareTo(rightEnd, leftEnd, ">="));
      }

      else {
        for (Object element : (Iterable)left) {
            if (!InEvaluator.in(element, (Iterable)right)) {
                return false;
            }
        }
        return true;
      }
    }
   return null;
  }
}
