package views;

import dao.Log;
import dao.RendererHighlighted;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;

public class formNhapHang extends JFrame{
    private String user;
    private int role;
    private JPanel mainPanel;
    private JTabbedPane tabbedPane1;
    private JTextField txtTimKiem;
    private JTable tblHangHoa;
    private JTextField txtMaSP;
    private JComboBox cbcSoLuong;
    private JTextField txtGiaNhap;
    private JComboBox cbcNguonhang;
    private JButton btnXacNhan;
    private JTextField txtTimKiemHoaDon;
    private JTable tblHoaDonNhap;
    private JLabel lblTongTien;
    DefaultTableModel _dtm;
    DefaultTableModel _dtmHoaDon;


    public formNhapHang() throws IOException {
        this.setTitle("Cửa sổ nhập hàng");
        this.setContentPane(mainPanel);
        this.setVisible(true);
        this.setSize(700, 400);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(2);
        _dtm = (DefaultTableModel) tblHangHoa.getModel();
        _dtmHoaDon = (DefaultTableModel) tblHoaDonNhap.getModel();
        _dtm.setColumnIdentifiers(new String []{
                "Mã sản phẩm", "Tên sản phẩm", "Loại", "Size", "Màu sắc", "Số lượng"
        });
        _dtmHoaDon.setColumnIdentifiers(new String []{
                "Mã hóa đơn", "Người nhập", "Nguồn hàng", "Ngày", "Tổng tiền"
        });

        RendererHighlighted renderer = new RendererHighlighted(txtTimKiem);
        tblHangHoa.setDefaultEditor(Object.class, null);
        TableRowSorter<TableModel> rowSorter
                = new TableRowSorter<>(tblHangHoa.getModel());
        tblHangHoa.setRowSorter(rowSorter);
        tblHangHoa.setDefaultRenderer(Object.class, renderer);

        RendererHighlighted renderer1 = new RendererHighlighted(txtTimKiemHoaDon);
        tblHoaDonNhap.setDefaultEditor(Object.class, null);
        TableRowSorter<TableModel> rowSorter1
                = new TableRowSorter<>(tblHoaDonNhap.getModel());
        tblHoaDonNhap.setRowSorter(rowSorter1);
        tblHoaDonNhap.setDefaultRenderer(Object.class, renderer1);


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


        // nút tìm kiếm hóa đơn nhập
        txtTimKiemHoaDon.getDocument().addDocumentListener(new DocumentListener() {
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
    }

    // đổ dữ liệu vào cbc số lượng
    private void loadCBCSoLuong(){
            cbcSoLuong.removeAllItems();
        for (int i = 1; i <= 500; i++) {
            cbcSoLuong.addItem(i);
        }
    }


    // đổ dữ liệu vào cbc nguồn hàng

    private void loadCbcNguonHang(){

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
        System.out.println(user + " bên form hàng hóa");
    }

    public static void main(String[] args) throws IOException {
        new formNhapHang();
    }
}
