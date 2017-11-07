import java.awt.Color;
import java.awt.Graphics;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.Mixer.Info;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ManipulateSound extends JPanel{

	static AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, false);
	static Info[] mixers;
	static Mixer m;
	static byte[] data;
	public static byte[] xs;
	public ManipulateSound(){
		
	}
	
	public void paintComponent(Graphics g){
		try{
			g.clearRect(0, 0, 1500, 1500);
			g.setColor(Color.black);
			for(int i = 0; i < xs.length - 1; i+= 1){
				//g.drawLine(i, xs[i] + 250, i + 1, xs[i+1] + 250);
				g.fillRect(i, 250, 1, Math.abs(xs[i]) * -1);
				//g.drawOval(i, xs[i] + 250, 1, 1);
			}/*
			for(int i = 1500; i < 3000; i+= 7){
				g.drawLine(i - 1500, xs[i] + 550, i + 1 - 1500, xs[i+1] + 550);
			}*/
			/*int x = 0;
			for(int i = 0; i < 4095; i+= 10){
				int val = (xs[i] + xs[i+1] + xs[i+2] + xs[i + 3]) / 3;
				g.fillRect(x, 250, 10, Math.abs(val) * -1);
				x+= 5;
			}*/
			
		}catch(NullPointerException np){}
		
	}
	
	static Random rand = new Random();
	
	public static void fillArray (byte[] arr) 
	{
		//rand.nextBytes(arr);
		for(int i = 0; i < arr.length; i++){
			arr[i] = (byte) (i - 127);
			//System.out.println(arr[i]);
		}
	}
	static int testLim = 15;
	static void manipulateBytes(byte[] arr){
		for(int i = arr.length - 1; i > -1; i--)
		{
			arr[i] = (byte) arr[arr.length - i - 1];
		}
		if(Math.abs(arr[0]) > testLim) arr[0] = (byte) testLim;
		for(int i = 0; i < arr.length; i++){
			testLim = (byte) (testLim + 1);
			
			/*if(i > 0){
				if(arr[i] > arr[i - 1]){
					arr[i] = (byte) (arr[i] - arr[i - 1] - arr[i]);					
				}else{
					arr[i] = (byte) (arr[i] - arr[i - 1] + arr[i]);
				}
			}*/
			if(Math.abs(arr[i]) > testLim && i > 0){
				arr[i] = arr[i-1];
			}
		}
	}
	public static void main(String[] args) {
		
		
		mixers = AudioSystem.getMixerInfo();
		m = AudioSystem.getMixer(mixers[4]);
		
		
		JFrame frame = new JFrame();
		long time = System.nanoTime();
		long sleep = 5000000;
		frame.add(new ManipulateSound());
		frame.pack();
		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		File file = new File("src/ff.wav");
		File data = new File("src/byteArrays.txt");
		PrintWriter writer;
		try {
			writer = new PrintWriter(data);
			writer.print("");
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		try{
			
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
			DataOutputStream out = new DataOutputStream(new FileOutputStream(data));
			DataInputStream in = new DataInputStream(new FileInputStream(data));
			
			DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
			final SourceDataLine sourceLine = (SourceDataLine) AudioSystem.getLine(info);
			sourceLine.open();

			int stream_size = 500;
			byte[] audio = new byte[stream_size];
			byte[] lutchiou = new byte[stream_size];
			int bytesRead;
			
			sourceLine.start();
			while(true)
			{
				
				bytesRead = audioStream.read(audio);
				if (bytesRead == -1){
					System.out.println("End of file");
					break;
				}
					out.write(audio);
					in.read(lutchiou);
					//manipulateBytes(lutchiou);
					xs = lutchiou;
					//System.out.println(Arrays.toString(audio));
					sourceLine.write(lutchiou, 0, lutchiou.length);
					frame.repaint();
			}
			out.close();
			in.close();
			audioStream.close();
		}catch(LineUnavailableException | UnsupportedAudioFileException | IOException lue) {lue.printStackTrace();}
	}
}