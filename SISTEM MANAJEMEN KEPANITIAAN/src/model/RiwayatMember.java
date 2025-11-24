package model;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Kelas yang menangani pencatatan riwayat aktivitas member.
 * Setiap aktivitas disimpan dalam berkas teks dengan format:
 * <pre>timestamp|anggotaId|event</pre>
 *
 * Berkas riwayat disimpan pada direktori <code>data</code>
 * dan akan dibuat secara otomatis apabila belum ada.
 */
public class RiwayatMember {
    private final Path file = Paths.get("data","riwayat_member.txt");
    private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Konstruktor yang memastikan direktori dan berkas riwayat tersedia.
     * Jika belum ada, akan dibuat secara otomatis.
     */
    public RiwayatMember() {
        try { if (!Files.exists(file.getParent())) Files.createDirectories(file.getParent());
              if (!Files.exists(file)) Files.createFile(file); } catch (IOException e) { System.err.println("init riwayat member: " + e.getMessage()); }
    }

    /**
     * Menambahkan entri riwayat baru secara synchronized.
     *
     * @param anggotaId ID anggota yang melakukan aktivitas.
     * @param event Deskripsi aktivitas yang dicatat.
     *
     * Format baris yang ditulis ke berkas adalah:
     * <pre>timestamp|anggotaId|event</pre>
     */
    public synchronized void add(String anggotaId, String event) {
        String line = LocalDateTime.now().format(fmt) + "|" + anggotaId + "|" + event;
        try (BufferedWriter bw = Files.newBufferedWriter(file, StandardOpenOption.APPEND)) {
            bw.write(line); bw.newLine();
        } catch (IOException e) { System.err.println("write riwayat member: " + e.getMessage()); }
    }

    /**
     * Mengambil seluruh riwayat aktivitas untuk anggota tertentu.
     *
     * @param anggotaId ID anggota yang ingin diambil riwayatnya.
     * @return Daftar string yang berisi waktu dan deskripsi aktivitas.
     */
    public List<String> getFor(String anggotaId) {
        try {
            if (!Files.exists(file)) return Collections.emptyList();
            List<String> all = Files.readAllLines(file);
            List<String> out = new ArrayList<>();
            for (String l : all) {
                String[] p = l.split("\\|",3);
                if (p.length<3) continue;
                if (p[1].equalsIgnoreCase(anggotaId)) out.add(p[0] + " | " + p[2]);
            }
            return out;
        } catch (IOException e) { System.err.println("read riwayat member: " + e.getMessage()); return Collections.emptyList(); }
    }

    /**
     * Mencetak riwayat aktivitas anggota dalam bentuk tabel sederhana.
     *
     * @param anggotaId ID anggota yang ingin dicetak riwayatnya.
     *
     * Jika riwayat kosong, akan menampilkan teks penanda.
     */
    public void print(String anggotaId) {
        List<String> rows = getFor(anggotaId);
        if (rows.isEmpty()) { System.out.println("(belum ada riwayat)"); return; }
        System.out.println("+---------------------+--------------------------------+");
        System.out.println("| Waktu               | Aksi                           |");
        System.out.println("+---------------------+--------------------------------+");
        for (String r : rows) {
            String[] p = r.split("\\|",2);
            String time = p[0].trim();
            String event = p.length>1 ? p[1].trim() : "";
            System.out.printf("| %-19s | %-30s |%n", time, event);
        }
        System.out.println("+---------------------+--------------------------------+");
    }
}

