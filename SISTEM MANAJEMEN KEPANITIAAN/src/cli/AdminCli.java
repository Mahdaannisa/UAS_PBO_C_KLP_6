package cli;

import model.*;
import java.util.*;

/**
 * Kelas CLI khusus untuk akun Admin.
 * <p>
 * Class ini yang menyediakan berbagai fitur manajemen admin seperti:
 * <ul>
 *     <li>Kelola data anggota (tambah, edit, hapus, lihat terurut, pencarian)</li>
 *     <li>Kelola divisi & tugas</li>
 *     <li>Melihat riwayat aktivitas admin</li>
 *     <li>Menyimpan data ke penyimpanan permanen</li>
 * </ul>
 * Seluruh tampilan dibuat berbasis CLI (Command Line Interface) dengan border tabel.
 */
public class AdminCLI {

    /** Objek Admin yang sedang login */
    private final Admin admin;

    /** Scanner untuk input */
    private final Scanner sc = new Scanner(System.in);

    /** Singleton penyimpanan data */
    private final DataStorage ds = DataStorage.getInstance();

    /** Riwayat aktivitas admin */
    private final RiwayatAdmin riwayAdmin = new RiwayatAdmin();

    // Warna CLI
    private final String RESET  = "\u001B[0m";
    private final String GREEN  = "\u001B[92m";
    private final String CYAN   = "\u001B[96m";
    private final String YELLOW = "\u001B[93m";
    private final String TITLE  = "\u001B[92m";

    /**
     * Konstruktor untuk AdminCLI.
     *
     * @param admin objek admin yang login
     */
    public AdminCLI(Admin admin) { this.admin = admin; }

    /**
     * Menjalankan loop utama menu admin.
     * <p>Berisi navigasi: anggota, divisi, tugas, pencarian, riwayat, dan penyimpanan.</p>
     */
    public void start() {
        System.out.println(TITLE + "Masuk sebagai Admin: " + admin.getName() + RESET);
        while (true) {
            System.out.println();
            System.out.println("------------------------------------------------------------");
            System.out.println(TITLE + "                       ADMIN MENU                         " + RESET);
            System.out.println("------------------------------------------------------------");
            System.out.println(CYAN + " [1] Kelola Anggota" + RESET);
            System.out.println(CYAN + " [2] Kelola Divisi & Tugas" + RESET);
            System.out.println(CYAN + " [3] Lihat Riwayat Admin" + RESET);
            System.out.println(CYAN + " [4] Cari Anggota (by ID/Name)" + RESET);
            System.out.println(YELLOW + " [S] Simpan Data" + RESET);
            System.out.println(YELLOW + " [0] Logout" + RESET);
            System.out.println("------------------------------------------------------------");
            System.out.print("Pilih > ");
            String opt = sc.nextLine().trim().toUpperCase();

            switch (opt) {
                case "1": kelolaAnggota(); break;
                case "2": kelolaDivisiTugas(); break;
                case "3": viewRiwayatAdmin(); break;
                case "4": cariAnggota(); break;
                case "S":
                    ds.saveAll();
                    System.out.println(GREEN + "Data disimpan." + RESET);
                    break;
                case "0":
                    System.out.println(GREEN + "Logout Admin." + RESET);
                    return;
                default:
                    System.out.println(YELLOW + "Pilihan tidak valid." + RESET);
            }
        }
    }
