package views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class formNhapHang extends JFrame{
    private JPanel mainPanel;
    private JTabbedPane tabbedPane1;
    private JTextField txtTimKiem;
    private JTable tblHangHoa;
    private JTextField textField1;
    private JComboBox comboBox1;
    private JTextField textField2;
    private JComboBox comboBox2;
    private JButton xácNhậnButton;
    private JTextField textField3;
    private JTable tblHoaDonNhap;
    DefaultTableModel _dtm;
    DefaultTableModel _dtmHoaDon;

    public formNhapHang(){
        this.setTitle("Cửa sổ nhập hàng");
        this.setContentPane(mainPanel);
        this.setVisible(true);
        this.setSize(700, 400);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(2);
        _dtm = (DefaultTableModel) tblHangHoa.getModel();
        _dtmHoaDon = (DefaultTableModel) tblHoaDonNhap.getModel();
        _dtm.setColumnIdentifiers(new String []{
                "Mã sản phẩm", "Tên sản phẩm", "Loại", "Size", "Màu sắc", "Số lượng"
        });
        _dtmHoaDon.setColumnIdentifiers(new String []{
                "Mã hóa đơn", "Người nhập", "Nguồn hàng", "Ngày", "Tổng tiền"
        });
    }

    public static void main(String[] args) {
        new formNhapHang();
    }
}
