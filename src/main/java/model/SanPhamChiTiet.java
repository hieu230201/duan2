package model;

public class SanPhamChiTiet extends SanPham {
    private int idChiTiet;
    private String color, size;
    private int soLuong;
    private double giaBan, giaVon;
    private String ngayNhap;
    private int trangThai;
    private String hinh;

    public SanPhamChiTiet(int id, String ten, int id1, String name, String moTa, int idChiTiet, String color, String size, int soLuong, double giaBan, double giaVon, String ngayNhap, int trangThai, String hinh) {
        super(id, ten, id1, name, moTa);
        this.idChiTiet = idChiTiet;
        this.color = color;
        this.size = size;
        this.soLuong = soLuong;
        this.giaBan = giaBan;
        this.giaVon = giaVon;
        this.ngayNhap = ngayNhap;
        this.trangThai = trangThai;
        this.hinh = hinh;
    }

    public SanPhamChiTiet(int idSP, String name, int idChiTiet, String color, String size, int soLuong, double giaBan, double giaVon, String ngayNhap, int trangThai, String hinh) {
        super(idSP, name);
        this.idChiTiet = idChiTiet;
        this.color = color;
        this.size = size;
        this.soLuong = soLuong;
        this.giaBan = giaBan;
        this.giaVon = giaVon;
        this.ngayNhap = ngayNhap;
        this.trangThai = trangThai;
        this.hinh = hinh;
    }

    public SanPhamChiTiet() {
    }

    public int getIdChiTiet() {
        return idChiTiet;
    }

    public void setIdChiTiet(int idChiTiet) {
        this.idChiTiet = idChiTiet;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(double giaBan) {
        this.giaBan = giaBan;
    }

    public double getGiaVon() {
        return giaVon;
    }

    public void setGiaVon(double giaVon) {
        this.giaVon = giaVon;
    }

    public String getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(String ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }
}