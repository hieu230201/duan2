package model;

public class Trahang {
    private int idHoaDOn;
    private String manv;
    private int idKhachHang;
    private String ngayTra;
    private long giaTriDon;

    public Trahang(int idHoaDOn, String manv, int idKhachHang, String ngayTra, long giaTriDon) {
        this.idHoaDOn = idHoaDOn;
        this.manv = manv;
        this.idKhachHang = idKhachHang;
        this.ngayTra = ngayTra;
        this.giaTriDon = giaTriDon;
    }

    public Trahang() {
    }

    public int getIdHoaDOn() {
        return idHoaDOn;
    }

    public void setIdHoaDOn(int idHoaDOn) {
        this.idHoaDOn = idHoaDOn;
    }

    public String getManv() {
        return manv;
    }

    public void setManv(String manv) {
        this.manv = manv;
    }

    public int getIdKhachHang() {
        return idKhachHang;
    }

    public void setIdKhachHang(int idKhachHang) {
        this.idKhachHang = idKhachHang;
    }

    public String getNgayTra() {
        return ngayTra;
    }

    public void setNgayTra(String ngayTra) {
        this.ngayTra = ngayTra;
    }

    public long getGiaTriDon() {
        return giaTriDon;
    }

    public void setGiaTriDon(long giaTriDon) {
        this.giaTriDon = giaTriDon;
    }
}
