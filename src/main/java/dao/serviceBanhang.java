package dao;

import model.BanHang;
import model.Nhaphang;
import untils.Connectt;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class serviceBanhang {
    List<BanHang> _lst;
    Connectt con = new Connectt();
    public serviceBanhang(){
        _lst = new ArrayList<>();
    }

    // tạo hóa đơn bán hàng
    public void inHoaDon(String manv, int idKhach,String date, int giaTriDon, int tienKhach, int tienThua, int giamGia) throws SQLException{
        String sql = "insert into hoaDonBanHang values (?,?,?,?,?,?, ?)";
        PreparedStatement pm = con.con().prepareStatement(sql);
        pm.setString(1, manv);
        pm.setInt(2, idKhach);
        pm.setDate(3, Date.valueOf(date));
        pm.setInt(4,giaTriDon);
        pm.setInt(5,tienKhach);
        pm.setInt(6, tienThua);
        pm.setInt(7,giamGia);
        pm.executeUpdate();
    }


    // tìm id hóa đơn bán hàng
    public int timHoaDon(String manv, int idKhach,String date, int giaTriDon, int tienKhach, int tienThua, int giamGia) throws SQLException{
        String sql = "select idHoaDonBanHang from hoaDonBanHang where manv = ? and idKhachHang = ? and ngayBan = ? and giaTriDonHang = ? and tienKhachDua = ? and tienThua = ? and giaGiam = ?";
        PreparedStatement pm = con.con().prepareStatement(sql);
        pm.setString(1, manv);
        pm.setInt(2, idKhach);
        pm.setDate(3, Date.valueOf(date));
        pm.setInt(4,giaTriDon);
        pm.setInt(5,tienKhach);
        pm.setInt(6, tienThua);
        pm.setInt(7,giamGia);
        ResultSet rs = pm.executeQuery();
        if(rs.next()){
            return rs.getInt(1);
        }
        return -1;
    }

    // khởi tạo chi tiết đơn hàng
    public void taoChiTietDonHang(int idHoaDon, int idChiTietSp, int soLuong, int giaSanPham) throws SQLException{
        String sql = "insert into chiTietHoaDonBan values (?,?,?,?, ?)";
        PreparedStatement pm = con.con().prepareStatement(sql);
        pm.setInt(1, idHoaDon);
        pm.setInt(2, idChiTietSp);
        pm.setInt(3, soLuong);
        pm.setInt(4, giaSanPham);
        pm.setDate(5, Date.valueOf(String.valueOf(LocalDate.now())));
        pm.executeUpdate();
    }









    // lấy danh sách hóa đơn từ db đổi vào list
    public List<BanHang> get_lst() throws SQLException {
        String sql = "select * from hoaDonBanHang";
        PreparedStatement pm = con.con().prepareStatement(sql);
        _lst.clear();
        ResultSet rs = pm.executeQuery();
        while (rs.next()){
            _lst.add(new BanHang(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getInt(5),
                    rs.getInt(6), rs.getInt(7), rs.getInt(8) ));
        }
        return _lst;
    }


}
