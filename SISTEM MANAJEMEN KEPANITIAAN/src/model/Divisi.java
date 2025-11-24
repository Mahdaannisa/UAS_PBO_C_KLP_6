package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Kelas Divisi merepresentasikan sebuah divisi dalam organisasi.
 * Setiap divisi memiliki nama dan daftar tugas yang terkait.
 *
 * <p>Objek Divisi biasanya digunakan untuk mengelompokkan tugas-tugas
 * tertentu berdasarkan bidang atau departemen, misalnya divisi "Acara",
 * "Humas", atau "Perlengkapan".</p>
 */
public class Divisi {
    private String nama;
    private final List<Tugas> tugasList = new ArrayList<>();

    /**
     * Membuat objek Divisi baru dengan nama yang diberikan.
     *
     * @param nama nama divisi
     */
    public Divisi(String nama) { this.nama = nama; }

    /**
     * Mengambil nama divisi.
     *
     * @return nama divisi
     */
    public String getNama() { return nama; }

    /**
     * Mengambil daftar tugas yang dimiliki divisi ini.
     *
     * @return list tugas dalam divisi
     */
    public List<Tugas> getTugasList() { return tugasList; }

    /**
     * Menambahkan sebuah tugas ke dalam divisi.
     *
     * @param t tugas yang akan ditambahkan
     */
    public void addTugas(Tugas t) { if (t!=null) tugasList.add(t); }

    /**
     * Menghapus tugas berdasarkan ID tugas.
     *
     * @param id ID tugas yang akan dihapus
     */
    public void removeTugasById(String id) { tugasList.removeIf(t->t.getId().equals(id)); }
