package Watermelon;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;





/** *//**
 * @author Administrator
 *
 */
public class test extends Thread{
    /** *//**
     *
     */
    private AudioInputStream ais;
    private String fileUrl="123.wav";

    public void run()  {
     
                
                try {
                    ais=AudioSystem.getAudioInputStream(new File(fileUrl));
                    AudioFormat af=ais.getFormat();
                    SourceDataLine sdl=null;
                    DataLine.Info info=new DataLine.Info(SourceDataLine.class,af);
                    sdl=(SourceDataLine) AudioSystem.getLine(info);
                    sdl.open(af);
                    sdl.start();
                    
                    //play
                    
                    int nByte=0;
                    byte[] buffer=new byte[128];
                    while(nByte!=-1) {
                        nByte=ais.read(buffer,0,128);
                        if(nByte>=0) {
                            int oByte=sdl.write(buffer, 0, nByte);                 
                        }
                    }
                    sdl.stop();
                    
                } catch (UnsupportedAudioFileException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (LineUnavailableException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                
         }
            
}