package model;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Kelas RiwayatAdmin digunakan untuk mencatat dan membaca riwayat aktivitas admin.
 * Setiap aktivitas dicatat secara persisten dalam file <code>data/riwayat_admin.txt</code>
 * dengan format:
 *
 * <pre>
 * timestamp|adminUsername|event
 * </pre>
 *
 * <p>Riwayat disimpan menggunakan metode append sehingga data lama tidak akan ditimpa.</p>
 */
public class RiwayatAdmin {
    private final Path file = Paths.get("data","riwayat_admin.txt");
    private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Konstruktor yang memastikan folder <code>data/</code> dan file log tersedia.
     * Jika belum ada, maka akan dibuat secara otomatis.
     */
    public RiwayatAdmin() {
        try { 
            if (!Files.exists(file.getParent())) Files.createDirectories(file.getParent());
            if (!Files.exists(file)) Files.createFile(file); 
        } catch (IOException e) { 
            System.err.println("init riwayat admin: " + e.getMessage()); 
        }
    }

     /**
     * Menambahkan entri baru ke dalam riwayat admin.
     * Entri dicatat dengan timestamp saat ini, username admin, dan deskripsi event.
     *
     * @param adminUsername username admin yang melakukan aksi
     * @param event deskripsi aksi atau aktivitas
     */
    public synchronized void add(String adminUsername, String event) {
        String line = LocalDateTime.now().format(fmt) + "|" + adminUsername + "|" + event;
        try (BufferedWriter bw = Files.newBufferedWriter(file, StandardOpenOption.APPEND)) {
            bw.write(line); 
            bw.newLine();
        } catch (IOException e) { 
            System.err.println("write riwayat admin: " + e.getMessage()); 
        }
    }

    /**
     * Mengambil seluruh baris riwayat admin dari file.
     *
     * @return list string berisi baris riwayat; kosong jika file tidak ada atau gagal dibaca
     */
    public List<String> getAll() {
        try { 
            if (!Files.exists(file)) return Collections.emptyList();
            return Files.readAllLines(file); 
        } catch (IOException e) { 
            System.err.println("read riwayat admin: " + e.getMessage()); 
            return Collections.emptyList(); 
        }
    }
