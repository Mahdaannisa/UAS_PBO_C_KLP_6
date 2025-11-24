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
