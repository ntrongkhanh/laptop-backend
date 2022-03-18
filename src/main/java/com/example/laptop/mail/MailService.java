package com.example.laptop.mail;

import com.example.laptop.db.entity.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Value("${laptop.app.email}")
    private String email;

    @Value("${laptop.app.password}")
    private String password;

    public MailService() {
    }

    public void sendConfirmMail(JavaMailSender emailSender, String userEmail, String token) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(userEmail);
        message.setSubject("Confirm account mail");
        message.setFrom(email);
        message.setText("Bạn đã đăng ký tài khoản trong web WAVI bằng email này."
                + "\nĐể xác nhận tài khoản, vui long truy cập link:"
//                + "http://localhost:4200/confirm-account?token=" + token + "&email=" + userEmail);
                + "http://localhost:8080/api/user/active?code=" + token + "&email=" + userEmail);
        emailSender.send(message);
    }

    public void sendForgotPasswordMail(JavaMailSender emailSender, String userEmail, String token) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(userEmail);
        message.setSubject("Forgot password");
        message.setFrom(email);
        message.setText("\nĐể reset mật khẩu, vui long truy cập link:"
//                + "http://localhost:4200/confirm-account?token=" + token + "&email=" + userEmail);
                + "http://localhost:8080/api/user/reset?code=" + token + "&email=" + userEmail);
        emailSender.send(message);
    }

    public void sendOrderMail(JavaMailSender emailSender, Order ordersEntity) {
        SimpleMailMessage message = new SimpleMailMessage();

        String productList = "";
        for (int i = 0; i < ordersEntity.getOrderDetails().size(); i++) {
            productList = productList + "-" + ordersEntity.getOrderDetails().get(i).getProduct().getName() + " x " +
                    ordersEntity.getOrderDetails().get(i).getQuantity() + "\n" +
                    "Giá: " + ordersEntity.getOrderDetails().get(i).getProduct().getPrice() + " x " +
                    ordersEntity.getOrderDetails().get(i).getQuantity() + " = " +
                    ordersEntity.getOrderDetails().get(i).getTotalPrice() + " vnđ\n";

        }
        message.setTo(ordersEntity.getUser().getEmail());
        message.setSubject("Xác nhận đơn hàng #" + ordersEntity.getOrderCode());
        message.setFrom(email);
        message.setText("Cảm ơn khách hàng " + ordersEntity.getUser().getName() + " đã đặt hàng tại WAVI\n" +
                "Thông tin đơn hàng: #" + ordersEntity.getOrderCode() + "\n" +
                "Tên khách hàng: " + ordersEntity.getUser().getName() + "\n" +
                "Email: " + ordersEntity.getUser().getEmail() + "\n" +
                "Số điện thoại: " + ordersEntity.getPhone() + "\n" +
                "Mã đơn hàng: " + ordersEntity.getOrderCode() + "\n" +
                "Địa chỉ giao: " + ordersEntity.getAddress() + "\n" +
                "Danh sách sản phẩm: " + "\n" +
                productList + "\n" +
                "Tổng giá: " + ordersEntity.getTotalPrice() + " vnđ.");
        emailSender.send(message);
    }
}
