package views;


import dao.Log;
import dao.RendererHighlighted;
import dao.serviceKhachHang;
import dao.serviceSanPhamChiTiet;
import model.KhachHang;
import model.SanPhamChiTiet;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;

public class formBanHang extends JFrame {
    private String user;
    private int role;
    private JPanel mainPanel;
    private JTabbedPane tabbedPane1;
    private JTextField txtTimKiemHoaDon;
    private JTable tblHoaDon;
    private JTextField txtTimKiemSanPham;
    private JTable tblHangHoa;
    private JTextField txtSoluong;
    private JComboBox cbcSdt;
    private JTextField txtTenKH;
    private JTable tblChiTiet;
    private JTextField txtTienKhachDua;
    private JButton btnXacNhan;
    private JLabel lblThuaTien;
    private JButton tínhĐượcGiảmButton;
    private JPanel pnlCBC;
    DefaultTableModel _dtm;
    DefaultTableModel _dtmHoaDon;
    DefaultTableModel _dtmBanHang;
    serviceSanPhamChiTiet serviceSanPhamChiTiet = new serviceSanPhamChiTiet();
    serviceKhachHang serviceKhachHang = new serviceKhachHang();


    public formBanHang() throws SQLException {
        this.setTitle("Cửa sổ bán hàng");
        this.setContentPane(mainPanel);
        this.setVisible(true);
        this.setSize(1400, 800);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(2);
        _dtm = (DefaultTableModel) tblHangHoa.getModel();
        _dtmHoaDon = (DefaultTableModel) tblHoaDon.getModel();
        _dtmBanHang = (DefaultTableModel) tblChiTiet.getModel();
        _dtm.setColumnIdentifiers(new String[]{
                "Mã sản phẩm", "Tên sản phẩm", "Loại", "Size", "Màu sắc", "Số lượng", "Giá Bán"
        });
        _dtmHoaDon.setColumnIdentifiers(new String[]{
                "Mã hóa đơn", "Tên khách hàng", "Nhân viên", "Ngày", "Tổng tiền"
        });
        _dtmBanHang.setColumnIdentifiers(new String[]{
                "Mã sản phẩm", "Tên sản phẩm", "Loại", "Giá giảm", "Size", "Màu sắc", "Số lượng bán", "Đơn giá"
        });

        loadCBC();
        loadTblSP();
        RendererHighlighted renderer = new RendererHighlighted(txtTimKiemSanPham);
        tblHangHoa.setDefaultEditor(Object.class, null);
        TableRowSorter<TableModel> rowSorter
                = new TableRowSorter<>(tblHangHoa.getModel());
        tblHangHoa.setRowSorter(rowSorter);
        tblHangHoa.setDefaultRenderer(Object.class, renderer);

        RendererHighlighted renderer1 = new RendererHighlighted(txtTimKiemHoaDon);
        tblHoaDon.setDefaultEditor(Object.class, null);
        TableRowSorter<TableModel> rowSorter1
                = new TableRowSorter<>(tblHoaDon.getModel());
        tblHoaDon.setRowSorter(rowSorter1);
        tblHoaDon.setDefaultRenderer(Object.class, renderer1);

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

        // nút tìm kiếm sản phẩm
        txtTimKiemSanPham.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = txtTimKiemSanPham.getText();
                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = txtTimKiemSanPham.getText();
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
                String text = txtTimKiemHoaDon.getText();
                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = txtTimKiemHoaDon.getText();
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

        // nút click từ tbl này sang tbl khác
        tblHangHoa.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int i = tblHangHoa.getSelectedRow();
                String input = "a";
                while (!input.matches("[0-9]{1,}")) {
                    input = JOptionPane.showInputDialog("mời bạn nhập số lượng");
                    if (input == null) {
                        return;
                    }
                    if (!input.matches("[0-9]{1,}")) {
                        JOptionPane.showMessageDialog(null, "số lượng phải nhập số");
                        continue;
                    }
                    if (Integer.parseInt(input) > Integer.parseInt(String.valueOf(tblHangHoa.getValueAt(i, 5)))) {
                        JOptionPane.showMessageDialog(null, "Số lượng này đã vượt quá hàng trong kho");
                        input = "a";
                    }
                }
                System.out.println(_dtmBanHang.getRowCount());
                if (_dtmBanHang.getRowCount() == 0) {
                    _dtmBanHang.addRow(new Object[]{
                            String.valueOf(tblHangHoa.getValueAt(i, 0)), String.valueOf(tblHangHoa.getValueAt(i, 1)), String.valueOf(tblHangHoa.getValueAt(i, 2)),
                            0, String.valueOf(tblHangHoa.getValueAt(i, 3)), String.valueOf(tblHangHoa.getValueAt(i, 4)), input, Integer.parseInt(input) * Double.valueOf(String.valueOf(tblHangHoa.getValueAt(i, 6)))
                    });
                }


            }
        });
    }

    // đổ dữ liệu vào cbc
    private void loadCBC() throws SQLException {
        cbcSdt.removeAllItems();
        for (KhachHang a : serviceKhachHang.getlist()
        ) {
            cbcSdt.addItem(a.getSdt());
        }
    }


    // đổ dữ liệu vào tbl nhập hàng
    private void loadTblSP() throws SQLException {
        _dtm = (DefaultTableModel) tblHangHoa.getModel();
        if (_dtm.getRowCount() > 0) {
            _dtm.setRowCount(0);
        }

        for (SanPhamChiTiet a : serviceSanPhamChiTiet.get_list()
        ) {
            _dtm.addRow(new Object[]{
                    a.getIdChiTiet(), a.getName(), a.getTen(), a.getSize(), a.getColor(), a.getSoLuong(), a.getGiaBan()
            });
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
        System.out.println(user + " bên form hàng hóa");
    }

    public static void main(String[] args) throws SQLException {
        new formBanHang();
    }

}
