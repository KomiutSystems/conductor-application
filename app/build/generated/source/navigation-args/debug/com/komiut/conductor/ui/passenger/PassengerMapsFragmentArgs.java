package com.komiut.conductor.ui.passenger;

import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.navigation.NavArgs;
import com.komiut.conductor.model.PassengerMapCoordinates;
import java.io.Serializable;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class PassengerMapsFragmentArgs implements NavArgs {
  private final HashMap arguments = new HashMap();

  private PassengerMapsFragmentArgs() {
  }

  private PassengerMapsFragmentArgs(HashMap argumentsMap) {
    this.arguments.putAll(argumentsMap);
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static PassengerMapsFragmentArgs fromBundle(@NonNull Bundle bundle) {
    PassengerMapsFragmentArgs __result = new PassengerMapsFragmentArgs();
    bundle.setClassLoader(PassengerMapsFragmentArgs.class.getClassLoader());
    if (bundle.containsKey("PassangerCoordinates")) {
      PassengerMapCoordinates PassangerCoordinates;
      if (Parcelable.class.isAssignableFrom(PassengerMapCoordinates.class) || Serializable.class.isAssignableFrom(PassengerMapCoordinates.class)) {
        PassangerCoordinates = (PassengerMapCoordinates) bundle.get("PassangerCoordinates");
      } else {
        throw new UnsupportedOperationException(PassengerMapCoordinates.class.getName() + " must implement Parcelable or Serializable or must be an Enum.");
      }
      if (PassangerCoordinates == null) {
        throw new IllegalArgumentException("Argument \"PassangerCoordinates\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("PassangerCoordinates", PassangerCoordinates);
    } else {
      throw new IllegalArgumentException("Required argument \"PassangerCoordinates\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public PassengerMapCoordinates getPassangerCoordinates() {
    return (PassengerMapCoordinates) arguments.get("PassangerCoordinates");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public Bundle toBundle() {
    Bundle __result = new Bundle();
    if (arguments.containsKey("PassangerCoordinates")) {
      PassengerMapCoordinates PassangerCoordinates = (PassengerMapCoordinates) arguments.get("PassangerCoordinates");
      if (Parcelable.class.isAssignableFrom(PassengerMapCoordinates.class) || PassangerCoordinates == null) {
        __result.putParcelable("PassangerCoordinates", Parcelable.class.cast(PassangerCoordinates));
      } else if (Serializable.class.isAssignableFrom(PassengerMapCoordinates.class)) {
        __result.putSerializable("PassangerCoordinates", Serializable.class.cast(PassangerCoordinates));
      } else {
        throw new UnsupportedOperationException(PassengerMapCoordinates.class.getName() + " must implement Parcelable or Serializable or must be an Enum.");
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
    PassengerMapsFragmentArgs that = (PassengerMapsFragmentArgs) object;
    if (arguments.containsKey("PassangerCoordinates") != that.arguments.containsKey("PassangerCoordinates")) {
      return false;
    }
    if (getPassangerCoordinates() != null ? !getPassangerCoordinates().equals(that.getPassangerCoordinates()) : that.getPassangerCoordinates() != null) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + (getPassangerCoordinates() != null ? getPassangerCoordinates().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "PassengerMapsFragmentArgs{"
        + "PassangerCoordinates=" + getPassangerCoordinates()
        + "}";
  }

  public static class Builder {
    private final HashMap arguments = new HashMap();

    public Builder(PassengerMapsFragmentArgs original) {
      this.arguments.putAll(original.arguments);
    }

    public Builder(@NonNull PassengerMapCoordinates PassangerCoordinates) {
      if (PassangerCoordinates == null) {
        throw new IllegalArgumentException("Argument \"PassangerCoordinates\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("PassangerCoordinates", PassangerCoordinates);
    }

    @NonNull
    public PassengerMapsFragmentArgs build() {
      PassengerMapsFragmentArgs result = new PassengerMapsFragmentArgs(arguments);
      return result;
    }

    @NonNull
    public Builder setPassangerCoordinates(@NonNull PassengerMapCoordinates PassangerCoordinates) {
      if (PassangerCoordinates == null) {
        throw new IllegalArgumentException("Argument \"PassangerCoordinates\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("PassangerCoordinates", PassangerCoordinates);
      return this;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public PassengerMapCoordinates getPassangerCoordinates() {
      return (PassengerMapCoordinates) arguments.get("PassangerCoordinates");
    }
  }
}
