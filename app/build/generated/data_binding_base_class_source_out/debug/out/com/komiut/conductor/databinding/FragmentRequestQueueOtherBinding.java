// Generated by view binder compiler. Do not edit!
package com.komiut.conductor.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import com.google.android.material.textfield.TextInputLayout;
import com.komiut.conductor.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentRequestQueueOtherBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button button6;

  @NonNull
  public final Spinner spinner;

  @NonNull
  public final TextInputLayout textInputLayout2;

  @NonNull
  public final TextInputLayout textInputLayout3;

  @NonNull
  public final TextView textView19;

  private FragmentRequestQueueOtherBinding(@NonNull ConstraintLayout rootView,
      @NonNull Button button6, @NonNull Spinner spinner, @NonNull TextInputLayout textInputLayout2,
      @NonNull TextInputLayout textInputLayout3, @NonNull TextView textView19) {
    this.rootView = rootView;
    this.button6 = button6;
    this.spinner = spinner;
    this.textInputLayout2 = textInputLayout2;
    this.textInputLayout3 = textInputLayout3;
    this.textView19 = textView19;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentRequestQueueOtherBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentRequestQueueOtherBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_request_queue_other, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentRequestQueueOtherBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.button6;
      Button button6 = rootView.findViewById(id);
      if (button6 == null) {
        break missingId;
      }

      id = R.id.spinner;
      Spinner spinner = rootView.findViewById(id);
      if (spinner == null) {
        break missingId;
      }

      id = R.id.textInputLayout2;
      TextInputLayout textInputLayout2 = rootView.findViewById(id);
      if (textInputLayout2 == null) {
        break missingId;
      }

      id = R.id.textInputLayout3;
      TextInputLayout textInputLayout3 = rootView.findViewById(id);
      if (textInputLayout3 == null) {
        break missingId;
      }

      id = R.id.textView19;
      TextView textView19 = rootView.findViewById(id);
      if (textView19 == null) {
        break missingId;
      }

      return new FragmentRequestQueueOtherBinding((ConstraintLayout) rootView, button6, spinner,
          textInputLayout2, textInputLayout3, textView19);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
