package Client;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Captcha {
	private static BufferedImage image = null;
	private static String vCode = null;
	private static ByteArrayOutputStream byteArrayOutputStream = null;
    private static final int WIDTH = 200, HEIGHT = 60;
    private static final String[] strArr = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "0",//
            "A", "B", "C", "D", "E", "F", "G", "H", "J", "K", "M", "N", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y",//
            "a", "b", "c", "d", "e", "f", "g", "h", "j", "k", "m", "n", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y" };

    static int fontSize = 35;
    static int len = 5;
    Captcha() throws IOException{
    	image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        vCode = drawGraphic(image); // Captcha 코드값
        byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpeg", byteArrayOutputStream);
    }
    public String getvCode() {
    	return vCode;
    }
    public byte[] getbyteArrayOutputStream() {
    	return byteArrayOutputStream.toByteArray();
    }
     public static void showWin(final byte[] data) {
    	//  생략
        try {
            JFrame frame = new JFrame("Test");
            frame.setLocationRelativeTo(null);
            JPanel imagePanel = (JPanel) frame.getContentPane();

            ImageIcon imageIcon = new ImageIcon(data);
            JLabel label = new JLabel(imageIcon);

            label.setBounds(0, 0, imageIcon.getIconWidth(), imageIcon.getIconHeight());

            imagePanel = (JPanel) frame.getContentPane();
            imagePanel.setOpaque(false);
            imagePanel.setLayout(new FlowLayout());

            frame.getLayeredPane().setLayout(null);
            frame.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 300);
            frame.setVisible(true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static  String drawGraphic(BufferedImage image) {
        Graphics g = image.createGraphics(); // 获取图形上下文 // 도형 아래 문맥을 얻다
        Random random = new Random(); // 生成随机类 // 임의 생성
        g.setColor(getRandColor(200, 250)); // 设定背景色 // 배경색 설정
        // setColor - 이 그래픽 컨텍스트의 현재 색상을 지정된 색상으로 설정합니다. 이 그래픽 컨텍스트를 사용하는 이후의 모든 그래픽 작업에서는 이 지정된 색상을 사용합니다.
        g.fillRect(0, 0, WIDTH, HEIGHT);
        // fillRect - 지정된 사각형을 채웁니다. 직사각형의 왼쪽 및 오른쪽 가장자리는 x및 x+너비-1입니다. 상단 및 하단 가장자리는 y및 y+높이-1입니다. 결과로 나온 직사각형은 높이 픽셀만큼 넓은 영역 폭 픽셀을 포함합니다. 사각형은 그래픽 컨텍스트의 현재 색상을 사용하여 채워집니다.
        g.setFont(new Font("Times New Roman", Font.PLAIN, fontSize)); // 设定字体 // 폰트 설정
        // 이 그래픽 컨텍스트의 글꼴을 지정된 글꼴로 설정합니다. 이 그래픽 컨텍스트를 사용하는 이후의 모든 텍스트 작업에서는 이 글꼴을 사용합니다. null인수가 자동으로 무시됩니다.
        g.setColor(getRandColor(160, 200)); // 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
        // 무작위로 발생하는 횡격선 155개를 무작위로 생성함으로써 이미지 인증 번호가 다른 프로그램에 의해 탐지되기 쉽지 않습니다.
        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(WIDTH);
            int y = random.nextInt(HEIGHT);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }
        String sRand = ""; // 取随机产生的认证码(6位数字) // 무작위 인증 코드(6자리 숫자)
        for (int i = 0; i < len; i++) {
            String rand = String.valueOf(strArr[random.nextInt(strArr.length)]);
            // nextInt - 가능한 모든 결합 값은 같은 확률로 생성됩니다.
            sRand += rand;
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110))); // 将认证码显示到图象中 // 인증 코드를 이미지 그래픽으로 표시한다.
            g.drawString(rand, fontSize * i + 6, fontSize); // 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成 // 함수가 호출된 색상은 같은 종류의 씨앗이 너무 가깝기 때문에 직접적으로 생성될 수밖에 없습니다.
        }
        g.dispose(); // 图象生效 // 화상이 효력을 발생하다.
        return sRand;
    }

    /*
     * * 给定范围获得随机颜色 // 범위를 정하여 무작위로 색깔을 정하다
     */
    private static  Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }
}