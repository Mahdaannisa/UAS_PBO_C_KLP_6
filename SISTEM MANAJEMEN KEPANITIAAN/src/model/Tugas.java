package model;

import java.time.LocalDate;

/**
 * Kelas yang merepresentasikan sebuah tugas dalam sistem.
 * Setiap tugas memiliki ID, judul, deskripsi, deadline (opsional),
 * serta status pengerjaan.
 */
public class Tugas {
    /**
     * Status pengerjaan tugas.
     * TODO = belum dikerjakan,
     * IN_PROGRESS = sedang dikerjakan,
     * DONE = selesai.
     */
    public enum Status { TODO, IN_PROGRESS, DONE }

    private String id;
    private String judul;
    private String deskripsi;
    private LocalDate deadline;
    private Status status = Status.TODO;

    /**
     * Membuat objek tugas baru.
     *
     * @param id ID unik tugas.
     * @param judul Judul atau nama tugas.
     * @param deskripsi Penjelasan atau keterangan mengenai tugas.
     */
    public Tugas(String id, String judul, String deskripsi) {
        this.id = id; this.judul = judul; this.deskripsi = deskripsi; this.status = Status.TODO;
    }

/** @return ID tugas. */
    public String getId() { return id; }

    /** @return Judul tugas. */
    public String getJudul() { return judul; }

    /** @return Deskripsi tugas. */
    public String getDeskripsi() { return deskripsi; }

    /** @return Deadline tugas, atau null jika belum ditentukan. */
    public LocalDate getDeadline() { return deadline; }

    /** @return Status pengerjaan tugas. */
    public Status getStatus() { return status; }
    
