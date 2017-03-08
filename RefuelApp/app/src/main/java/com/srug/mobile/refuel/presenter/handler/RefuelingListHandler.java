package com.srug.mobile.refuel.presenter.handler;

import com.srug.mobile.refuel.model.mediator.data.Refueling;
import com.srug.mobile.refuel.presenter.RefuelingViewModel;

import java.util.ArrayList;
import java.util.List;

public class RefuelingListHandler {

    private RefuelingViewModel mSelectedRefueling;
    private List<RefuelingViewModel> mRefuelingList = new ArrayList<>();

    public RefuelingListHandler() {
    }

    public List<RefuelingViewModel> getRefuelingList() {
        return mRefuelingList;
    }

    public void setSelectedRefueling(Long refuelingId, Long vehicleId) {
        RefuelingViewModel viewModel = getRefuelingViewModel(refuelingId, vehicleId);
        resetCurrentSelection();
        if (viewModel != null) {
            viewModel.setIsSelected(true);
        }
    }

    public void initializeList(List<Refueling> refuelingList) {
        mRefuelingList = new ArrayList<>();
        for (Refueling refueling : refuelingList) {
            RefuelingViewModel viewModel = generateViewModel(refueling);
            mRefuelingList.add(viewModel);
        }
    }

    private RefuelingViewModel getRefuelingViewModel(Long refuelingId, Long vehicleId) {
        for (RefuelingViewModel refuelingViewModel : mRefuelingList) {
            if (refuelingViewModel.getId() == refuelingId &&
                    refuelingViewModel.getVehicleId() == vehicleId) {
                return refuelingViewModel;
            }
        }
        return null;
    }

    private void resetCurrentSelection() {
        for (RefuelingViewModel refuelingViewModel : mRefuelingList) {
            if (refuelingViewModel.isSelected()) {
                refuelingViewModel.setIsSelected(false);
            }
        }
    }

    private RefuelingViewModel generateViewModel(Refueling refueling) {
        RefuelingViewModel viewModel = new RefuelingViewModel(
                refueling.getId(),
                refueling.getVehicleId());
        viewModel.setDate(refueling.getDate());
        viewModel.setDistance(refueling.getDistance());
        viewModel.setPrice(refueling.getPrice());
        viewModel.setAmount(refueling.getAmount());
        return viewModel;
    }
}
