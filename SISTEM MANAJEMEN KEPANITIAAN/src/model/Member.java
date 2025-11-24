package model;

/**
 * Member account (terkait ke Anggota via idAnggota).
 */
public class Member extends Account {
    private String idAnggota;
    public Member(String username, String password, String name, String idAnggota) {
        super(username,password,name); this.idAnggota = idAnggota;
    }
    public String getIdAnggota() { return idAnggota; }
    public void setIdAnggota(String id) { this.idAnggota = id; }
    @Override public String getRole() { return "MEMBER"; }
}
