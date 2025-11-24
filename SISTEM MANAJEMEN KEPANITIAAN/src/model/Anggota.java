package model;

/**
 * Representasi anggota kepanitiaan.
 * Menyimpan informasi dasar seperti ID, nama, divisi, dan nomor HP.
 * Class ini digunakan baik oleh admin maupun member untuk proses
 * pengelolaan data kepanitiaan.
 */
public class Anggota {
    private String id;
    private String nama;
    private String divisi;
    private String noHp;

    /**
     * Konstruktor untuk membuat objek Anggota baru.
     *
     * @param id      ID unik anggota
     * @param nama    Nama lengkap anggota
     * @param divisi  Divisi tempat anggota bertugas
     * @param noHp    Nomor HP anggota
     */
 public Anggota(String id, String nama, String divisi, String noHp) {
        this.id = id; this.nama = nama; this.divisi = divisi; this.noHp = noHp;
    }

    /** @return ID anggota */
    public String getId() { return id; }

    /** @return Nama anggota */
    public String getNama() { return nama; }

    /** @return Nama divisi tempat anggota berada */
    public String getDivisi() { return divisi; }

    /** @return Nomor HP anggota */
    public String getNoHp() { return noHp; }
    
    /**
     * Mengubah nama anggota.
     * @param nama Nama baru
     */
    public void setNama(String nama) { this.nama = nama; }

    /**
     * Mengubah divisi anggota.
     * @param divisi Divisi baru
     */
    public void setDivisi(String divisi) { this.divisi = divisi; }

    /**
     * Mengubah nomor HP anggota.
     * @param noHp Nomor HP baru
     */
    public void setNoHp(String noHp) { this.noHp = noHp; }

    /**
     * Mengubah objek Anggota menjadi string untuk disimpan ke file.
     * Format: id,nama,divisi,noHp
     *
     * @return representasi string dari anggota
     */
    @Override
    public String toString() { return id + "," + nama + "," + divisi + "," + noHp; }

    /**
     * Mengubah string hasil penyimpanan menjadi objek Anggota kembali.
     *
     * @param s String dengan format: id,nama,divisi,noHp
     * @return Objek Anggota atau null jika format tidak valid
     */
    public static Anggota fromString(String s) {
        String[] p = s.split(",", -1);
        if (p.length < 4) return null;
        return new Anggota(p[0], p[1], p[2], p[3]);
    }
}
