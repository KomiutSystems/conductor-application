package com.komiut.conductor.ui.fingerprint;

import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.navigation.NavArgs;
import com.komiut.conductor.model.OfflineUser;
import java.io.Serializable;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class EnrollFingerprintFragmentArgs implements NavArgs {
  private final HashMap arguments = new HashMap();

  private EnrollFingerprintFragmentArgs() {
  }

  private EnrollFingerprintFragmentArgs(HashMap argumentsMap) {
    this.arguments.putAll(argumentsMap);
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static EnrollFingerprintFragmentArgs fromBundle(@NonNull Bundle bundle) {
    EnrollFingerprintFragmentArgs __result = new EnrollFingerprintFragmentArgs();
    bundle.setClassLoader(EnrollFingerprintFragmentArgs.class.getClassLoader());
    if (bundle.containsKey("OfflineUser")) {
      OfflineUser OfflineUser;
      if (Parcelable.class.isAssignableFrom(OfflineUser.class) || Serializable.class.isAssignableFrom(OfflineUser.class)) {
        OfflineUser = (OfflineUser) bundle.get("OfflineUser");
      } else {
        throw new UnsupportedOperationException(OfflineUser.class.getName() + " must implement Parcelable or Serializable or must be an Enum.");
      }
      if (OfflineUser == null) {
        throw new IllegalArgumentException("Argument \"OfflineUser\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("OfflineUser", OfflineUser);
    } else {
      throw new IllegalArgumentException("Required argument \"OfflineUser\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public OfflineUser getOfflineUser() {
    return (OfflineUser) arguments.get("OfflineUser");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public Bundle toBundle() {
    Bundle __result = new Bundle();
    if (arguments.containsKey("OfflineUser")) {
      OfflineUser OfflineUser = (OfflineUser) arguments.get("OfflineUser");
      if (Parcelable.class.isAssignableFrom(OfflineUser.class) || OfflineUser == null) {
        __result.putParcelable("OfflineUser", Parcelable.class.cast(OfflineUser));
      } else if (Serializable.class.isAssignableFrom(OfflineUser.class)) {
        __result.putSerializable("OfflineUser", Serializable.class.cast(OfflineUser));
      } else {
        throw new UnsupportedOperationException(OfflineUser.class.getName() + " must implement Parcelable or Serializable or must be an Enum.");
      }
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
    EnrollFingerprintFragmentArgs that = (EnrollFingerprintFragmentArgs) object;
    if (arguments.containsKey("OfflineUser") != that.arguments.containsKey("OfflineUser")) {
      return false;
    }
    if (getOfflineUser() != null ? !getOfflineUser().equals(that.getOfflineUser()) : that.getOfflineUser() != null) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + (getOfflineUser() != null ? getOfflineUser().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "EnrollFingerprintFragmentArgs{"
        + "OfflineUser=" + getOfflineUser()
        + "}";
  }

  public static class Builder {
    private final HashMap arguments = new HashMap();

    public Builder(EnrollFingerprintFragmentArgs original) {
      this.arguments.putAll(original.arguments);
    }

    public Builder(@NonNull OfflineUser OfflineUser) {
      if (OfflineUser == null) {
        throw new IllegalArgumentException("Argument \"OfflineUser\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("OfflineUser", OfflineUser);
    }

    @NonNull
    public EnrollFingerprintFragmentArgs build() {
      EnrollFingerprintFragmentArgs result = new EnrollFingerprintFragmentArgs(arguments);
      return result;
    }

    @NonNull
    public Builder setOfflineUser(@NonNull OfflineUser OfflineUser) {
      if (OfflineUser == null) {
        throw new IllegalArgumentException("Argument \"OfflineUser\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("OfflineUser", OfflineUser);
      return this;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public OfflineUser getOfflineUser() {
      return (OfflineUser) arguments.get("OfflineUser");
    }
  }
}
