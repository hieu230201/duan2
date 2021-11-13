package views;

import javax.swing.*;
import dao.serviceNhanVien;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class formDoiMatKhau extends JFrame{
    private String user;
    private JPasswordField txtPassOld;
    private JPasswordField txtPassNew;
    private JPasswordField txtConfirm;
    private JButton btnDoiPass;
    private JButton btnThoat;
    private JPanel mainPanel;
    private JLabel lblTitle;
    serviceNhanVien serviceNhanVienn = new serviceNhanVien();


    public formDoiMatKhau() {
        this.setTitle("Đổi Mật Khẩu");
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(2);
        this.setSize(400,300);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setResizable(false); // chống chỉnh sửa size frame

        run();  // chữ chạy

        // nút thoát chương trình
        btnThoat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        // nút đổi mật khẩu
        btnDoiPass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (loi()){
                    try {
                        JOptionPane.showMessageDialog(null, serviceNhanVienn.doiMatKhau(user, String.valueOf(txtPassNew.getPassword()), String.valueOf(txtPassOld.getPassword())));
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }


    // nút check lỗi form
    private boolean loi(){
        if(String.valueOf(txtPassNew.getPassword()).isBlank() || String.valueOf(txtPassNew.getPassword()).isEmpty()){
            JOptionPane.showMessageDialog(null, "không được để trống mật khẩu mới");
            return false;
        }

        if(String.valueOf(txtPassOld.getPassword()).isBlank() || String.valueOf(txtPassOld.getPassword()).isEmpty()){
            JOptionPane.showMessageDialog(null, "không được để trống mật khẩu cũ");
            return false;
        }
        if(String.valueOf(txtConfirm.getPassword()).isBlank() || String.valueOf(txtConfirm.getPassword()).isEmpty()){
            JOptionPane.showMessageDialog(null, "vui lòng xác nhận mã");
            return false;
        }
        if(!String.valueOf(txtConfirm.getPassword()).equals(String.valueOf(txtPassNew.getPassword()))){
            JOptionPane.showMessageDialog(null, "xác nhận lại mật khẩu");
            txtConfirm.setText("");
            return false;
        }
        return  true;
    }


    // thread chữ chạy
    private void run() {
        Thread thread = new Thread() {
            @Override
            public void run() {

                while (true) {
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    lblTitle.setText(lblTitle.getText().charAt(lblTitle.getText().length() - 1) + lblTitle.getText().substring(0, lblTitle.getText().length() - 1));
                }
            }
        };
        thread.start();
    }

    public void setUser(String user) {
        this.user = user;
    }

    public static void main(String[] args) {
        new formDoiMatKhau();
    }
}
