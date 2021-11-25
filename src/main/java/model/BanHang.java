package model;

public class BanHang {
    private int idHoaDon;
    private String manv;
    private int idKH;
    private String ngayBan;
    private int giaTriDon, tienKhachDua, tienThua, giamGia;

    public BanHang(int idHoaDon, String manv, int idKH, String ngayBan, int giaTriDon, int tienKhachDua, int tienThua, int giamGia) {
        this.idHoaDon = idHoaDon;
        this.manv = manv;
        this.idKH = idKH;
        this.ngayBan = ngayBan;
        this.giaTriDon = giaTriDon;
        this.tienKhachDua = tienKhachDua;
        this.tienThua = tienThua;
        this.giamGia = giamGia;
    }

    public BanHang() {
    }

    public int getIdHoaDon() {
        return idHoaDon;
    }

    public void setIdHoaDon(int idHoaDon) {
        this.idHoaDon = idHoaDon;
    }

    public String getManv() {
        return manv;
    }

    public void setManv(String manv) {
        this.manv = manv;
    }

    public int getIdKH() {
        return idKH;
    }

    public void setIdKH(int idKH) {
        this.idKH = idKH;
    }

    public String getNgayBan() {
        return ngayBan;
    }

    public void setNgayBan(String ngayBan) {
        this.ngayBan = ngayBan;
    }

    public int getGiaTriDon() {
        return giaTriDon;
    }

    public void setGiaTriDon(int giaTriDon) {
        this.giaTriDon = giaTriDon;
    }

    public int getTienKhachDua() {
        return tienKhachDua;
    }

    public void setTienKhachDua(int tienKhachDua) {
        this.tienKhachDua = tienKhachDua;
    }

    public int getTienThua() {
        return tienThua;
    }

    public void setTienThua(int tienThua) {
        this.tienThua = tienThua;
    }

    public int getGiamGia() {
        return giamGia;
    }

    public void setGiamGia(int giamGia) {
        this.giamGia = giamGia;
    }
}
