package views;

import dao.Log;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;

public class formHangHoa extends JFrame{
    private JPanel mainPanel;
    private JTextField textField1;
    private JButton thêmHàngHóaButton;
    private JPanel panelCha;
    private JPanel panelBang;
    private JPanel panelCapNhat;
    private JTable tblHangHoa;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JButton thêmHàngButton;
    DefaultTableModel _dtm;

    public formHangHoa() throws IOException {
        Log log = new Log("hieupro.txt");
        this.setTitle("Cửa sổ hàng hóa");
        this.setContentPane(mainPanel);
        this.setVisible(true);
        this.setSize(400, 300);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(2);
        panelCha.removeAll();
        panelCha.add(panelBang);
        panelCha.repaint();
        panelCha.revalidate();
        _dtm = (DefaultTableModel) tblHangHoa.getModel();
        _dtm.setColumnIdentifiers(new String []{
                "Mã sản phẩm", "Tên sản phẩm", "Số lượng", "Giá bán", "Giá vốn", "Giảm giá", "Size", "Màu sắc"
        });
    }

    public static void main(String[] args) throws IOException {
        new formHangHoa();
    }
}
