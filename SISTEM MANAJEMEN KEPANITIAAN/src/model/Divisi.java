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
