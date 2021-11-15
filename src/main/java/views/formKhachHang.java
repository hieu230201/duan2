package views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class formKhachHang extends JFrame{
    private JPanel mainPanel;
    private JTabbedPane tabbedPane1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JButton thêmButton;
    private JButton sửaButton;
    private JButton xóaButton;
    private JButton mớiButton;
    private JTable tblKhachHang;
    DefaultTableModel _dtm;

    public formKhachHang(){
        this.setTitle("Khách hàng thân thiết");
        this.setContentPane(mainPanel);
        this.setSize(500,300);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(2);
        this.setResizable(false); // chống chỉnh sửa size frame
        this.setVisible(true);
        _dtm = (DefaultTableModel) tblKhachHang.getModel();
        _dtm.setColumnIdentifiers(new String []{
                "Họ tên", "Địa Chỉ", "Số Điện Thoại"
        });
        tblKhachHang.setModel(_dtm);
    }

    public static void main(String[] args) {
        new formKhachHang();
    }
}
