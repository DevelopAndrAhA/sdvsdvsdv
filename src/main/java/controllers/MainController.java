package controllers;


/*import javafx.util.Pair;
import org.springframework.context.event.ContextRefreshedEvent;*/
import neural_network.FaceRecognizer;
import neural_network.models.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import service.MyServiceClass;

/*import javax.annotation.PostConstruct;*/
import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;


@Controller
@RequestMapping(value = "api")
public class MainController {
	@Autowired
	MyServiceClass service;
	@Autowired
	ServletContext servletContext;

	List<FullFaceFeatures> fullFaceFeatures = new ArrayList<FullFaceFeatures>();
	FaceRecognizer faceRecognizer = new FaceRecognizer();



	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)
	public String getData(){
		return "hello";
	}

	@ResponseBody
	@RequestMapping(value = "new_face",method = RequestMethod.POST)
	public Object add(
			@RequestParam("crop") MultipartFile crop,
			@RequestParam("largePohto") MultipartFile largePohto,
			@RequestParam("username")String username,
			@RequestParam("lng")String lng,
			@RequestParam("lat")String lat) {
		String photoName = null;
		Date date = new Date();

		long currTime = date.getYear()+date.getDay()+date.getMonth()+date.getTime();
		File theDir = new File(FileUtils.getUserDirectory()+File.separator+"images");
		if (!theDir.exists()){
			theDir.mkdir();
		}
		try{
			byte[] bytes = largePohto.getBytes();
			String rootPath = theDir.getAbsolutePath();
			photoName = currTime+".jpg";
			File serverFile = new File(rootPath+ File.separator +  photoName);
			FileOutputStream fileOutputStream = new FileOutputStream(serverFile);
			BufferedOutputStream stream = new BufferedOutputStream(fileOutputStream);
			stream.write(bytes);
			fileOutputStream.close();
			stream.flush();
			stream.close();


			BufferedImage bufferedImage = resize(largePohto);
			File outputfile = new File(rootPath+File.separator+currTime+"_SMALL"+".jpg");
			try{
				ImageIO.write(bufferedImage, "jpg", outputfile);
			}catch (Exception e){e.printStackTrace();}


			FaceFeatures faceFeatures = faceRecognizer.addNew(crop);

			FullFaceFeatures features = new FullFaceFeatures(username);
			features.setFaceFeatures(1,faceFeatures);
			features.setIdentifier(new Date().getSeconds());
			features.setPhotoName(currTime+"");
			faceFeatures.setLng(Double.parseDouble(lng));
			faceFeatures.setLat(Double.parseDouble(lat));
			faceFeatures.setFullFaceFeatures(features);

			fullFaceFeatures.add(features);
			service.save(features);
			ResultContainer resultContainer = new ResultContainer();
			resultContainer.setStatus(200);
			resultContainer.setDesc("success");
			return resultContainer;

		}catch (Exception e){e.printStackTrace();}



		return "errrror";


	}


	@ResponseBody
	@RequestMapping(value = "search",method = RequestMethod.POST)
	public Object search(@RequestParam("file") MultipartFile multipartFile,@RequestParam("percent")float percent,@RequestParam("inpDate")String inpDate) {
		java.sql.Date date = java.sql.Date.valueOf(inpDate);
		List<Prediction> prediction = faceRecognizer.searchFromPool(multipartFile,fullFaceFeatures,percent,date);
		if(prediction!=null){
			return prediction;
		}
		ResultContainer resultContainer = new ResultContainer();
		resultContainer.setStatus(404);
		resultContainer.setDesc("Нет данных");
		return resultContainer;
	}


	@ResponseBody
	@RequestMapping(value = "image", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	public  byte[]  getPhoto(@RequestParam("imgname")String imgname){
		try {
			String rootPath = FileUtils.getUserDirectory()+File.separator+"images"+File.separator+imgname;

			InputStream in = null;
			File f = new File(rootPath);
			in = new FileInputStream(f);
			return IOUtils.toByteArray(in);
		}catch (Exception e){}
		return null;
	}




	@ResponseBody
	@RequestMapping(value = "getInitData",method = RequestMethod.GET)
	public Object getFirstData(@RequestParam("lat")String lat,@RequestParam("lng")String lng){
		List<ResponseModel>list = service.getFullFeatures(Double.parseDouble(lat),Double.parseDouble(lng));
		return list;
	}

	/*@ResponseBody
	@RequestMapping(value = "perc",method = RequestMethod.GET)
	public String perc(){
		Init init = new Init();

		double eva1[] = init.getEva1();
		double eva2[] = init.getEva2();
		double eva3[] = init.getEva3();
		double nic1[] = init.getNicole1();
		double nic2[] = init.getNicole2();

		HashMap hashMap = new HashMap();

		hashMap.put("eva1",eva1);
		hashMap.put("nic2",nic2);
		hashMap.put("eva2",eva2);
		hashMap.put("eva3",eva3);
		hashMap.put("nic1",nic1);



		*//*Pair p = l2_search(hashMap,nic1);
		System.out.printf(p.getKey()+"");*//*

		matchTwoFeatureArrays(eva1, eva1);
		matchTwoFeatureArrays(eva1, eva2);
		matchTwoFeatureArrays(eva1, eva3);


		matchTwoFeatureArrays(eva2, eva1);
		matchTwoFeatureArrays(eva2, eva2);
		matchTwoFeatureArrays(eva2, eva3);


		matchTwoFeatureArrays(eva3, eva1);
		matchTwoFeatureArrays(eva3, eva2);
		matchTwoFeatureArrays(eva3, eva3);

		matchTwoFeatureArrays(nic1, nic2);

		return "string";
	}*/

	public BufferedImage resize(MultipartFile photo) {


		BufferedImage bufferedImage = null;
		try {
			byte [] bytes = photo.getBytes();
			InputStream is = new ByteArrayInputStream(bytes);
			bufferedImage = ImageIO.read(is);
		}catch (Exception e){}

		bufferedImage = resize(bufferedImage,bufferedImage.getWidth()/5,bufferedImage.getHeight()/5);
		return bufferedImage;
	}
	public static BufferedImage resize(BufferedImage img, int newW, int newH) {
		Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
		BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_RGB);

		Graphics2D g2d = dimg.createGraphics();
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();
		return dimg;
	}

	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() {
		fullFaceFeatures = service.getFullFeatures();
	}



/*	private void matchTwoFeatureArrays(double [] first, double[] second) {
		float distance = euclidDistance(first, second);
		final float distanceThreshold = 0.6f;
		float percentage = Math.min(100, 100 * distanceThreshold / distance);
		final float percentageThreshold = 70.0f;

		Prediction prediction = new Prediction(percentage, percentage >= percentageThreshold, "username", 0,"username");


		System.out.println(prediction.toString());
	}


	private float euclidDistance(double[] first, double[] second) {
		double sum = 0;
		for (int i = 0; i < first.length; i++) {
			sum += Math.abs(first[i] - second[i]);
		}
		return (float) Math.sqrt(sum);
	}


    public Pair l2_search(HashMap<String,double[]> hashMap,double [] search){
		Pair<String,Double> ret=null;
		double distance=0;
		Set<String> keys = hashMap.keySet();
		Iterator iterator = keys.iterator();
		while(iterator.hasNext()){
			String key = (String)iterator.next();
			System.out.println(key);
			double knownEmb[] = hashMap.get(key);
			for (int i = 0; i < search.length; i++) {
				double diff = search[i] - knownEmb[i];
				distance += diff*diff;
			}
			distance = Math.sqrt(distance);
			if (ret == null || distance < ret.getValue()) {
				ret = new Pair(key, distance);
			}
		}
		return ret;
	}*/

}
