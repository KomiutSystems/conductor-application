package com.komiut.conductor.ui.card;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import com.komiut.conductor.R;

public class CardOperationsFragmentDirections {
  private CardOperationsFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionCardOperationsFragmentToNavigationCard() {
    return new ActionOnlyNavDirections(R.id.action_cardOperationsFragment_to_navigation_card);
  }

  @NonNull
  public static NavDirections actionCardOperationsFragmentToNFCTopFragment() {
    return new ActionOnlyNavDirections(R.id.action_cardOperationsFragment_to_NFCTopFragment);
  }
}
