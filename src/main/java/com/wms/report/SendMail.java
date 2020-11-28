package  com.wms.report;

import com.sun.mail.smtp.SMTPTransport;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class SendMail {

    public   String PASSWORD = "Cybershot@903";
    public  String EMAIL_FROM = "sandeepkumarsingh2546@gmail.com";

    public  void sendMail(String fileName) {
        System.out.println("email wala method called !! for email : ");
        Properties prop = System.getProperties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        System.out.println("emails id we have = ");
        Session session = Session.getInstance(prop, (Authenticator)null);
        Message msg = new MimeMessage(session);
        try {
            msg.setFrom(new InternetAddress("sandeepkumarsingh2546@gmail.com"));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("sksingh2546@gmail.com,zdshehreen@gmail.com", false));
            msg.setSubject("Stock Product");
            MimeBodyPart p1 = new MimeBodyPart();
            //p1.setText("message :- ");
            MimeBodyPart p2 = new MimeBodyPart();
            Date date =new Date();
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            System.out.println("C:\\wmsExcel\\wmsStock"+sdf.format(date)+".xls");
             FileDataSource fds = new FileDataSource("C:\\wmsExcel\\wmsStock"+sdf.format(date)+".xls");
             //FileDataSource fds = new FileDataSource("opt\\CMMS_Reports\\" + filename);
             p2.setDataHandler(new DataHandler(fds));
             p2.setFileName(fds.getName());
                p2.setText("mail sent");
//            System.out.println("fds value " + fds.getName());
            final Multipart mp = new MimeMultipart();
            mp.addBodyPart(p1);
            mp.addBodyPart(p2);
            msg.setContent(mp);
            System.out.println("mp = " + mp+" email = "+EMAIL_FROM+" pswrd= "+PASSWORD);
            SMTPTransport t = (SMTPTransport)session.getTransport("smtp");
            t.connect("smtp.gmail.com", EMAIL_FROM, PASSWORD);
            t.sendMessage(msg, msg.getAllRecipients());
            System.out.println("Response: " + t.getLastServerResponse());
            t.close();
        }
        catch (MessagingException e)
        {
            e.printStackTrace();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}

