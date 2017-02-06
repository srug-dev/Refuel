package com.srug.mobile.refuel;

import com.srug.mobile.refuel.common.ContextView;
import com.srug.mobile.refuel.common.PresenterOps;

public interface MVP {

    interface MainRequiredViewOps extends ContextView {

    }

    interface MainProvidedPresenterOps extends PresenterOps<MainRequiredViewOps> {

    }
}
