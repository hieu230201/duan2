package views;

import dao.Log;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;

public class formThongKe extends JFrame{
    private JPanel mainPanel;
    private JTabbedPane tabbedPane1;
    private JComboBox comboBox1;
    private JRadioButton theoBảngRadioButton;
    private JRadioButton theoBiểuĐồRadioButton;
    private JTable tblDoanhThu;
    private JTable tblDoanhSo;
    private JComboBox comboBox2;
    private JRadioButton theoBảngRadioButton1;
    private JRadioButton theoBiểuĐồRadioButton1;
    DefaultTableModel _dtm;
    DefaultTableModel _dtmDoanhSO;


    public formThongKe() throws IOException {
        Log log = new Log("hieupro.txt");
        this.setTitle("Cửa sổ thống kê");
        this.setContentPane(mainPanel);
        this.setVisible(true);
        this.setSize(700, 400);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(2);
        _dtm = (DefaultTableModel) tblDoanhThu.getModel();
        _dtmDoanhSO = (DefaultTableModel) tblDoanhSo.getModel();
        _dtm.setColumnIdentifiers(new String []{
                "Tháng", "Sản phẩm bán", "Tổng giá bán", "Tổng giá vốn", "Doanh thu"
        });
        _dtmDoanhSO.setColumnIdentifiers(new String []{
                "Mã sản phẩm", "Tên sản phẩm", "Số lượng bán"
        });
    }

    public static void main(String[] args) throws IOException {
        new formThongKe();
    }
}
