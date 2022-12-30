import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class imagecom extends JFrame implements ActionListener, ChangeListener {
    before panel = new before();
    after panel2 = new after();
    JLabel Lafter = new JLabel();
    JButton[] btn = new JButton[5];
    JFileChooser chooser = new JFileChooser();
    Image readimage = null;
    BufferedImage img;
    JSlider b;
    imagecom(){
        setSize(1200, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("영상 처리");
        setLocationRelativeTo(null);

        setLayout(null);

        b = new JSlider(0, 200, 120);
        b.setPaintTicks(true);
        b.setPaintTicks(true);
        //b.setPaintLabels(true);

        b.setMajorTickSpacing(50);
        //b.setMinorTickSpacing(5);
        b.setBackground(Color.white);

        b.setOrientation(SwingConstants.VERTICAL);
        add(b);
        b.setBounds(1040, 260, 40, 150);

        btn[0] = new JButton("불러오기");
        btn[1] = new JButton("저장하기");
        btn[2] = new JButton("흑백");
        btn[3] = new JButton("밝기");
        btn[4] = new JButton("대조");

        panel.setBounds(10,10,500,645);
        panel2.setBounds(520, 10, 500, 645);
        btn[0].setBounds(1040, 10, 127, 40);
        btn[1].setBounds(1040, 60, 127, 40);
        btn[2].setBounds(1040, 110, 127, 40);
        btn[3].setBounds(1040, 160, 127, 40);
        btn[4].setBounds(1040, 210, 127, 40);

        getContentPane().setBackground(Color.white);
        panel2.setBackground(Color.yellow);
        panel.setBackground(Color.PINK);
        btn[0].setBackground(Color.pink);
        btn[1].setBackground(Color.PINK);
        btn[2].setBackground(Color.pink);
        btn[3].setBackground(Color.pink);
        btn[4].setBackground(Color.pink);

        add(panel);
        add(panel2);
        add(btn[0]);
        add(btn[1]);
        add(btn[2]);
        add(btn[3]);
        add(btn[4]);

        btn[0].addActionListener(this);
        btn[1].addActionListener(this);
        btn[2].addActionListener(this);
        btn[3].addActionListener(this);
        btn[4].addActionListener(this);

        b.addChangeListener(this);

        setVisible(true);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        Image imgcopy = img;
        BufferedImage img2 = imageToBufferedImage(imgcopy);
        b = (JSlider)e.getSource();
        for(int y = 0; y < img2.getHeight(); y++) {
            for(int x = 0; x < img2.getWidth(); x++) {
                Color color = new Color(img2.getRGB(x, y));
                int r = (int)Math.min(color.getRed() + b.getValue(), 255);
                int g = (int)Math.min(color.getGreen()+b.getValue(), 255);
                int b2 = (int)Math.min(color.getBlue()+b.getValue(), 255);
                img2.setRGB(x, y, new Color(r, g, b2).getRGB());
            }
        }
        Lafter.setIcon(new ImageIcon(img2));
    }

    class before extends JPanel{
        before(){

        }
        public void paint(Graphics g) {
            super.paint(g);
            g.drawImage(readimage, 0, 0, 500, 645, this);
        }
    }

    class after extends JPanel{
        after(){

        }
        public void paint(Graphics g){
            super.paint(g);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("불러오기")){
            System.out.println("불러오기");
            FileNameExtensionFilter filter = (new FileNameExtensionFilter("jpg, jpeg, png", "jpg", "jpeg", "png"));
            chooser.setMultiSelectionEnabled(false);        //다중 선택 불가
            chooser.setFileFilter(filter);
            if(chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
                readimage = new ImageIcon(chooser.getSelectedFile().getAbsolutePath()).getImage();
                panel.repaint();
            }
        }
        if(e.getActionCommand().equals("흑백")){
            try{
                File file = new File(chooser.getSelectedFile().getAbsolutePath());
                System.out.println(chooser.getSelectedFile().getAbsolutePath());
                Image image = ImageIO.read(file);
                image = image.getScaledInstance(500, 645, Image.SCALE_DEFAULT);
                img = imageToBufferedImage(image);
            }catch (Exception a){
                a.printStackTrace();
            }
            for(int y = 0; y < img.getHeight(); y++) {
                for(int x = 0; x < img.getWidth(); x++) {
                    Color colour = new Color(img.getRGB(x, y));
                    int Y = (int) (0.2126 * colour.getRed() + 0.7152 * colour.getGreen() + 0.0722 * colour.getBlue());
                    img.setRGB(x, y, new Color(Y, Y, Y).getRGB());
                }
            }
            panel2.add(Lafter);
            Lafter.setIcon(new ImageIcon(img));
        }
        if(e.getActionCommand().equals("밝기")){
            try{
                File file = new File(chooser.getSelectedFile().getAbsolutePath());
                Image image = ImageIO.read(file);
                image = image.getScaledInstance(500, 645, Image.SCALE_DEFAULT);
                img = imageToBufferedImage(image);
            }catch (Exception a){
                a.printStackTrace();
            }
            for(int y = 0; y < img.getHeight(); y++) {
                for(int x = 0; x < img.getWidth(); x++) {
                    Color color = new Color(img.getRGB(x, y));
                    int r = (int)Math.min(color.getRed(), 255);
                    int g = (int)Math.min(color.getGreen(), 255);
                    int b2 = (int)Math.min(color.getBlue(), 255);
                    img.setRGB(x, y, new Color(r, g, b2).getRGB());
                }
            }
            panel2.add(Lafter);
            Lafter.setIcon(new ImageIcon(img));
        }
        if(e.getActionCommand().equals("대조")){
            try{
                File file = new File(chooser.getSelectedFile().getAbsolutePath());
                Image image = ImageIO.read(file);
                image = image.getScaledInstance(500, 645, Image.SCALE_DEFAULT);
                img = imageToBufferedImage(image);
            }catch (Exception a){
                a.printStackTrace();
            }
            for(int y = 0; y < img.getHeight(); y++) {
                for(int x = 0; x < img.getWidth(); x++) {
                    Color color = new Color(img.getRGB(x, y));
                    System.out.println("aaaa");
                    int r = (color.getRed() > 128) ? color.getRed() + b.getValue() : color.getRed() - b.getValue();
                    int g = (color.getGreen() > 128) ? color.getGreen() + b.getValue() : color.getGreen() - b.getValue();
                    int b2 = (color.getBlue() > 128) ? color.getBlue() + b.getValue() : color.getBlue() - b.getValue();
                    if(r > 255){

                    }
                    r = (int)Math.min(r, 255);
                    r = (int)Math.max(r,0);
                    g = (int)Math.min(g, 255);
                    g = (int)Math.max(g,0);
                    b2 = (int)Math.min(b2, 255);
                    b2 = (int)Math.max(b2,0);
                    img.setRGB(x, y, new Color(r, g, b2).getRGB());
                }
            }
            panel2.add(Lafter);
            Lafter.setIcon(new ImageIcon(img));
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
    public static void main(String[] args) {
        new imagecom();
    }
}
