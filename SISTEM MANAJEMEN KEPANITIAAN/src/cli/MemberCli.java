package cli;

import model.*;

import java.util.*;

/**
 * Kelas MemberCLI menangani semua fitur yang dapat diakses oleh Member.
 *
 * <p>Fitur yang disediakan:
 * <ul>
 *     <li>Melihat data diri</li>
 *     <li>Melihat daftar tugas divisi dalam bentuk tabel</li>
 *     <li>Update status tugas (IN_PROGRESS / DONE)</li>
 *     <li>Pencarian gabungan divisi & tugas (global)</li>
 *     <li>Filter tugas (status / divisi)</li>
 *     <li>Sortir tugas (status / nama tugas / divisi)</li>
 *     <li>Melihat riwayat aktivitas pribadi</li>
 * </ul>
 *
 * <p>Menu utama menggunakan format ringkas (option 4 membuka submenu pencarian & filter).
 *
 * <p>Catatan: kelas ini menggunakan {@link DataStorage} untuk membaca/menyimpan data
 * dan {@link RiwayatMember} untuk mencatat aksi yang dilakukan member.
 */
public class MemberCLI {
    private final Member member;
    private final Scanner sc = new Scanner(System.in);
    private final DataStorage ds = DataStorage.getInstance();
    private final RiwayatMember riway = new RiwayatMember();

    private final String RESET  = "\u001B[0m";
    private final String GREEN  = "\u001B[92m";
    private final String CYAN   = "\u001B[96m";
    private final String YELLOW = "\u001B[93m";
    private final String TITLE  = "\u001B[92m";

    /**
     * Konstruktor MemberCLI.
     *
     * @param member akun member yang sedang login.
     */
    public MemberCLI(Member member) {
        this.member = member;
    }

    /**
     * Menampilkan menu utama member dan menangani semua navigasi.
     *
     * Menu (format B):
     * <pre>
     * [1] Lihat Data Diri
     * [2] Lihat Tugas Divisi
     * [3] Update Status Tugas
     * [4] Menu Pencarian & Filter
     * [5] Lihat Riwayat
     * [0] Logout
     * </pre>
     */
    public void start() {
        System.out.println(TITLE + "Masuk sebagai Member: " + member.getName() + RESET);

        while (true) {
            try {
                System.out.println();
                System.out.println("============================================================");
                System.out.println(TITLE + "                        MEMBER MENU                        " + RESET);
                System.out.println("============================================================");
                System.out.println(CYAN + " [1] Lihat Data Diri" + RESET);
                System.out.println(CYAN + " [2] Lihat Tugas Divisi" + RESET);
                System.out.println(CYAN + " [3] Update Status Tugas" + RESET);
                System.out.println(CYAN + " [4] Menu Pencarian & Filter" + RESET);
                System.out.println(CYAN + " [5] Lihat Riwayat" + RESET);
                System.out.println(YELLOW + " [0] Logout" + RESET);
                System.out.println("------------------------------------------------------------");
                System.out.print("Pilih > ");
                String opt = sc.nextLine().trim();

                switch (opt) {
                    case "1": lihatDiri(); pauseForBack(); break;
                    case "2": lihatTugas(); pauseForBack(); break;
                    case "3": updateStatus(); pauseForBack(); break;
                    case "4": menuPencarianFilter(); break;
                    case "5": lihatRiwayat(); pauseForBack(); break;
                    case "0":
                        System.out.println(GREEN + "Logout." + RESET);
                        return;
                    default:
                        System.out.println(YELLOW + "Pilihan tidak valid." + RESET);
                }

            } catch (Exception ex) {
                System.out.println(YELLOW + "Error: " + ex.getMessage() + RESET);
            }
        }
    }

    /**
     * Menampilkan data diri member (ID, nama, divisi, nomor HP).
     */
    private void lihatDiri() {
        Anggota a = ds.findAnggotaById(member.getIdAnggota());
        if (a == null) {
            System.out.println(YELLOW + "(data tidak ditemukan)" + RESET);
            return;
        }

        System.out.println("------------------------------------------------------------");
        System.out.printf(" ID     : %s%n", a.getId());
        System.out.printf(" Nama   : %s%n", a.getNama());
        System.out.printf(" Divisi : %s%n", a.getDivisi());
        System.out.printf(" HP     : %s%n", a.getNoHp());
        System.out.println("------------------------------------------------------------");
    }

    /**
     * Menampilkan daftar tugas yang dimiliki divisi member dalam bentuk tabel.
     */
    private void lihatTugas() {
        Anggota a = ds.findAnggotaById(member.getIdAnggota());
        if (a == null) {
            System.out.println(YELLOW + "(data tidak ditemukan)" + RESET);
            return;
        }

        Divisi d = ds.findDivisiByName(a.getDivisi());
        if (d == null) {
            System.out.println(YELLOW + "(divisi tidak ditemukan)" + RESET);
            return;
        }

        List<Tugas> tugas = new ArrayList<>(d.getTugasList());
        if (tugas.isEmpty()) {
            System.out.println(YELLOW + "(tidak ada tugas)" + RESET);
            return;
        }

        tugas.sort(Comparator.comparing(Tugas::getId));

        System.out.println("------------------------------------------------------------");
        System.out.println("Tugas untuk divisi " + d.getNama() + ":");
        System.out.println("+------------+------------------------------+------------+");
        System.out.printf("| %-10s | %-28s | %-10s |%n", "ID Tugas", "Judul", "Status");
        System.out.println("+------------+------------------------------+------------+");

        for (Tugas t : tugas) {
            System.out.printf("| %-10s | %-28s | %-10s |%n",
                    t.getId(),
                    truncate(t.getJudul(), 28),
                    t.getStatus());
            System.out.println("+------------+------------------------------+------------+");
        }
    }

    /**
     * Mengubah status tugas member.
     * Tugas hanya dapat diubah bila ID sesuai dan berada di divisi member.
     */
    private void updateStatus() {
        System.out.print("Masukkan ID tugas: ");
        String tid = sc.nextLine().trim();

        Anggota a = ds.findAnggotaById(member.getIdAnggota());
        if (a == null) {
            System.out.println(YELLOW + "(data tidak ditemukan)" + RESET);
            return;
        }

        Divisi d = ds.findDivisiByName(a.getDivisi());
        if (d == null) {
            System.out.println(YELLOW + "(divisi tidak ditemukan)" + RESET);
            return;
        }

        for (Tugas t : d.getTugasList()) {
            if (t.getId().equals(tid)) {

                System.out.println(" [1] IN_PROGRESS");
                System.out.println(" [2] DONE");
                System.out.print("Pilih > ");
                String s = sc.nextLine().trim();

                if ("1".equals(s)) t.setStatus(Tugas.Status.IN_PROGRESS);
                else if ("2".equals(s)) t.setStatus(Tugas.Status.DONE);
                else {
                    System.out.println(YELLOW + "Pilihan tidak valid." + RESET);
                    return;
                }

                riway.add(member.getIdAnggota(), "UPDATE_TUGAS," + tid + "," + member.getUsername());
                ds.saveAll();

                System.out.println(GREEN + "Status tugas diperbarui." + RESET);
                return;
            }
        }

        System.out.println(YELLOW + "Tugas tidak ditemukan." + RESET);
    }

    /**
     * Menampilkan riwayat aktivitas milik member.
     */
    private void lihatRiwayat() {
        System.out.println("------------------------------------------------------------");
        System.out.println("Riwayat pribadi:");
        System.out.println("------------------------------------------------------------");
        riway.print(member.getIdAnggota());
    }

    // ===========================
    // MENU PENCARIAN & FILTER
    // ===========================

    /**
     * Submenu yang menggabungkan pencarian, filter, dan sortir tugas.
     *
     * Submenu:
     * [1] Cari Divisi & Tugas
     * [2] Filter Tugas
     * [3] Sortir Tugas
     * [0] Kembali
     */
    private void menuPencarianFilter() {
        while (true) {
            System.out.println();
            System.out.println("------------------------------------------------------------");
            System.out.println(TITLE + "               PENCARIAN & FILTER TUGAS                    " + RESET);
            System.out.println("------------------------------------------------------------");
            System.out.println(" [1] Cari Divisi & Tugas");
            System.out.println(" [2] Filter Tugas (status / divisi)");
            System.out.println(" [3] Sortir Tugas (status / nama / divisi)");
            System.out.println(" [0] Kembali");
            System.out.println("------------------------------------------------------------");
            System.out.print("Pilih > ");
            String o = sc.nextLine().trim();

            switch (o) {
                case "1": searchDivisiTugasMember(); pauseForBack(); break;
                case "2": filterTugas(); pauseForBack(); break;
                case "3": sortTugas(); pauseForBack(); break;
                case "0": return;
                default: System.out.println(YELLOW + "Pilihan tidak valid." + RESET);
            }
        }
    }

    /**
     * Pencarian gabungan divisi & tugas untuk Member (hanya menampilkan hasil).
     *
     * <p>Keyword dicari di:
     * - nama divisi
     * - id tugas
     * - judul tugas
     * - deskripsi tugas
     *
     * Hasil tugas menampilkan divisi asal tugas.
     */
    private void searchDivisiTugasMember() {
        System.out.print("Masukkan keyword pencarian (divisi/tugas): ");
        String q = sc.nextLine().trim().toLowerCase();
        if (q.isEmpty()) {
            System.out.println(YELLOW + "Keyword kosong." + RESET);
            return;
        }

        List<Divisi> divMatches = new ArrayList<>();
        List<String> tugasMatches = new ArrayList<>();

        for (Divisi d : ds.getDivisiList()) {
            if (d.getNama() != null && d.getNama().toLowerCase().contains(q)) {
                divMatches.add(d);
            }
            for (Tugas t : d.getTugasList()) {
                if ((t.getId()!=null && t.getId().toLowerCase().contains(q)) ||
                        (t.getJudul()!=null && t.getJudul().toLowerCase().contains(q)) ||
                        (t.getDeskripsi()!=null && t.getDeskripsi().toLowerCase().contains(q))) {

                    tugasMatches.add(String.format("[%s] %s | %s", d.getNama(), t.getId(), t.getJudul()));
                }
            }
        }

        System.out.println("\n================== HASIL PENCARIAN ==================");
        System.out.println("\nDIVISI:");
        if (divMatches.isEmpty()) System.out.println("(tidak ada divisi yang cocok)");
        else for (Divisi d : divMatches) System.out.println("- " + d.getNama());

        System.out.println("\nTUGAS:");
        if (tugasMatches.isEmpty()) System.out.println("(tidak ada tugas yang cocok)");
        else tugasMatches.forEach(s -> System.out.println("- " + s));
        System.out.println("=====================================================");
    }
