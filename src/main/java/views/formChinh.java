package views;

import dao.Log;
import io.github.cdimascio.dotenv.Dotenv;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.*;
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
import java.util.Properties;
import java.util.logging.Level;

public class formChinh extends JFrame {
    private String user;
    private int role;
    private JPanel mainForm;
    private JMenu mnuHeThong;
    private JMenuItem mniDangXuat;
    private JMenuItem mniDoiPass;
    private JMenuItem mniKetThuc;
    private JMenu mnuQuanLi;
    private JMenuItem mniNhanVien;
    private JMenuItem mniNguonHang;
    private JMenuItem mniLoaiHang;
    private JMenuItem mniSanPham;
    private JMenuItem mniNhapHang;
    private JMenuItem mniThongKe;
    private JButton btnDangXuat;
    private JLabel lblTime;
    private JMenuItem mniXuatHang;
    private JButton btnNhapHang;
    private JButton btnbanHang;
    private JButton btnSanPham;
    private JButton btnBaoLoi;
    private JButton btnKhachHang;
    private JMenuItem mniTraHang;

    public formChinh() throws IOException {

        this.setTitle("Cửa sổ chính");
        this.setContentPane(mainForm);
        pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(2);
        this.setResizable(false); // chống chỉnh sửa size frame
        this.setVisible(true);
        run();
        Dotenv dotenv = Dotenv.configure().load();
        // khi mở form sẽ mã nhân viên và vai trò của nhân viên đó
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                luuText();
            }
        });


        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        // nút đăng xuất
        mniDangXuat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = JOptionPane.showConfirmDialog(null, "Bạn có muốn đăng xuất không?", "Thông báo", JOptionPane.YES_NO_OPTION);
                if (i == JOptionPane.YES_OPTION) {
                    try {
                        loginFormm loginForm = new loginFormm();
                    } catch (SQLException ex) {
                        try {
                            baoLoi(ex);
                        } catch (IOException exc) {
                            exc.printStackTrace();
                        }
                    }
                    dispose();
                }

            }
        });

        // nút đăng xuất
        btnDangXuat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = JOptionPane.showConfirmDialog(null, "Bạn có muốn đăng xuất không?", "Thông báo", JOptionPane.YES_NO_OPTION);
                if (i == JOptionPane.YES_OPTION) {
                    try {
                        loginFormm loginForm = new loginFormm();
                    } catch (SQLException ex) {
                        try {
                            baoLoi(ex);
                        } catch (IOException exc) {
                            exc.printStackTrace();
                        }
                    }
                    dispose();
                }
            }
        });

        //nút tắt chương trình
        mniKetThuc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // nút đổi mật khẩu
        mniDoiPass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                formDoiMatKhau formDoiMatKhau = null;
                try {
                    formDoiMatKhau = new formDoiMatKhau();
                } catch (IOException ex) {
                    try {
                        baoLoi(ex);
                    } catch (IOException exc) {
                        exc.printStackTrace();
                    }
                }
                formDoiMatKhau.setUser(user);
                formDoiMatKhau.setRole(role);
                dispose();
            }
        });

        // nút nhân viên
        mniNhanVien.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    formNhanVien formNhanVien = new formNhanVien();
                    formNhanVien.setUser(user);
                    formNhanVien.setRole(role);
                    dispose();
                } catch (SQLException | IOException ex) {
                    try {
                        baoLoi(ex);
                    } catch (IOException exc) {
                        exc.printStackTrace();
                    }
                }
            }
        });


        // nút nhân viên
        mniNguonHang.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    formNguonHang formNguonHang = new formNguonHang();
                    formNguonHang.setUser(user);
                    formNguonHang.setRole(role);
                    dispose();
                } catch (SQLException | IOException ex) {
                    try {
                        baoLoi(ex);
                    } catch (IOException exc) {
                        exc.printStackTrace();
                    }
                }
            }
        });

        // nút khách hàng
        btnKhachHang.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    formKhachHang formKhachHang = new formKhachHang();
                    formKhachHang.setUser(user);
                    formKhachHang.setRole(role);
                    dispose();
                } catch (SQLException | IOException ex) {
                    try {
                        baoLoi(ex);
                    } catch (IOException exc) {
                        exc.printStackTrace();
                    }
                }
            }
        });


        // nút loại hàng
        mniLoaiHang.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    formSanPham formSanPham = new formSanPham();
                    formSanPham.setRole(role);
                    formSanPham.setUser(user);
                    dispose();
                } catch (IOException | SQLException ex) {
                    try {
                        baoLoi(ex);
                    } catch (IOException exc) {
                        exc.printStackTrace();
                    }
                }
            }
        });

        // vào nút hàng hóa
        btnSanPham.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    formHangHoa formHangHoa = new formHangHoa();
                    formHangHoa.setRole(role);
                    formHangHoa.setUser(user);
                    dispose();
                } catch (IOException | SQLException ex) {
                    try {
                        baoLoi(ex);
                    } catch (IOException exc) {
                        exc.printStackTrace();
                    }
                }
            }
        });


        // vào nút hàng hóa
        mniSanPham.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    formHangHoa formHangHoa = new formHangHoa();
                    formHangHoa.setRole(role);
                    formHangHoa.setUser(user);
                    dispose();
                } catch (IOException | SQLException ex) {
                    try {
                        baoLoi(ex);
                    } catch (IOException exc) {
                        exc.printStackTrace();
                    }
                }
            }
        });

        // nút nhập hàng
        mniNhapHang.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    formNhapHang formNhapHang = new formNhapHang();
                    formNhapHang.setRole(role);
                    formNhapHang.setUser(user);
                    dispose();
                } catch (IOException | SQLException ex) {
                    try {
                        baoLoi(ex);
                    } catch (IOException exc) {
                        exc.printStackTrace();
                    }
                }
            }
        });
        // nút nhập hàng
        btnNhapHang.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    formNhapHang formNhapHang = new formNhapHang();
                    formNhapHang.setRole(role);
                    formNhapHang.setUser(user);
                    dispose();
                } catch (IOException | SQLException ex) {
                    try {
                        baoLoi(ex);
                    } catch (IOException exc) {
                        exc.printStackTrace();
                    }
                }
            }
        });

        // nút báo lỗi khi gặp sự cố
        btnBaoLoi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = JOptionPane.showConfirmDialog(null, "Bạn có muốn gửi lỗi cho admin không?", "Hỏi", JOptionPane.YES_NO_OPTION);
                if (i == JOptionPane.YES_OPTION) {

                    String user = dotenv.get("MY_ENV_VAR1");
                    String pass = dotenv.get("MY_EVV_VAR2");
                    String to = "hieuntph15836@fpt.edu.vn";
                    String subject = "Lỗi Chương Trình Dự Án Mẫu";
                    String message = "Admin ơi fix lỗi cho em đi ";
                    Properties props = System.getProperties();
                    props.put("mail.smtp.user", "username");
                    props.put("mail.smtp.host", "smtp.gmail.com");
                    props.put("mail.smtp.port", "587");
                    props.put("mail.debug", "true");
                    props.put("mail.smtp.auth", "true");
                    props.put("mail.smtp.starttls.enable", "true");
                    props.put("mail.smtp.EnableSSL.enable", "true");

                    props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                    props.setProperty("mail.smtp.socketFactory.fallback", "false");
                    props.setProperty("mail.smtp.port", "465");
                    props.setProperty("mail.smtp.socketFactory.port", "465");

                    Session sessiona = Session.getInstance(props,
                            new Authenticator() {
                                protected PasswordAuthentication getPasswordAuthentication() {
                                    return new PasswordAuthentication(user, pass);
                                }
                            });
                    try {
                        Message messagea = new MimeMessage(sessiona);
                        messagea.setFrom(new InternetAddress(user));
                        messagea.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
                        messagea.setSubject(subject);

                        messagea.setText(message);
                        MimeBodyPart messageBodyPart = new MimeBodyPart();
                        Multipart multipart = new MimeMultipart();
                        messageBodyPart = new MimeBodyPart();
                        String fileName = "hieupro.txt";
                        DataSource source = new FileDataSource("\\Desktop\\duan2\\hieupro.txt");
                        messageBodyPart.setDataHandler(new DataHandler(source));
                        messageBodyPart.setFileName(fileName);
                        multipart.addBodyPart(messageBodyPart);
                        messagea.setContent(multipart);
                        Transport.send(messagea);
                        JOptionPane.showMessageDialog(null, "Gửi lỗi thành công");
                    } catch (Exception a) {
                        throw new RuntimeException(a);
                    }
                }
            }
        });

        // nút bán hàng
        btnbanHang.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    formBanHang formBanHang = new formBanHang();
                    formBanHang.setRole(role);
                    formBanHang.setUser(user);
                    dispose();
                } catch (SQLException ex) {
                    try {
                        baoLoi(ex);
                    } catch (IOException exc) {
                        exc.printStackTrace();
                    }
                }
            }
        });

        // nút bán hàng
        mniXuatHang.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    formBanHang formBanHang = new formBanHang();
                    formBanHang.setRole(role);
                    formBanHang.setUser(user);
                    dispose();
                } catch (SQLException ex) {
                    try {
                        baoLoi(ex);
                    } catch (IOException exc) {
                        exc.printStackTrace();
                    }
                }
            }
        });

        // nút bán hàng
        mniTraHang.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                try {
//                    formTraHang formTraHang = new formTraHang();
//                    formTraHang.setRole(role);
//                    formTraHang.setUser(user);
//                    dispose();
//                } catch (SQLException ex) {
//                    try {
//                        baoLoi(ex);
//                    } catch (IOException exc) {
//                        exc.printStackTrace();
//                    }
//                }
            }
        });
    }


    // báo lỗi trên form
    private void baoLoi(Exception ex) throws IOException {
        Log log = new Log("hieupro.txt");
        JOptionPane.showMessageDialog(null, "gặp lỗi rồi! Quay lại để gửi lỗi cho admin nha");
        log.logger.setLevel(Level.WARNING);
        log.logger.info(ex.getMessage());
        log.logger.warning("Lỗi ở form chính");
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String sStackTrace = sw.toString(); // stack trace as a string
        log.logger.severe(sStackTrace);
    }

    // Phương thức set giá trị cho 2 biến phân quyền
    public void setUser(String user) {
        this.user = user;
    }

    public void setRole(int role) {
        this.role = role;
    }


    // đọc dữ liệu phân quyền lên form
    private void luuText() {

        if (role != 1) {
            mniNhanVien.setEnabled(false);
            mniThongKe.setEnabled(false);
        }
        System.out.println(user + " bên form chính");
    }


    // đồng hồ
    private void run() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                int i = 90;
                while (true) {
                    Date t = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat();
                    sdf.applyPattern("HH:mm:ss");
                    String ta = sdf.format(t);
                    lblTime.setText(ta);
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        };
        thread.start();
    }

    public static void main(String[] args) throws IOException {
        new formChinh();
    }

}
