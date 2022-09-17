package vn.ltt.shop.view;

import vn.ltt.shop.model.Role;

public class MainLauncher {
    private static final LoginView loginView = new LoginView();

    public static void mainLauncher(Role role) {
        loginView.login(role);
    }
}
