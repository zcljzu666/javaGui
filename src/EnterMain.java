import com.zcl.service.ReportService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EnterMain {

    private JLabel label;
    private JButton button1;
    private JTextField textField1;
    private JTextField textField2;
    private JPanel panel;

    public void go() {
        JFrame frame = new JFrame("统计电话拨打量");
        frame.setSize(350, 100);

        textField2 = new JTextField();
        textField2.setText("生成结果：");

        panel =  new JPanel();
        panel.setLayout(new GridLayout(1, 1));
        panel.setSize(300, 40);

        label = new JLabel("查询日期：");
        label.setSize(20,30);

        textField1 = new JTextField();
        textField1.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        textField1.setSize(160, 30);

        button1 = new JButton("生成excel");
        button1.addActionListener(new ButtonListener(textField1,textField2));
        button1.setSize(160,30);

        panel.add(label);
        panel.add(textField1);
        panel.add(button1);

        frame.add(panel, BorderLayout.NORTH);
        frame.add(textField2, BorderLayout.CENTER);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    class ButtonListener implements ActionListener {
        private JTextField textField1;
        private JTextField textField2;

        public ButtonListener(JTextField textField1,JTextField textField2) {
            this.textField1 = textField1;
            this.textField2 = textField2;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("生成excel开始");
            long beginTime = System.currentTimeMillis();
            String queryDate = this.textField1.getText();
            //上送的日期如果为空,默认查询当天
            if(queryDate == null || "".equals(queryDate)){
                queryDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            }
            this.textField2.setText("生成excel文件进行中,莫着急......");
            String flag = new ReportService().exportExcel(queryDate);
            if ("1".equals(flag)) {
                System.out.println("生成文件成功!");
                this.textField2.setText("生成文件成功！耗时" + (double)(System.currentTimeMillis() - beginTime)/1000 + " 秒");
            } else {
                System.out.println("生成文件失败!");
                this.textField2.setText("生成文件失败！");
            }

        }
    }

    public static void main(String[] args) {
        EnterMain frame = new EnterMain();
        frame.go();
    }
}
