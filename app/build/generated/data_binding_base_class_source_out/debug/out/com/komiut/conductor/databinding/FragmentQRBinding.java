// Generated by view binder compiler. Do not edit!
package com.komiut.conductor.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import com.komiut.conductor.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentQRBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button pay;

  @NonNull
  public final Button topUp;

  private FragmentQRBinding(@NonNull ConstraintLayout rootView, @NonNull Button pay,
      @NonNull Button topUp) {
    this.rootView = rootView;
    this.pay = pay;
    this.topUp = topUp;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentQRBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentQRBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_q_r, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentQRBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.pay;
      Button pay = rootView.findViewById(id);
      if (pay == null) {
        break missingId;
      }

      id = R.id.topUp;
      Button topUp = rootView.findViewById(id);
      if (topUp == null) {
        break missingId;
      }

      return new FragmentQRBinding((ConstraintLayout) rootView, pay, topUp);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}