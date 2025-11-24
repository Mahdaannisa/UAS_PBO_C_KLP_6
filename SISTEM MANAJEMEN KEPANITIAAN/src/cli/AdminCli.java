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

    // ============================================================
    //                      KELOLA ANGGOTA
    // ============================================================

    /**
     * Menampilkan submenu kelola anggota.
     * <p>Mengatur fitur tambah, edit, hapus, dan lihat anggota.</p>
     */
    private void kelolaAnggota() {
        while (true) {
            try {
                System.out.println();
                System.out.println("------------------------------------------------------------");
                System.out.println(TITLE + "                   KELOLA ANGGOTA                          " + RESET);
                System.out.println("------------------------------------------------------------");
                System.out.println(" [1] Tambah Anggota");
                System.out.println(" [2] Edit Anggota");
                System.out.println(" [3] Hapus Anggota");
                System.out.println(" [4] Lihat Semua Anggota (Terurut per divisi)");
                System.out.println(" [0] Kembali");
                System.out.println("------------------------------------------------------------");
                System.out.print("Pilih > ");

                String o = sc.nextLine().trim();
                switch (o) {
                    case "1": tambahAnggota(); break;
                    case "2": editAnggota(); break;
                    case "3": hapusAnggota(); break;
                    case "4": lihatAnggotaTerurut(); break;
                    case "0": return;
                    default: System.out.println(YELLOW + "Pilihan tidak valid." + RESET);
                }

                pauseForBack();
            } catch (Exception ex) {
                System.out.println(YELLOW + "Error: " + ex.getMessage() + RESET);
            }
        }
    }

    /**
     * Menambahkan anggota baru ke sistem.
     * <p>ID wajib unik dan nama tidak boleh kosong.</p>
     */
    private void tambahAnggota() {
        System.out.print("ID        : "); String id = sc.nextLine().trim();
        System.out.print("Nama      : "); String nama = sc.nextLine().trim();
        System.out.print("Divisi    : "); String div = sc.nextLine().trim();
        System.out.print("No HP     : "); String hp = sc.nextLine().trim();

        if (id.isEmpty() || nama.isEmpty()) {
            System.out.println(YELLOW + "ID & Nama wajib." + RESET);
            return;
        }
        if (ds.findAnggotaById(id) != null) {
            System.out.println(YELLOW + "ID sudah ada." + RESET);
            return;
        }

        Anggota a = new Anggota(id, nama, div, hp);
        ds.addAnggota(a);

        riwayAdmin.add(admin.getUsername(), "ADD_ANGGOTA," + id + "," + nama);
        ds.saveAll();

        System.out.println(GREEN + "Anggota berhasil ditambahkan." + RESET);

        // auto tampilkan tabel setelah menambah
        lihatAnggotaTerurut();
    }
 /**
     * Mengedit data anggota berdasarkan ID.
     */
    private void editAnggota() {
        System.out.print("Masukkan ID anggota yang ingin diedit: ");
        String id = sc.nextLine().trim();

        Anggota a = ds.findAnggotaById(id);
        if (a == null) {
            System.out.println(YELLOW + "ID anggota tidak ditemukan." + RESET);
            return;
        }

        System.out.print("Nama baru (" + a.getNama() + "): ");
        String nama = sc.nextLine().trim();
        System.out.print("Divisi baru (" + a.getDivisi() + "): ");
        String div = sc.nextLine().trim();
        System.out.print("No HP baru (" + a.getNoHp() + "): ");
        String hp = sc.nextLine().trim();

        if (!nama.isEmpty()) a.setNama(nama);
        if (!div.isEmpty()) a.setDivisi(div);
        if (!hp.isEmpty()) a.setNoHp(hp);

        riwayAdmin.add(admin.getUsername(), "EDIT_ANGGOTA," + id);
        ds.saveAll();

        System.out.println(GREEN + "Data anggota berhasil diperbarui." + RESET);
    }

    /**
     * Menghapus anggota berdasarkan ID.
     */
    private void hapusAnggota() {
        System.out.print("Masukkan ID anggota yang ingin dihapus: ");
        String id = sc.nextLine().trim();

        Anggota a = ds.findAnggotaById(id);
        if (a == null) {
            System.out.println(YELLOW + "ID anggota tidak ditemukan." + RESET);
            return;
        }

        ds.removeAnggotaById(id);
        riwayAdmin.add(admin.getUsername(), "DELETE_ANGGOTA," + id);
        ds.saveAll();

        System.out.println(GREEN + "Anggota berhasil dihapus." + RESET);
    }

    /**
     * Menampilkan semua anggota terurut berdasarkan divisi dalam bentuk tabel.
     */
    private void lihatAnggotaTerurut() {
        List<Anggota> list = new ArrayList<>(ds.getAnggotaList());

        if (list.isEmpty()) {
            System.out.println("(Tidak ada anggota)");
            return;
        }

        list.sort(Comparator.comparing(Anggota::getDivisi, String.CASE_INSENSITIVE_ORDER)
                .thenComparing(Anggota::getNama, String.CASE_INSENSITIVE_ORDER));

        String currentDiv = "";
        int total = 0;

        System.out.println("============================================================");
        System.out.println("                DAFTAR ANGGOTA TERURUT DIVISI");
        System.out.println("============================================================");

        for (Anggota a : list) {
            String div = a.getDivisi() == null ? "" : a.getDivisi();

            if (!div.equalsIgnoreCase(currentDiv)) {
                currentDiv = div;

                // hitung anggota per divisi
                int count = 0;
                for (Anggota x : list)
                    if ((x.getDivisi()==null?"":x.getDivisi()).equalsIgnoreCase(currentDiv)) count++;

                System.out.println();
                System.out.println("DIVISI : " + (currentDiv.isEmpty() ? "(tidak diisi)" : currentDiv) +
                        " (" + count + " anggota)");
                System.out.println("+------------+----------------------+--------------+");
                System.out.println("| ID         | NAMA                 | NO HP        |");
                System.out.println("+------------+----------------------+--------------+");
            }

            System.out.printf("| %-10s | %-20s | %-12s |%n",
                    a.getId(), a.getNama(), a.getNoHp());
            System.out.println("+------------+----------------------+--------------+");

            total++;
        }

        System.out.println("\n------------------------------------------------------------");
        System.out.println(GREEN + "Total anggota: " + total + RESET);
    }

    /**
     * Fitur pencarian anggota menggunakan ID atau nama (partial search).
     */
    private void cariAnggota() {
        System.out.print("Masukkan ID atau Nama (partial OK): ");
        String q = sc.nextLine().trim().toLowerCase();

        List<Anggota> found = new ArrayList<>();
        for (Anggota a : ds.getAnggotaList()) {
            if (a.getId().toLowerCase().contains(q)
                    || a.getNama().toLowerCase().contains(q)) {

                found.add(a);
            }
        }

        if (found.isEmpty()) {
            System.out.println(YELLOW + "Tidak ditemukan anggota dengan query: " + q + RESET);
            return;
        }

        System.out.println("+------------+----------------------+--------------+");
        System.out.println("| ID         | NAMA                 | NO HP        |");
        System.out.println("+------------+----------------------+--------------+");

        for (Anggota a : found) {
            System.out.printf("| %-10s | %-20s | %-12s |%n",
                    a.getId(), a.getNama(), a.getNoHp());
            System.out.println("+------------+----------------------+--------------+");
        }
    }

    // ============================================================
    //                      DIVISI & TUGAS
    // ============================================================

    /**
     * Menampilkan submenu pengelolaan divisi dan tugas.
     */
    private void kelolaDivisiTugas() {
        while (true) {
            try {
                System.out.println();
                System.out.println("------------------------------------------------------------");
                System.out.println(TITLE + "                KELOLA DIVISI & TUGAS                      " + RESET);
                System.out.println("------------------------------------------------------------");
                System.out.println(" [1] Tambah Divisi");
                System.out.println(" [2] Tambah Tugas ke Divisi");
                System.out.println(" [3] Lihat Divisi & Tugas");
                System.out.println(" [4] Hapus Tugas dari Divisi");
                System.out.println(" [0] Kembali");
                System.out.println("------------------------------------------------------------");
                System.out.print("Pilih > ");

                String o = sc.nextLine().trim();
                switch (o) {
                    case "1": tambahDivisi(); break;
                    case "2": tambahTugas(); break;
                    case "3": lihatDivisiTugas(); break;
                    case "4": hapusTugas(); break;
                    case "0": return;
                    default: System.out.println(YELLOW + "Pilihan tidak valid." + RESET);
                }

                pauseForBack();
            } catch (Exception ex) {
                System.out.println(YELLOW + "Error: " + ex.getMessage() + RESET);
            }
        }
    }

    /**
     * Menambah divisi baru.
     */
    private void tambahDivisi() {
        System.out.print("Nama Divisi: ");
        String dn = sc.nextLine().trim();

        if (dn.isEmpty()) {
            System.out.println(YELLOW + "Nama wajib." + RESET);
            return;
        }

        if (ds.findDivisiByName(dn) != null) {
            System.out.println(YELLOW + "Divisi sudah ada." + RESET);
            return;
        }

        ds.addDivisi(new Divisi(dn));
        riwayAdmin.add(admin.getUsername(), "ADD_DIVISI," + dn);
        ds.saveAll();

        System.out.println(GREEN + "Divisi ditambahkan." + RESET);
    }

    /**
     * Menambahkan tugas ke dalam suatu divisi.
     */
    private void tambahTugas() {
        System.out.print("Divisi tujuan: ");
        String dn = sc.nextLine().trim();

        Divisi d = ds.findDivisiByName(dn);
        if (d == null) {
            System.out.println(YELLOW + "Divisi tidak ditemukan." + RESET);
            return;
        }

        System.out.print("ID Tugas: ");
        String tid = sc.nextLine().trim();
        System.out.print("Judul: ");
        String judul = sc.nextLine().trim();
        System.out.print("Deskripsi: ");
        String desc = sc.nextLine().trim();

        d.addTugas(new Tugas(tid, judul, desc));
        riwayAdmin.add(admin.getUsername(), "ADD_TUGAS," + tid + "," + dn);
        ds.saveAll();

        System.out.println(GREEN + "Tugas ditambahkan ke divisi " + dn + RESET);
    }

    /**
     * Menghapus tugas dari divisi berdasarkan ID tugas.
     */
    private void hapusTugas() {
        System.out.print("Divisi: ");
        String dn = sc.nextLine().trim();

        Divisi d = ds.findDivisiByName(dn);
        if (d == null) {
            System.out.println(YELLOW + "Divisi tidak ditemukan." + RESET);
            return;
        }

        System.out.print("ID tugas yang ingin dihapus: ");
        String tid = sc.nextLine().trim();

        boolean exists = false;
        for (Tugas t : d.getTugasList()) {
            if (t.getId().equals(tid)) { exists = true; break; }
        }

        if (!exists) {
            System.out.println(YELLOW + "Tugas tidak ditemukan di divisi tersebut." + RESET);
            return;
        }

        d.removeTugasById(tid);
        riwayAdmin.add(admin.getUsername(), "DELETE_TUGAS," + tid + "," + dn);
        ds.saveAll();

        System.out.println(GREEN + "Tugas dihapus." + RESET);
    }

    /**
     * Menampilkan seluruh divisi dan tugas dalam format tabel.
     */
    private void lihatDivisiTugas() {
        List<Divisi> list = new ArrayList<>(ds.getDivisiList());
        list.sort(Comparator.comparing(Divisi::getNama, String.CASE_INSENSITIVE_ORDER));

        System.out.println("============================================================");
        System.out.println("                    DAFTAR DIVISI & TUGAS");
        System.out.println("============================================================");

        if (list.isEmpty()) {
            System.out.println("(Belum ada divisi)");
            System.out.println("============================================================");
            return;
        }

        for (Divisi d : list) {
            System.out.printf("\nDIVISI: %s   (Total Tugas: %d)%n",
                    d.getNama(), d.getTugasList().size());

            System.out.println("+------------+------------------------------+------------+");
            System.out.printf("| %-10s | %-28s | %-10s |%n",
                    "ID Tugas", "Judul", "Status");
            System.out.println("+------------+------------------------------+------------+");

            if (d.getTugasList().isEmpty()) {
                System.out.printf("| %-46s |%n", "(Tidak ada tugas)");
                System.out.println("+------------+------------------------------+------------+");
                continue;
            }

            List<Tugas> tugasList = new ArrayList<>(d.getTugasList());
            tugasList.sort(Comparator.comparing(Tugas::getId));

            for (Tugas t : tugasList) {
                System.out.printf("| %-10s | %-28s | %-10s |%n",
                        t.getId(), truncate(t.getJudul(), 28), t.getStatus());
                System.out.println("+------------+------------------------------+------------+");
            }
        }

        System.out.println("\n============================================================");
    }
