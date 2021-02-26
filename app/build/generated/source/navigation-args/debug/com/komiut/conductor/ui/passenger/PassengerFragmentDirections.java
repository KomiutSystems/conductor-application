package com.komiut.conductor.ui.passenger;

import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import com.komiut.conductor.R;
import com.komiut.conductor.model.PassengerMapCoordinates;
import java.io.Serializable;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class PassengerFragmentDirections {
  private PassengerFragmentDirections() {
  }

  @NonNull
  public static ActionNavigationPassengerToPassengerMapsFragment actionNavigationPassengerToPassengerMapsFragment(
      @NonNull PassengerMapCoordinates PassangerCoordinates) {
    return new ActionNavigationPassengerToPassengerMapsFragment(PassangerCoordinates);
  }

  public static class ActionNavigationPassengerToPassengerMapsFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    private ActionNavigationPassengerToPassengerMapsFragment(
        @NonNull PassengerMapCoordinates PassangerCoordinates) {
      if (PassangerCoordinates == null) {
        throw new IllegalArgumentException("Argument \"PassangerCoordinates\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("PassangerCoordinates", PassangerCoordinates);
    }

    @NonNull
    public ActionNavigationPassengerToPassengerMapsFragment setPassangerCoordinates(
        @NonNull PassengerMapCoordinates PassangerCoordinates) {
      if (PassangerCoordinates == null) {
        throw new IllegalArgumentException("Argument \"PassangerCoordinates\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("PassangerCoordinates", PassangerCoordinates);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
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
    public int getActionId() {
      return R.id.action_navigation_passenger_to_passengerMapsFragment;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public PassengerMapCoordinates getPassangerCoordinates() {
      return (PassengerMapCoordinates) arguments.get("PassangerCoordinates");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionNavigationPassengerToPassengerMapsFragment that = (ActionNavigationPassengerToPassengerMapsFragment) object;
      if (arguments.containsKey("PassangerCoordinates") != that.arguments.containsKey("PassangerCoordinates")) {
        return false;
      }
      if (getPassangerCoordinates() != null ? !getPassangerCoordinates().equals(that.getPassangerCoordinates()) : that.getPassangerCoordinates() != null) {
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
      result = 31 * result + (getPassangerCoordinates() != null ? getPassangerCoordinates().hashCode() : 0);
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionNavigationPassengerToPassengerMapsFragment(actionId=" + getActionId() + "){"
          + "PassangerCoordinates=" + getPassangerCoordinates()
          + "}";
    }
  }
}
