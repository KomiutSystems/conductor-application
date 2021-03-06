// Generated by view binder compiler. Do not edit!
package com.komiut.conductor.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import com.airbnb.lottie.LottieAnimationView;
import com.komiut.conductor.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentFingerprintVerificationBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button button4;

  @NonNull
  public final ConstraintLayout constraintLayout4;

  @NonNull
  public final LottieAnimationView lottieAnimationView;

  @NonNull
  public final TextView textView12;

  @NonNull
  public final TextView textView13;

  private FragmentFingerprintVerificationBinding(@NonNull ConstraintLayout rootView,
      @NonNull Button button4, @NonNull ConstraintLayout constraintLayout4,
      @NonNull LottieAnimationView lottieAnimationView, @NonNull TextView textView12,
      @NonNull TextView textView13) {
    this.rootView = rootView;
    this.button4 = button4;
    this.constraintLayout4 = constraintLayout4;
    this.lottieAnimationView = lottieAnimationView;
    this.textView12 = textView12;
    this.textView13 = textView13;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentFingerprintVerificationBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentFingerprintVerificationBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_fingerprint_verification, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentFingerprintVerificationBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.button4;
      Button button4 = rootView.findViewById(id);
      if (button4 == null) {
        break missingId;
      }

      id = R.id.constraintLayout4;
      ConstraintLayout constraintLayout4 = rootView.findViewById(id);
      if (constraintLayout4 == null) {
        break missingId;
      }

      id = R.id.lottieAnimationView;
      LottieAnimationView lottieAnimationView = rootView.findViewById(id);
      if (lottieAnimationView == null) {
        break missingId;
      }

      id = R.id.textView12;
      TextView textView12 = rootView.findViewById(id);
      if (textView12 == null) {
        break missingId;
      }

      id = R.id.textView13;
      TextView textView13 = rootView.findViewById(id);
      if (textView13 == null) {
        break missingId;
      }

      return new FragmentFingerprintVerificationBinding((ConstraintLayout) rootView, button4,
          constraintLayout4, lottieAnimationView, textView12, textView13);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
