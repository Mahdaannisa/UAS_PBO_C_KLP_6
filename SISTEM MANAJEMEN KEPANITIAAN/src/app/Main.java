package app;

import cli.LoginCLI;

/**
 * Entry point aplikasi Sistem Manajemen Kepanitiaan.
 */
public class Main {
    public static void main(String[] args) {
        LoginCLI login = new LoginCLI();
        login.start();
    }
}

