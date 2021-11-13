package views;


import dao.RendererHighlighted;
import dao.serviceNhanVien;
import model.NhanVien;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class formNhanVien extends JFrame{
    private JPanel mainPanel;
    private JTabbedPane tabbedPane1;
    private JTable tblNhanVien;
    private JTextField txtHoTen;
    private JTextField txtDiaChi;
    private JTextField txtSDT;
    private JTextField txtEmail;
    private JPasswordField txtMk;
    private JRadioButton rdbNV;
    private JRadioButton rdbChu;
    private JButton btnThem;
    private JButton btnSua;
    private JButton btnXoa;
    private JButton btnMoi;
    private JTextField txtMaNV;
    private JTextField txtTimKiem;
    private JButton btnTBLXoa;
    DefaultTableModel _dtm;
    serviceNhanVien _list = new serviceNhanVien();
    boolean check = false;

    public  formNhanVien() throws SQLException {
        this.setTitle("Quản lí nhân viên");
        this.setContentPane(mainPanel);
        this.setSize(700,500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(2);
        this.setResizable(false); // chống chỉnh sửa size frame
        this.setVisible(true);
        _dtm = (DefaultTableModel) tblNhanVien.getModel();
        _dtm.setColumnIdentifiers(new String []{
                "Mã Nhân Viên", "Họ Tên", "Địa Chỉ", "Số Điện Thoại", "Email", "Mật Khẩu", "Chức Vụ"
        });
        tblNhanVien.setModel(_dtm);
        loadtbl();
        rdbChu.setSelected(true);
        RendererHighlighted renderer = new RendererHighlighted(txtTimKiem);
        tblNhanVien.setDefaultEditor(Object.class, null);
        TableRowSorter<TableModel> rowSorter
                = new TableRowSorter<>(tblNhanVien.getModel());

        tblNhanVien.setRowSorter(rowSorter);
        tblNhanVien.setDefaultRenderer(Object.class, renderer);

        // nút thêm nhân viên
        btnThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (txtMaNV.getText().isBlank() || txtMaNV.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Mã Nhân Viên Không Được Để Trống");
                    xoaForm();
                    return;
                }

                if(check){
                    try {
                        JOptionPane.showMessageDialog(null, _list.themLai(txtMaNV.getText()));
                        loadtblXoa();
                        xoaForm();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    return;
                }
                if(loi()){
                    try {
                        JOptionPane.showMessageDialog(null, _list.addNV(nv()));
                        _dtm.addRow(new Object[]{
                                nv().getManv(), nv().getHoten(), nv().getDaichi(), nv().getSdt(), nv().getEmail(), nv().getMatkhau() ,nv().getRole() == 1 ? "Trưởng Phòng" : "Nhân Viên"});
                        xoaForm();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        // mouse click nhân viên từ tbl lên form
        tblNhanVien.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int i = tblNhanVien.getSelectedRow();
                try {
                    NhanVien nv = _list.getlist().get(i);
                    txtMk.setText(nv.getMatkhau());
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                txtMaNV.setText(String.valueOf(tblNhanVien.getValueAt(i, 0)));
                txtHoTen.setText(String.valueOf(tblNhanVien.getValueAt(i, 1)));
                txtDiaChi.setText(String.valueOf(tblNhanVien.getValueAt(i, 2)));
                txtSDT.setText(String.valueOf(tblNhanVien.getValueAt(i, 3)));
                txtEmail.setText(String.valueOf(tblNhanVien.getValueAt(i, 4)));
                txtMk.setText(String.valueOf(tblNhanVien.getValueAt(i, 5)));
                if (String.valueOf(tblNhanVien.getValueAt(i, 6)).equalsIgnoreCase("Chủ")) {
                    rdbChu.setSelected(true);
                } else {
                    rdbNV.setSelected(true);
                }


            }
        });

        // nút xóa form
        btnMoi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                xoaForm();
            }
        });

        // nút sửa nhân viên
        btnSua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(loi()){
                    try {
                        JOptionPane.showMessageDialog(null, _list.updateNV(nv()));
                        xoaForm();
                        loadtbl();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });


        // nút xóa nhân viên
        btnXoa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog("Mời bạn nhập mã nhân viên cần xóa");
                try {
                    JOptionPane.showMessageDialog(null, _list.deleteNV(input));
                    xoaForm();
                    loadtbl();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            }
        });
        btnTBLXoa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!check){
                    try {
                        loadtblXoa();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    check = true;
                    JOptionPane.showMessageDialog(null, "Đã hiện thị những nhân viên bị xóa");
                    btnTBLXoa.setText("Hiện Thị Lại Nhân Viên Đang Làm");
                    btnThem.setText("Thêm Lại");
                    btnSua.setEnabled(false);
                    txtHoTen.setEnabled(false);
                    txtEmail.setEnabled(false);
                    txtMk.setEnabled(false);
                    txtDiaChi.setEnabled(false);
                    txtSDT.setEnabled(false);
                    return;
                }


                if(check){
                    try {
                        loadtbl();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    JOptionPane.showMessageDialog(null, "Đã hiện thị những nhân viên đang làm");
                    btnTBLXoa.setText("Hiện Thị Những Nhân Viên Đã Xóa");
                    check = false;
                    btnThem.setText("Thêm");
                    btnSua.setEnabled(true);
                    txtHoTen.setEnabled(true);
                    txtEmail.setEnabled(true);
                    txtMk.setEnabled(true);
                    txtDiaChi.setEnabled(true);
                    txtSDT.setEnabled(true);
                }
            }
        });


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

    }


    // Load dữ liệu lên table
    private void loadtbl() throws SQLException {
        _dtm = (DefaultTableModel) tblNhanVien.getModel();
        while (_dtm.getRowCount() > 0) {
            _dtm.setRowCount(0);
        }
        for (NhanVien nv : _list.getlist()
        ) {
            _dtm.addRow(new Object[]{
                  nv.getManv(), nv.getHoten(), nv.getDaichi(), nv.getSdt(), nv.getEmail(), nv.getMatkhau() ,nv.getRole() == 1 ? "Chủ" : "Nhân Viên"});
        }
    }


    // Load table những nhân viên đã xóa
    private void loadtblXoa() throws SQLException {
        _dtm = (DefaultTableModel) tblNhanVien.getModel();
        while (_dtm.getRowCount() > 0) {
            _dtm.setRowCount(0);
        }
        for (NhanVien nv : _list.getlistXoa()
        ) {
            _dtm.addRow(new Object[]{
                    nv.getManv(), nv.getHoten(), nv.getDaichi(), nv.getSdt(), nv.getEmail(), nv.getMatkhau() ,nv.getRole() == 1 ? "Chủ" : "Nhân Viên"});
        }
    }

    //Phương thức đọc form lấy ra nhân viên
    private NhanVien nv() {
        return new NhanVien(txtMaNV.getText(),  txtHoTen.getText(), txtDiaChi.getText(), txtSDT.getText(), txtEmail.getText() ,String.valueOf(txtMk.getPassword()), (rdbChu.isSelected() ? 1 : 0));
    }

    // Xóa Form
    private void xoaForm() {
        txtEmail.setText("");
        txtMaNV.setText("");
        txtHoTen.setText("");
        txtDiaChi.setText("");
        txtSDT.setText("");
        txtMk.setText("");
        rdbChu.setSelected(true);
    }


    // Phương thức check lỗi trên form
    private boolean loi(){

        return  true;
    }


    public static void main(String[] args) throws SQLException {
        new formNhanVien();
    }
}
