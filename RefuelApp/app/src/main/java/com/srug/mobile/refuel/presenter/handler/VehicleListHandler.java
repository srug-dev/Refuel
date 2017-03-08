package com.srug.mobile.refuel.presenter.handler;

import com.srug.mobile.refuel.model.mediator.data.Vehicle;
import com.srug.mobile.refuel.presenter.VehicleViewModel;

import java.util.ArrayList;
import java.util.List;

public class VehicleListHandler {

    private VehicleViewModel mSelectedVehicle;
    private List<VehicleViewModel> mVehicleList = new ArrayList<>();

    public VehicleListHandler() {
    }

    public List<VehicleViewModel> getVehicleList() {
        return mVehicleList;
    }

    public void setSelectedVehicle(Long vehicleId, Long userId) {
        VehicleViewModel viewModel = getVehicleViewModel(vehicleId, userId);
        resetCurrentSelection();
        if (viewModel != null) {
            viewModel.setIsSelected(true);
        }
    }

    public VehicleViewModel getSelectedVehicle() {
        for (VehicleViewModel vehicle :
                mVehicleList) {
            if (vehicle.isSelected()) {
                return vehicle;
            }
        }
        return null;
    }

    public void initializeList(List<Vehicle> vehicleList) {
        mVehicleList = new ArrayList<>();
        for (Vehicle vehicle : vehicleList) {
            VehicleViewModel viewModel = generateViewModel(vehicle);
            mVehicleList.add(viewModel);
        }
    }

    private VehicleViewModel getVehicleViewModel(Long vehicleId, Long userId) {
        for (VehicleViewModel viewModel : mVehicleList) {
            if (viewModel.getId().equals(vehicleId) &&
                    viewModel.getUserId().equals(userId)) {
                return viewModel;
            }
        }
        return null;
    }

    private void resetCurrentSelection() {
        for (VehicleViewModel viewModel : mVehicleList) {
            if (viewModel.isSelected()) {
                viewModel.setIsSelected(false);
            }
        }
    }

    private VehicleViewModel generateViewModel(Vehicle vehicle) {
        VehicleViewModel viewModel = new VehicleViewModel(vehicle.getId(), vehicle.getUserId());
        viewModel.setBrand(vehicle.getBrand());
        viewModel.setModel(vehicle.getModel());
        viewModel.setPlate(vehicle.getPlate());
        return viewModel;
    }
}
