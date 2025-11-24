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
