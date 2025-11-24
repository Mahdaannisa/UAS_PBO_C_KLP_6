# PROJECT-GROUP 6

**UAS PRAKTIKUM PEMROGRAMAN BERORIENTASI OBJEK**

ANGGOTA KELOMPOK 6:
1. MAHDA ANNISA (2408107010036)
2. SILVIA PUTRI (2408107010086)
3. SAYED ZAKI AQRAM (2408107010087)
4. ZANNA ZIKRAANA (2408107010094)
5. ARSHA ALIFA MAHMUD (2408107010095)
6. MIRDHA AULIA ZAHRA (2408107010115)

# SISTEM MANAJEMEN KEPANITIAAN
## 1️⃣ Deskripsi Sistem
Sistem Manajemen Kepanitiaan ini dibuat untuk membantu pengelolaan panitia acara menggunakan konsep **Pemrograman Berorientasi Objek (PBO)** dengan antarmuka **CLI (Command Line Interface)**. Sistem Manajemen Kepanitiaan ini yang digunakan untuk mengelola divisi, tugas, anggota, serta aktivitas dalam kepanitiaan.  
Terdapat dua jenis pengguna, yaitu **Admin** dan **Member**, yang masing-masing memiliki peran dan akses berbeda.

Sistem menyediakan **dua role utama**:
### 👤 Admin
- Login sebagai admin  
- CRUD Divisi  
- CRUD Tugas  
- Melihat seluruh tugas & divisi  
- Mengelola data anggota  
- Melihat aktivitas member  

### 👥 Member
- Login sebagai member  
- Melihat daftar divisi dan tugas  
- Mengerjakan / update status tugas (Pending → Done)  
- Melihat Riwayat Tugas yang sudah dikerjakan  
- Melihat informasi divisi tempat ia tergabung  

Sistem juga sudah menerapkan:

- ✔ *Encapsulation* (getter & setter)  
- ✔ *Inheritance* (User → Admin & Member)  
- ✔ *Polymorphism* (method overriding)  
- ✔ *Abstract class / Interface*  
- ✔ *Collection* (ArrayList)  
- ✔ *Exception Handling*  
- ✔ *Javadoc setiap class dan method*  
- ✔ *Modularisasi package lengkap*  

---

## 2️⃣ Cara Menjalankan Program 

### 1) Visual Studio Code

#### ✔ A. Buka folder project  
Pastikan berada di folder utama project hasil extract ZIP.

#### ✔ B. Masuk ke folder `src`
Program utama berada di file **App.java**.
cd src

#### ✔ C. Compile program
javac App.java

#### ✔ D. Jalankan program
java App

