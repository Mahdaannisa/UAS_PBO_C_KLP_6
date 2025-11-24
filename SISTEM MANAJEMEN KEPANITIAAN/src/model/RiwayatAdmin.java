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
