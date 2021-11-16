package views;

import dao.Log;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;

public class formSanPham extends JFrame{
    private JPanel mainPanel;
    private JTabbedPane tabbedPane1;
    private JComboBox cbcLoaiSP;
    private JButton btnThemLoai;
    private JButton btnSuaLoai;
    private JTextField txtTenSP;
    private JTextArea txtMoTa;
    private JButton btnThemSP;
    private JButton btnSuaSP;
    private JButton btnNew;
    private JTable tblSanPham;
    private JTextField txtTimKiem;
    DefaultTableModel _dtm;

    public  formSanPham() throws IOException {
        Log log = new Log("hieupro.txt");
        this.setTitle("Quản lí sản phẩm");
        this.setContentPane(mainPanel);
        this.setSize(600,450);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(2);
        this.setResizable(false); // chống chỉnh sửa size frame
        this.setVisible(true);
        _dtm = (DefaultTableModel) tblSanPham.getModel();
        _dtm.setColumnIdentifiers(new String []{
                "ID Sản Phẩm", "Tên Sản Phẩm", "Loại Sản Phẩm", "Mô Tả"
        });
        tblSanPham.setModel(_dtm);
    }

    public static void main(String[] args) throws IOException {
        new formSanPham();
    }
}
