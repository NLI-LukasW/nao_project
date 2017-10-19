package nao.main;

import java.nio.ByteBuffer;
import java.util.List;

import org.opencv.core.CvType;
import org.opencv.core.Mat;

import com.aldebaran.qi.Application;
import com.aldebaran.qi.helper.ALProxy;
import com.aldebaran.qi.helper.proxies.ALTextToSpeech;
import com.aldebaran.qi.helper.proxies.ALVideoDevice;

public class NaoRun {

	private static final String NAO_CAMERA_NAME = "Nao Image";

	public static void main(String[] args) throws Exception {
		String robotUrl = "tcp://127.0.0.1:9559";
		// Create a new application
		Application application = new Application(args, robotUrl);
		// Start your application
		application.start();
		// Create an ALTextToSpeech object and link it to your current session
		ALTextToSpeech tts = new ALTextToSpeech(application.session());
		// Make your robot say something
		tts.say("Hello World!");
		ALVideoDevice alvd = new ALVideoDevice(application.session());
		alvd.subscribeCamera(NAO_CAMERA_NAME, 0, 1, 9, 30);
		List<Object> imageRemote = (List<Object>) alvd.getImageRemote(NAO_CAMERA_NAME);
		ByteBuffer b = (ByteBuffer) imageRemote.get(6);
		Mat image = new Mat((int) imageRemote.get(2), (int) imageRemote.get(1), CvType.CV_8UC3);
		image.put(0, 0, b.array());
		alvd.unsubscribe(NAO_CAMERA_NAME);
	}

}
