package com.srug.mobile.refuel;

import com.srug.mobile.refuel.common.ContextView;
import com.srug.mobile.refuel.common.PresenterOps;
import com.srug.mobile.refuel.presenter.RefuelingViewModel;
import com.srug.mobile.refuel.view.ui.RefuelingListAdapter;
import com.srug.mobile.refuel.view.ui.UserListAdapter;
import com.srug.mobile.refuel.view.ui.VehicleListAdapter;

import java.util.Date;

public interface MVP {

    interface MainRequiredViewOps extends ContextView {

        void setAdapter(UserListAdapter userListAdapter);

        void setAdapter(VehicleListAdapter vehicleListAdapter);

        void setAdapter(RefuelingListAdapter refuelingListAdapter);

        void showToast(String message);

        void openNewRefueling(long vehicleId);

        void stopRefreshing();
    }

    interface MainProvidedPresenterOps extends PresenterOps<MainRequiredViewOps> {

        void newRefueling();

        void reloadArchive();
    }

    interface RefuelingRequiredViewOps extends ContextView {

        void setRefueling(RefuelingViewModel refueling);

        void refreshRefueling();

        void showToast(String message);

        void closeActivity(int result);
    }

    interface RefuelingProvidedPresenterOps extends PresenterOps<RefuelingRequiredViewOps> {

        void executeCalculate();

        void executeSubmit();

        void updateRefuelingDate(Date date);

        void updateRefuelingDistance(Double distance);

        void updateRefuelingPrice(Double price);

        void updateRefuelingAmount(Double amount);
    }
}
