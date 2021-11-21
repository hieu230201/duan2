package views;

import dao.*;
import model.LoaiSP;
import model.SanPham;
import model.SanPhamChiTiet;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
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
    private JLabel lblHinh;
    private JButton btnLoadAnh;
    private JButton btnUpdate;
    DefaultTableModel _dtm;
    boolean check = false;
    String pic = "";
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
                "Mã sản phẩm", "Tên sản phẩm", "Số lượng", "Giá bán", "Giá vốn", "Size", "Màu sắc", "Ngày Nhập"
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
                       xoaForm();
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

        // phương thức load ảnh
        btnLoadAnh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lblHinh.setIcon(image());
            }
        });

        // click ảnh từ tbl lên form
        tblHangHoa.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                int i = tblHangHoa.getSelectedRow();

                txtGiaBan.setText(String.valueOf(tblHangHoa.getValueAt(i,3)));
                txtGiaVon.setText(String.valueOf(tblHangHoa.getValueAt(i,4)));
                txtSize.setText(String.valueOf(tblHangHoa.getValueAt(i,5)));
                txtColor.setText(String.valueOf(tblHangHoa.getValueAt(i,6)));

                try {

                    cbcLoaiSP.setSelectedItem(serviceLoaiSP.layTenNguonHang(String.valueOf(tblHangHoa.getValueAt(i,1))));
                    cbcTenSP.setSelectedItem(String.valueOf(tblHangHoa.getValueAt(i,1)));
                    SanPhamChiTiet sp = serviceSanPhamChiTiet.get_list().get(i);
                    ImageIcon imageIcon = new ImageIcon(sp.getHinh());
                    Image image = imageIcon.getImage();
                    Image image1 = image.getScaledInstance(250, 250, 1000);
                    imageIcon = new ImageIcon(image1);
                    lblHinh.setIcon(imageIcon);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                if(!check){
                    check = true;
                    panelCha.removeAll();
                    panelCha.add(panelCapNhat);
                    panelCha.repaint();
                    panelCha.revalidate();
                    btnChuyenForm.setText("Quay lại danh sách");


                }

            }
        });

        // nút sửa hàng
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    int i = tblHangHoa.getSelectedRow();
                    if(i == -1){
                        JOptionPane.showMessageDialog(null, "Vui lòng chọn 1 sản phẩm để sửa");
                        return;
                    }
                try {
                    System.out.println(tblHangHoa.getValueAt(i,0));
                    SanPhamChiTiet sp = sp();
                    sp.setIdChiTiet((Integer) tblHangHoa.getValueAt(i,0));
                    JOptionPane.showMessageDialog(null,serviceSanPhamChiTiet.updateSanPham(sp));
                    loadtbl();
                    xoaForm();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }



    // phương thức xóa form
    private void xoaForm(){
        txtColor.setText("");
        txtGiaBan.setText("");
        txtGiaVon.setText("");
        txtSize.setText("");
        pic = "";
        lblHinh.setIcon(null);
    }
    //Phương thức ảnh
    public ImageIcon image() {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "JPG & GIF Images", "jpg", "gif", "png");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            pic = String.valueOf(chooser.getSelectedFile());
            ImageIcon imageIcon = new ImageIcon(pic);
            Image image = imageIcon.getImage();
            Image image1 = image.getScaledInstance(250, 250, 1000);
            imageIcon = new ImageIcon(image1);
            return imageIcon;
        }
        return null;

    }

    // phương thức load tbl
    private void loadtbl() throws SQLException {
        _dtm = (DefaultTableModel) tblHangHoa.getModel();
        if(_dtm.getRowCount() > 0){
            _dtm.setRowCount(0);
        }

        for (SanPhamChiTiet a: serviceSanPhamChiTiet.get_list()
        ) {
            _dtm.addRow(new Object[]{
                    a.getIdChiTiet() , a.getName(), a.getSoLuong(), a.getGiaBan(), a.getGiaVon(), a.getSize(), a.getColor(), a.getNgayNhap() == null ? "chưa có" : a.getNgayNhap()
            });

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
        sp.setHinh(pic);
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
