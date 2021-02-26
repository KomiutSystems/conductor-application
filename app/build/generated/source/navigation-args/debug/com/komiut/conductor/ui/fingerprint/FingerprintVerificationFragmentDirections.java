package com.komiut.conductor.ui.fingerprint;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.komiut.conductor.R;

public class FingerprintVerificationFragmentDirections {
  private FingerprintVerificationFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionFingerprintVerificationFragmentToMainActivity2() {
    return new ActionOnlyNavDirections(R.id.action_fingerprintVerificationFragment_to_mainActivity2);
  }
}
