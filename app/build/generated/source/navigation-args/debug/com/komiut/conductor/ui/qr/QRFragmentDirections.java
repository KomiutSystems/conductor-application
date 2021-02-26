package com.komiut.conductor.ui.qr;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.komiut.conductor.R;

public class QRFragmentDirections {
  private QRFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionQRFragmentToQRPayFragment() {
    return new ActionOnlyNavDirections(R.id.action_QRFragment_to_QRPayFragment);
  }

  @NonNull
  public static NavDirections actionQRFragmentToQRTopUPFragment() {
    return new ActionOnlyNavDirections(R.id.action_QRFragment_to_QRTopUPFragment);
  }
}
