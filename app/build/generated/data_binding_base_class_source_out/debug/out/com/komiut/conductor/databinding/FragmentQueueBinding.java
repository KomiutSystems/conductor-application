// Generated by view binder compiler. Do not edit!
package com.komiut.conductor.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import com.komiut.conductor.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentQueueBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final RecyclerView recyclerView;

  @NonNull
  public final Button requestQueue;

  private FragmentQueueBinding(@NonNull ConstraintLayout rootView,
      @NonNull RecyclerView recyclerView, @NonNull Button requestQueue) {
    this.rootView = rootView;
    this.recyclerView = recyclerView;
    this.requestQueue = requestQueue;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentQueueBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentQueueBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_queue, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentQueueBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.recyclerView;
      RecyclerView recyclerView = rootView.findViewById(id);
      if (recyclerView == null) {
        break missingId;
      }

      id = R.id.requestQueue;
      Button requestQueue = rootView.findViewById(id);
      if (requestQueue == null) {
        break missingId;
      }

      return new FragmentQueueBinding((ConstraintLayout) rootView, recyclerView, requestQueue);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
