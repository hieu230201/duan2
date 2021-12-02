package dao;

import model.SanPhamChiTiet;
import untils.Connectt;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class serviceSizeColor {
    List<SanPhamChiTiet> _lstSize;
    List<SanPhamChiTiet> _lstColor;
    Connectt con = new Connectt();
    public serviceSizeColor(){
        _lstSize = new ArrayList<>();
        _lstColor = new ArrayList<>();
    }

    public List<SanPhamChiTiet> get_lstSize() throws SQLException{
        String sql = "select distinct sizesp from chiTietSP";
        PreparedStatement pm = con.con().prepareStatement(sql);
        ResultSet rs = pm.executeQuery();
        _lstSize.clear();
        while (rs.next()){
            SanPhamChiTiet sp = new SanPhamChiTiet();
            sp.setSize(rs.getString(1));
            _lstSize.add(sp);
        }
        return _lstSize;
    }

    public List<SanPhamChiTiet> get_lstColor() throws SQLException{
        String sql = "select distinct color from chiTietSP";
        PreparedStatement pm = con.con().prepareStatement(sql);
        ResultSet rs = pm.executeQuery();
        _lstColor.clear();
        while (rs.next()){
            SanPhamChiTiet sp = new SanPhamChiTiet();
            sp.setColor(rs.getString(1));
            _lstColor.add(sp);
        }
        return _lstColor;
    }
}
