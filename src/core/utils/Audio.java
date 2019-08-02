package core.utils;

import org.lwjgl.openal.*;
import org.lwjgl.system.*;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.function.Consumer;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.stb.STBVorbis.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.libc.LibCStdlib.*;

public class Audio {

	private static HashMap<String, Integer> sounds = new HashMap<>();

	private static long	device;
	private static long	context;

	private static Collection<Integer> activeSounds = new ArrayList<>();

	public static void init() {
		String defaultDeviceName = alcGetString(0, ALC_DEFAULT_DEVICE_SPECIFIER);
		device            = alcOpenDevice(defaultDeviceName);

		int[] attributes = {0};
		context    = alcCreateContext(device, attributes);
		alcMakeContextCurrent(context);

		ALCCapabilities alcCapabilities = ALC.createCapabilities(device);
		@SuppressWarnings("unused") ALCapabilities  alCapabilities  = AL.createCapabilities(alcCapabilities);

	}

	public static void load() {
		File folder = new File("./res/audio/");
		for (File file : folder.listFiles(new FilenameFilter() {

			@Override public boolean accept(File arg0, String arg1) {
				return arg1.endsWith(".ogg");
			}

		})) {
			load(file);
		}
	}

	private static void load(File file) {
		ShortBuffer rawAudioBuffer;

		int channels;
		int sampleRate;

		try (MemoryStack stack = stackPush()) {
		    //Allocate space to store return information from the function
		    IntBuffer channelsBuffer   = stack.mallocInt(1);
		    IntBuffer sampleRateBuffer = stack.mallocInt(1);

		    rawAudioBuffer = stb_vorbis_decode_filename(file.getPath(), channelsBuffer, sampleRateBuffer);

		    //Retreive the extra information that was stored in the buffers by the function
		    channels = channelsBuffer.get(0);
		    sampleRate = sampleRateBuffer.get(0);
		}

		//Find the correct OpenAL format
		int format = -1;
		if (channels == 1) {
		    format = AL_FORMAT_MONO16;
		} else if (channels == 2) {
		    format = AL_FORMAT_STEREO16;
		}

		//Request space for the buffer
		int bufferPointer = alGenBuffers();

		//Send the data to OpenAL
		alBufferData(bufferPointer, format, rawAudioBuffer, sampleRate);

		//Free the memory allocated by STB
		free(rawAudioBuffer);
		sounds.put(file.getName().replace(".ogg", ""), bufferPointer);
	}

	private static int[] getPointers(String sound) {
		int bufferPointer = sounds.get(sound);
		int sourcePointer = alGenSources();
		return new int[]{bufferPointer,sourcePointer};
	}
	
	public static void playSound(String sound) {
		int[] pointers = getPointers(sound);

		// Assign our buffer to the source
		alSourcei(pointers[1], AL_BUFFER, pointers[0]);
		alSourcePlay(pointers[1]);
		activeSounds.add(pointers[1]);
	}
	
	public static void playSound(String sound, Consumer<int[]> run) {
		int[] pointers = getPointers(sound);

		// Assign our buffer to the source
		alSourcei(pointers[1], AL_BUFFER, pointers[0]);
		run.accept(pointers);
		alSourcePlay(pointers[1]);
		activeSounds.add(pointers[1]);
	}
	
	public static void setGain(float gain,int[] pointers) {
		alSourcef(pointers[1],AL_GAIN,gain);
	}
	
	public static void setLooping(boolean looping, int[] pointers) {
		alSourcef(pointers[1], AL_LOOPING, (looping ? 1:0));
	}
	
	public static void setPitch(float pitch,int[] pointers) {
		alSourcef(pointers[1],AL_PITCH,pitch);
	}
	
	public static void update() {
		for(int sourcePointer : activeSounds) {
			int[] state = {0};
			alGetSourcei(sourcePointer, AL_SOURCE_STATE, state);
			if(state[0]==AL_STOPPED) {
				alDeleteSources(sourcePointer);
			}
		}
	}

	public static void destroy() {
		for(int bufferPointer : sounds.values()) {
			alDeleteBuffers(bufferPointer);
		}
		alcDestroyContext(context);
		alcCloseDevice(device);
	}

}
