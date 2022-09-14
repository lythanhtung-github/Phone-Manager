package vn.ltt.shop.view;

import vn.ltt.shop.model.Role;

public class MainLauncher {
    public static void mainLauncher(Role role){
        LoginView loginView = new LoginView();
        loginView.login(role);
    }
}
