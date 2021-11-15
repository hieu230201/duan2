package views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class formNguonHang extends JFrame{
    private JPanel mainPanel;
    private JTabbedPane tabbedPane1;
    private JTextField txtHoTen;
    private JTextField txtDiaChi;
    private JTextField txtSDT;
    private JTable tblNguonHang;
    private JTextField txtTimKiem;
    private JButton btnLast;
    private JButton preButton;
    private JButton btnFirst;
    private JButton btnNext;
    private JButton btnThem;
    private JButton btnSua;
    private JButton btnXoa;
    private JButton btnMoi;
    DefaultTableModel _dtm;

    public formNguonHang(){
        this.setTitle("Nguồn Hàng");
        this.setContentPane(mainPanel);
        this.setSize(500,300);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(2);
        this.setResizable(false); // chống chỉnh sửa size frame
        this.setVisible(true);
        _dtm = (DefaultTableModel) tblNguonHang.getModel();
        _dtm.setColumnIdentifiers(new String []{
                 "Tên Nguồn Hàng", "Địa Chỉ", "Số Điện Thoại"
        });
        tblNguonHang.setModel(_dtm);
    }

    public static void main(String[] args) {
        new formNguonHang();
    }
}
