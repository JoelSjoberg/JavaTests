import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class ReadSound {

	public static void main(String[] args) {
		
		AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, true);
		
		File file = new File("src/ff.wav");
		File result = new File("src/res.wav");
				
		try {
			DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
			final TargetDataLine targetLine = (TargetDataLine) AudioSystem.getLine(info);
			targetLine.open();
			
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
			AudioInputStream newStream;
			newStream = new AudioInputStream(targetLine);
			
			System.out.println(audioStream.getFrameLength());
			
			int bpf = audioStream.getFormat().getFrameSize();
			if (bpf == AudioSystem.NOT_SPECIFIED)
			{
				bpf = 1;
			}
			int totalFramesRead = 0;
			int bytesRead;
			int framesRead;
			byte[] audio = new byte[1024 * bpf];
			AudioFileFormat.Type fileType = AudioSystem.getAudioFileFormat(file).getType();
			
			while(true)
			{
				bytesRead = audioStream.read(audio);
				framesRead = bytesRead / bpf;
			    totalFramesRead += framesRead;

				if (bytesRead == -1){
					System.out.println(totalFramesRead);
					break;	
				}
				
				//manipulateBytes(audio);
				
				targetLine.read(audio, 0, audio.length);

				//AudioSystem.write(newStream, fileType, result);
				AudioSystem.write(audioStream, fileType, result);
			}
			System.out.println("Done writing");
			
			
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	static void manipulateBytes(byte[] arr){
		for(int i = 0; i < arr.length; i++)
		{
			arr[i] = (byte) (Math.random() * 127);
		}
	}
}