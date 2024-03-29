package controllers;


/*
import org.springframework.context.event.ContextRefreshedEvent;
import neural_network.FaceRecognizer;
import javax.annotation.PostConstruct;*/
import neural_network.models.*;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
import java.net.HttpURLConnection;
import java.net.URL;
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
	//FaceRecognizer faceRecognizer = new FaceRecognizer();


	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() {
		Date date = new Date();
		String [] dateMas =date.toString().split(" ");
		String dateStr =  dateMas[5];
		int month = date.getMonth()+1;
		if(month<10){
			dateStr = dateStr+"-0"+month;
		}else{
			dateStr = dateStr+"-"+month;
		}
		if(dateMas[2].length()<2){
			dateStr = dateStr+"-0"+dateMas[2];
		}else{
			dateStr = dateStr+"-"+dateMas[2];
		}
		fullFaceFeatures = service.getFullFaceFeatures();
		/*for(int i=0;i<fullFaceFeatures.size();i++){
			if(fullFaceFeatures.get(i).getCenter().getFeatures().length<192){
				service.delete(fullFaceFeatures.get(i).getCenter(),fullFaceFeatures.get(i));
			}
		}*/
	}

	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)
	public String getData(){
		return "hello";
	}

	@ResponseBody
	@RequestMapping(value = "new_face",method = RequestMethod.POST)
	public Object add(
			@RequestParam("largePohto") MultipartFile largePohto,
			@RequestParam("username")String username,
			@RequestParam("deviceId")String deviceId,
			@RequestParam("crop")String crop,
			@RequestParam("city_id")String city_id,
			@RequestParam("lng")String lng,
			@RequestParam("lat")String lat) {
		String [] simSlash = lat.split("/");
		lat = simSlash[0];
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
			for(int i=0;i<cropSplit.length;i++){
				mas[i] = Float.parseFloat(cropSplit[i]);
			}


			//FaceFeatures faceFeatures = faceRecognizer.addNew(crop);
			FaceFeatures faceFeatures = new FaceFeatures(mas,1);

			FullFaceFeatures features = new FullFaceFeatures(username);
			features.setFaceFeatures(1,faceFeatures);
			features.setDeviceId(deviceId);
			features.setCity_id(Integer.parseInt(city_id));
			features.setIdentifier(new Date().getSeconds());
			features.setPhotoName(currTime + "");
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



		return "error";


	}

	@ResponseBody
	@RequestMapping(value = "history",method = RequestMethod.GET)
	public Object get(@RequestParam("deviceId")String deviceId) {

		List<FullFaceFeatures> featuresTmp = new ArrayList<FullFaceFeatures>();

		for (int i = 0; i < fullFaceFeatures.size(); i++) {

			if (fullFaceFeatures.get(i).getDeviceId().equals(deviceId)) {
				FullFaceFeatures tmpFullFaceFeatures = new FullFaceFeatures();

				tmpFullFaceFeatures.setFullFaceFeatures_id(fullFaceFeatures.get(i).getFullFaceFeatures_id());
				tmpFullFaceFeatures.setPhotoName(fullFaceFeatures.get(i).getPhotoName());
				tmpFullFaceFeatures.setInp_date(fullFaceFeatures.get(i).getInp_date());
				tmpFullFaceFeatures.setFaceLabel(fullFaceFeatures.get(i).getFaceLabel());
				tmpFullFaceFeatures.setDeviceId(fullFaceFeatures.get(i).getDeviceId());
				tmpFullFaceFeatures.setIdentifier(fullFaceFeatures.get(i).getIdentifier());
				tmpFullFaceFeatures.setCity_id(fullFaceFeatures.get(i).getCity_id());
				try{
					tmpFullFaceFeatures.setLat(fullFaceFeatures.get(i).getFaceFeatures(1).getLat());
				}catch (Exception e){}
				try{
					tmpFullFaceFeatures.setLng(fullFaceFeatures.get(i).getFaceFeatures(1).getLng());
				}catch (Exception e){}

				featuresTmp.add(tmpFullFaceFeatures);
			}
		}

		if (featuresTmp.size() != 0) {
			return featuresTmp;
		} else {
			return "{'features':'size==null'}";
		}
	}

	@ResponseBody
	@RequestMapping(value = "getFullFaceFeatures",method = RequestMethod.GET)
	public Object getFullFaceFeatures(@RequestParam("fullFaceFeatures_id")String fullFaceFeatures_id) {
		FullFaceFeatures fullFaceFeatures1 = service.getFullFaceFeatures(Long.parseLong(fullFaceFeatures_id));
		if(fullFaceFeatures1!=null){
			fullFaceFeatures1.setFaceFeatures(1,null);
			return fullFaceFeatures1;
		}
		return null;
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
	public Object getFirstData(@RequestParam("city_id")String city_id){
		List<ResponseModelImg>list = service.getFullFeatures(Integer.parseInt(city_id));
		return list;
	}

	@ResponseBody
	@RequestMapping(value = "delete",method = RequestMethod.GET)
	public Object delete(@RequestParam("deviceId")String deviceId,@RequestParam("fullFaceFeatures_id")String fullFaceFeatures_id){
		int tmpFullFaceFeatures_id = Integer.parseInt(fullFaceFeatures_id);
		service.deleteFace(deviceId, tmpFullFaceFeatures_id);
		Iterator<FullFaceFeatures> iterator = fullFaceFeatures.iterator();
		while (iterator.hasNext()) {
			FullFaceFeatures fullFaceFeature = iterator.next();

			if (fullFaceFeature.getDeviceId().equals(deviceId) && fullFaceFeature.getFullFaceFeatures_id()==tmpFullFaceFeatures_id) {
				iterator.remove();
				try{
					File file = new File(FileUtils.getUserDirectory()+File.separator+"images"+File.separator+fullFaceFeature.getPhotoName()+".jpg");
					file.delete();
					file = new File(FileUtils.getUserDirectory()+File.separator+"images"+File.separator+fullFaceFeature.getPhotoName()+"_SMALL.jpg");
					file.delete();
				}catch (Exception e){}
			}
		}
		return "{'status':200}";
	}

	@ResponseBody
	@RequestMapping(value = "getFirstData4imgs",method = RequestMethod.GET)
	public Object getFirstData4imgs(){
		List<ResponseModelImg>list = service.getFullFeatures4imgs();
		return list;
	}

	@ResponseBody
	@RequestMapping(value = "search",method = RequestMethod.GET)
	public Object search(@RequestParam("crop")String crop,@RequestParam("fromDate")String fromDate,@RequestParam("toDate")String toDate,@RequestParam("city_id")String city_idP){
		String simSlash [] = city_idP.split("/");
		int city_id = Integer.parseInt(simSlash[0]);
		List<Prediction> predictions = new ArrayList<Prediction>();
		//List<ResponseModel> fullFaceFeatures = service.getFullFeatures(inpDateP);
		float mas [] = new float[192];
		String [] cropSplit = crop.split(",");
		if(cropSplit.length!=192){
			return "[{'key':'mas.length!=192["+cropSplit.length+"]'}]";
		}
		for(int i=0;i<cropSplit.length;i++){
			mas[i] = Float.parseFloat(cropSplit[i]);
		}
		for (int i = fullFaceFeatures.size() - 1; i >= 0; i--) {
			if (city_id != fullFaceFeatures.get(i).getCity_id()) {
				continue;
			}
			try {
				float saved_crop[] = fullFaceFeatures.get(i).getFaceFeatures(1).getFeatures();
				Prediction prediction = calculateDistance(saved_crop, mas, fullFaceFeatures.get(i).getFaceLabel(), fullFaceFeatures.get(i).getPhotoName());
				if (prediction != null) {
					prediction.setInpDate(fromDate);
					prediction.setLat(fullFaceFeatures.get(i).getFaceFeatures(1).getLat());
					prediction.setLng(fullFaceFeatures.get(i).getFaceFeatures(1).getLng());
					predictions.add(prediction);
				}
			} catch (Exception e) {}
		}
		//Collections.sort(predictions);
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
		String simSlash [] = id.split("/");
		int vId = Integer.parseInt(simSlash[0]);
		return service.updateGoogleAdsFlag(vId,status);
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
	@ResponseBody
	@RequestMapping(value = "getToken",method = RequestMethod.GET)
	public Object getTokenController(@RequestParam("mod")String mod){
		//String MODEL_ID = getModel();
		//if(MODEL_ID!=null){
			getToken(mod);
		//}
		return "deleted";
	}

	public BufferedImage resize(MultipartFile photo) {


		BufferedImage bufferedImage = null;
		try {
			byte [] bytes = photo.getBytes();
			InputStream is = new ByteArrayInputStream(bytes);
			bufferedImage = ImageIO.read(is);
		}catch (Exception e){}

		bufferedImage = resize(bufferedImage,bufferedImage.getWidth()/2,bufferedImage.getHeight()/2);
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


	public Prediction searchSimEuklid(float [] knownEmb,float [] search,String key,String photoName){
		Prediction prediction = null;
		float distance=0;
		for (int i = 0; i < search.length; i++) {
			float diff = search[i] - knownEmb[i];
			distance += diff*diff;
		}
		distance = (float) Math.sqrt(distance);
		if(distance<=1.15f){
			prediction = new Prediction(distance, key, 0,photoName);
			return prediction;
		}
		return null;
	}

	private Prediction calculateDistance(float[] emb1, float[] emb2, String key, String photoName) {
		Prediction prediction = null;
		float squaredDiffSum = 0;
		for (int i = 0; i < emb1.length; i++) {
			float diff = emb1[i] - emb2[i];
			squaredDiffSum += diff * diff;
		}
		float distance = (float) Math.sqrt(squaredDiffSum);
		if (distance <= 1.2f) {
			prediction = new Prediction(distance, key, 0, photoName);
			return prediction;
		}
		return null;
	}

	public Prediction searchSimCosinus(float[] knownEmb, float[] search, String key, String photoName) {
		Prediction prediction = null;
		double cosDistance = 0;
		double norm1 = 0;
		double norm2 = 0;

		for (int i = 0; i < search.length; i++) {
			cosDistance += knownEmb[i] * search[i];
			norm1 += knownEmb[i] * knownEmb[i];
			norm2 += search[i] * search[i];
		}

		// Избегаем деления на 0 при нормализации векторов
		if (norm1 == 0 || norm2 == 0) {
			return null;
		}

		// Нормализуем векторы
		norm1 = Math.sqrt(norm1);
		norm2 = Math.sqrt(norm2);

		// Вычисляем косинус угла между векторами
		cosDistance = cosDistance / (norm1 * norm2);

		// Если косинус угла больше или равен 0.9, то считаем, что найдено совпадение
		if (cosDistance >= 0.9) {
			prediction = new Prediction((float) cosDistance, key, 0, photoName);
		}

		return prediction;
	}


	String token = "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";
	public String  getModel(){
		String MODEL_ID=null;
		try{
			OkHttpClient client = new OkHttpClient();
			Request request = new Request.Builder()
					.url("https://api.openai.com/v1/models")
					.header("Authorization", "Bearer " + token)
					.build();
			try (Response response = client.newCall(request).execute()) {
				JSONObject root = new JSONObject(response.body().string());
				JSONArray gpt3Id = root.getJSONArray("data");
				for (int i = 0; i < gpt3Id.length(); i++) {
					JSONObject model = gpt3Id.getJSONObject(i);
					if (model.getString("id").equals("davinci")) {
						MODEL_ID = model.getString("id");
						break;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}catch (Exception e){e.printStackTrace();}
		if(MODEL_ID!=null){
			return MODEL_ID;
		}
		return null;
	}

	public void getToken(String MODEL_ID){
		try {
			OkHttpClient client = new OkHttpClient();

			okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/json");
			okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, "{{\n" +
					"  \"model\": \"davinci\",\n" +
					"  \"messages\": [{\"role\": \"user\", \"content\": \"Hello!\"}]\n" +
					"}\n");

			Request request = new Request.Builder()
					.url("https://api.openai.com/v1/chat/completions")
					.post(body)
					.addHeader("Content-Type", "application/json")
					.addHeader("Authorization", "Bearer "+token)
					.build();

			Response response = client.newCall(request).execute();
			if (!response.isSuccessful()) {
				throw new RuntimeException("Failed to get response: " + response.code() + " " + response.message());
			}

			String responseBody = response.body().string();
			System.out.println(responseBody);
		} catch (IOException e) {
			e.printStackTrace();
		}


	}

}
