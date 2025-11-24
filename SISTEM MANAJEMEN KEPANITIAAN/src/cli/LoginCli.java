package cli;

import model.*;
import java.util.*;

/**
 * Menangani proses login dan registrasi pada sistem.
 * Menyediakan antarmuka CLI untuk Admin dan Member.
 */
public class LoginCLI {

    /** Scanner untuk input CLI */
    private final Scanner sc = new Scanner(System.in);

    /** Akses ke penyimpanan data global */
    private final DataStorage ds = DataStorage.getInstance();

    // Warna CLI
    private final String RESET  = "\u001B[0m";
    private final String GREEN  = "\u001B[92m";
    private final String CYAN   = "\u001B[96m";
    private final String YELLOW = "\u001B[93m";
    private final String TITLE  = "\u001B[92m";

    /**
     * Memulai tampilan utama sistem (menu login).
     * Loop berjalan terus hingga user memilih exit.
     */
    public void start() {
        ds.loadAll(); // load semua data saat aplikasi mulai

        while (true) {
            try {
                System.out.println();
                System.out.println("------------------------------------------------------------");
                System.out.println(TITLE + "                   SISTEM MANAJEMEN KEPANITIAAN            " + RESET);
                System.out.println("------------------------------------------------------------");
                System.out.println(CYAN   + " [1] Login Admin"   + RESET);
                System.out.println(CYAN   + " [2] Login Member"  + RESET);
                System.out.println(YELLOW + " [3] Register Member" + RESET);
                System.out.println(YELLOW + " [0] Exit" + RESET);
                System.out.println("------------------------------------------------------------");
                System.out.print("Pilih > ");

                String opt = sc.nextLine().trim();

                switch (opt) {
                    case "1": adminLogin();   break;
                    case "2": memberLogin();  break;
                    case "3": registerMember(); break;
                    case "0":
                        ds.saveAll();
                        System.out.println(GREEN + "Data disimpan. Keluar." + RESET);
                        System.exit(0);
                        break;
                    default:
                        System.out.println(YELLOW + "Pilihan tidak valid." + RESET);
                }
            } catch (Exception ex) {
                System.out.println(YELLOW + "Terjadi error: " + ex.getMessage() + RESET);
            }
        }
    }

    /**
     * Login akun admin.
     * Jika berhasil, diarahkan ke menu AdminCLI.
     */
    private void adminLogin() {
        System.out.print("Username: ");
        String u = sc.nextLine().trim();

        System.out.print("Password: ");
        String p = sc.nextLine().trim();

        Admin a = ds.loginAdmin(u, p);

        if (a == null) {
            System.out.println(YELLOW + "Login admin gagal." + RESET);
            return;
        }

        new AdminCLI(a).start();
        ds.loadAll(); // reload data setelah kembali
    }

    /**
     * Login akun member.
     * Jika berhasil, diarahkan ke menu MemberCLI.
     */
    private void memberLogin() {
        System.out.print("Username: ");
        String u = sc.nextLine().trim();

        System.out.print("Password: ");
        String p = sc.nextLine().trim();

        Member m = ds.loginMember(u, p);

        if (m == null) {
            System.out.println(YELLOW + "Login member gagal." + RESET);
            return;
        }

        new MemberCLI(m).start();
        ds.loadAll();
    }
