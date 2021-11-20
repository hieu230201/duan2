package views;

import dao.*;
import model.LoaiSP;
import model.SanPham;
import model.SanPhamChiTiet;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.logging.Level;

public class formHangHoa extends JFrame{
    private String user;
    private int role;
    private JPanel mainPanel;
    private JTextField txtTimKiem;
    private JButton btnChuyenForm;
    private JPanel panelCha;
    private JPanel panelBang;
    private JPanel panelCapNhat;
    private JTable tblHangHoa;
    private JComboBox cbcTenSP;
    private JComboBox cbcLoaiSP;
    private JTextField txtGiaBan;
    private JTextField txtSize;
    private JTextField txtColor;
    private JButton btnThemHang;
    private JTextField txtGiaVon;
    DefaultTableModel _dtm;
    boolean check = false;
    serviceLoaiSP serviceLoaiSP = new serviceLoaiSP();
    serviceSanPham serviceSanPham = new serviceSanPham();
    serviceSanPhamChiTiet serviceSanPhamChiTiet = new serviceSanPhamChiTiet();

    public formHangHoa() throws IOException, SQLException {

        this.setTitle("Cửa sổ hàng hóa");
        this.setContentPane(mainPanel);
        this.setVisible(true);
        this.setSize(700, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(2);

        _dtm = (DefaultTableModel) tblHangHoa.getModel();
        _dtm.setColumnIdentifiers(new String []{
                "Mã sản phẩm", "Tên sản phẩm", "Số lượng", "Giá bán", "Giá vốn", "Size", "Màu sắc"
        });

        tblHangHoa.setModel(_dtm);
        RendererHighlighted renderer = new RendererHighlighted(txtTimKiem);
        tblHangHoa.setDefaultEditor(Object.class, null);
        TableRowSorter<TableModel> rowSorter
                = new TableRowSorter<>(tblHangHoa.getModel());
        tblHangHoa.setRowSorter(rowSorter);
        tblHangHoa.setDefaultRenderer(Object.class, renderer);
        loadCBC();
        loadtbl();
        loadCBCSP();



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

        // chuyểngiữa form thêm và tbl
        btnChuyenForm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!check){
                    check = true;
                    panelCha.removeAll();
                    panelCha.add(panelCapNhat);
                    panelCha.repaint();
                    panelCha.revalidate();
                    btnChuyenForm.setText("Quay lại danh sách");

                    return;
                }

                    check = false;
                    panelCha.removeAll();
                    panelCha.add(panelBang);
                    panelCha.repaint();
                    panelCha.revalidate();
                    btnChuyenForm.setText("Thêm hàng hóa");
            }
        });

        // nút tìm kiếm
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

        // load sản phẩm theo loại
        cbcLoaiSP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    loadCBCSP();
                } catch (SQLException ex) {
                    try {
                        baoLoi(ex);
                    } catch (IOException exc) {
                        exc.printStackTrace();
                    }
                }
            }
        });

        // thêm 1 sản phẩm mới
        btnThemHang.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(loi()){
                    try {
                        JOptionPane.showMessageDialog(null, serviceSanPhamChiTiet.addChiTiet(sp()));
                        loadtbl();
                        txtColor.setText("");
                        txtGiaBan.setText("");
                        txtGiaVon.setText("");
                        txtSize.setText("");

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
    }


    // phương thức load tbl
    private void loadtbl() throws SQLException {
        _dtm = (DefaultTableModel) tblHangHoa.getModel();
        if(_dtm.getRowCount() > 0){
            _dtm.setRowCount(0);
        }
        int stt = 1;
        for (SanPhamChiTiet a: serviceSanPhamChiTiet.get_list()
        ) {
            _dtm.addRow(new Object[]{
                    stt , a.getName(), a.getSoLuong(), a.getGiaBan(), a.getGiaVon(), a.getSize(), a.getColor()
            });
            stt++;
        }
    }

    // load loại sản phẩm cbc
    private void loadCBC() throws SQLException {
        cbcLoaiSP.removeAllItems();
        for (LoaiSP a: serviceLoaiSP.get_list()
        ) {
            cbcLoaiSP.addItem(a.getTen());
        }
    }
    
    // load sản phẩm dựa theo tên loại sản phẩm
    private void loadCBCSP() throws SQLException{
        cbcTenSP.removeAllItems();
        for (SanPham a : serviceSanPham.getSP(cbcLoaiSP.getSelectedItem().toString())
                ) {
            cbcTenSP.addItem(a.getName());
        }
    }

    // phương thức khởi tạo 1 chi tiết sản phẩm
    public SanPhamChiTiet sp() throws SQLException {
        SanPhamChiTiet sp = new SanPhamChiTiet();
        sp.setSize(txtSize.getText());
        sp.setColor(txtColor.getText());
        sp.setIdSP(serviceSanPham.get1Loai(cbcTenSP.getSelectedItem().toString()));
        sp.setGiaBan(Double.parseDouble(txtGiaBan.getText()));
        sp.setGiaVon(Double.parseDouble(txtGiaVon.getText()));
        return sp;
    }


    // phương thức check lỗi
    private boolean loi(){
        if(txtColor.getText().isEmpty() || txtColor.getText().isBlank()){
            JOptionPane.showMessageDialog(null, "Màu không được để trống");
            return false;
        }
        if(txtGiaBan.getText().isEmpty() || txtGiaBan.getText().isBlank()){
            JOptionPane.showMessageDialog(null, "Giá bán không được để trống");
            return false;
        }
        if(txtGiaVon.getText().isEmpty() || txtGiaVon.getText().isBlank()){
            JOptionPane.showMessageDialog(null, "Giá vốn không được để trống");
            return false;
        }
        if(txtSize.getText().isEmpty() || txtSize.getText().isBlank()){
            JOptionPane.showMessageDialog(null, "Size không được để trống");
            return false;
        }
        return true;
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

    public static void main(String[] args) throws IOException, SQLException {
        new formHangHoa();
    }
}
