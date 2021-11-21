package model;

public class Nhaphang {
    private int idNhapHang;
    private String maNhanVien;
    private int idNguonHang;
    private String ngayNhap;
    private int giaNhap;

    public Nhaphang() {
    }

    public Nhaphang(int idNhapHang, String maNhanVien, int idNguonHang, String ngayNhap, int giaNhap) {
        this.idNhapHang = idNhapHang;
        this.maNhanVien = maNhanVien;
        this.idNguonHang = idNguonHang;
        this.ngayNhap = ngayNhap;
        this.giaNhap = giaNhap;
    }

    public int getIdNhapHang() {
        return idNhapHang;
    }

    public void setIdNhapHang(int idNhapHang) {
        this.idNhapHang = idNhapHang;
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public int getIdNguonHang() {
        return idNguonHang;
    }

    public void setIdNguonHang(int idNguonHang) {
        this.idNguonHang = idNguonHang;
    }

    public String getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(String ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    public int getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(int giaNhap) {
        this.giaNhap = giaNhap;
    }
}
