
import com.github.sarxos.webcam.Webcam;
//import com.github.sarxos.webcam.WebcamResolution;
//import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
//import java.net.ServerSocket;
import java.net.Socket;
//import static com.github.sarxos.webcam.Webcam.*;
@SuppressWarnings("serial")
public class CamClient {
    static int port=55004;

    public static void main (String[] args) throws IOException
    {
        Webcam wCam = null;

        try {

            //LOCALHOST
            DataOutputStream sender = new DataOutputStream(new BufferedOutputStream(new Socket("127.0.0.1", port).getOutputStream()));
            //wCam = Webcam.getDefault();
            wCam = Webcam.getWebcams().get(0);

            //wCam.setViewSize(new Dimension(1024,768));
            //wCam.setViewSize(WebcamResolution.VGA.getSize());
            wCam.setViewSize(new Dimension(320,240));

            wCam.open();
            while (true)
            {
                BufferedImage frame = wCam.getImage(); //get frame from webcam

                int frameWidth = frame.getWidth();
                int frameHeight = frame.getHeight();

                sender.writeInt(frameWidth);
                sender.writeInt(frameHeight);

                int[] pixelData = new int[frameWidth * frameHeight];
                frame.getRGB(0, 0, frameWidth, frameHeight, pixelData, 0, frameWidth);

                for (int i = 0; i < pixelData.length; i++)
                {
                    sender.writeInt(pixelData[i]);
                }
            }
        } catch(Exception ex ){
            System.out.println("***** "+ex.getMessage()+" *****") ;
        }finally {

            //release resources used by library
            if (wCam != null)
            {
                wCam.close();
            }
        }
    }


}