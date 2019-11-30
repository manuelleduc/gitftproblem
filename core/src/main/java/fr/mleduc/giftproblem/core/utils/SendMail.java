package fr.mleduc.giftproblem.core.utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendMail {

    private final String username;
    private final String password;

    public SendMail(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static void main(String[] args) {
        String destinataire = "SENDERMAIL";
        String donneur = "FROM";
        String receveur = "RECEVEUR";
        new SendMail("SENDERMAIL", "PASS").send(destinataire, donneur, receveur);
    }

    public void send(String destinataire, String donneur, String receveur) {

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("manuel.leduc@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(destinataire)
            );
            message.setSubject("Cadeaux de Noël");
            message.setContent("Bonjour,\n\n"
                    + "Pour Noël, <strong>" + donneur + "</strong> doit offrir un cadeau (5 à 20€ maxi ou de sa fabrication) à <strong>" + receveur + "</strong>.\n\n" +
                    "A bientôt,\n\n" +
                    "Manuel", "text/html; charset=utf-8");

            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}

