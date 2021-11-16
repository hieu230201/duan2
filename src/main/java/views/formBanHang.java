package views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class formBanHang extends JFrame{
    private JPanel mainPanel;
    private JTabbedPane tabbedPane1;
    private JTextField txtTimKiemHoaDon;
    private JTable tblHoaDon;
    private JTextField txtTimKiemSanPham;
    private JTable tblHangHoa;
    private JTextField txtSoluong;
    private JTextField txtMaGiam;
    private JTextField txtSDT;
    private JTextField txtTenKH;
    private JTable tblChiTiet;
    private JTextField txtTienKhachDua;
    private JButton btnXacNhan;
    private JLabel lblThuaTien;
    DefaultTableModel _dtm;
    DefaultTableModel _dtmHoaDon;
    DefaultTableModel _dtmBanHang;

    public formBanHang(){
        this.setTitle("Cửa sổ bán hàng");
        this.setContentPane(mainPanel);
        this.setVisible(true);
        this.setSize(700, 400);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(2);
        _dtm = (DefaultTableModel) tblHangHoa.getModel();
        _dtmHoaDon = (DefaultTableModel) tblHoaDon.getModel();
        _dtmBanHang = (DefaultTableModel) tblChiTiet.getModel();
        _dtm.setColumnIdentifiers(new String []{
                "Mã sản phẩm", "Tên sản phẩm", "Loại", "Size", "Màu sắc", "Số lượng"
        });
        _dtmHoaDon.setColumnIdentifiers(new String []{
                "Mã hóa đơn", "Tên khách hàng", "Nhân viên", "Ngày", "Tổng tiền"
        });
        _dtmBanHang.setColumnIdentifiers(new String []{
                "Mã sản phẩm", "Tên sản phẩm", "Loại", "Đơn giá", "Giá giảm","Size", "Màu sắc", "Số lượng bán"
        });
    }

    public static void main(String[] args) {
        new formBanHang();
    }
}
