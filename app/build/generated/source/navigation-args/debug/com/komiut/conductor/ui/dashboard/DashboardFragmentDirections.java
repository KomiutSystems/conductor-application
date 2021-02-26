package com.komiut.conductor.ui.dashboard;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.komiut.conductor.R;

public class DashboardFragmentDirections {
  private DashboardFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionNavigationDashboardToEnrollFingerprintFragment2() {
    return new ActionOnlyNavDirections(R.id.action_navigation_dashboard_to_enrollFingerprintFragment2);
  }
}
