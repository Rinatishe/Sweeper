import sweeper.Box;
import sweeper.Coord;
import sweeper.Game;
import sweeper.Ranges;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Sweeper extends JFrame {
    private Game game;

    private JPanel panel;// переменная
    private JLabel label;
    private final int cols = 9;//количество столббцов
    private final int rows = 9;//количество строк
    private final int bombs = 10;//количество строк
    private final int image_size = 50;//размер картинки

    public static void main(String[] args) {
        new Sweeper();
    }

    private Sweeper(){
        game = new Game(cols,rows,bombs);
        game.start();
        setImages();
        initLabel();
        initPanel();
        initFrame();
    }

    private void initLabel(){
        label = new JLabel("Welcome");
        add(label, BorderLayout.SOUTH);
    }

    //метод инициализации панели
    private void initPanel(){
        panel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (Coord coord:Ranges.getAllCoord())
                {
                    g.drawImage((Image) game.getBox(coord).image,
                            coord.x* image_size, coord.y*image_size, this);
                }
            }
        };

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX()/image_size;
                int y = e.getY()/image_size;
                Coord coord = new Coord(x,y);
                if(e.getButton() == MouseEvent.BUTTON1)
                    game.pressLeftButton (coord);
                if(e.getButton() == MouseEvent.BUTTON3)
                    game.pressRightButton (coord);
                if(e.getButton() == MouseEvent.BUTTON2)
                    game.start();
                label.setText(getMassege());
                panel.repaint();
            }
        });
        panel.setPreferredSize(new Dimension(
                Ranges.getSize().x*image_size,
                Ranges.getSize().y*image_size)); //размер панели
        add(panel);//метод добавления панели в форму
    }

    private String getMassege() {
        switch (game.getState()){
            case PLAYED: return "Think twice";
            case BOMBED: return "You Lose!Big-ba-da-boom";
            case WINNER: return "You Win!";
            default: return "ola";
        }
    }

    private void initFrame(){

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //при закрытии программы останваливать прогу
        setTitle("Sweeper");// заголовок окна
        setResizable(false);// нельзя менять размер окна
        setVisible(true);//видность формы
        pack(); // метод который устанавливает минимальный размер контейнера, который достаточен для отображения всех компонентов
        setIconImage(getImage("icon"));//добавление иконки окну
        setLocationRelativeTo(null); //расположение окна по центу
    }

    private void setImages(){
        for (Box box : Box.values())
            box.image = getImage(box.name().toLowerCase());
    }
    // функция для получения и установке картинок
    private Image getImage (String name){
        String filename = "img/" + name + ".png";//имя файла для картинки
        ImageIcon icon = new ImageIcon(getClass().getResource(filename));//создаем объект из файла
        return icon.getImage();
    }
}
