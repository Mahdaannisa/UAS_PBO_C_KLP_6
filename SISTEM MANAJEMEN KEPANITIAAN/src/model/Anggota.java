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
