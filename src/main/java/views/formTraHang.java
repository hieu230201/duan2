package views;

import dao.Log;
import dao.RendererHighlighted;
import dao.serviceBanhang;
import dao.serviceKhachHang;
import model.BanHang;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;

public class formTraHang extends JFrame {
    private String user;
    private int role;
    private JPanel mainPanel;
    private JTextField txtTimKiem;
    private JComboBox cbcNam;
    private JComboBox cbcThang;
    private JTable tblHoaDon;
    private JTextField txtSDT;
    private JTextField txtName;
    private JCheckBox ckcRemember;
    private JTable tblChiTiet;
    private JButton btnFindHoaDon;
    private JTabbedPane tabbedPane1;
    private JTable table1;
    private JTable table2;
    DefaultTableModel _dtmHoaDon;
    DefaultTableModel _dtmChiTiet;
    serviceBanhang serviceBanhang = new serviceBanhang();
    serviceKhachHang serviceKhachHang = new serviceKhachHang();
    public formTraHang(){
        this.setTitle("Cửa sổ trả hàng");
        this.setContentPane(mainPanel);
        this.setSize(1400, 800);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(2);
        this.setResizable(false); // chống chỉnh sửa size frame
        this.setVisible(true);
        loadCBC();
        _dtmHoaDon = (DefaultTableModel) tblHoaDon.getModel();
        _dtmChiTiet = (DefaultTableModel) tblChiTiet.getModel();

        _dtmHoaDon.setColumnIdentifiers(new String[]{
                "Mã hóa đơn", "Nhân viên" , "Tên khách hàng", "Ngày", "Tổng tiền", "Tiền Khách Đưa", "Tiền Thừa", "Giảm giá"
        });

        _dtmChiTiet.setColumnIdentifiers(new String []{
                "Mã hóa đơn" , "Mã nhân viên", "Tên khách hàng", "Tên Sản Phẩm", "Size", "Màu sắc", "Số lượng bán", "Đơn giá"
        });

        RendererHighlighted renderer = new RendererHighlighted(txtTimKiem);
        tblHoaDon.setDefaultEditor(Object.class, null);
        TableRowSorter<TableModel> rowSorter
                = new TableRowSorter<>(tblHoaDon.getModel());
        tblHoaDon.setRowSorter(rowSorter);
        tblHoaDon.setDefaultRenderer(Object.class, renderer);

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
                } catch (IOException ex) {
                    try {
                        baoLoi(ex);
                    } catch (IOException exc) {
                        exc.printStackTrace();
                    }
                }
                formChinh.setUser(user);
                formChinh.setRole(role);
                dispose();
            }
        });


        // nút tìm kiếm sản phẩm
        txtTimKiem.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = txtTimKiem.getText();
                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = txtTimKiem.getText();
                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });

        btnFindHoaDon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(ckcRemember.isSelected()){
                    try {
                        loadtblHoaDon();
                    } catch (SQLException ex) {
                        try {
                            baoLoi(ex);
                        } catch (IOException exc) {
                            exc.printStackTrace();
                        }
                    }
                }else {
                    try {
                        loadtblHoaDonKhongTime();
                    } catch (SQLException ex) {
                        try {
                            baoLoi(ex);
                        } catch (IOException exc) {
                            exc.printStackTrace();
                        }
                    }
                }
            }
        });
        tblHoaDon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int i = tblHoaDon.getSelectedRow();
                try {
                    serviceBanhang.inChiTietDon(Integer.parseInt(String.valueOf(tblHoaDon.getValueAt(i,0))), _dtmChiTiet);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    // quy đổi tiền
    public static String toCurrency(long tienTe){
        Locale lc = new Locale("vi","VN");
        NumberFormat nf = NumberFormat.getCurrencyInstance(lc);
        return  nf.format(tienTe);
    }

    // quy đổi tiền về long
    public static long soNguyen(String tien){
        tien = tien.substring(0, tien.length() - 2).trim();
        tien = tien.replace(".", "");
        System.out.println(tien);
        return Long.parseLong(tien);
    }



    // đổ dữ liệu vào tbl hóa đơn
    private void loadtblHoaDon() throws SQLException {
        _dtmHoaDon = (DefaultTableModel) tblHoaDon.getModel();
        if(_dtmHoaDon.getRowCount() > 0){
            _dtmHoaDon.setRowCount(0);
        }
        for (BanHang a : serviceBanhang.getList(Integer.parseInt(cbcNam.getSelectedItem().toString()), Integer.parseInt(cbcThang.getSelectedItem().toString()), txtName.getText(), txtSDT.getText())) {
            _dtmHoaDon.addRow(new Object[]{
                    a.getIdHoaDon(), a.getManv(), serviceKhachHang.timTenTheoID(a.getIdKH()), a.getNgayBan(), toCurrency(a.getGiaTriDon()), toCurrency(a.getTienKhachDua()), toCurrency(a.getTienThua()), toCurrency(a.getGiamGia())
            });
        }
    }

    // đổ dữ liệu vào tbl hóa đơn
    private void loadtblHoaDonKhongTime() throws SQLException {
        _dtmHoaDon = (DefaultTableModel) tblHoaDon.getModel();
        if(_dtmHoaDon.getRowCount() > 0){
            _dtmHoaDon.setRowCount(0);
        }
        for (BanHang a : serviceBanhang.getListKhongTime(txtName.getText(), txtSDT.getText())) {
            _dtmHoaDon.addRow(new Object[]{
                    a.getIdHoaDon(), a.getManv(), serviceKhachHang.timTenTheoID(a.getIdKH()), a.getNgayBan(), toCurrency(a.getGiaTriDon()), toCurrency(a.getTienKhachDua()), toCurrency(a.getTienThua()), toCurrency(a.getGiamGia())
            });
        }
    }

    private void loadCBC(){
        cbcNam.removeAllItems();
        cbcThang.removeAllItems();
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        simpleDateFormat.applyPattern("yyyy");
        String format = simpleDateFormat.format(date);
        for (int i = Integer.parseInt(format); i >= 2010; i--) {
            cbcNam.addItem(i);
        }
        for (int i = 1; i <= 12 ; i++) {
            cbcThang.addItem(i);
        }
    }







    // Phương thức set giá trị cho 2 biến phân quyền
    public void setUser(String user) {
        this.user = user;
    }

    public void setRole(int role) {
        this.role = role;
    }

    private void baoLoi(Exception ex) throws IOException {
        Log log = new Log("hieupro.txt");
        log.logger.setLevel(Level.WARNING);
        JOptionPane.showMessageDialog(null, "gặp lỗi rồi! Quay lại để gửi lỗi cho admin nha");
        log.logger.info(ex.getMessage());
        log.logger.warning("lỗi bên form nhân viên");

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String sStackTrace = sw.toString(); // stack trace as a string
        log.logger.severe(sStackTrace);
    }

    // đọc dữ liệu phân quyền lên form
    private void luuText() {

        System.out.println(user + " bên form trả hàng");
    }

    public static void main(String[] args) {
        new formTraHang();
    }
}
