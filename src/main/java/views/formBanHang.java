package views;

import dao.RendererHighlighted;
import dao.serviceSanPhamChiTiet;
import model.SanPhamChiTiet;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.sql.SQLException;

public class formBanHang extends JFrame{
    private JPanel mainPanel;
    private JTabbedPane tabbedPane1;
    private JTextField txtTimKiemHoaDon;
    private JTable tblHoaDon;
    private JTextField txtTimKiemSanPham;
    private JTable tblHangHoa;
    private JTextField txtSoluong;
    private JTextField txtMaGiam;
    private JTextField txtSDT;
    private JTextField txtTenKH;
    private JTable tblChiTiet;
    private JTextField txtTienKhachDua;
    private JButton btnXacNhan;
    private JLabel lblThuaTien;
    DefaultTableModel _dtm;
    DefaultTableModel _dtmHoaDon;
    DefaultTableModel _dtmBanHang;
    serviceSanPhamChiTiet serviceSanPhamChiTiet = new serviceSanPhamChiTiet();
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
        _dtm.setColumnIdentifiers(new String []{
                "Mã sản phẩm", "Tên sản phẩm", "Loại", "Size", "Màu sắc", "Số lượng", "Giá Bán"
        });
        _dtmHoaDon.setColumnIdentifiers(new String []{
                "Mã hóa đơn", "Tên khách hàng", "Nhân viên", "Ngày", "Tổng tiền"
        });
        _dtmBanHang.setColumnIdentifiers(new String []{
                "Mã sản phẩm", "Tên sản phẩm", "Loại", "Đơn giá", "Giá giảm","Size", "Màu sắc", "Số lượng bán"
        });
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
    }

    // đổ dữ liệu vào tbl nhập hàng
    private void loadTblSP() throws SQLException {
        _dtm = (DefaultTableModel) tblHangHoa.getModel();
        if(_dtm.getRowCount() > 0){
            _dtm.setRowCount(0);
        }

        for (SanPhamChiTiet a: serviceSanPhamChiTiet.get_list()
        ) {
            _dtm.addRow(new Object[]{
                    a.getIdChiTiet() , a.getName(), a.getTen(), a.getSize(), a.getColor(), a.getSoLuong(), a.getGiaBan()
            });
        }


    }

    public static void main(String[] args) throws SQLException {
        new formBanHang();
    }
}
