import javax.imageio.ImageIO;
import javax.sound.sampled.Line;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class imagecom extends JFrame implements ActionListener {
    JPanel before = new JPanel();
    JPanel after = new JPanel();
    JLabel Lafter = new JLabel();
    JLabel Lbefore = new JLabel();
    JButton[] btn = new JButton[5];
    JFileChooser inputChooser = new JFileChooser();
    JFileChooser outputChooser = new JFileChooser();
    BufferedImage img;
    JSlider light, contrast;
    Font font;
    imagecom(){
        setSize(1200, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("영상 처리");
        setLocationRelativeTo(null);

        setLayout(null);

        light = new JSlider(0, 200, 120);
        contrast = new JSlider(-200, 200, 120);
        light.setPaintTicks(true);
        contrast.setPaintTicks(true);
        light.setPaintTicks(true);
        contrast.setPaintTicks(true);

        light.setMajorTickSpacing(50);
        contrast.setMajorTickSpacing(50);

        light.setOrientation(SwingConstants.VERTICAL);
        contrast.setOrientation(SwingConstants.VERTICAL);

        btn[0] = new JButton("불러오기");
        btn[1] = new JButton("저장하기");
        btn[2] = new JButton("흑백");
        btn[3] = new JButton("Smoothing");
        btn[4] = new JButton("edge");
//        btn[5] = new JButton("Smoothing");
//        btn[6] = new JButton("edge");
        JLabel two = new JLabel("밝기    대비");

        before.setBounds(10,10,500,645);
        after.setBounds(520, 10, 500, 645);
        btn[0].setBounds(1040, 100, 127, 40);
        btn[1].setBounds(1040, 160, 127, 40);
        btn[2].setBounds(1040, 220, 127, 40);
        btn[3].setBounds(1040, 280, 127, 40);
        btn[4].setBounds(1040, 350, 127, 40);
        light.setBounds(1060, 420, 40, 150);
        contrast.setBounds(1110, 420, 40, 150);
        two.setBounds(1055, 560, 127, 40);
//        btn[5].setBounds(1040, 420, 127, 40);
//        btn[6].setBounds(1040, 470, 127, 40);

        font = new Font("SanSerif", Font.BOLD, 17);
        two.setFont(font);

        getContentPane().setBackground(Color.white);
        after.setBackground(Color.white);
        before.setBackground(Color.white);
        btn[0].setBackground(Color.pink);
        btn[1].setBackground(Color.PINK);
        btn[2].setBackground(Color.pink);
        btn[3].setBackground(Color.pink);
        btn[4].setBackground(Color.pink);
        light.setBackground(Color.white);
        contrast.setBackground(Color.white);
//        btn[5].setBackground(Color.PINK);
//        btn[6].setBackground(Color.PINK);

        before.add(Lbefore);
        after.add(Lafter);
        add(before);
        add(after);
        add(btn[0]);
        add(btn[1]);
        add(btn[2]);
        add(btn[3]);
        add(btn[4]);
        add(light);
        add(contrast);
        add(two);
//        add(btn[5]);
//        add(btn[6]);

        btn[0].addActionListener(this);
        btn[1].addActionListener(this);
        btn[2].addActionListener(this);
        btn[3].addActionListener(this);
        btn[4].addActionListener(this);
//        btn[5].addActionListener(this);
//        btn[6].addActionListener(this);

        light.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Image imgcopy = img;
                BufferedImage img2 = imageToBufferedImage(imgcopy);
                light = (JSlider) e.getSource();
                for(int y = 0; y < img2.getHeight(); y++) {
                    for(int x = 0; x < img2.getWidth(); x++) {
                        Color color = new Color(img2.getRGB(x, y));
                        int r = (int)Math.min(color.getRed() + light.getValue(), 255);
                        int g = (int)Math.min(color.getGreen() + light.getValue(), 255);
                        int b2 = (int)Math.min(color.getBlue() + light.getValue(), 255);
                        img2.setRGB(x, y, new Color(r, g, b2).getRGB());
                    }
                    Lafter.setIcon(new ImageIcon(img2));
                }
            }
        });

        contrast.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Image imgcopy = img;
                BufferedImage img2 = imageToBufferedImage(imgcopy);
                contrast = (JSlider) e.getSource();
                for(int y = 0; y < img2.getHeight(); y++) {
                    for(int x = 0; x < img2.getWidth(); x++) {
                        Color color = new Color(img2.getRGB(x, y));
                        int r = (color.getRed() > 128) ? color.getRed() + contrast.getValue() : color.getRed() - contrast.getValue();
                        int g = (color.getGreen() > 128) ? color.getGreen() + contrast.getValue() : color.getGreen() - contrast.getValue();
                        int b2 = (color.getBlue() > 128) ? color.getBlue() + contrast.getValue() : color.getBlue() - contrast.getValue();
                        r = (int)Math.min(r, 255);
                        r = (int)Math.max(r, 0);
                        g = (int)Math.min(g, 255);
                        g = (int)Math.max(g, 0);
                        b2 = (int)Math.min(b2, 255);
                        b2 = (int)Math.max(b2, 0);
                        img2.setRGB(x, y, new Color(r, g, b2).getRGB());
                    }
                }
                Lafter.setIcon(new ImageIcon(img2));
            }
        });
        setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("불러오기")){
            System.out.println("불러오기");
            FileNameExtensionFilter filter = (new FileNameExtensionFilter("jpg, jpeg, png", "jpg", "jpeg", "png"));
            inputChooser.setMultiSelectionEnabled(false);        //다중 선택 불가
            inputChooser.setFileFilter(filter);
            if(inputChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
                img = FileToBufferedImage();
                Lbefore.setIcon(new ImageIcon(img));
            }
        }
        if(e.getActionCommand().equals("흑백")){
            img = FileToBufferedImage();
            img = GrayImage(img);
            Lafter.setIcon(new ImageIcon(img));
        }
        if(e.getActionCommand().equals("밝기")){
            img = FileToBufferedImage();
            img = LightImage(img);
            Lafter.setIcon(new ImageIcon(img));
        }
        if(e.getActionCommand().equals("대비")){
            img = FileToBufferedImage();
            for(int y = 0; y < img.getHeight(); y++) {
                for(int x = 0; x < img.getWidth(); x++) {
                    Color color = new Color(img.getRGB(x, y));
                    int r = color.getRed();
                    int g = color.getGreen();
                    int b2 = color.getBlue();
                    img.setRGB(x, y, new Color(r, g, b2).getRGB());
                }
            }
            Lafter.setIcon(new ImageIcon(img));
        }
        if(e.getActionCommand().equals("Smoothing")){
            img = FileToBufferedImage();
            img = SmootingImage(img, -2, 3);
            Lafter.setIcon(new ImageIcon(img));
        }
        if(e.getActionCommand().equals("edge")){
            img = FileToBufferedImage();
            img = GrayImage(img);
            img = SmootingImage(img, -1, 2);
            int[][] height = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};
            int[][] width = {{-1,-2,-1}, {0, 0, 0}, {1, 2, 1}};
            int[][] height1 = new int[img.getWidth()][img.getHeight()];
            int[][] width1 = new int[img.getWidth()][img.getHeight()];
            BufferedImage outputImage1 = new BufferedImage(img.getWidth(), img.getHeight(), Image.SCALE_SMOOTH);
            int r, g;
            r = g = 0;
            for(int y = 0; y < outputImage1.getHeight(); y++){
                for(int x = 0; x < outputImage1.getWidth(); x++){
                    for(int i = -1; i < 2; i++){
                        for(int j = -1; j < 2; j++){
                            if(x + i > 0 && y + j > 0 && x + i < outputImage1.getWidth() && y + j < outputImage1.getHeight()){
                                Color color = new Color(img.getRGB(x + i, y + j));
                                r += (color.getRed() * height[i + 1][j + 1]);
                                g += (color.getRed() * width[i + 1][j + 1]);
                            }
                        }
                    }
                    height1[x][y] = r * r;
                    width1[x][y] = g * g;
                    double ee = Math.sqrt(height1[x][y] + width1[x][y]);
                    if(ee > 100)
                        ee = 0;
                    else
                        ee = 255;
                    outputImage1.setRGB(x, y, new Color((int)ee, (int)ee, (int)ee).getRGB());
                    r = g = 0;
                }
            }
            img = outputImage1;
            Lafter.setIcon(new ImageIcon(outputImage1));
        }
        if(e.getActionCommand().equals("저장하기")){
            outputChooser.setFileFilter(new FileNameExtensionFilter("jpg, jpeg, png", "jpg", "jpeg", "png"));
            outputChooser.setMultiSelectionEnabled(false);
            if(outputChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION){
                try{
                    System.out.println(outputChooser.getSelectedFile().getAbsolutePath());
                    File file = new File(outputChooser.getSelectedFile().getAbsolutePath() + ".jpg");
                    ImageIO.write(img, "jpg", file);
                }catch (Exception a){
                    a.printStackTrace();
                }
            }
        }
    }
    public BufferedImage imageToBufferedImage(Image im) {
        BufferedImage bi = new BufferedImage
                (im.getWidth(null),im.getHeight(null),BufferedImage.TYPE_INT_RGB);
        Graphics bg = bi.getGraphics();
        bg.drawImage(im, 0, 0, null);
        bg.dispose();
        return bi;
    }
    public BufferedImage FileToBufferedImage(){
        try{
            File file = new File(inputChooser.getSelectedFile().getAbsolutePath());
            Image readimage = ImageIO.read(file);
            readimage = readimage.getScaledInstance(500, 645, Image.SCALE_DEFAULT);
            img = imageToBufferedImage(readimage);
        }catch (Exception a){
            a.printStackTrace();
        }
        return img;
    }
    public BufferedImage GrayImage(BufferedImage img){
        for(int y = 0; y < img.getHeight(); y++) {
            for(int x = 0; x < img.getWidth(); x++) {
                Color colour = new Color(img.getRGB(x, y));
                int Y = (int) (0.2126 * colour.getRed() + 0.7152 * colour.getGreen() + 0.0722 * colour.getBlue());
                img.setRGB(x, y, new Color(Y, Y, Y).getRGB());
            }
        }
        return img;
    }
    public BufferedImage LightImage(BufferedImage img){
        for(int y = 0; y < img.getHeight(); y++) {
            for(int x = 0; x < img.getWidth(); x++) {
                Color color = new Color(img.getRGB(x, y));
                int r = color.getRed();
                int g = color.getGreen();
                int b2 = color.getBlue();
                img.setRGB(x, y, new Color(r, g, b2).getRGB());
            }
        }
        return img;
    }
    public BufferedImage SmootingImage(BufferedImage img, int start, int end){
        int[][] red = new int[img.getWidth()][img.getHeight()];
        int[][] green = new int[img.getWidth()][img.getHeight()];
        int[][] blue = new int[img.getWidth()][img.getHeight()];
        int r, g, b, a;
        r = g = b = a= 0;
        BufferedImage outputImage = new BufferedImage(img.getWidth(), img.getHeight(), Image.SCALE_SMOOTH);
        for(int y = 0; y < img.getHeight(); y++) {
            for(int x = 0; x < img.getWidth(); x++) {
                for(int i = start; i < end; i++){
                    for(int j = start; j < end; j++){
                        if(x + i > 0 && y + j > 0 && x + i < img.getWidth() && y + j < img.getHeight()){
                            a++;
                            Color color = new Color(img.getRGB(x + i, y + j));
                            r += color.getRed();
                            g += color.getGreen();
                            b += color.getBlue();
                        }
                    }
                }
                red[x][y] = r / a;
                green[x][y] = g / a;
                blue[x][y] = b / a;
                outputImage.setRGB(x, y, new Color(red[x][y], green[x][y], blue[x][y]).getRGB());
                a = r = g = b = 0;
            }
        }
        return outputImage;
    }
    public static void main(String[] args) {
        new imagecom();
    }
}