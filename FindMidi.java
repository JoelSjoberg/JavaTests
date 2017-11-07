import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

public class FindMidi {

	public static void main(String[] args) {
		MidiDevice[] synthinfos = new MidiDevice[50];
		MidiDevice device = null;
		MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
		for(int i = 0; i < infos.length; i++){
			try {
				device = MidiSystem.getMidiDevice(infos[i]);
				System.out.println(device);
				if(device instanceof Synthesizer){
					synthinfos[i] = device;
				}
			} catch (MidiUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
