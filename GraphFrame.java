/*
 This Frame will graph the students grade
Jan 10th 2020
 */
//package comsci;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JFrame;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class GraphFrame extends JFrame
{

    private final Color BKGROUND_COLOR = new Color(100, 99, 200);
    private Object[][] studentData;
    private Image drawingImage;
    private int[] score;
    private double scoreFactor = 0;
    private double average = 0;

    public void getScore()
    {//get score from db
        score = new int[studentData.length];
        for (int i = 0; i < studentData.length; i++)
        {
            score[i] = Integer.parseInt(
                    studentData[i][studentData[0].length - 1].toString());
            if (scoreFactor < score[i])
            {
                scoreFactor = score[i];
            }
        }
        scoreFactor += 10 + scoreFactor / 20;
        for (int i = 0; i < score.length; i++)
        {
            average += score[i];
        }
        average /= score.length;
    }

    public void drawInformation()
    {
//draw data
        drawingImage = this.createImage(this.getWidth(), this.getHeight());
        Graphics graphics = drawingImage.getGraphics();
        graphics.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        graphics.setColor(BKGROUND_COLOR);
        graphics.fillRect(0, 0, this.getWidth(), this.getHeight());
        graphics.setColor(Color.BLACK);
        // All based on the test 
        graphics.drawLine(30, 10, 30, this.getHeight() - 30);
        graphics.drawLine(10, this.getHeight() - 50,
                this.getWidth() - 20, this.getHeight() - 50);
        graphics.drawString("Score", 80, 50);
        graphics.drawString("Name", this.getWidth() - 40, this.getHeight() - 30);
        int distant = (this.getHeight() - 20) / (studentData.length);
        for (int i = 0; i < studentData.length; i++)
        {
            graphics.drawString(
                    studentData[i][0].toString(), distant * i + distant / 2, this.getHeight() - 20);
            graphics.fillRect(i * distant + distant / 2 - 10,
                    (int) (this.getHeight() - score[i]
                    / scoreFactor * (this.getHeight() - 80)) - 60, 20, 20);
        }
        graphics.setColor(Color.RED);
        for (int i = 1; i < studentData.length; i++)
        {
            graphics.drawString(studentData[i][studentData[0].length - 1]
                    .toString(), distant * i + distant / 2,
                    (int) (this.getHeight() - score[i]
                    / scoreFactor * (this.getHeight() - 80)) - 80);
            graphics.drawLine(i * distant - distant / 2,
                    (int) (this.getHeight() - score[i - 1]
                    / scoreFactor * (this.getHeight() - 80)) - 50,
                    i * distant + distant / 2,
                    (int) (this.getHeight() - score[i]
                    / scoreFactor * (this.getHeight() - 80)) - 50);
        }
        graphics.drawString(
                studentData[0][studentData[0].length - 1].toString(), distant / 2,
                (int) (this.getHeight() - score[0]
                / scoreFactor * (this.getHeight() - 80)) - 80);
        graphics.drawString("Average", 35,
                (int) (this.getHeight() - average
                / scoreFactor * (this.getHeight() - 80)) - 80);
        graphics.drawLine(35,
                (int) (this.getHeight() - average
                / scoreFactor * (this.getHeight() - 80)) - 50,
                this.getWidth() - 100,
                (int) (this.getHeight() - average
                / scoreFactor * (this.getHeight() - 80)) - 50);
        this.repaint();
    }

    public GraphFrame(Object[][] studentData)
    {
        super("Graph Frame");
        this.setBounds(100, 500, 1000, 600);
        this.getContentPane().setBackground(BKGROUND_COLOR);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.studentData = studentData;
        //Coustruct Button
        //add Label
        this.setVisible(true);
        getScore();
        drawInformation();
    }

    // find if the button pressed or not
    @Override

    public void paint(Graphics g)
    {//to draw the image
        super.paint(g);
        g.drawImage(drawingImage, 0, 0, null);

    }

    public static void main(String[] args)
    {
        //new GraphFrame();
    }

}
