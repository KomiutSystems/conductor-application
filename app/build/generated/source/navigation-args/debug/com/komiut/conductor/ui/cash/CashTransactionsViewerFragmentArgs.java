package com.komiut.conductor.ui.cash;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.navigation.NavArgs;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class CashTransactionsViewerFragmentArgs implements NavArgs {
  private final HashMap arguments = new HashMap();

  private CashTransactionsViewerFragmentArgs() {
  }

  private CashTransactionsViewerFragmentArgs(HashMap argumentsMap) {
    this.arguments.putAll(argumentsMap);
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static CashTransactionsViewerFragmentArgs fromBundle(@NonNull Bundle bundle) {
    CashTransactionsViewerFragmentArgs __result = new CashTransactionsViewerFragmentArgs();
    bundle.setClassLoader(CashTransactionsViewerFragmentArgs.class.getClassLoader());
    if (bundle.containsKey("DateOfTransaction")) {
      String DateOfTransaction;
      DateOfTransaction = bundle.getString("DateOfTransaction");
      if (DateOfTransaction == null) {
        throw new IllegalArgumentException("Argument \"DateOfTransaction\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("DateOfTransaction", DateOfTransaction);
    } else {
      throw new IllegalArgumentException("Required argument \"DateOfTransaction\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public String getDateOfTransaction() {
    return (String) arguments.get("DateOfTransaction");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public Bundle toBundle() {
    Bundle __result = new Bundle();
    if (arguments.containsKey("DateOfTransaction")) {
      String DateOfTransaction = (String) arguments.get("DateOfTransaction");
      __result.putString("DateOfTransaction", DateOfTransaction);
    }
    return __result;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
        return true;
    }
    if (object == null || getClass() != object.getClass()) {
        return false;
    }
    CashTransactionsViewerFragmentArgs that = (CashTransactionsViewerFragmentArgs) object;
    if (arguments.containsKey("DateOfTransaction") != that.arguments.containsKey("DateOfTransaction")) {
      return false;
    }
    if (getDateOfTransaction() != null ? !getDateOfTransaction().equals(that.getDateOfTransaction()) : that.getDateOfTransaction() != null) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + (getDateOfTransaction() != null ? getDateOfTransaction().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "CashTransactionsViewerFragmentArgs{"
        + "DateOfTransaction=" + getDateOfTransaction()
        + "}";
  }

  public static class Builder {
    private final HashMap arguments = new HashMap();

    public Builder(CashTransactionsViewerFragmentArgs original) {
      this.arguments.putAll(original.arguments);
    }

    public Builder(@NonNull String DateOfTransaction) {
      if (DateOfTransaction == null) {
        throw new IllegalArgumentException("Argument \"DateOfTransaction\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("DateOfTransaction", DateOfTransaction);
    }

    @NonNull
    public CashTransactionsViewerFragmentArgs build() {
      CashTransactionsViewerFragmentArgs result = new CashTransactionsViewerFragmentArgs(arguments);
      return result;
    }

    @NonNull
    public Builder setDateOfTransaction(@NonNull String DateOfTransaction) {
      if (DateOfTransaction == null) {
        throw new IllegalArgumentException("Argument \"DateOfTransaction\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("DateOfTransaction", DateOfTransaction);
      return this;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public String getDateOfTransaction() {
      return (String) arguments.get("DateOfTransaction");
    }
  }
}
