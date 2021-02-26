package com.komiut.conductor.ui.login;

import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.komiut.conductor.R;
import com.komiut.conductor.model.OfflineUser;
import java.io.Serializable;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class LoginFragmentDirections {
  private LoginFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionLoginFragment2ToMainActivity2() {
    return new ActionOnlyNavDirections(R.id.action_loginFragment2_to_mainActivity2);
  }

  @NonNull
  public static ActionLoginFragment2ToEnrollFingerprintFragment actionLoginFragment2ToEnrollFingerprintFragment(
      @NonNull OfflineUser OfflineUser) {
    return new ActionLoginFragment2ToEnrollFingerprintFragment(OfflineUser);
  }

  @NonNull
  public static NavDirections actionLoginFragment2ToFingerprintVerificationFragment() {
    return new ActionOnlyNavDirections(R.id.action_loginFragment2_to_fingerprintVerificationFragment);
  }

  public static class ActionLoginFragment2ToEnrollFingerprintFragment implements NavDirections {
    private final HashMap arguments = new HashMap();

    private ActionLoginFragment2ToEnrollFingerprintFragment(@NonNull OfflineUser OfflineUser) {
      if (OfflineUser == null) {
        throw new IllegalArgumentException("Argument \"OfflineUser\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("OfflineUser", OfflineUser);
    }

    @NonNull
    public ActionLoginFragment2ToEnrollFingerprintFragment setOfflineUser(
        @NonNull OfflineUser OfflineUser) {
      if (OfflineUser == null) {
        throw new IllegalArgumentException("Argument \"OfflineUser\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("OfflineUser", OfflineUser);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
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
    public int getActionId() {
      return R.id.action_loginFragment2_to_enrollFingerprintFragment;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public OfflineUser getOfflineUser() {
      return (OfflineUser) arguments.get("OfflineUser");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionLoginFragment2ToEnrollFingerprintFragment that = (ActionLoginFragment2ToEnrollFingerprintFragment) object;
      if (arguments.containsKey("OfflineUser") != that.arguments.containsKey("OfflineUser")) {
        return false;
      }
      if (getOfflineUser() != null ? !getOfflineUser().equals(that.getOfflineUser()) : that.getOfflineUser() != null) {
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
      result = 31 * result + (getOfflineUser() != null ? getOfflineUser().hashCode() : 0);
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionLoginFragment2ToEnrollFingerprintFragment(actionId=" + getActionId() + "){"
          + "OfflineUser=" + getOfflineUser()
          + "}";
    }
  }
}
