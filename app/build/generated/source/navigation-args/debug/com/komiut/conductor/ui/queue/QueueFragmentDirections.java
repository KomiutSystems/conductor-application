package com.komiut.conductor.ui.queue;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.komiut.conductor.R;

public class QueueFragmentDirections {
  private QueueFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionNavigationQueueToRequestQueueFragment() {
    return new ActionOnlyNavDirections(R.id.action_navigation_queue_to_requestQueueFragment);
  }
}
