package model;

/**
 * Member account (terkait ke Anggota via idAnggota).
 */
public class Member extends Account {
    private String idAnggota;
    public Member(String username, String password, String name, String idAnggota) {
        super(username,password,name); this.idAnggota = idAnggota;
    }
