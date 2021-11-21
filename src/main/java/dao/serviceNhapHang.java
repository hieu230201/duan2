package dao;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import model.Nhaphang;
import untils.Connectt;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class serviceNhapHang {
    List<Nhaphang> _lst;
    Connectt con = new Connectt();

    public serviceNhapHang(){
        _lst = new ArrayList<>();
    }


    // khởi tạo 1 đơn hàng
    public void khoiTaoDonHang(String ma, int idNguonHang,String a, int giaTri) throws SQLException {
        System.out.println(ma + " " + idNguonHang + " " + a + " " + giaTri);
        String sql = "insert into hoaDonNhapHang values (?,?,?,?)";
        PreparedStatement pm = con.con().prepareStatement(sql);
        pm.setString(1, ma);
        pm.setInt(2, idNguonHang);
        pm.setDate(3, Date.valueOf(a));
        pm.setInt(4, giaTri);
        pm.executeUpdate();
    }

    // khởi tạo hóa đơn chi tiết
    public void khoiTaoChiTietDonHang(int idHoaDon, int idSP, int soLuong, int gia)throws SQLException{
        String sql = "insert into chiTietHoaDonNhap values (?,?,?,?)";
        PreparedStatement pm = con.con().prepareStatement(sql);
        pm.setInt(1, idHoaDon);
        pm.setInt(2, idSP);
        pm.setInt(3, soLuong);
        pm.setInt(4, gia);
        pm.executeUpdate();

    }



    // tìm id của 1 hóa đơn
    public int hoaDon(String ma, int idNguonHang,String a, int giaTri) throws SQLException {
        String sql = "select * from hoaDonNhapHang where manv = ? and idNguonHang = ? and giaTriDonHang = ? and ngayNhap = ?";
        PreparedStatement pm = con.con().prepareStatement(sql);
        pm.setString(1, ma);
        pm.setInt(2, idNguonHang);
        pm.setInt(3, giaTri);
        pm.setDate(4, Date.valueOf(a));
        ResultSet rs = pm.executeQuery();
        if(rs.next()){
            return rs.getInt(1);
        }
        return -1;
    }





    // xuất danh sách đơn hàng từ db ra

    public List<Nhaphang> get_lst() throws SQLException {
        String sql = "select * from hoaDonNhapHang";
        PreparedStatement pm = con.con().prepareStatement(sql);
        _lst.clear();
        ResultSet rs = pm.executeQuery();
        while (rs.next()){
            _lst.add(new Nhaphang(rs.getInt(1),rs.getString(2),rs.getInt(3), rs.getString(4), rs.getInt(5) ));
        }
        return _lst;
    }
}
