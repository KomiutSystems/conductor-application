package com.komiut.conductor.ui.cash;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import com.komiut.conductor.R;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class CashTransactionsFragmentDirections {
  private CashTransactionsFragmentDirections() {
  }

  @NonNull
  public static ActionCashTransactionsFragmentToCashTransactionsViewerFragment actionCashTransactionsFragmentToCashTransactionsViewerFragment(
      @NonNull String DateOfTransaction) {
    return new ActionCashTransactionsFragmentToCashTransactionsViewerFragment(DateOfTransaction);
  }

  public static class ActionCashTransactionsFragmentToCashTransactionsViewerFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    private ActionCashTransactionsFragmentToCashTransactionsViewerFragment(
        @NonNull String DateOfTransaction) {
      if (DateOfTransaction == null) {
        throw new IllegalArgumentException("Argument \"DateOfTransaction\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("DateOfTransaction", DateOfTransaction);
    }

    @NonNull
    public ActionCashTransactionsFragmentToCashTransactionsViewerFragment setDateOfTransaction(
        @NonNull String DateOfTransaction) {
      if (DateOfTransaction == null) {
        throw new IllegalArgumentException("Argument \"DateOfTransaction\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("DateOfTransaction", DateOfTransaction);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
      Bundle __result = new Bundle();
      if (arguments.containsKey("DateOfTransaction")) {
        String DateOfTransaction = (String) arguments.get("DateOfTransaction");
        __result.putString("DateOfTransaction", DateOfTransaction);
      }
      return __result;
    }

    @Override
    public int getActionId() {
      return R.id.action_cashTransactionsFragment_to_cashTransactionsViewerFragment;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public String getDateOfTransaction() {
      return (String) arguments.get("DateOfTransaction");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionCashTransactionsFragmentToCashTransactionsViewerFragment that = (ActionCashTransactionsFragmentToCashTransactionsViewerFragment) object;
      if (arguments.containsKey("DateOfTransaction") != that.arguments.containsKey("DateOfTransaction")) {
        return false;
      }
      if (getDateOfTransaction() != null ? !getDateOfTransaction().equals(that.getDateOfTransaction()) : that.getDateOfTransaction() != null) {
        return false;
      }
      if (getActionId() != that.getActionId()) {
        return false;
      }
      return true;
    }

    @Override
    public int hashCode() {
      int result = 1;
      result = 31 * result + (getDateOfTransaction() != null ? getDateOfTransaction().hashCode() : 0);
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionCashTransactionsFragmentToCashTransactionsViewerFragment(actionId=" + getActionId() + "){"
          + "DateOfTransaction=" + getDateOfTransaction()
          + "}";
    }
  }
}
