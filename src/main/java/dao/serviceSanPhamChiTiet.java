package dao;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import model.SanPham;
import model.SanPhamChiTiet;
import untils.Connectt;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class serviceSanPhamChiTiet {
    List<SanPhamChiTiet> _list;
    Connectt con = new Connectt();
    public serviceSanPhamChiTiet(){
        _list = new ArrayList<>();
    }

    // lấy hết dữ liệu từ db đổ vào list
    public List<SanPhamChiTiet> get_list() throws SQLException {
        String sql = "select * from chiTietSP join SanPham SP on SP.id = chiTietSP.id_sp join loaiSanPham lSP on lSP.id = SP.id_loaisp";
        PreparedStatement pm = con.con().prepareStatement(sql);
        ResultSet rs = pm.executeQuery();
        _list.clear();
        while (rs.next()) {
            _list.add(new SanPhamChiTiet(rs.getInt(14), rs.getString(15), rs.getInt(2),rs.getString(12), rs.getString(13), rs.getInt(1),rs.getString(3), rs.getString(4), rs.getInt(5),rs.getDouble(6), rs.getDouble(7), rs.getString(8), rs.getInt(9)));

        }
        return _list;
    }

    // thêm chi tiết cho sản phẩm
    public String addChiTiet(SanPhamChiTiet sp) throws SQLException {
        String sql = "INSERT INTO chiTietSP (id_sp, color, sizesp, soluong, giaban, giavon, ngaynhap,trangthai) VALUES (?,?,?,0,?,?,null,1)";

        PreparedStatement pm = con.con().prepareStatement(sql);
        pm.setInt(1, sp.getIdSP());
        pm.setString(2, sp.getColor());
        pm.setString(3, sp.getSize());
        pm.setInt(4, (int) sp.getGiaBan());
        pm.setInt(5, (int) sp.getGiaVon());


        String sql1 = "select * from chiTietSp where id_sp = ? and color = ? and sizesp = ?";
        PreparedStatement pm1 = con.con().prepareStatement(sql1);
        pm1.setInt(1, sp.getIdSP());
        pm1.setString(2, sp.getColor());
        pm1.setString(3, sp.getSize());
        ResultSet rs = pm1.executeQuery();
        if(rs.next()){
            return "đã tồn tại mặt hàng này";
        }
        if (pm.executeUpdate() > 0) {
            return "Thêm thành công";
        }
        return "Thêm không thành công";
    }

    // phương thức check trùng chi tiết sản phẩm

}
