package model;

import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 * Kelas DataStorage menyediakan penyimpanan data sederhana (persisten)
 * menggunakan file teks pada folder <code>data/</code>.
 *
 * <p>File yang digunakan:</p>
 * <ul>
 *     <li><b>accounts.txt</b> — menyimpan akun (format: type,username,password,name,linkedId)</li>
 *     <li><b>anggota.txt</b> — menyimpan data anggota (format: id,name,divisi,nohp)</li>
 *     <li><b>divisi.txt</b> — menyimpan divisi dan tugas 
 *         (format: <code>DIV|namaDivisi</code> lalu <code>TASK|tostring</code>)</li>
 * </ul>
 *
 * <p>Kelas ini menggunakan pola Singleton untuk memastikan hanya ada satu instance
 * DataStorage dalam aplikasi.</p>
 */
public class DataStorage {
    private static DataStorage instance;

    private final Path dataDir = Paths.get("data");
    private final Path accountsFile = dataDir.resolve("accounts.txt");
    private final Path anggotaFile = dataDir.resolve("anggota.txt");
    private final Path divisiFile = dataDir.resolve("divisi.txt");

    private final List<Account> accounts = new ArrayList<>();
    private final List<Anggota> anggotaList = new ArrayList<>();
    private final List<Divisi> divisiList = new ArrayList<>();

    /**
     * Konstruktor privat (Singleton).  
     * Membuat folder <code>data/</code> jika belum ada, mengisi data default bila kosong,
     * kemudian memuat seluruh data dari file.
     */
    private DataStorage() {
        try { 
            if (!Files.exists(dataDir)) Files.createDirectories(dataDir); 
        } catch (IOException e) { 
            System.err.println("init data dir: " + e.getMessage()); 
        }
        seedDefaultsIfEmpty();
        loadAll();
    }

    /**
     * Mengambil instance tunggal dari DataStorage (Singleton).
     *
     * @return instance DataStorage
     */
    public static synchronized DataStorage getInstance() {
        if (instance == null) instance = new DataStorage();
        return instance;
    }

    /**
     * Mengisi data default bila tidak ada file penyimpanan yang ditemukan.
     * Data default berisi:
     * <ul>
     *     <li>1 Admin</li>
     *     <li>1 Anggota</li>
     *     <li>1 Member terhubung dengan anggota tersebut</li>
     *     <li>1 Divisi dengan 1 tugas</li>
     * </ul>
     */
    private void seedDefaultsIfEmpty() {
        if (Files.exists(accountsFile) || Files.exists(anggotaFile) || Files.exists(divisiFile)) return;

        accounts.add(new Admin("admin","admin123","Admin Utama"));
        Anggota a = new Anggota("U1","User Contoh","Acara","08123456789");
        anggotaList.add(a);
        accounts.add(new Member("user1","user123","User Contoh","U1"));
        Divisi d = new Divisi("Acara");
        d.addTugas(new Tugas("T1","Susun Rundown","Buat rundown acara"));
        divisiList.add(d);
        saveAll();
    }
// ---------------- ACCESSORS ----------------

    /**
     * Mengambil daftar seluruh akun.
     *
     * @return list akun
     */
    public List<Account> getAccounts() { return accounts; }

    /**
     * Mengambil daftar seluruh anggota.
     *
     * @return list anggota
     */
    public List<Anggota> getAnggotaList() { return anggotaList; }

    /**
     * Mengambil daftar seluruh divisi.
     *
     * @return list divisi
     */
    public List<Divisi> getDivisiList() { return divisiList; }

    // ---------------- FINDERS & LOGIN ----------------

    /**
     * Mencari akun berdasarkan username (case-insensitive).
     *
     * @param username username yang dicari
     * @return Account jika ditemukan, null jika tidak
     */
    public Account findAccountByUsername(String username) {
        if (username==null) return null;
        for (Account a : accounts) if (username.equalsIgnoreCase(a.getUsername())) return a;
        return null;
    }

    /**
     * Melakukan login untuk Admin.
     *
     * @param username username admin
     * @param password password admin
     * @return Admin jika cocok, null jika gagal
     */
    public Admin loginAdmin(String username, String password) {
        Account a = findAccountByUsername(username);
        if (a instanceof Admin && a.checkPassword(password)) return (Admin) a;
        return null;
    }

    /**
     * Melakukan login untuk Member.
     *
     * @param username username member
     * @param password password member
     * @return Member jika cocok, null jika gagal
     */
    public Member loginMember(String username, String password) {
        Account a = findAccountByUsername(username);
        if (a instanceof Member && a.checkPassword(password)) return (Member) a;
        return null;
    }

    /**
     * Mencari anggota berdasarkan ID.
     *
     * @param id ID anggota
     * @return Anggota jika ditemukan, null jika tidak
     */
    public Anggota findAnggotaById(String id) {
        if (id==null) return null;
        for (Anggota a : anggotaList) if (id.equals(a.getId())) return a;
        return null;
    }

    /**
     * Mencari divisi berdasarkan nama (case-insensitive).
     *
     * @param name nama divisi
     * @return Divisi jika ditemukan, null jika tidak
     */
    public Divisi findDivisiByName(String name) {
        if (name==null) return null;
        for (Divisi d : divisiList) if (name.equalsIgnoreCase(d.getNama())) return d;
        return null;
    }
