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
