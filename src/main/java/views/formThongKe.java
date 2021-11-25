package views;

import dao.Log;
import dao.serviceThongKe;
import untils.Connectt;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;

public class formThongKe extends JFrame {
    private String user;
    private int role;
    private JPanel mainPanel;
    private JTabbedPane tabbedPane1;
    private JComboBox cbcNamDoanhThu;
    private JRadioButton rdbTheoBangDT;
    private JRadioButton rdbBieuĐoT;
    private JTable tblDoanhThu;
    private JTable tblDoanhSo;
    private JComboBox cbcNamDoanhSo;
    private JRadioButton rdbBangDS;
    private JRadioButton rdbBieuDoDS;
    DefaultTableModel _dtm;
    DefaultTableModel _dtmDoanhSO;
    serviceThongKe serviceThongKe = new serviceThongKe();
    public formThongKe() throws IOException, SQLException {

        this.setTitle("Cửa sổ thống kê");
        this.setContentPane(mainPanel);
        this.setVisible(true);
        this.setSize(700, 400);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(2);
        _dtm = (DefaultTableModel) tblDoanhThu.getModel();
        _dtmDoanhSO = (DefaultTableModel) tblDoanhSo.getModel();
        _dtm.setColumnIdentifiers(new String[]{
              "Tháng", "Số sản phẩm bán", "Tổng giá bán", "Số sản phẩm mua", "Tổng giá vốn", "Doanh thu"
        });
        _dtmDoanhSO.setColumnIdentifiers(new String[]{
                "Mã sản phẩm", "Tên sản phẩm", "Số lượng bán"
        });
        loadCBC();
        loadTBLDoanhThu();
        loadDoanhSo();

        // mở chương trình và lưu giá trị
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                luuText();
            }
        });

        //tắt chương trình quay lại form chính
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                formChinh formChinh = null;
                try {
                    formChinh = new formChinh();
                    formChinh.setUser(user);
                    formChinh.setRole(role);
                    dispose();
                } catch (IOException ex) {
                    try {
                        baoLoi(ex);
                    } catch (IOException exc) {
                        exc.printStackTrace();
                    }
                }

            }
        });

        cbcNamDoanhThu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    loadTBLDoanhThu();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            }
        });
        cbcNamDoanhSo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    loadDoanhSo();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private void loadTBLDoanhThu() throws SQLException {
        _dtm.setRowCount(0);
        int stt = 1;

        while (stt <= 12){
            String a [] = serviceThongKe.loadDoanhSo(Integer.parseInt(cbcNamDoanhThu.getSelectedItem().toString()), stt);
            int tong = ((a[1] == null) ? 0 : Integer.parseInt(a[1])) - ((a[3] == null) ? 0 : Integer.parseInt(a[3]));
                _dtm.addRow(new Object[]{
                         stt,a[0],a[1], a[2],a[3], tong
                });
            stt++;
        }


    }

    private void loadDoanhSo()throws SQLException{
        _dtmDoanhSO.setRowCount(0);
        serviceThongKe.loadSP(Integer.parseInt(cbcNamDoanhSo.getSelectedItem().toString()), _dtmDoanhSO);
    }



    private void loadCBC(){
        cbcNamDoanhSo.removeAllItems();
        cbcNamDoanhThu.removeAllItems();
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
            simpleDateFormat.applyPattern("yyyy");
            String format = simpleDateFormat.format(date);
        for (int i = Integer.parseInt(format); i >= 2010; i--) {
                cbcNamDoanhThu.addItem(i);
                cbcNamDoanhSo.addItem(i);
        }
    }




    // set quyền + báo lỗi form
    public void setUser(String user) {
        this.user = user;
    }

    public void setRole(int role) {
        this.role = role;
    }

    // báo lỗi ngoại lệ
    private void baoLoi(Exception ex) throws IOException {
        Log log = new Log("hieupro.txt");
        log.logger.setLevel(Level.WARNING);
        JOptionPane.showMessageDialog(null, "gặp lỗi rồi! Quay lại để gửi lỗi cho admin nha");
        log.logger.info(ex.getMessage());
        log.logger.warning("lỗi bên form khách hàng");

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String sStackTrace = sw.toString(); // stack trace as a string
        log.logger.severe(sStackTrace);
    }

    // đọc dữ liệu phân quyền lên form
    private void luuText() {
        System.out.println(user + " bên form thống kê");
    }


    public static void main(String[] args) throws IOException, SQLException {
        new formThongKe();
    }

}
