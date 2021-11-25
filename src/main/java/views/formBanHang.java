package views;


import dao.*;
import model.BanHang;
import model.KhachHang;
import model.SanPhamChiTiet;

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
import java.time.LocalDate;
import java.util.logging.Level;

public class formBanHang extends JFrame {
    private String user;
    private int role;
    private JPanel mainPanel;
    private JTabbedPane tabbedPane1;
    private JTable tblHoaDon;
    private JTextField txtTimKiemSanPham;
    private JTable tblHangHoa;
    private JTextField txtSDT;
    private JTextField txtTenKH;
    private JTable tblChiTiet;
    private JTextField txtTienKhachDua;
    private JButton btnGiamGia;
    private JButton btnXoaSP;
    private JButton btnThanhToan;
    private JLabel lblTong;
    private JLabel lblThua;
    private JButton btnFind;
    private JButton btnTinhTien;
    DefaultTableModel _dtm;
    DefaultTableModel _dtmHoaDon;
    DefaultTableModel _dtmBanHang;
    serviceSanPhamChiTiet serviceSanPhamChiTiet = new serviceSanPhamChiTiet();
    serviceKhachHang serviceKhachHang = new serviceKhachHang();
    serviceBanhang serviceBanhang = new serviceBanhang();
    int id = -1;
    long giaGiam = 0;
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
                "Mã sản phẩm", "Tên sản phẩm", "Loại", "Size", "Màu sắc", "Số lượng còn lại", "Giá Bán"
        });
        _dtmHoaDon.setColumnIdentifiers(new String[]{
                "Mã hóa đơn", "Nhân viên" , "Tên khách hàng", "Ngày", "Tổng tiền", "Tiền Khách Đưa", "Tiền Thừa", "Giảm giá"
        });
        _dtmBanHang.setColumnIdentifiers(new String[]{
                "Mã sản phẩm", "Tên sản phẩm", "Loại", "Size", "Màu sắc", "Số lượng bán", "Đơn giá"
        });

        loadtblHoaDon();
        loadTblSP();
        RendererHighlighted renderer = new RendererHighlighted(txtTimKiemSanPham);
        tblHangHoa.setDefaultEditor(Object.class, null);
        TableRowSorter<TableModel> rowSorter
                = new TableRowSorter<>(tblHangHoa.getModel());
        tblHangHoa.setRowSorter(rowSorter);
        tblHangHoa.setDefaultRenderer(Object.class, renderer);


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
                            String.valueOf(tblHangHoa.getValueAt(i, 0)), String.valueOf(tblHangHoa.getValueAt(i, 1)), String.valueOf(tblHangHoa.getValueAt(i, 2))
                            , String.valueOf(tblHangHoa.getValueAt(i, 3)), String.valueOf(tblHangHoa.getValueAt(i, 4)), input, (long)Integer.parseInt(input) * Double.parseDouble(String.valueOf(tblHangHoa.getValueAt(i, 6)))
                    });
                    int a = (int) (Integer.parseInt(input) * Double.parseDouble(String.valueOf(tblHangHoa.getValueAt(i, 6))));
                    lblTong.setText(String.valueOf(a));
                    return;
                }
                if(_dtmBanHang.getRowCount() > 0){
                    long tong = 0;
                    boolean flag = false;
                    for (int j = 0; j < _dtmBanHang.getRowCount(); j++) {
                        if(String.valueOf(tblHangHoa.getValueAt(i,0)).equals(String.valueOf(tblChiTiet.getValueAt(j,0)))){
                            _dtmBanHang.setValueAt(Integer.parseInt(String.valueOf(tblChiTiet.getValueAt(j,5))) + Integer.parseInt(input), j , 5 );
                            _dtmBanHang.setValueAt((long)Double.parseDouble(String.valueOf(tblChiTiet.getValueAt(j,5))) * Double.parseDouble(String.valueOf(tblHangHoa.getValueAt(i,6))), j, 6);
                            flag = true;
                        }
                        double a = Double.parseDouble(String.valueOf(tblChiTiet.getValueAt(j,6)));
                        tong += a;
                    }
                    if(!flag){
                        _dtmBanHang.addRow(new Object[]{
                                String.valueOf(tblHangHoa.getValueAt(i, 0)), String.valueOf(tblHangHoa.getValueAt(i, 1)), String.valueOf(tblHangHoa.getValueAt(i, 2))
                                , String.valueOf(tblHangHoa.getValueAt(i, 3)), String.valueOf(tblHangHoa.getValueAt(i, 4)), input, Integer.parseInt(input) * Double.parseDouble(String.valueOf(tblHangHoa.getValueAt(i, 6)))
                        });
                        double a = Integer.parseInt(input) * Double.parseDouble(String.valueOf(tblHangHoa.getValueAt(i, 6)));
                        tong += a;
                    }
                    lblTong.setText(String.valueOf(tong));


                }


            }
        });

        // tìm khách hàng theo sđt
        btnFind.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    if(txtSDT.getText().isBlank() || txtSDT.getText().isEmpty()){
                        JOptionPane.showMessageDialog(null, "Vui lòng nhập số điện thoại để tìm");
                    }else {
                        try {
                            if(serviceKhachHang.timTen(txtSDT.getText()) == null){
                                JOptionPane.showMessageDialog(null, "Khách hàng này chưa mua bao giờ");
                                int i = JOptionPane.showConfirmDialog(null, "Bạn muốn thêm khách hàng này không?");
                                if(i == JOptionPane.YES_OPTION){
                                   String input = JOptionPane.showInputDialog(null, "Vui lòng nhập tên khách hàng");
                                   JOptionPane.showMessageDialog(null, serviceKhachHang.themKhachHang(new KhachHang(input, txtSDT.getText(), 0)));
                                   txtTenKH.setText(input);


                                }
                            }else {
                                txtTenKH.setText(serviceKhachHang.timTen(txtSDT.getText()).getTen());


                            }

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

        // tính % đc giảm
        btnGiamGia.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(txtSDT.getText().isEmpty() || txtSDT.getText().isBlank()){
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập sđt để tính tiền giảm");
                    return;
                }
                if(txtTenKH.getText().isEmpty() || txtTenKH.getText().isBlank()){
                    JOptionPane.showMessageDialog(null, "chưa có tên khách hàng");
                    return;
                }
                try {
                    if(serviceKhachHang.timTen(txtSDT.getText()).getDiem() <= 5){
                        JOptionPane.showMessageDialog(null, "Khách này không được giảm giá");

                    }else if (serviceKhachHang.timTen(txtSDT.getText()).getDiem() <= 10){
                        JOptionPane.showMessageDialog(null, "Khách này được giảm 5% tổng đơn hàng");
                        long tong = Long.parseLong(lblTong.getText());
                        tong = tong - (tong / 100 * 5);
                        giaGiam = tong / 100 * 5;
                        lblTong.setText(String.valueOf(tong));
                    }else {
                        JOptionPane.showMessageDialog(null, "Khách này được giảm 10% tổng đơn hàng");
                        long tong = Long.parseLong(lblTong.getText());
                        tong = tong - (tong / 100 * 10);
                        giaGiam = tong / 100 * 5;
                        lblTong.setText(String.valueOf(tong));
                    }
                } catch (SQLException ex) {
                    try {
                        baoLoi(ex);
                    } catch (IOException exc) {
                        exc.printStackTrace();
                    }
                }


            }
        });


        // tính tiền cho khách
        btnTinhTien.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(txtTienKhachDua.getText().isBlank() || txtTienKhachDua.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "nhập tiền khách đưa để tính");
                    return;
                }
                if(!txtTienKhachDua.getText().matches("[0-9]{1,}")){
                    JOptionPane.showMessageDialog(null, "tiền vui lòng nhập số");
                    return;
                }
                long tong =  (Long.parseLong(txtTienKhachDua.getText()) - Integer.parseInt(lblTong.getText()));
                lblThua.setText(String.valueOf(tong));
            }
        });

         // xóa sản phẩm nếu khách ko chọn nữa
        btnXoaSP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row[] = tblChiTiet.getSelectedRows();
                for (int i = row.length - 1; i >= 0; i--) {
                    _dtmBanHang.removeRow(row[i]);
                }
                if (_dtmBanHang.getRowCount() > 0) {
                    long tong = 0;
                    for (int j = 0; j < _dtmBanHang.getRowCount(); j++) {
                        tong += Double.parseDouble(String.valueOf(_dtmBanHang.getValueAt(j, 6)));
                    }
                    lblTong.setText(String.valueOf(tong));
                }
                if(_dtmBanHang.getRowCount() == 0){
                    lblTong.setText("0");
                }
            }
        });

        // in hóa đơn bán hàng
        btnThanhToan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(txtTenKH.getText().isBlank() || txtTenKH.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Chưa có tên khách hàng để in đơn");
                    return;
                }
                if(txtSDT.getText().isBlank() || txtSDT.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Chưa có sđt khách hàng để in đơn");
                    return;
                }
                if(txtTienKhachDua.getText().isBlank() || txtTienKhachDua.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "vui lòng nhập tiền khách đưa để in đơn");
                    return;
                }
                if(_dtmBanHang.getRowCount() == 0){
                    JOptionPane.showMessageDialog(null, "Chưa có sản phẩm để tạo đơn");
                    return;
                }
                try {
                    serviceBanhang.inHoaDon(user, serviceKhachHang.timTen(txtSDT.getText()).getId(), String.valueOf(LocalDate.now()), Integer.parseInt(lblTong.getText()), Integer.parseInt(txtTienKhachDua.getText()), Integer.parseInt(lblThua.getText()), (int) giaGiam);
                    int id = serviceBanhang.timHoaDon(user, serviceKhachHang.timTen(txtSDT.getText()).getId(), String.valueOf(LocalDate.now()), Integer.parseInt(lblTong.getText()), Integer.parseInt(txtTienKhachDua.getText()), Integer.parseInt(lblThua.getText()), (int) giaGiam);
                    for (int i = 0; i < _dtmBanHang.getRowCount(); i++) {
                        double a = Double.parseDouble(String.valueOf(tblChiTiet.getValueAt(i,6)));
                        serviceBanhang.taoChiTietDonHang(id, Integer.parseInt((String) tblChiTiet.getValueAt(i,0)),Integer.parseInt((String) tblChiTiet.getValueAt(i,5)), (int) a);
                        for (int j = 0; j < _dtm.getRowCount(); j++) {
                            if(Integer.parseInt(String.valueOf(tblChiTiet.getValueAt(i, 0))) == Integer.parseInt(String.valueOf(tblHangHoa.getValueAt(j,0)))){
                                int soLuong = Integer.parseInt(String.valueOf(tblHangHoa.getValueAt(j,5))) - Integer.parseInt(String.valueOf(tblChiTiet.getValueAt(i,5)));
                                serviceSanPhamChiTiet.updateSoLuong(soLuong,Integer.parseInt(String.valueOf(tblHangHoa.getValueAt(j,0))));
                                _dtm.setValueAt(soLuong,j,5);
                                break;
                            }
                        }
                    }
                    JOptionPane.showMessageDialog(null, "Đã tạo hóa đơn cho khách");
                    int diem = serviceKhachHang.timTen(txtSDT.getText()).getDiem() + 1;
                    serviceKhachHang.suaDiem(serviceKhachHang.timTen(txtSDT.getText()).getId(), diem);
                    loadtblHoaDon();
                    _dtmBanHang.setRowCount(0);
                } catch (SQLException ex) {
                    try {
                        baoLoi(ex);
                    } catch (IOException exc) {
                        exc.printStackTrace();
                    }
                }
            }
        });
    }


    // đổ dữ liệu vào tbl hóa đơn
    private void loadtblHoaDon() throws SQLException {
        _dtmHoaDon = (DefaultTableModel) tblHoaDon.getModel();
        if(_dtmHoaDon.getRowCount() > 0){
            _dtmHoaDon.setRowCount(0);
        }
        for (BanHang a : serviceBanhang.get_lst()) {
            _dtmHoaDon.addRow(new Object[]{
                    a.getIdHoaDon(), a.getManv(), serviceKhachHang.timTenTheoID(a.getIdKH()), a.getNgayBan(), a.getGiaTriDon(), a.getTienKhachDua(), a.getTienThua(), a.getGiamGia()
            });
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
        System.out.println(user + " bên form bán hàng");
    }

    public static void main(String[] args) throws SQLException {
        new formBanHang();
    }

}
