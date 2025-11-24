package model;

/**
 * Kelas abstrak yang merepresentasikan sebuah akun dalam sistem.
 * Kelas ini menyimpan informasi dasar seperti username, password, dan nama,
 * serta menyediakan metode untuk melakukan pengecekan autentikasi.
 */
public abstract class Account {
    private String username;
    private String password;
    private String name;

    /**
     * Membuat objek Account baru dengan username, password, dan nama yang diberikan.
     *
     * @param username username akun
     * @param password password akun
     * @param name nama pemilik akun
     */
    public Account(String username, String password, String name) {
        this.username = username; 
        this.password = password; 
        this.name = name;
    }

    /**
     * Mengambil username akun.
     *
     * @return username
     */
    public String getUsername() { return username; }

    /**
     * Mengambil password akun.
     *
     * @return password
     */
    public String getPassword() { return password; }

    /**
     * Mengambil nama pemilik akun.
     *
     * @return nama pemilik akun
     */
    public String getName() { return name; }

    /**
     * Mengubah password akun.
     *
     * @param p password baru
     */
    public void setPassword(String p) { this.password = p; }

    /**
     * Mengubah nama pemilik akun.
     *
     * @param n nama baru
     */
    public void setName(String n) { this.name = n; }

    /**
     * Mengecek apakah password yang diberikan sesuai dengan password akun.
     *
     * @param p password yang ingin dicek
     * @return true jika password cocok, false jika tidak
     */
    public boolean checkPassword(String p) { 
        return password != null && password.equals(p); 
    }

    /**
     * Mengembalikan peran (role) dari akun.
     * Metode ini harus diimplementasikan oleh kelas turunan.
     *
     * @return peran akun
     */
    public abstract String getRole();
}

