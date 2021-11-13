package views;

import javax.swing.*;

public class formNhapHang extends JFrame{
    private JPanel mainPanel;
    private JTabbedPane tabbedPane1;
    private JTextField txtTimKiem;
    private JTable tblHangHoa;
    public formNhapHang(){
        this.setTitle("Cửa sổ hàng hóa");
        this.setContentPane(mainPanel);
        this.setVisible(true);
        this.setSize(400, 300);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(2);
    }

    public static void main(String[] args) {
        new formNhapHang();
    }
}
