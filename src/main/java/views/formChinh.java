package views;

import javax.swing.*;

public class formChinh extends JFrame{
    private JPanel mainForm;
    private JMenu mnuHeThong;
    private JMenuItem mniDangXuat;
    private JMenuItem mniDoiPass;
    private JMenuItem mniKetThuc;
    private JMenu mnuQuanLi;
    private JMenuItem mniNhanVien;
    private JMenuItem mniNguonHang;
    private JMenuItem mniLoaiHang;
    private JMenuItem mniSanPham;
    private JMenuItem mniNhapHang;
    private JMenuItem mniThongKe;
    private JButton đăngXuấtButton;
    private JLabel lblTime;
    private JMenuItem mniXuatHang;

    public formChinh(){
        this.setTitle("Cửa sổ chính");
        this.setContentPane(mainForm);
        pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(2);
        this.setResizable(false); // chống chỉnh sửa size frame
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new formChinh();
    }
}
