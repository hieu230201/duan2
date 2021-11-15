package views;


import dao.LoginService;
import model.NhanVien;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class loginFormm extends JFrame{
    private JPanel mainPanel;
    private JTextField txtTaiKhoan;
    private JPasswordField txtPass;
    private JCheckBox ckcRemember;
    private JButton btnLogin;
    private JButton btnThoat;
    private JLabel lblQuenPass;
    NhanVien nv;
    int dem = 0;
    LoginService loginService = new LoginService();
    public loginFormm() throws SQLException {
        this.setTitle("Đăng Nhập");
        this.setContentPane(mainPanel);
        this.setLocationRelativeTo(null);
        this.setSize(700,500);
        this.setVisible(true);
        this.setDefaultCloseOperation(2);


        if (loginService.acconutDaLuu()[0] == null) {
            txtTaiKhoan.setText("");
            txtPass.setText("");
        } else {
            txtTaiKhoan.setText(loginService.acconutDaLuu()[0]);
            txtPass.setText(loginService.acconutDaLuu()[1]);
        }
        this.setResizable(false); // chống chỉnh sửa size frame


        // nút thoát chương trình
        btnThoat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // nút đăng nhập
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(loi()){
                    try {

                        nv = loginService.login(txtTaiKhoan.getText(), String.valueOf(txtPass.getPassword()));
                       if(nv == null){
                           dem++;
                       }

                        if (dem >= 5) {
                            int i = JOptionPane.showConfirmDialog(null, "Bạn có phải là " + txtTaiKhoan.getText() + " không?", "Thông Báo", JOptionPane.YES_NO_OPTION);

                            if (i == JOptionPane.YES_OPTION) {
                                QuenMatKhauu quenMatKhauu = new QuenMatKhauu();
                                dispose();
                            }
                        }

                       if(ckcRemember.isSelected()){
                           loginService.remember(txtTaiKhoan.getText(), String.valueOf(txtPass.getText()));
                       }else{
                           loginService.remember(null, null);
                       }


                       JOptionPane.showMessageDialog(null, "Chào mừng " + nv.getManv() + " đã đến với chương trình");
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        // mouse click để qua form quên mật khẩu
        lblQuenPass.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                QuenMatKhauu quenMatKhauu = new QuenMatKhauu();
                dispose();
            }
        });
    }



    // phương thức check lỗi
    private boolean loi(){
        if(txtTaiKhoan.getText().isEmpty() || txtTaiKhoan.getText().isBlank()){
            JOptionPane.showMessageDialog(null, "Tài khoản không được để trống", "Lỗi", 1);
            return false;
        }
        if(txtPass.getText().isEmpty() || txtPass.getText().isBlank()){
            JOptionPane.showMessageDialog(null, "Mật khẩu không được để trống", "Lỗi", 1);
            return false;
        }
        return true;
    }



    // main chạy chương trình
    public static void main(String[] args) throws SQLException {
        new loginFormm();
    }
}
