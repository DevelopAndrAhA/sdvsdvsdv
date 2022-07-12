package controllers;


/*
import org.springframework.context.event.ContextRefreshedEvent;
import neural_network.FaceRecognizer;
import javax.annotation.PostConstruct;*/
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

	//List<FullFaceFeatures> fullFaceFeatures = new ArrayList<FullFaceFeatures>();
	//FaceRecognizer faceRecognizer = new FaceRecognizer();


	/*@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() {
		fullFaceFeatures = service.getFullFeatures();
	}*/

	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)
	public String getData(){
		return "hello";
	}

	@ResponseBody
	@RequestMapping(value = "new_face",method = RequestMethod.POST)
	public Object add(
			/*@RequestParam("crop") MultipartFile crop,*/
			@RequestParam("largePohto") MultipartFile largePohto,
			@RequestParam("username")String username,
			@RequestParam("crop")String crop,
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


			float mas [] = new float[192];
			String [] cropSplit = crop.split(",");
			int k=0;
			for(int i=0;i<cropSplit.length;i++){
				mas[i] = Float.parseFloat(cropSplit[i]);
				k=i;
			}

			System.out.println(k);

			//FaceFeatures faceFeatures = faceRecognizer.addNew(crop);
			FaceFeatures faceFeatures = new FaceFeatures(mas,1);

			FullFaceFeatures features = new FullFaceFeatures(username);
			features.setFaceFeatures(1,faceFeatures);
			features.setIdentifier(new Date().getSeconds());
			features.setPhotoName(currTime+"");
			faceFeatures.setLng(Double.parseDouble(lng));
			faceFeatures.setLat(Double.parseDouble(lat));
			faceFeatures.setFullFaceFeatures(features);

			//fullFaceFeatures.add(features);
			service.save(features);
			ResultContainer resultContainer = new ResultContainer();
			resultContainer.setStatus(200);
			resultContainer.setDesc("success");
			return resultContainer;

		}catch (Exception e){e.printStackTrace();}



		return "error";


	}


	/*@ResponseBody
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
	}*/


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
		List<ResponseModelImg>list = service.getFullFeatures(Double.parseDouble(lat),Double.parseDouble(lng));
		return list;
	}

	@ResponseBody
	@RequestMapping(value = "getFirstData4imgs",method = RequestMethod.GET)
	public Object getFirstData4imgs(){
		List<ResponseModelImg>list = service.getFullFeatures4imgs();
		return list;
	}

	@ResponseBody
	@RequestMapping(value = "search",method = RequestMethod.GET)
	public Object perc(@RequestParam("crop")String crop,@RequestParam("inpDate")String inpDateP,@RequestParam("inpDate")String lat,@RequestParam("inpDate")String lng){

		List<Prediction> predictions = new ArrayList<Prediction>();
		List<ResponseModel> fullFaceFeatures = service.getFullFeatures(inpDateP);
		float mas [] = new float[192];
		String [] cropSplit = crop.split(",");
		if(cropSplit.length!=192){
			return "mas.length!=192";
		}
		for(int i=0;i<cropSplit.length;i++){
			mas[i] = Float.parseFloat(cropSplit[i]);
		}
		for(int i=0;i<fullFaceFeatures.size();i++){
			float saved_crop [] = fullFaceFeatures.get(i).getFeatures();
			Prediction prediction = searchSim(saved_crop,mas,fullFaceFeatures.get(i).getFaceLabel(),fullFaceFeatures.get(i).getPhotoName());
			if(prediction!=null){
				prediction.setInpDate(inpDateP);
				predictions.add(prediction);
			}
		}
		Collections.sort(predictions);
		return predictions;
	}


	@ResponseBody
	@RequestMapping(value = "ads_sts",method = RequestMethod.GET)
	public Object adssts(){
		return service.googleAdsFlag();
	}


	@ResponseBody
	@RequestMapping(value = "change_status",method = RequestMethod.GET)
	public Object getFirstData4imgs(@RequestParam("status")String status,@RequestParam("id")String id){
		return service.updateGoogleAdsFlag(Integer.parseInt(id),status);
	}

	@ResponseBody
	@RequestMapping(value = "create_ads_obj",method = RequestMethod.GET)
	public Object create_ads_obj(){
		GoogleAdsFlag googleAdsFlag = new GoogleAdsFlag();
		googleAdsFlag.setStatus("N");
		service.save(googleAdsFlag);
		return googleAdsFlag;
	}
	@ResponseBody
	@RequestMapping(value = "deleteAdsStatus",method = RequestMethod.GET)
	public Object deleteAdsStatus(){
		service.deleteAdsStatus();
		return "deleted";
	}
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



	public Prediction searchSim(float [] knownEmb,float [] search,String key,String photoName){
		Prediction prediction = null;
		float distance=0;
		for (int i = 0; i < search.length; i++) {
			float diff = search[i] - knownEmb[i];
			distance += diff*diff;
		}
		distance = (float) Math.sqrt(distance);
		if(distance<=1.1f){
			prediction = new Prediction(distance, key, 0,photoName);
			return prediction;
		}
		return null;
	}

	/*private Prediction matchTwoFeatureArrays(float [] first, float[] second,float percentage_p,String photoName) {

		float distance = euclidDistance(first, second);
		final float distanceThreshold = 0.6f;
		float percentage = Math.min(100, 100 * distanceThreshold / distance);
		final float percentageThreshold = 70.0f;

		Prediction prediction = new Prediction(percentage, percentage >= percentageThreshold, "0", 0,photoName);
		if(prediction.getPercentage()>=percentage_p){
			return prediction;
		}
		return null;
	}*/
	/*private Prediction matchTwoFeatureArrays(float [] first, float[] second,float percentage_p,String photoName) {

		float distance = euclidDistance(first, second);
		final float distanceThreshold = 0.6f;
		float percentage = Math.min(100, 100 * distanceThreshold / distance);
		final float percentageThreshold = 70.0f;

		Prediction prediction = new Prediction(percentage, percentage >= percentageThreshold, "0", 0,photoName);
		if(prediction.getPercentage()>=percentage_p){
			return prediction;
		}
		return null;
	}


	private float euclidDistance(float[] first, float[] second) {
		float sum = 0;
		for (int i = 0; i < first.length; i++) {
			sum += Math.abs(first[i] - second[i]);
		}
		return (float) Math.sqrt(sum);
	}
*/




}
