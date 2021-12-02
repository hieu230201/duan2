package views;

import dao.serviceBanhang;
import dao.serviceSanPhamChiTiet;
import model.SanPhamChiTiet;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Locale;

public class formDoiHang extends JFrame{
    private JPanel mainPanel;
    private JTabbedPane tabbedPane1;
    private JTextField txtMa;
    private JButton btnFindHoaDon;
    private JTable tblHoaDonCu;
    private JLabel lblTong;
    private JButton btnHoaDon;
    private JTable tblSanPham;
    private JTable tblhoaDonTraHang;
    private JTable tblChiTietDonHang;
    private JTable tblHoaDonMoi;
    private JLabel lblTien;
    DefaultTableModel _dtmHoaDonCu;
    DefaultTableModel _dtmHoaDonMoi;
    DefaultTableModel _dtmSP;
    serviceSanPhamChiTiet serviceSanPhamChiTiet = new serviceSanPhamChiTiet();
    dao.serviceBanhang serviceBanhang = new serviceBanhang();

    public formDoiHang() throws SQLException {
        this.setTitle("Đổi Hàng");
        this.setContentPane(mainPanel);
        this.setSize(1400, 800);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(2);
        this.setResizable(false); // chống chỉnh sửa size frame
        this.setVisible(true);
        _dtmHoaDonCu = (DefaultTableModel) tblHoaDonCu.getModel();
        _dtmHoaDonMoi = (DefaultTableModel) tblHoaDonMoi.getModel();
        _dtmSP = (DefaultTableModel) tblSanPham.getModel();
        _dtmSP.setColumnIdentifiers(new String[]{
                "Mã sản phẩm", "Tên sản phẩm", "Loại", "Size", "Màu sắc", "Số lượng còn lại", "Giá Bán"
        });
        _dtmHoaDonCu.setColumnIdentifiers(new String []{
                "Mã hóa đơn","Mã Nhân Viên", "Tên Khách Hàng", "Tên sản phẩm", "Size", "Màu sắc", "Số lượng bán", "Đơn giá"
        });
        _dtmHoaDonMoi.setColumnIdentifiers(new String []{
                "Mã hóa đơn","Mã Nhân Viên", "Tên Khách Hàng", "Tên sản phẩm", "Size", "Màu sắc", "Số lượng bán", "Đơn giá"
        });
        loadTblSP();


        btnFindHoaDon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    int maHoaDon = Integer.parseInt(txtMa.getText());
                try {
                    serviceBanhang.inChiTietDon(maHoaDon, _dtmHoaDonCu);
                    serviceBanhang.inChiTietDon(maHoaDon, _dtmHoaDonMoi);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        tblHoaDonMoi.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String maSP = JOptionPane.showInputDialog(null, "Mời nhập mã sản phẩm muốn đổi");

            }
        });
    }

    // đổ dữ liệu vào tbl nhập hàng
    private void loadTblSP() throws SQLException {
        _dtmSP = (DefaultTableModel) tblSanPham.getModel();
        if (_dtmSP.getRowCount() > 0) {
            _dtmSP.setRowCount(0);
        }

        for (SanPhamChiTiet a : serviceSanPhamChiTiet.get_list()
        ) {
            _dtmSP  .addRow(new Object[]{
                    a.getIdChiTiet(), a.getName(), a.getTen(), a.getSize(), a.getColor(), a.getSoLuong(), toCurrency((int) a.getGiaBan())
            });
        }


    }

    // quy đổi tiền
    public static String toCurrency(long tienTe){
        Locale lc = new Locale("vi","VN");
        NumberFormat nf = NumberFormat.getCurrencyInstance(lc);
        return  nf.format(tienTe);
    }
    // quy đổi tiền về long
    public static long soNguyen(String tien){
        tien = tien.substring(0, tien.length() - 2).trim();
        tien = tien.replace(".", "");
        System.out.println(tien);
        return Long.parseLong(tien);
    }




    public static void main(String[] args) throws SQLException {
        new formDoiHang();
    }
}
