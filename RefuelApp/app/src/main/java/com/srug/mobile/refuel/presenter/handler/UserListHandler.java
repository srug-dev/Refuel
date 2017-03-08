package com.srug.mobile.refuel.presenter.handler;

import com.srug.mobile.refuel.model.mediator.data.User;
import com.srug.mobile.refuel.presenter.UserViewModel;

import java.util.ArrayList;
import java.util.List;

public class UserListHandler {

    private UserViewModel mSelectedUser;
    private List<UserViewModel> mUserList = new ArrayList<>();

    public UserListHandler() {
    }

    public List<UserViewModel> getUserList() {
        return mUserList;
    }

    public void setSelectedUser(Long userId) {
        UserViewModel viewModel = getViewModel(userId);
        resetCurrentSelection();
        if (viewModel != null) {
            viewModel.setIsSelected(true);
        }
    }

    public void initializeList(List<User> userList) {
        mUserList = new ArrayList<>();
        for (User user : userList) {
            UserViewModel viewModel = generateViewModel(user);
            mUserList.add(viewModel);
        }
    }

    private UserViewModel getViewModel(Long userId) {
        for (UserViewModel viewModel : mUserList) {
            if (viewModel.getId().equals(userId)) {
                return viewModel;
            }
        }
        return null;
    }

    private void resetCurrentSelection() {
        for (UserViewModel viewModel : mUserList) {
            if (viewModel.isSelected()) {
                viewModel.setIsSelected(false);
            }
        }
    }

    private UserViewModel generateViewModel(User user) {
        UserViewModel viewModel = new UserViewModel(user.getId());
        viewModel.setEmail(user.getEmail());
        return viewModel;
    }
}
